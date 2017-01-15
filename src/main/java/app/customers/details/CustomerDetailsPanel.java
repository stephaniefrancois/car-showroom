package app.customers.details;

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

public final class CustomerDetailsPanel extends JPanel
        implements ListEventListener, ItemDetailsListener, IRaiseEvents<ItemDetailsListener> {

    private final CardLayout contentPresenter;
    private final Pair<String, CustomerEditorPanel> carEditorView = new Pair<>(CustomerEditorPanel.class.getName(), new CustomerEditorPanel());
    private final Pair<String, NoItemSelectedPanel> noCustomerSelectedView = new Pair<>(NoItemSelectedPanel.class.getName(), new NoItemSelectedPanel("No customer selected for preview ..."));
    private final Pair<String, PreviewSelectedCustomerPanel> previewCustomerView = new Pair<>(PreviewSelectedCustomerPanel.class.getName(), new PreviewSelectedCustomerPanel());
    private final EventProducersAggregate<ItemDetailsListener> eventProducers;
    
    public CustomerDetailsPanel() {
        setMinimumSize(ComponentSizes.MINIMUM_DETAILS_PANEL_SIZE);
        setBorder(BorderStyles.getTitleBorder("Customer details:"));
        contentPresenter = new CardLayout();
        setLayout(contentPresenter);

        add(carEditorView.getValue(), carEditorView.getKey());
        add(noCustomerSelectedView.getValue(), noCustomerSelectedView.getKey());
        add(previewCustomerView.getValue(), previewCustomerView.getKey());

        navigateToNoCustomerSelected();

        carEditorView.getValue().addListener(this);
        previewCustomerView.getValue().addListener(this);

        eventProducers = new EventProducersAggregate<>(Arrays.asList(
                this.carEditorView.getValue(),
                this.previewCustomerView.getValue()
        ));
    }

    public void navigateToNoCustomerSelected() {
        contentPresenter.show(this, this.noCustomerSelectedView.getKey());
    }

    public void previewSelectedCustomer(int carId) {
        this.previewCustomerView.getValue().previewCustomer(carId);
        contentPresenter.show(this, this.previewCustomerView.getKey());
    }

    @Override
    public void addListener(ItemDetailsListener listenerToAdd) {
        this.eventProducers.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(ItemDetailsListener listenerToRemove) {
        this.eventProducers.removeListener(listenerToRemove);
    }

    @Override
    public void itemDeleted(BasicEventArgs e) {
        this.navigateToNoCustomerSelected();
    }

    @Override
    public void itemSelected(BasicEventArgs e) {
        this.previewSelectedCustomer(e.getId());
    }

    @Override
    public void itemCreationRequested() {
        this.carEditorView.getValue().createCustomer();
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void itemEditRequested(BasicEventArgs e) {
        this.carEditorView.getValue().editCustomer(e.getId());
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void itemSaved(BasicEventArgs e) {
        this.previewSelectedCustomer(e.getId());
    }

    @Override
    public void itemEditCancelled(BasicEventArgs e) {
        if (e.getId() == 0) {
            this.navigateToNoCustomerSelected();
        } else {
            this.previewSelectedCustomer(e.getId());
        }
    }
}
