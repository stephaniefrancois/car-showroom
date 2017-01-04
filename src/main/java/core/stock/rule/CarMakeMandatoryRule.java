package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MandatoryFieldRule;

public final class CarMakeMandatoryRule
        extends MandatoryFieldRule<CarProperties, String> {
    public CarMakeMandatoryRule() {
        super("Make");
    }

    @Override
    protected String getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getMake();
    }
}
