package core.validation.rules;

import core.domain.validation.ValidationSummary;
import core.validation.RuleFor;
import core.validation.ValidationRule;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MinRequiredLengthRuleTest {

    @Test
    public void GivenDataFieldHasValueOfRequiredLengthWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.minLength(5, "data", FakeModel::getData);

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
                RuleFor.minLength(5, "data", FakeModel::getData);

        FakeModel data = new FakeModel((String) null);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldHasTooShortValueWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.minLength(5, "data", FakeModel::getData);

        FakeModel data = new FakeModel("1234");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }

    @Test
    public void GivenDataFieldHasEmptySpacesOfRequiredLengthWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.minLength(5, "data", FakeModel::getData);

        FakeModel data = new FakeModel("     ");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}