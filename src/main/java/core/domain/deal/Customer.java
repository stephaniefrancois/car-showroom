package core.domain.deal;

import java.time.LocalDate;

public final class Customer implements CustomerProperties {
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

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public LocalDate getCustomerSince() {
        return customerSince;
    }

    @Override
    public String getCity() {
        return city;
    }
}
