package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MinRequiredLengthRule;

public final class CarMakeMinLengthRule
        extends MinRequiredLengthRule<CarProperties> {
    public CarMakeMinLengthRule() {
        super("Make", 2);
    }

    @Override
    protected String getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getMake();
    }
}
