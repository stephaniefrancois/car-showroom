package app.customers;

import app.common.listing.SearchableListPanel;
import app.common.search.SearchPanel;
import app.customers.details.CustomerDetailsPanel;
import app.customers.listing.CustomersListPanel;
import core.domain.deal.CustomerProperties;

import javax.swing.*;
import java.awt.*;

public final class CustomersPanel extends JPanel {
    private final SearchableListPanel<CustomerProperties> searchableCustomers;
    private final CustomerDetailsPanel customerDetails;

    public CustomersPanel() {
        setLayout(new BorderLayout());

        this.searchableCustomers = new SearchableListPanel(
                new SearchPanel(),
                new CustomersListPanel()
        );
        this.customerDetails = new CustomerDetailsPanel();

        add(this.searchableCustomers, BorderLayout.CENTER);
        add(this.customerDetails, BorderLayout.EAST);

        this.searchableCustomers.addListener(this.customerDetails);
        this.customerDetails.addListener(this.searchableCustomers);
    }
}
