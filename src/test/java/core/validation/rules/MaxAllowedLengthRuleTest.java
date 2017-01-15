package core.validation.rules;

import core.validation.RuleFor;
import core.validation.ValidationRule;
import core.validation.model.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MaxAllowedLengthRuleTest {

    @Test
    public void GivenDataFieldHasValueOfAllowedLengthWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.maxLength(5, "data", FakeModel::getData);

        FakeModel data = new FakeModel("12345");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.maxLength(5, "data", FakeModel::getData);

        FakeModel data = new FakeModel((String) null);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldHasTooLongValueWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.maxLength(5, "data", FakeModel::getData);

        FakeModel data = new FakeModel("123456");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }

    @Test
    public void GivenDataFieldHasValueStartingOrEndingWithSpacesExceedingAllowedLengthWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.maxLength(5, "data", FakeModel::getData);

        FakeModel data = new FakeModel("  12345 ");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }
}