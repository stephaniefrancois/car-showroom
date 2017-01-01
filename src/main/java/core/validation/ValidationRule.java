package core.validation;

import core.domain.car.CarProperties;
import core.domain.validation.ValidationSummary;

public interface ValidationRule {
    ValidationSummary validate(CarProperties car);
}
