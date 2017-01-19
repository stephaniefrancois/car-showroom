package core.validation.rules;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;

public final class AllowSetOfValuesRule<TModelToValidate>
        extends ValidationRuleBase<TModelToValidate, String> {

    private final List<String> allowedValues;

    public AllowSetOfValuesRule(String fieldName,
                                List<String> allowedValues,
                                Function<TModelToValidate, String> valueSelector) {
        super(fieldName, valueSelector);
        Objects.requireNonNull(allowedValues);
        this.allowedValues = allowedValues;
    }

    @Override
    protected String getDefaultErrorMessage(String value, String fieldName) {
        StringJoiner sj = new StringJoiner(", ");
        allowedValues.forEach(sj::add);
        return String.format("Value for '%s' is not valid! Allowed values are: %s", fieldName, sj.toString());
    }

    @Override
    protected boolean isValueValid(String valueToValidate) {
        if (valueToValidate == null) {
            return true;
        }

        return allowedValues.stream()
                .filter(v -> v.toLowerCase().equals(valueToValidate.toLowerCase()))
                .count() > 0;
    }
}
