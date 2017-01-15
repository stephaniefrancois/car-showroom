package app.cars.details;

import app.common.BasicEventArgs;
import app.common.details.ItemDetailsListener;
import app.common.details.NoItemSelectedPanel;
import app.common.listing.ListEventListener;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import common.EventProducersAggregate;
import common.IRaiseEvents;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public final class CarDetailsPanel extends JPanel
        implements ListEventListener, ItemDetailsListener, IRaiseEvents<ItemDetailsListener> {

    private final CardLayout contentPresenter;
    private final Pair<String, CarEditorPanel> carEditorView = new Pair<>(CarEditorPanel.class.getName(), new CarEditorPanel());
    private final Pair<String, NoItemSelectedPanel> noCarSelectedView = new Pair<>(NoItemSelectedPanel.class.getName(), new NoItemSelectedPanel("No car selected for preview ..."));
    private final Pair<String, PreviewSelectedCarPanel> previewCarView = new Pair<>(PreviewSelectedCarPanel.class.getName(), new PreviewSelectedCarPanel());
    private final EventProducersAggregate<ItemDetailsListener> eventProducers;

    public CarDetailsPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_DETAILS_PANEL_SIZE);
        setBorder(BorderStyles.getTitleBorder("Car details:"));
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);

        add(carEditorView.getValue(), carEditorView.getKey());
        add(noCarSelectedView.getValue(), noCarSelectedView.getKey());
        add(previewCarView.getValue(), previewCarView.getKey());

        navigateToNoCarSelected();

        carEditorView.getValue().addListener(this);
        previewCarView.getValue().addListener(this);

        eventProducers = new EventProducersAggregate<>(Arrays.asList(
                this.carEditorView.getValue(),
                this.previewCarView.getValue()
        ));
    }

    public void navigateToNoCarSelected() {
        contentPresenter.show(this, this.noCarSelectedView.getKey());
    }

    public void previewSelectedCar(int carId) {
        this.previewCarView.getValue().previewCar(carId);
        contentPresenter.show(this, this.previewCarView.getKey());
    }

    @Override
    public void itemDeleted(BasicEventArgs e) {
        this.navigateToNoCarSelected();
    }

    @Override
    public void itemSelected(BasicEventArgs e) {
        this.previewSelectedCar(e.getId());
    }

    @Override
    public void itemCreationRequested() {
        this.carEditorView.getValue().createCar();
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void itemEditRequested(BasicEventArgs e) {
        this.carEditorView.getValue().editCar(e.getId());
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void itemSaved(BasicEventArgs e) {
        this.previewSelectedCar(e.getId());
    }

    @Override
    public void itemEditCancelled(BasicEventArgs e) {
        if (e.getId() == 0) {
            this.navigateToNoCarSelected();
        } else {
            this.previewSelectedCar(e.getId());
        }
    }

    @Override
    public void addListener(ItemDetailsListener listenerToAdd) {
        this.eventProducers.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(ItemDetailsListener listenerToRemove) {
        this.eventProducers.removeListener(listenerToRemove);
    }
}
