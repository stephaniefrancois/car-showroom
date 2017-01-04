package core.validation.rules;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MinRequiredLengthRule<TModelToValidate>
        implements ValidationRule<TModelToValidate> {
    private final int minRequiredLength;
    private final String fieldName;

    protected MinRequiredLengthRule(String fieldName, int minRequiredLength) {
        this.fieldName = fieldName;
        this.minRequiredLength = minRequiredLength;
    }

    protected abstract String getValueToValidate(TModelToValidate modelToValidate);

    @Override
    public ValidationSummary validate(TModelToValidate modelToValidate) {
        if (modelToValidate == null) {
            return new ValidationSummary();
        }

        List<ValidationError> errors = new ArrayList<>();
        String valueToValidate = getValueToValidate(modelToValidate);

        if (valueToValidate != null &&
                valueToValidate.trim().length() < minRequiredLength) {
            errors.add(new ValidationError(fieldName,
                    fieldName + " is '" + valueToValidate.trim().length() +
                            "' is characters length, but the minimum required length is '" +
                            minRequiredLength + "'."));
        }

        return new ValidationSummary(errors);
    }
}
