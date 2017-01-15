package app.customers.listing;

import app.common.listing.ItemsListPanel;
import app.objectComposition.ServiceLocator;
import core.customer.model.Customer;
import data.CustomerRepository;

import java.util.List;

public final class CustomersListPanel extends ItemsListPanel<Customer> {
    private final CustomerRepository customerRepository;

    public CustomersListPanel() {
        super(new CustomerTableModel(),
                "Our customers:",
                "We have no customers :(");

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.customerRepository = ServiceLocator
                .getComposer()
                .getCustomerRepository();
        this.refresh();
    }

    @Override
    protected List<Customer> getAllItems() {
        return customerRepository.getCustomers();
    }

    @Override
    protected String getMessageForItemDeleteDialog(Customer item) {
        return String.format("Do you really want to delete '%s %s' ?", item.getFirstName(), item.getLastName());
    }

    @Override
    protected void removeItem(Customer itemToDelete) {
        this.customerRepository.removeCustomer(itemToDelete.getId());
    }

    @Override
    protected List<Customer> findItems(String searchCriteria) {
        return this.customerRepository.findCustomers(searchCriteria);
    }
}
