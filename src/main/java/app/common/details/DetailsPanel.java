package app.common.details;

import app.common.BasicEventArgs;
import app.common.listing.ListEventListener;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import common.EventProducersAggregate;
import common.IRaiseEvents;
import core.IHaveIdentifier;
import core.ItemFactory;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public abstract class DetailsPanel<TModel extends IHaveIdentifier,
        TItemFactory extends ItemFactory<TModel>> extends JPanel
        implements ListEventListener, ItemDetailsListener, IRaiseEvents<ItemDetailsListener> {

    private final CardLayout contentPresenter;
    private final Pair<String, EditorPanel> editorView;
    private final Pair<String, NoItemSelectedPanel> notSelectedView;
    private final Pair<String, PreviewSelectedItemPanel<TModel>> previewSelectedItemView;
    private final EventProducersAggregate<ItemDetailsListener> eventProducers;

    protected DetailsPanel(Pair<String, EditorPanel> editorView,
                           Pair<String, PreviewSelectedItemPanel<TModel>> previewSelectedItemView) {
        setMinimumSize(ComponentSizes.MINIMUM_DETAILS_PANEL_SIZE);
        setBorder(BorderStyles.getTitleBorder("Details:"));

        this.editorView = editorView;
       this.previewSelectedItemView = previewSelectedItemView;
       this.notSelectedView = new Pair<>(NoItemSelectedPanel.class.getName(), new NoItemSelectedPanel("No item is selected for preview ..."));

        contentPresenter = new CardLayout();
        setLayout(contentPresenter);

        add(editorView.getValue(), editorView.getKey());
        add(notSelectedView.getValue(), notSelectedView.getKey());
        add(previewSelectedItemView.getValue(), previewSelectedItemView.getKey());

        navigateToNotSelected();

        editorView.getValue().addListener(this);
        previewSelectedItemView.getValue().addListener(this);

        eventProducers = new EventProducersAggregate<>(Arrays.asList(
                this.editorView.getValue(),
                this.previewSelectedItemView.getValue()
        ));
    }

    public final void navigateToNotSelected() {
        contentPresenter.show(this, this.notSelectedView.getKey());
    }

    public final void previewSelectedItem(int id) {
        this.previewSelectedItemView.getValue().previewItem(id);
        contentPresenter.show(this, this.previewSelectedItemView.getKey());
    }

    @Override
    public final void itemDeleted(BasicEventArgs e) {
        this.navigateToNotSelected();
    }

    @Override
    public final void itemSelected(BasicEventArgs e) {
        this.previewSelectedItem(e.getId());
    }

    @Override
    public final void itemCreationRequested() {
        this.editorView.getValue().createItem();
        contentPresenter.show(this, this.editorView.getKey());
    }

    @Override
    public final void itemEditRequested(BasicEventArgs e) {
        this.editorView.getValue().editItem(e.getId());
        contentPresenter.show(this, this.editorView.getKey());
    }

    @Override
    public final void itemSaved(BasicEventArgs e) {
        this.previewSelectedItem(e.getId());
    }

    @Override
    public final void itemEditCancelled(BasicEventArgs e) {
        if (e.getId() == 0) {
            this.navigateToNotSelected();
        } else {
            this.previewSelectedItem(e.getId());
        }
    }

    @Override
    public final void addListener(ItemDetailsListener listenerToAdd) {
        this.eventProducers.addListener(listenerToAdd);
    }

    @Override
    public final void removeListener(ItemDetailsListener listenerToRemove) {
        this.eventProducers.removeListener(listenerToRemove);
    }
}
