package core.validation.rules;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MaxAllowedLengthRule<TModelToValidate>
        implements ValidationRule<TModelToValidate> {
    private final int maxAllowedLength;
    private final String fieldName;

    protected MaxAllowedLengthRule(String fieldName, int maxAllowedLength) {
        this.fieldName = fieldName;
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

        if (valueToValidate != null &&
                valueToValidate.trim().length() > maxAllowedLength) {
            errors.add(new ValidationError(fieldName,
                    fieldName + " is '" + valueToValidate.trim().length() +
                            "' is characters length, but the maximum allowed length is '" +
                            maxAllowedLength + "'."));
        }

        return new ValidationSummary(errors);
    }
}
