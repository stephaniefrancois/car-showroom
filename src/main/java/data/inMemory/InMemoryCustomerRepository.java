package data.inMemory;

import core.customer.model.Customer;
import data.CustomerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class InMemoryCustomerRepository implements CustomerRepository {
    private ArrayList<Customer> customers = new ArrayList<>(Arrays.asList(
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
                    LocalDate.of(2000, 1, 1)),
            new Customer(5,
                    "Joan",
                    "Bow",
                    "Delaware",
                    LocalDate.of(2000, 1, 1)),
            new Customer(4,
                    "John",
                    "Smith",
                    "Trou aux biches",
                    LocalDate.of(2000, 1, 1))
    ));

    @Override
    public List<Customer> getCustomers() {
        return customers.stream().map(c -> c).collect(Collectors.toList());
    }

    @Override
    public Customer getCustomer(int customerId) {
        List<Customer> filteredCustomers =
                customers.stream()
                        .filter(c -> c.getId() == customerId)
                        .collect(Collectors.toList());

        if (filteredCustomers.isEmpty()) {
            return null;
        }

        return filteredCustomers.get(0);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer.getId() == 0) {
            customer = addNewCustomer(customer);
        } else {
            customer = updateExistingCustomer(customer);
        }

        return customer;
    }

    private Customer addNewCustomer(Customer customer) {
        Optional<Integer> highestIndex =
                this.customers
                        .stream()
                        .map(Customer::getId).max(Integer::compare);
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

    private Customer updateExistingCustomer(Customer customer) {
        List<Customer> filteredCustomers =
                customers.stream()
                        .filter(c -> c.getId() == customer.getId())
                        .collect(Collectors.toList());
        int indexOfUpdatedCustomer = this.customers.indexOf(filteredCustomers.get(0));
        this.customers.set(indexOfUpdatedCustomer, customer);
        return customer;
    }

    @Override
    public void removeCustomer(int customerId) {
        List<Customer> customerToDelete = this.customers.stream()
                .filter(c -> c.getId() == customerId)
                .collect(Collectors.toList());

        for (Customer customer : customerToDelete) {
            this.customers.remove(customer);
        }
    }

    @Override
    public List<Customer> findCustomers(String searchCriteria) {
        return this.customers.stream()
                .filter(c -> c.getFirstName().toLowerCase().contains(searchCriteria.toLowerCase()) ||
                        c.getLastName().toLowerCase().contains(searchCriteria.toLowerCase())
                        || c.getCity().toLowerCase().contains(searchCriteria.toLowerCase()))
                .collect(Collectors.toList());
    }
}
