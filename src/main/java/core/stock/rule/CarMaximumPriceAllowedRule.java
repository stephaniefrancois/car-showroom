package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MaxAllowedValueRule;

import java.math.BigDecimal;

public final class CarMaximumPriceAllowedRule
        extends MaxAllowedValueRule<CarProperties, BigDecimal> {
    public CarMaximumPriceAllowedRule() {
        super("Price", new BigDecimal(1000000));
    }

    @Override
    protected BigDecimal getValueToValidate(
            CarProperties modelToValidate) {
        return modelToValidate.getPrice();
    }
}
