package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MaxAllowedValueRule;

public final class CarMaximumNumberOfSeatsAllowedRule
        extends MaxAllowedValueRule<CarProperties, Integer> {
    public CarMaximumNumberOfSeatsAllowedRule() {
        super("Number of Seats", 7);
    }

    @Override
    protected Integer getValueToValidate(
            CarProperties modelToValidate) {
        return modelToValidate.getNumberOfSeats();
    }
}
