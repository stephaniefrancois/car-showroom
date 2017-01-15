package core.deal.validation;

import core.deal.model.CarDealDetails;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public final class RuleBasedCarDealValidator extends RuleBasedValidator<CarDealDetails> {
    public RuleBasedCarDealValidator(ValidationRulesProvider<CarDealDetails> rulesProvider) {
        super(rulesProvider);
    }
}
