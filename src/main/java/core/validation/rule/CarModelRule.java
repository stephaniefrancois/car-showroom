package core.validation.rule;

import core.domain.car.CarProperties;

public final class CarModelRule extends MandatoryLengthRestrictedFieldRule {
    public CarModelRule() {
        super("Model", 2, 50);
    }

    @Override
    protected String getValueToValidate(CarProperties car) {
        return car.getModel();
    }
}
