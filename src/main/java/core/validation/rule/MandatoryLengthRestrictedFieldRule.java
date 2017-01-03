package core.validation.rule;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MandatoryLengthRestrictedFieldRule<TModelToValidate>
        implements ValidationRule<TModelToValidate> {
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

    protected abstract String getValueToValidate(TModelToValidate modelToValidate);

    @Override
    public ValidationSummary validate(TModelToValidate modelToValidate) {
        if (modelToValidate == null) {
            return new ValidationSummary();
        }

        List<ValidationError> errors = new ArrayList<>();
        String valueToValidate = getValueToValidate(modelToValidate);

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
