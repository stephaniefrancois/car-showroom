package core.stock.validation;

import core.stock.model.CarDetails;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public final class RuleBasedCarValidator extends RuleBasedValidator<CarDetails> {
    public RuleBasedCarValidator(ValidationRulesProvider<CarDetails> rulesProvider) {
        super(rulesProvider);
    }
}
