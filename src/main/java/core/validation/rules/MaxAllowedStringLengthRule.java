package core.validation.rules;

import java.util.function.Function;

public final class MaxAllowedStringLengthRule<TModelToValidate>
        extends ValidationRuleBase<TModelToValidate, String> {

    private final int maxAllowedLength;

    public MaxAllowedStringLengthRule(int maxAllowedLength,
                                      String fieldName,
                                      Function<TModelToValidate, String> valueSelector) {
        super(fieldName, valueSelector);
        this.maxAllowedLength = maxAllowedLength;
    }

    @Override
    protected String getDefaultErrorMessage(String value, String fieldName) {
        return String.format("Value '%s' for '%s' is '%s' characters " +
                        "long and is too long, since maximum allowed value is '%s'!",
                value,
                fieldName,
                value.length(),
                maxAllowedLength);
    }

    @Override
    protected boolean isValueValid(String valueToValidate) {
        return valueToValidate == null ||
                valueToValidate.trim().length() <= maxAllowedLength;
    }
}
