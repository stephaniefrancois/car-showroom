package core.stock.rule;

import core.domain.car.CarProperties;
import core.domain.car.Transmission;
import core.validation.rules.MandatoryFieldRule;

public final class CarTransmissionMandatoryRule
        extends MandatoryFieldRule<CarProperties, Transmission> {
    public CarTransmissionMandatoryRule() {
        super("Transmission");
    }

    @Override
    protected Transmission getValueToValidate(CarProperties modelToValidate) {
        return modelToValidate.getTransmission();
    }
}
