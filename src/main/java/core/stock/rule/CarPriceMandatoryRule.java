package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MandatoryFieldRule;

import java.math.BigDecimal;

public final class CarPriceMandatoryRule
        extends MandatoryFieldRule<CarProperties, BigDecimal> {
    public CarPriceMandatoryRule() {
        super("Price");
    }

    @Override
    protected BigDecimal getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getPrice();
    }
}
