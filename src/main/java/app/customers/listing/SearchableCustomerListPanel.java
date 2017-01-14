package app.customers.listing;

import app.customers.CustomerEventArgs;
import app.customers.details.CustomerDetailsListener;
import app.customers.search.SearchPanel;
import common.IRaiseEvents;

import javax.swing.*;
import java.awt.*;

public final class SearchableCustomerListPanel extends JPanel implements
        CustomerDetailsListener,
        IRaiseEvents<CustomerListener> {
    private final SearchPanel searchPanel;
    private final CustomersListPanel customersList;

    public SearchableCustomerListPanel() {
        setLayout(new BorderLayout());

        this.searchPanel = new SearchPanel();
        this.customersList = new CustomersListPanel();

        add(this.searchPanel, BorderLayout.NORTH);
        add(this.customersList, BorderLayout.CENTER);

        this.searchPanel.addListener(this.customersList);
    }

    @Override
    public void addListener(CustomerListener listenerToAdd) {
        customersList.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CustomerListener listenerToRemove) {
        customersList.removeListener(listenerToRemove);
    }

    @Override
    public void customerEditRequested(CustomerEventArgs e) {

    }

    @Override
    public void customerSaved(CustomerEventArgs e) {
        this.customersList.refresh();
    }

    @Override
    public void customerEditCancelled(CustomerEventArgs e) {

    }
}
