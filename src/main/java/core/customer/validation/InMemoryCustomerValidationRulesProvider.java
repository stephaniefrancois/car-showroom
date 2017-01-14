package core.customer.validation;

import core.domain.deal.CustomerProperties;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

public final class InMemoryCustomerValidationRulesProvider
        extends ValidationRulesProvider<CustomerProperties> {

    private final int minimumValueLength = 2;
    private final int maximumValueLength = 50;

    public InMemoryCustomerValidationRulesProvider() {
        addRule(RuleFor.mandatory("First Name", CustomerProperties::getFirstName));
        addRule(RuleFor.minLength(minimumValueLength, "First Name", CustomerProperties::getFirstName));
        addRule(RuleFor.maxLength(maximumValueLength, "First Name", CustomerProperties::getFirstName));

        addRule(RuleFor.mandatory("Last Name", CustomerProperties::getLastName));
        addRule(RuleFor.minLength(minimumValueLength, "Last Name", CustomerProperties::getLastName));
        addRule(RuleFor.maxLength(maximumValueLength, "Last Name", CustomerProperties::getLastName));

        addRule(RuleFor.mandatory("City", CustomerProperties::getCity));
        addRule(RuleFor.minLength(minimumValueLength, "City", CustomerProperties::getCity));
        addRule(RuleFor.maxLength(maximumValueLength, "City", CustomerProperties::getCity));
    }
}
