package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MaxAllowedLengthRule;

public final class CarModelMaxLengthRule
        extends MaxAllowedLengthRule<CarProperties> {
    public CarModelMaxLengthRule() {
        super("Model", 100);
    }

    @Override
    protected String getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getModel();
    }
}
