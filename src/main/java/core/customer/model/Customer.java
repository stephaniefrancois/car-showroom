package core.customer.model;

import core.IHaveIdentifier;

import java.time.LocalDate;

public final class Customer implements IHaveIdentifier {
    private final int customerId;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final LocalDate customerSince;

    public Customer(int customerId,
                    String firstName,
                    String lastName,
                    String city,
                    LocalDate customerSince) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.customerSince = customerSince;
    }

    public Customer(String firstName,
                    String lastName,
                    String city) {
        this(0, firstName, lastName, city, LocalDate.now());
    }

    public int getId() {
        return customerId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
