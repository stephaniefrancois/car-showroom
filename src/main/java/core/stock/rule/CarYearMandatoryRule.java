package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MandatoryFieldRule;

public final class CarYearMandatoryRule
        extends MandatoryFieldRule<CarProperties, Integer> {
    public CarYearMandatoryRule() {
        super("Year");
    }

    @Override
    protected Integer getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getYear();
    }
}
