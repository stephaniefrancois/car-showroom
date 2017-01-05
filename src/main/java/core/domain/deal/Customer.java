package core.domain.deal;

import java.time.LocalDate;

public final class Customer {
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final LocalDate customerSince;

    public Customer(String firstName,
                    String lastName,
                    LocalDate dateOfBirth,
                    LocalDate customerSince) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.customerSince = customerSince;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
