package data;

import core.domain.deal.CustomerProperties;

import java.util.List;

public interface CustomerRepository {
    List<CustomerProperties> getCustomers();

    CustomerProperties getCustomer(int customerId);

    CustomerProperties saveCustomer(CustomerProperties customer);

    void removeCustomer(int customerId);

    List<CustomerProperties> findCustomers(String searchCriteria);
}
