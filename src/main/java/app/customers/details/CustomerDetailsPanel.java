package app.customers.details;

import app.common.details.DetailsPanel;
import core.customer.CustomerFactory;
import core.domain.deal.CustomerProperties;
import javafx.util.Pair;

public final class CustomerDetailsPanel extends DetailsPanel<CustomerProperties, CustomerFactory> {
    public CustomerDetailsPanel() {
        super(
                new Pair<>(CustomerEditorPanel.class.getName(), new CustomerEditorPanel()),
                new Pair<>(PreviewSelectedCustomerPanel.class.getName(), new PreviewSelectedCustomerPanel())
        );
      }
}
