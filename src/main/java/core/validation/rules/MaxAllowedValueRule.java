package core.validation.rules;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MaxAllowedValueRule<TModelToValidate,
        TValue extends Number & Comparable<TValue>>
        implements ValidationRule<TModelToValidate> {
    private final TValue maximumAllowedValue;
    private final String fieldName;

    protected MaxAllowedValueRule(String fieldName, TValue maximumAllowedValue) {
        this.fieldName = fieldName;
        this.maximumAllowedValue = maximumAllowedValue;
    }

    protected abstract TValue getValueToValidate(TModelToValidate modelToValidate);

    @Override
    public ValidationSummary validate(TModelToValidate modelToValidate) {
        if (modelToValidate == null) {
            return new ValidationSummary();
        }

        List<ValidationError> errors = new ArrayList<>();
        TValue valueToValidate = getValueToValidate(modelToValidate);
        if (valueToValidate != null &&
                valueToValidate.compareTo(maximumAllowedValue) == 1) {
            errors.add(new ValidationError(fieldName,
                    fieldName + " is '" + valueToValidate +
                            "' but the maximum allowed value is '" +
                            maximumAllowedValue + "'."));
        }

        return new ValidationSummary(errors);
    }
}
