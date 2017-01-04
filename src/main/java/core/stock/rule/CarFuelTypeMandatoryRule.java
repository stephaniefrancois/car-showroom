package core.stock.rule;

import core.domain.car.CarProperties;
import core.domain.car.FuelType;
import core.validation.rules.MandatoryFieldRule;

public final class CarFuelTypeMandatoryRule
        extends MandatoryFieldRule<CarProperties, FuelType> {
    public CarFuelTypeMandatoryRule() {
        super("Fuel Type");
    }

    @Override
    protected FuelType getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getFuelType();
    }
}
