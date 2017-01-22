package app.customers.details;

import app.common.details.DetailsPanel;
import core.customer.CustomerBuilder;
import core.customer.model.Customer;
import javafx.util.Pair;

public final class CustomerDetailsPanel extends DetailsPanel<Customer, CustomerBuilder> {
    public CustomerDetailsPanel() {
        super(
                new Pair<>(CustomerEditorPanel.class.getName(), new CustomerEditorPanel()),
                new Pair<>(PreviewSelectedCustomerPanel.class.getName(), new PreviewSelectedCustomerPanel())
        );
      }
}
