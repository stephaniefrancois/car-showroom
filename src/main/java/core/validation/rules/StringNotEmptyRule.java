package core.validation.rules;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public abstract class StringNotEmptyRule<TModelToValidate>
        implements ValidationRule<TModelToValidate> {
    private final String fieldName;

    protected StringNotEmptyRule(String fieldName) {
        this.fieldName = fieldName;
    }

    protected abstract String getValueToValidate(TModelToValidate modelToValidate);

    @Override
    public ValidationSummary validate(TModelToValidate modelToValidate) {
        if (modelToValidate == null) {
            return new ValidationSummary();
        }

        List<ValidationError> errors = new ArrayList<>();
        String valueToValidate = getValueToValidate(modelToValidate);

        if (valueToValidate == null ||
                valueIsNotSupplied(valueToValidate)) {
            errors.add(new ValidationError(fieldName,
                    fieldName + " must contain a value!"));
        }

        return new ValidationSummary(errors);
    }

    private boolean valueIsNotSupplied(String valueToValidate) {
        return valueToValidate.trim().length() == 0;
    }
}
