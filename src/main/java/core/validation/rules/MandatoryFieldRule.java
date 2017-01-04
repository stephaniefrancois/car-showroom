package core.validation.rules;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class MandatoryFieldRule<TModelToValidate, TValueToValidate>
        implements ValidationRule<TModelToValidate> {
    private final String fieldName;

    protected MandatoryFieldRule(String fieldName) {
        this.fieldName = fieldName;
    }

    protected abstract TValueToValidate getValueToValidate(TModelToValidate modelToValidate);

    @Override
    public ValidationSummary validate(TModelToValidate modelToValidate) {
        if (modelToValidate == null) {
            return new ValidationSummary();
        }

        List<ValidationError> errors = new ArrayList<>();
        TValueToValidate valueToValidate = getValueToValidate(modelToValidate);

        if (valueToValidate == null) {
            errors.add(new ValidationError(fieldName, fieldName + " is a mandatory field!"));

            return new ValidationSummary(errors);
        }

        return new ValidationSummary();
    }
}
