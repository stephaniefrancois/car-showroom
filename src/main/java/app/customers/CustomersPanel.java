package app.customers;

import app.ContentPanel;
import app.common.listing.ListOptions;
import app.common.listing.SearchableListPanel;
import app.common.search.SearchPanel;
import app.customers.details.CustomerDetailsPanel;
import app.customers.listing.CustomersListPanel;
import core.customer.model.Customer;

import java.awt.*;

public final class CustomersPanel extends ContentPanel {
    private final SearchableListPanel<Customer> searchableCustomers;
    private final CustomerDetailsPanel customerDetails;

    public CustomersPanel() {
        setLayout(new BorderLayout());

        this.searchableCustomers = new SearchableListPanel(
                new SearchPanel(),
                new CustomersListPanel(
                        ListOptions.AllowEditingItems("Our customers:",
                                "We have no customers :("))
        );
        this.customerDetails = new CustomerDetailsPanel();

        add(this.searchableCustomers, BorderLayout.CENTER);
        add(this.customerDetails, BorderLayout.EAST);

        this.searchableCustomers.addListener(this.customerDetails);
        this.customerDetails.addListener(this.searchableCustomers);
    }

    @Override
    public void activated() {
        this.searchableCustomers.refresh();
    }
}
