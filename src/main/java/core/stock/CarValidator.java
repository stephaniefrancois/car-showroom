package core.stock;

import core.domain.car.CarProperties;
import core.domain.validation.ValidationSummary;

public interface CarValidator {
    ValidationSummary validate(CarProperties car);
}
