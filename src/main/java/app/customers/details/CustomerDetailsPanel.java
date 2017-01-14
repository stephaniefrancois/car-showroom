package app.customers.details;

import app.customers.CustomerEventArgs;
import app.customers.listing.CustomerListener;
import app.styles.BorderStyles;
import app.styles.ComponentSizes;
import common.EventProducersAggregate;
import common.IRaiseEvents;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public final class CustomerDetailsPanel extends JPanel
        implements CustomerListener, CustomerDetailsListener, IRaiseEvents<CustomerDetailsListener> {

    private final CardLayout contentPresenter;
    private final Pair<String, CustomerEditorPanel> carEditorView = new Pair<>(CustomerEditorPanel.class.getName(), new CustomerEditorPanel());
    private final Pair<String, NoCustomerSelectedPanel> noCustomerSelectedView = new Pair<>(NoCustomerSelectedPanel.class.getName(), new NoCustomerSelectedPanel());
    private final Pair<String, PreviewSelectedCustomerPanel> previewCustomerView = new Pair<>(PreviewSelectedCustomerPanel.class.getName(), new PreviewSelectedCustomerPanel());
    private final EventProducersAggregate<CustomerDetailsListener> eventProducers;
    
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

    public void previewSelectedCar(int carId) {
        this.previewCustomerView.getValue().previewCustomer(carId);
        contentPresenter.show(this, this.previewCustomerView.getKey());
    }

    @Override
    public void addListener(CustomerDetailsListener listenerToAdd) {
        this.eventProducers.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CustomerDetailsListener listenerToRemove) {
        this.eventProducers.removeListener(listenerToRemove);
    }

    @Override
    public void customerDeleted(CustomerEventArgs e) {
        this.navigateToNoCustomerSelected();
    }

    @Override
    public void customerSelected(CustomerEventArgs e) {
        this.previewSelectedCar(e.getCustomerId());
    }

    @Override
    public void customerCreationRequested() {
        this.carEditorView.getValue().createCustomer();
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void customerEditRequested(CustomerEventArgs e) {
        this.carEditorView.getValue().editCustomer(e.getCustomerId());
        contentPresenter.show(this, this.carEditorView.getKey());
    }

    @Override
    public void customerSaved(CustomerEventArgs e) {
        this.previewSelectedCar(e.getCustomerId());
    }

    @Override
    public void customerEditCancelled(CustomerEventArgs e) {
        if (e.getCustomerId() == 0) {
            this.navigateToNoCustomerSelected();
        } else {
            this.previewSelectedCar(e.getCustomerId());
        }
    }
}
