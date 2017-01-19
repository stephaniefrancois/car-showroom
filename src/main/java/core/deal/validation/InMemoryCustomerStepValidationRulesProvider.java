package core.deal.validation;

import core.deal.model.CarDealDetails;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

public final class InMemoryCustomerStepValidationRulesProvider
        extends ValidationRulesProvider<CarDealDetails> {

    public InMemoryCustomerStepValidationRulesProvider() {
        addRule(RuleFor.mandatory("Customer", CarDealDetails::getCustomer));
    }
}
