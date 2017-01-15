package app.objectComposition;


import core.ItemFactoryProvider;
import core.customer.CustomerFactory;
import core.domain.deal.CustomerProperties;

public class ObjectComposerBasedCustomerFactoryProvider implements ItemFactoryProvider<CustomerProperties, CustomerFactory> {
    @Override
    public CustomerFactory createItemFactory() {
        return ServiceLocator.getComposer().getCustomerFactory();
    }

    @Override
    public CustomerFactory createItemFactory(CustomerProperties item) {
        return ServiceLocator.getComposer().getCustomerFactory(item);
    }
}

