package core.validation.rules;

import java.util.function.Function;

public final class NotEmptyRule<TModelToValidate>
        extends ValidationRuleBase<TModelToValidate, String> {

    public NotEmptyRule(String fieldName,
                        Function<TModelToValidate, String> valueSelector) {
        super(fieldName, valueSelector);
    }

    @Override
    protected String getDefaultErrorMessage(String value, String fieldName) {
        return String.format("Value for '%s' can not be empty!", fieldName);
    }

    @Override
    protected boolean isValueValid(String valueToValidate) {
        return valueToValidate != null &&
                valueToValidate.trim().length() > 0;
    }
}
