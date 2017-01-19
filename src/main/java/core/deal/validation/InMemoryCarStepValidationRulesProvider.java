package core.deal.validation;

import core.deal.model.CarDealDetails;
import core.validation.RuleFor;
import core.validation.ValidationRulesProvider;

public final class InMemoryCarStepValidationRulesProvider
        extends ValidationRulesProvider<CarDealDetails> {

    public InMemoryCarStepValidationRulesProvider() {
        addRule(RuleFor.mandatory("Car", CarDealDetails::getCar));
    }
}
