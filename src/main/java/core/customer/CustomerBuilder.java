package core.customer;

import core.ItemBuilder;
import core.customer.model.Customer;
import core.validation.Validator;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class CustomerBuilder implements ItemBuilder<Customer> {
    private final Validator<Customer> validator;
    private int customerId;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDate customerSince;

    public CustomerBuilder(Validator<Customer> validator) {

        Objects.requireNonNull(validator,
                "'validator' must be supplied!");

        this.validator = validator;
        customerId = 0;
        firstName = "";
        lastName = "";
        city = "";
        customerSince = LocalDate.from(LocalDateTime.now());
    }

    public CustomerBuilder(Customer customer, Validator<Customer> validator) {
        this(validator);

        Objects.requireNonNull(customer,
                "'customer' must be supplied!");

        customerId = customer.getId();
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        city = customer.getCity();
        customerSince = customer.getCustomerSince();
    }

    public int getId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public Customer build() throws ValidationException {
        ValidationSummary summary = validate();
        if (!summary.getIsValid()) {
            throw new ValidationException(summary.getValidationErrors());
        }

        return buildCustomer();
    }

    private Customer buildCustomer() {
        return new Customer(customerId,
                firstName,
                lastName,
                city,
                customerSince);
    }

    public ValidationSummary validate() {
        Customer customer = this.buildCustomer();
        return validator.validate(customer);
    }
}
