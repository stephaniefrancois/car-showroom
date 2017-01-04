package core.domain.deal;

import java.util.Date;

public final class Customer {
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
    private final Date customerSince;

    public Customer(String firstName,
                    String lastName,
                    Date dateOfBirth,
                    Date customerSince) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.customerSince = customerSince;
    }

    public Date getCustomerSince() {
        return customerSince;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
