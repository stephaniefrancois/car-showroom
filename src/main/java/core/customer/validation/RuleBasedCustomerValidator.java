package core.customer.validation;

import core.customer.model.Customer;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public final class RuleBasedCustomerValidator extends RuleBasedValidator<Customer> {
    public RuleBasedCustomerValidator(ValidationRulesProvider<Customer> rulesProvider) {
        super(rulesProvider);
    }
}
