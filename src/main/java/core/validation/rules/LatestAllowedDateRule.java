package core.validation.rules;

import java.time.LocalDate;
import java.util.function.Function;

public final class LatestAllowedDateRule<TModelToValidate>
        extends ValidationRuleBase<TModelToValidate, LocalDate> {

    private final LocalDate maximumAllowedDate;

    public LatestAllowedDateRule(LocalDate maximumAllowedDate,
                                 String fieldName,
                                 Function<TModelToValidate, LocalDate> valueSelector) {
        super(fieldName, valueSelector);
        this.maximumAllowedDate = maximumAllowedDate;
    }

    @Override
    protected String getDefaultErrorMessage(LocalDate value, String fieldName) {
        return String.format("'%s' value is '%s' but the latest allowed DATE is '%s'.",
                fieldName,
                value,
                maximumAllowedDate);
    }

    @Override
    protected boolean isValueValid(LocalDate valueToValidate) {
        return valueToValidate == null ||
                valueToValidate.compareTo(maximumAllowedDate) < 1;
    }
}
