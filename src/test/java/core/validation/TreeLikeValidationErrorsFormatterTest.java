package core.validation;

import common.StringExtensions;
import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class TreeLikeValidationErrorsFormatterTest {
    private final String NewLine = StringExtensions.NewLineSeparator;

    @Test
    public void GivenNoErrorsWhenFormattedThenNoErrorsMessageShouldBeReturned() {
        // Given
        ValidationSummary summary = new ValidationSummary();
        ValidationErrorsFormatter sut = new TreeLikeValidationErrorsFormatter();

        // When
        String result = sut.format(summary);

        // Then
        assertThat(result, equalTo("No validation errors found."));
    }

    @Test
    public void GivenSummaryWithSingleErrorWhenFormattedThenFieldNameAndIndentedErrorShouldBeDisplayed() {
        // Given
        ValidationSummary summary = new ValidationSummary(Arrays.asList(
                new ValidationError("Field", "Field has an error!")
        ));
        ValidationErrorsFormatter sut = new TreeLikeValidationErrorsFormatter();

        // When
        String result = sut.format(summary);

        // Then
        assertThat(result, equalTo(String.format("Field:%s  - Field has an error!", NewLine)));
    }

    @Test
    public void GivenSummaryWithMultipleErrorsForSameFieldWhenFormattedThenBothShouldBeDisplayed() {
        // Given
        ValidationSummary summary = new ValidationSummary(Arrays.asList(
                new ValidationError("Field", "Field has an error!"),
                new ValidationError("Field", "Field has another error!")
        ));
        ValidationErrorsFormatter sut = new TreeLikeValidationErrorsFormatter();

        // When
        String result = sut.format(summary);

        // Then
        assertThat(result, equalTo(
                String.format("Field:%s  - Field has an error!" +
                                "%s  - Field has another error!",
                        NewLine,
                        NewLine)));
    }

    @Test
    public void GivenSummaryWithMultipleErrorsForMultipleFieldsWhenFormattedThenErrorsShouldBeGroupedByField() {
        // Given
        ValidationSummary summary = new ValidationSummary(Arrays.asList(
                new ValidationError("Field1", "Error11!"),
                new ValidationError("Field2", "Error23!"),
                new ValidationError("Field1", "Error12!"),
                new ValidationError("Field3", "Error34!"),
                new ValidationError("Field3", "Error35!")
        ));
        ValidationErrorsFormatter sut = new TreeLikeValidationErrorsFormatter();

        // When
        String result = sut.format(summary);

        // Then
        assertThat(result, equalTo(
                String.format("Field1:%s  - Error11!" +
                                "%s  - Error12!" +
                                "%s%s" +
                                "Field2:%s  - Error23!" +
                                "%s%S" +
                                "Field3:%s  - Error34!" +
                                "%s  - Error35!",
                        NewLine,
                        NewLine,
                        NewLine,
                        NewLine,
                        NewLine,
                        NewLine,
                        NewLine,
                        NewLine,
                        NewLine)));
    }
}