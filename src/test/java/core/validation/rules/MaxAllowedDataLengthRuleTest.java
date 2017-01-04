package core.validation.rules;

import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;
import testing.helpers.MaxAllowedDataLengthRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MaxAllowedDataLengthRuleTest {

    @Test
    public void GivenDataFieldHasValueOfAllowedLengthWhenValidatingThenRuleShouldPass() {
        // Given
        MaxAllowedDataLengthRule sut = new MaxAllowedDataLengthRule(5);
        FakeModel data = new FakeModel("12345");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        MaxAllowedDataLengthRule sut = new MaxAllowedDataLengthRule(5);
        String value = null;
        FakeModel data = new FakeModel(value);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldHasTooLongValueWhenValidatingThenRuleShouldFail() {
        // Given
        MaxAllowedDataLengthRule sut = new MaxAllowedDataLengthRule(5);
        FakeModel data = new FakeModel("123456");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }

    @Test
    public void GivenDataFieldHasValueStartingOrEndingWithSpacesExceedingAllowedLengthWhenValidatingThenRuleShouldPass() {
        // Given
        MaxAllowedDataLengthRule sut = new MaxAllowedDataLengthRule(5);
        FakeModel data = new FakeModel("  12345 ");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }
}