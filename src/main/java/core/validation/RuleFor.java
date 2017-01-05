package core.validation;

import core.validation.rules.*;

import java.time.LocalDate;
import java.util.function.Function;

public class RuleFor {

    public static <M, V>
    MandatoryFieldRule<M, V> mandatory(String fieldName,
                                       Function<M, V> valueSelector) {
        return new MandatoryFieldRule<>(fieldName, valueSelector);
    }

    public static <M>
    LatestAllowedDateRule<M> latestAllowedDate(LocalDate latestAllowedDate,
                                               String fieldName,
                                               Function<M, LocalDate> valueSelector) {
        return new LatestAllowedDateRule<>(latestAllowedDate, fieldName, valueSelector);
    }

    public static <M>
    EarliestAllowedDateRule<M> earliestAllowedDate(LocalDate earliestAllowedDate,
                                                   String fieldName,
                                                   Function<M, LocalDate> valueSelector) {
        return new EarliestAllowedDateRule<>(earliestAllowedDate, fieldName, valueSelector);
    }

    public static <M> NotEmptyRule<M> notEmpty(String fieldName,
                                               Function<M, String> valueSelector) {
        return new NotEmptyRule<>(fieldName, valueSelector);
    }

    public static <M, V extends Number & Comparable<V>>
    MaxNumericValueAllowedRule<M, V> maxValue(V maximumAllowedValue,
                                              String fieldName,
                                              Function<M, V> valueSelector) {
        return new MaxNumericValueAllowedRule<>(maximumAllowedValue, fieldName, valueSelector);
    }

    public static <M, V extends Number & Comparable<V>>
    MinRequiredNumericValueRule<M, V> minValue(V minimumRequiredValue,
                                               String fieldName,
                                               Function<M, V> valueSelector) {
        return new MinRequiredNumericValueRule<>(minimumRequiredValue, fieldName, valueSelector);
    }

    public static <M> MinRequiredStringLengthRule<M> minLength(int minimumRequiredLength,
                                                               String fieldName,
                                                               Function<M, String> valueSelector) {
        return new MinRequiredStringLengthRule<>(minimumRequiredLength,
                fieldName, valueSelector);
    }

    public static <M> MaxAllowedStringLengthRule<M> maxLength(int maximumAllowedLength,
                                                              String fieldName,
                                                              Function<M, String> valueSelector) {
        return new MaxAllowedStringLengthRule<>(maximumAllowedLength,
                fieldName, valueSelector);
    }
}
