package core.validation.rules;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import core.validation.ValidationRule;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

public abstract class ValidationRuleBase<TModelToValidate, TValueToValidate>
        implements ValidationRule<TModelToValidate> {

    private final String fieldName;
    private final Function<TModelToValidate, TValueToValidate> valueSelector;

    public ValidationRuleBase(
            String fieldName,
            Function<TModelToValidate, TValueToValidate> valueSelector) {

        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(valueSelector);

        this.fieldName = fieldName;
        this.valueSelector = valueSelector;
    }

    protected abstract String getDefaultErrorMessage(TValueToValidate value, String fieldName);

    protected abstract boolean isValueValid(TValueToValidate valueToValidate);

    @Override
    public ValidationSummary validate(TModelToValidate modelToValidate) {
        if (modelToValidate == null) {
            return new ValidationSummary();
        }

        TValueToValidate valueToValidate = valueSelector.apply(modelToValidate);

        if (!isValueValid(valueToValidate)) {
            String errorMessage = getDefaultErrorMessage(valueToValidate, fieldName);
            return new ValidationSummary(
                    Collections.singletonList(new ValidationError(fieldName, errorMessage)));
        }

        return new ValidationSummary();
    }
}
