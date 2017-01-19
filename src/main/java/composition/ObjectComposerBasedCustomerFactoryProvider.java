package composition;

import core.ItemFactoryProvider;
import core.customer.CustomerFactory;
import core.customer.model.Customer;

public class ObjectComposerBasedCustomerFactoryProvider implements ItemFactoryProvider<Customer, CustomerFactory> {
    @Override
    public CustomerFactory createItemFactory() {
        return ServiceLocator.getComposer().getCustomerFactory();
    }

    @Override
    public CustomerFactory createItemFactory(Customer item) {
        return ServiceLocator.getComposer().getCustomerFactory(item);
    }
}

