package core.validation.rules;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MinRequiredValueRule<TModelToValidate,
        TValue extends Number & Comparable<TValue>>
        implements ValidationRule<TModelToValidate> {
    private final TValue minimumAllowedValue;
    private final String fieldName;

    protected MinRequiredValueRule(String fieldName, TValue minimumAllowedValue) {
        this.fieldName = fieldName;
        this.minimumAllowedValue = minimumAllowedValue;
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
                valueToValidate.compareTo(minimumAllowedValue) == -1) {
            errors.add(new ValidationError(fieldName,
                    fieldName + " is '" + valueToValidate +
                            "' but the allowed minimum value is '" +
                            minimumAllowedValue + "'."));
        }

        return new ValidationSummary(errors);
    }
}
