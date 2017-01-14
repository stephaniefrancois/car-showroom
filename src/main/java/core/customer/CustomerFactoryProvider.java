package core.customer;

import core.domain.deal.CustomerProperties;

public interface CustomerFactoryProvider {
    CustomerFactory createCustomerFactory();

    CustomerFactory createCustomerFactory(CustomerProperties customer);
}
