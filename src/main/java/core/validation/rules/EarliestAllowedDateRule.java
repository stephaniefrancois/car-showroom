package core.validation.rules;

import java.time.LocalDate;
import java.util.function.Function;

public final class EarliestAllowedDateRule<TModelToValidate>
        extends ValidationRuleBase<TModelToValidate, LocalDate> {

    private final LocalDate earliestAllowedDate;

    public EarliestAllowedDateRule(LocalDate earliestAllowedDate,
                                   String fieldName,
                                   Function<TModelToValidate, LocalDate> valueSelector) {
        super(fieldName, valueSelector);
        this.earliestAllowedDate = earliestAllowedDate;
    }

    @Override
    protected String getDefaultErrorMessage(LocalDate value, String fieldName) {
        return String.format("'%s' value is '%s' but the earliest allowed DATE is '%s'.",
                fieldName,
                value,
                earliestAllowedDate);
    }

    @Override
    protected boolean isValueValid(LocalDate valueToValidate) {
        return valueToValidate == null ||
                valueToValidate.compareTo(earliestAllowedDate) > -1;
    }
}
