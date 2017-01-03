package core.deal;

import core.domain.deal.CarDeal;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRule;

import java.util.List;

public final class RuleBasedDealValidator extends RuleBasedValidator<CarDeal> {
    public RuleBasedDealValidator(List<ValidationRule<CarDeal>> validationRules) {
        super(validationRules);
    }
}
