package core.validation.rules;

import java.util.function.Function;

public final class MandatoryFieldRule<TModelToValidate, TValueToValidate>
        extends ValidationRuleBase<TModelToValidate, TValueToValidate> {

    public MandatoryFieldRule(String fieldName,
                              Function<TModelToValidate, TValueToValidate> valueSelector) {
        super(fieldName, valueSelector);
    }

    @Override
    protected String getDefaultErrorMessage(TValueToValidate value, String fieldName) {
        return String.format("Value for '%s' must be supplied!", fieldName);
    }

    @Override
    protected boolean isValueValid(TValueToValidate valueToValidate) {
        return valueToValidate != null;
    }
}
