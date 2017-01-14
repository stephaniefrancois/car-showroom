package app.objectComposition;


import core.customer.CustomerFactory;
import core.customer.CustomerFactoryProvider;
import core.domain.deal.CustomerProperties;

public class ObjectComposerBasedCustomerFactoryProvider implements CustomerFactoryProvider {

    @Override
    public CustomerFactory createCustomerFactory() {
        return ServiceLocator.getComposer().getCustomerFactory();
    }

    @Override
    public CustomerFactory createCustomerFactory(CustomerProperties customer) {
        return ServiceLocator.getComposer().getCustomerFactory(customer);
    }
}
