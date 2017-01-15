package data;

import core.domain.deal.Customer;
import core.domain.deal.CustomerProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class InMemoryCustomerRepository implements CustomerRepository {
    private ArrayList<CustomerProperties> customers = new ArrayList<>(Arrays.asList(
            new Customer(1,
                    "Stephanie",
                    "Francois",
                    "Trou aux Biches",
                    LocalDate.of(2010, 1, 1)),
            new Customer(2,
                    "John",
                    "Dow",
                    "New York",
                    LocalDate.of(2015, 1, 1)),
            new Customer(3,
                    "Jane",
                    "Dow",
                    "Chicago",
                    LocalDate.of(2000, 1, 1))
    ));

    @Override
    public List<CustomerProperties> getCustomers() {
        return customers.stream().map(c -> c).collect(Collectors.toList());
    }

    @Override
    public CustomerProperties getCustomer(int customerId) {
        List<CustomerProperties> filteredCustomers =
                customers.stream()
                        .filter(c -> c.getId() == customerId)
                        .collect(Collectors.toList());

        if (filteredCustomers.isEmpty()) {
            return null;
        }

        return filteredCustomers.get(0);
    }

    @Override
    public CustomerProperties saveCustomer(CustomerProperties customer) {
        if (customer.getId() == 0) {
            customer = addNewCustomer(customer);
        } else {
            customer = updateExistingCustomer(customer);
        }

        return customer;
    }

    private CustomerProperties addNewCustomer(CustomerProperties customer) {
        Optional<Integer> highestIndex =
                this.customers
                        .stream()
                        .map(CustomerProperties::getId).max(Integer::compare);
        int newIndex = 1;

        if (highestIndex.isPresent()) {
            newIndex = highestIndex.get() + 1;
        }

        Customer newCustomer = new Customer(
                newIndex,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getCity(),
                customer.getCustomerSince());

        this.customers.add(newCustomer);
        return newCustomer;
    }

    private CustomerProperties updateExistingCustomer(CustomerProperties customer) {
        List<CustomerProperties> filteredCustomers =
                customers.stream()
                        .filter(c -> c.getId() == customer.getId())
                        .collect(Collectors.toList());
        int indexOfUpdatedCustomer = this.customers.indexOf(filteredCustomers.get(0));
        this.customers.set(indexOfUpdatedCustomer, customer);
        return customer;
    }

    @Override
    public void removeCustomer(int customerId) {
        List<CustomerProperties> customerToDelete = this.customers.stream()
                .filter(c -> c.getId() == customerId)
                .collect(Collectors.toList());

        for (CustomerProperties customer : customerToDelete) {
            this.customers.remove(customer);
        }
    }

    @Override
    public List<CustomerProperties> findCustomers(String searchCriteria) {
        return this.customers.stream()
                .filter(c -> c.getFirstName().toLowerCase().contains(searchCriteria.toLowerCase()) ||
                        c.getLastName().toLowerCase().contains(searchCriteria.toLowerCase())
                || c.getCity().toLowerCase().contains(searchCriteria.toLowerCase()))
                .collect(Collectors.toList());
    }
}
