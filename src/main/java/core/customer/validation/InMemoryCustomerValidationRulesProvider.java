package core.customer.validation;

import core.customer.model.Customer;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

public final class InMemoryCustomerValidationRulesProvider
        extends ValidationRulesProvider<Customer> {

    private final int minimumValueLength = 2;
    private final int maximumValueLength = 50;

    public InMemoryCustomerValidationRulesProvider() {
        addRule(RuleFor.mandatory("First Name", Customer::getFirstName));
        addRule(RuleFor.minLength(minimumValueLength, "First Name", Customer::getFirstName));
        addRule(RuleFor.maxLength(maximumValueLength, "First Name", Customer::getFirstName));

        addRule(RuleFor.mandatory("Last Name", Customer::getLastName));
        addRule(RuleFor.minLength(minimumValueLength, "Last Name", Customer::getLastName));
        addRule(RuleFor.maxLength(maximumValueLength, "Last Name", Customer::getLastName));

        addRule(RuleFor.mandatory("City", Customer::getCity));
        addRule(RuleFor.minLength(minimumValueLength, "City", Customer::getCity));
        addRule(RuleFor.maxLength(maximumValueLength, "City", Customer::getCity));
    }
}
