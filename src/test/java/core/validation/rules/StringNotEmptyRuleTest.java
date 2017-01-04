package core.validation.rules;

import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.DataIsNotEmptyRule;
import testing.helpers.FakeModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class StringNotEmptyRuleTest {

    @Test
    public void GivenDataFieldHasValueWhenValidatingThenRuleShouldPass() {
        // Given
        DataIsNotEmptyRule sut = new DataIsNotEmptyRule();
        FakeModel data = new FakeModel("data");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldIsNullWhenValidatingThenRuleShouldFail() {
        // Given
        DataIsNotEmptyRule sut = new DataIsNotEmptyRule();
        String value = null;
        FakeModel data = new FakeModel(value);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }

    @Test
    public void GivenDataFieldHasEmptyStringWhenValidatingThenRuleShouldFail() {
        // Given
        DataIsNotEmptyRule sut = new DataIsNotEmptyRule();
        FakeModel data = new FakeModel("");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }

    @Test
    public void GivenDataFieldHaveEmptySpacesWhenValidatingThenRuleShouldFail() {
        // Given
        DataIsNotEmptyRule sut = new DataIsNotEmptyRule();
        FakeModel data = new FakeModel("  ");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}