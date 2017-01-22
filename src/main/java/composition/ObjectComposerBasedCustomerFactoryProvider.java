package composition;

import core.ItemFactoryProvider;
import core.customer.CustomerBuilder;
import core.customer.model.Customer;

public class ObjectComposerBasedCustomerFactoryProvider implements ItemFactoryProvider<Customer, CustomerBuilder> {
    @Override
    public CustomerBuilder createItemFactory() {
        return ServiceLocator.getComposer().getCustomerFactory();
    }

    @Override
    public CustomerBuilder createItemFactory(Customer item) {
        return ServiceLocator.getComposer().getCustomerFactory(item);
    }
}

