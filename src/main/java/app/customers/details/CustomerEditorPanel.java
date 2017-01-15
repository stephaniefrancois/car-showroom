package app.customers.details;

import app.common.details.EditorPanel;
import app.common.validation.ValidationSummaryPanel;
import app.objectComposition.ServiceLocator;
import core.customer.CustomerFactory;
import core.domain.car.UnableToUpdateCarException;
import core.domain.deal.CustomerProperties;
import data.CustomerRepository;

public final class CustomerEditorPanel extends EditorPanel<CustomerProperties, CustomerFactory> {
    private final CustomerRepository customerRepository;

    public CustomerEditorPanel() {
        super(ServiceLocator.getComposer().getCustomerFactoryProvider(),
                new CustomerEditorInputsPanel(),
                new ValidationSummaryPanel()
        );
        this.customerRepository = ServiceLocator.getComposer().getCustomerRepository();
    }

    @Override
    protected CustomerProperties saveItem(CustomerProperties itemToSave) throws UnableToUpdateCarException {
        return customerRepository.saveCustomer(itemToSave);
    }

    @Override
    protected CustomerProperties getItem(int id) {
        return this.customerRepository.getCustomer(id);
    }
}
