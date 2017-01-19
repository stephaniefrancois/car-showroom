package app.customers.details;

import app.common.details.SimpleEditorPanel;
import app.common.validation.ValidationSummaryPanel;
import composition.ServiceLocator;
import core.customer.CustomerFactory;
import core.customer.model.Customer;
import core.stock.model.UnableToUpdateCarException;
import data.CustomerRepository;

public final class CustomerEditorPanel extends SimpleEditorPanel<Customer, CustomerFactory> {
    private final CustomerRepository customerRepository;

    public CustomerEditorPanel() {
        super(ServiceLocator.getComposer().getCustomerFactoryProvider(),
                new CustomerEditorInputsPanel(),
                new ValidationSummaryPanel()
        );
        this.customerRepository = ServiceLocator.getComposer().getCustomerRepository();
    }

    @Override
    protected Customer saveItem(Customer itemToSave) throws UnableToUpdateCarException {
        return customerRepository.saveCustomer(itemToSave);
    }

    @Override
    protected Customer getItem(int id) {
        return this.customerRepository.getCustomer(id);
    }
}
