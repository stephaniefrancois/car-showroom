package core.customer.validation;

import core.domain.car.CarProperties;
import core.domain.deal.CustomerProperties;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public final class RuleBasedCustomerValidator extends RuleBasedValidator<CustomerProperties> {
    public RuleBasedCustomerValidator(ValidationRulesProvider<CustomerProperties> rulesProvider) {
        super(rulesProvider);
    }
}
