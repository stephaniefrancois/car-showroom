package core.stock.rule;

import core.domain.car.BodyStyle;
import core.domain.car.CarProperties;
import core.validation.rules.MandatoryFieldRule;

public final class CarBodyStyleMandatoryRule
        extends MandatoryFieldRule<CarProperties, BodyStyle> {
    public CarBodyStyleMandatoryRule() {
        super("Body Style");
    }

    @Override
    protected BodyStyle getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getBodyStyle();
    }
}
