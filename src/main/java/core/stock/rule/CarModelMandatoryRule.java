package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MandatoryFieldRule;

public final class CarModelMandatoryRule
        extends MandatoryFieldRule<CarProperties, String> {
    public CarModelMandatoryRule() {
        super("Model");
    }

    @Override
    protected String getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getModel();
    }
}
