package core.validation.rules;

import java.util.function.Function;

public final class MinRequiredStringLengthRule<TModelToValidate>
        extends ValidationRuleBase<TModelToValidate, String> {

    private final int minRequiredLength;

    public MinRequiredStringLengthRule(int minRequiredLength,
                                       String fieldName,
                                       Function<TModelToValidate, String> valueSelector) {
        super(fieldName, valueSelector);
        this.minRequiredLength = minRequiredLength;
    }

    @Override
    protected String getDefaultErrorMessage(String value, String fieldName) {
        return String.format("Value '%s' for '%s' is '%s' characters " +
                        "long and is too short, since minimum required value is '%s'!",
                value,
                fieldName,
                value.length(),
                minRequiredLength);
    }

    @Override
    protected boolean isValueValid(String valueToValidate) {
        return valueToValidate == null ||
                valueToValidate.trim().length() >= minRequiredLength;
    }
}
