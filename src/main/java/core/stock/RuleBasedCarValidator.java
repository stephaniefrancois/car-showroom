package core.stock;

import core.domain.car.CarProperties;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRule;

import java.util.List;

public final class RuleBasedCarValidator extends RuleBasedValidator<CarProperties> {
    public RuleBasedCarValidator(List<ValidationRule<CarProperties>> validationRules) {
        super(validationRules);
    }
}
