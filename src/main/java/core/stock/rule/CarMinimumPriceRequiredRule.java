package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MinRequiredValueRule;

import java.math.BigDecimal;

public final class CarMinimumPriceRequiredRule
        extends MinRequiredValueRule<CarProperties, BigDecimal> {
    public CarMinimumPriceRequiredRule() {
        super("Price", new BigDecimal(1));
    }

    @Override
    protected BigDecimal getValueToValidate(
            CarProperties modelToValidate) {
        return modelToValidate.getPrice();
    }
}
