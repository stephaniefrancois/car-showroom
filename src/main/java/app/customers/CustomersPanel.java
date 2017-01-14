package app.customers;

import app.customers.details.CustomerDetailsPanel;
import app.customers.listing.SearchableCustomerListPanel;

import javax.swing.*;
import java.awt.*;

public final class CustomersPanel extends JPanel {
    private final SearchableCustomerListPanel searchableCustomers;
    private final CustomerDetailsPanel customerDetails;

    public CustomersPanel() {
        setLayout(new BorderLayout());

        this.searchableCustomers = new SearchableCustomerListPanel();
        this.customerDetails = new CustomerDetailsPanel();

        add(this.searchableCustomers, BorderLayout.CENTER);
        add(this.customerDetails, BorderLayout.EAST);

        this.searchableCustomers.addListener(this.customerDetails);
        this.customerDetails.addListener(this.searchableCustomers);
    }
}
