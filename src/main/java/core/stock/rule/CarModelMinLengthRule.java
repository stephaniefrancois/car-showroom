package core.stock.rule;

import core.domain.car.CarProperties;
import core.validation.rules.MinRequiredLengthRule;

public final class CarModelMinLengthRule
        extends MinRequiredLengthRule<CarProperties> {
    public CarModelMinLengthRule() {
        super("Model", 2);
    }

    @Override
    protected String getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getModel();
    }
}
