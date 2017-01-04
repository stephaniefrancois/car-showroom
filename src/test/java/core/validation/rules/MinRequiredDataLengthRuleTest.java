package core.validation.rules;

import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;
import testing.helpers.MinRequiredDataLengthRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MinRequiredDataLengthRuleTest {

    @Test
    public void GivenDataFieldHasValueOfRequiredLengthWhenValidatingThenRuleShouldPass() {
        // Given
        MinRequiredDataLengthRule sut = new MinRequiredDataLengthRule(5);
        FakeModel data = new FakeModel("12345");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        MinRequiredDataLengthRule sut = new MinRequiredDataLengthRule(5);
        String value = null;
        FakeModel data = new FakeModel(value);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldHasTooShortValueWhenValidatingThenRuleShouldFail() {
        // Given
        MinRequiredDataLengthRule sut = new MinRequiredDataLengthRule(5);
        FakeModel data = new FakeModel("1234");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }

    @Test
    public void GivenDataFieldHasEmptySpacesOfRequiredLengthWhenValidatingThenRuleShouldFail() {
        // Given
        MinRequiredDataLengthRule sut = new MinRequiredDataLengthRule(5);
        FakeModel data = new FakeModel("     ");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}