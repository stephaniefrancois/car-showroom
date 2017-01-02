package core.validation.rule;

import core.domain.car.CarProperties;

public final class CarMakeRule extends MandatoryLengthRestrictedFieldRule {
    public CarMakeRule() {
        super("Make", 2, 50);
    }

    @Override
    protected String getValueToValidate(CarProperties car) {
        return car.getMake();
    }
}
