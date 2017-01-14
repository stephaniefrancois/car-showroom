package core.customer;

import core.domain.deal.Customer;
import core.domain.deal.CustomerProperties;
import core.domain.validation.ValidationException;
import core.domain.validation.ValidationSummary;
import core.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class CustomerFactory implements CustomerProperties {
    private final Validator<CustomerProperties> validator;
    private int customerId;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDate customerSince;

    public CustomerFactory(Validator<CustomerProperties> validator) {

        Objects.requireNonNull(validator,
                "'validator' must be supplied!");

        this.validator = validator;
        customerId = 0;
        firstName = "";
        lastName = "";
        city = "";
        customerSince = LocalDate.from(LocalDateTime.now());
    }

    public CustomerFactory(CustomerProperties customer, Validator<CustomerProperties> validator) {
        this(validator);

        Objects.requireNonNull(customer,
                "'customer' must be supplied!");

        customerId = customer.getCustomerId();
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        city = customer.getCity();
        customerSince = customer.getCustomerSince();
    }

    @Override
    public int getCustomerId() {
        return customerId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public CustomerProperties build() throws ValidationException {
        ValidationSummary summary = validate();
        if (!summary.getIsValid()) {
            throw new ValidationException(summary.getValidationErrors());
        }

        return new Customer(customerId,
                firstName,
                lastName,
                city,
                customerSince);
    }

    public ValidationSummary validate() {
        return validator.validate(this);
    }
}
