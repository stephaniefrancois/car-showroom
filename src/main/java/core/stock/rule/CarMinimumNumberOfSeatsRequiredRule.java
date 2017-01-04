package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MinRequiredValueRule;

public final class CarMinimumNumberOfSeatsRequiredRule
        extends MinRequiredValueRule<CarProperties, Integer> {
    public CarMinimumNumberOfSeatsRequiredRule() {
        super("Number of Seats", 2);
    }

    @Override
    protected Integer getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getNumberOfSeats();
    }
}
