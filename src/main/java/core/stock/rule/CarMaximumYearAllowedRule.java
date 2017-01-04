package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MaxAllowedValueRule;

import java.time.LocalDateTime;

public final class CarMaximumYearAllowedRule
        extends MaxAllowedValueRule<CarProperties, Integer> {
    public CarMaximumYearAllowedRule() {
        super("Year", getOneYearFromNow());
    }

    private static Integer getOneYearFromNow() {
        return LocalDateTime.now().getYear() + 1;
    }

    @Override
    protected Integer getValueToValidate(
            CarProperties modelToValidate) {
        return modelToValidate.getYear();
    }
}
