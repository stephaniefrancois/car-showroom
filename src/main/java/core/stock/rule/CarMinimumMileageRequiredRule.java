package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MinRequiredValueRule;

public final class CarMinimumMileageRequiredRule
        extends MinRequiredValueRule<CarProperties, Integer> {
    public CarMinimumMileageRequiredRule() {
        super("Mileage", 0);
    }

    @Override
    protected Integer getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getMileage();
    }
}
