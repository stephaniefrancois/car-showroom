package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MinRequiredValueRule;

public final class CarMinimumYearRequiredRule
        extends MinRequiredValueRule<CarProperties, Integer> {
    public CarMinimumYearRequiredRule() {
        super("Year", 2000);
    }

    @Override
    protected Integer getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getYear();
    }
}
