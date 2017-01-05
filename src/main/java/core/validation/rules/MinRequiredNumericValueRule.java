package core.validation.rules;

import java.util.function.Function;

public final class MinRequiredNumericValueRule<TModelToValidate,
        TValueToValidate extends Number & Comparable<TValueToValidate>>
        extends ValidationRuleBase<TModelToValidate, TValueToValidate> {

    private final TValueToValidate minimumRequiredValue;

    public MinRequiredNumericValueRule(TValueToValidate minimumRequiredValue,
                                       String fieldName,
                                       Function<TModelToValidate, TValueToValidate> valueSelector) {
        super(fieldName, valueSelector);
        this.minimumRequiredValue = minimumRequiredValue;
    }

    @Override
    protected String getDefaultErrorMessage(TValueToValidate value, String fieldName) {
        return String.format("Value '%s' for '%s' is too small, since the minimum required value is '%s'!",
                value,
                fieldName,
                minimumRequiredValue);
    }

    @Override
    protected boolean isValueValid(TValueToValidate valueToValidate) {
        return valueToValidate == null ||
                valueToValidate.compareTo(minimumRequiredValue) > -1;
    }
}
