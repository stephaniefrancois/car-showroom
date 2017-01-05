package core.validation.rules;

import java.util.function.Function;

public final class MaxNumericValueAllowedRule<TModelToValidate,
        TValueToValidate extends Number & Comparable<TValueToValidate>>
        extends ValidationRuleBase<TModelToValidate, TValueToValidate> {

    private final TValueToValidate maximumAllowedValue;

    public MaxNumericValueAllowedRule(TValueToValidate maximumAllowedValue,
                                      String fieldName,
                                      Function<TModelToValidate, TValueToValidate> valueSelector) {
        super(fieldName, valueSelector);
        this.maximumAllowedValue = maximumAllowedValue;
    }

    @Override
    protected String getDefaultErrorMessage(TValueToValidate value, String fieldName) {
        return String.format("Value '%s' for '%s' is too large, since the maximum allowed value is '%s'!",
                value,
                fieldName,
                maximumAllowedValue);
    }

    @Override
    protected boolean isValueValid(TValueToValidate valueToValidate) {
        return valueToValidate == null ||
                valueToValidate.compareTo(maximumAllowedValue) < 1;
    }
}
