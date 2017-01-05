package core.deal.validation;

import core.domain.deal.CarDealProperties;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public final class RuleBasedDealValidator extends RuleBasedValidator<CarDealProperties> {
    public RuleBasedDealValidator(ValidationRulesProvider<CarDealProperties> rulesProvider) {
        super(rulesProvider);
    }
}
