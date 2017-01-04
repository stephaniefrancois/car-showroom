package core.deal;

import core.domain.deal.CarDealProperties;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRule;

import java.util.List;

public final class RuleBasedDealValidator extends RuleBasedValidator<CarDealProperties> {
    public RuleBasedDealValidator(List<ValidationRule<CarDealProperties>> validationRules) {
        super(validationRules);
    }
}
