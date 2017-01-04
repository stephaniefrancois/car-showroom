package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.StringNotEmptyRule;

public final class CarColorRequiredRule
        extends StringNotEmptyRule<CarProperties> {
    public CarColorRequiredRule() {
        super("Color");
    }

    @Override
    protected String getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getColor();
    }
}
