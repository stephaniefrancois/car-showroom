package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MaxAllowedLengthRule;

public final class CarMakeMaxLengthRule
        extends MaxAllowedLengthRule<CarProperties> {
    public CarMakeMaxLengthRule() {
        super("Make", 100);
    }

    @Override
    protected String getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getMake();
    }
}
