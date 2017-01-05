package core.stock.validation;

import core.domain.car.CarProperties;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;

public final class RuleBasedCarValidator extends RuleBasedValidator<CarProperties> {
    public RuleBasedCarValidator(ValidationRulesProvider<CarProperties> rulesProvider) {
        super(rulesProvider);
    }
}
