package data;

import core.customer.model.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getCustomers();

    Customer getCustomer(int customerId);

    Customer saveCustomer(Customer customer);

    void removeCustomer(int customerId);

    List<Customer> findCustomers(String searchCriteria);
}
