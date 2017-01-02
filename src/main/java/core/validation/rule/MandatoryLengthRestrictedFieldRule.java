package core.validation.rule;

import core.domain.car.CarProperties;
import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MandatoryLengthRestrictedFieldRule implements ValidationRule {
    private final int minRequiredLength;
    private final int maxAllowedLength;
    private final String fieldName;

    protected MandatoryLengthRestrictedFieldRule(String fieldName,
                                                 int minRequiredLength,
                                                 int maxAllowedLength) {
        this.fieldName = fieldName;
        this.minRequiredLength = minRequiredLength;
        this.maxAllowedLength = maxAllowedLength;
    }

    protected abstract String getValueToValidate(CarProperties car);

    @Override
    public ValidationSummary validate(CarProperties car) {
        if (car == null) {
            return new ValidationSummary();
        }

        List<ValidationError> errors = new ArrayList<>();
        String valueToValidate = getValueToValidate(car);

        if (valueIsNotSupplied(valueToValidate)) {
            errors.add(new ValidationError(fieldName, fieldName + " is a mandatory field!"));
        } else {
            if (valueToValidate.trim().length() < minRequiredLength) {
                errors.add(new ValidationError(fieldName,
                        fieldName + " is '" + valueToValidate.trim().length() +
                                "' is characters length, but the minimum required length is '" +
                                minRequiredLength + "'."));
            }

            if (valueToValidate.length() > maxAllowedLength) {
                errors.add(new ValidationError(fieldName,
                        fieldName + " is '" + valueToValidate.trim().length() +
                                "' is characters length, but the maximum allowed length is '" +
                                maxAllowedLength + "'."));
            }
        }

        return new ValidationSummary(errors);
    }

    private boolean valueIsNotSupplied(String valueToValidate) {
        return valueToValidate == null || valueToValidate.trim().length() == 0;
    }
}
