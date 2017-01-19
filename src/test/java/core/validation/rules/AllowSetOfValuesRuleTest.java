package core.validation.rules;

import core.validation.RuleFor;
import core.validation.ValidationRule;
import core.validation.model.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class AllowSetOfValuesRuleTest {

    @Test
    public void GivenDataFieldHasValueWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.allowSetOfValues("data", Arrays.asList("value1", "value2"), FakeModel::getData);

        FakeModel data = new FakeModel("value1");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldIsNullWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.allowSetOfValues("data", Arrays.asList("value1", "value2"), FakeModel::getData);

        FakeModel data = new FakeModel((String) null);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldDontHaveAllowedValueWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.allowSetOfValues("data", Arrays.asList("value1", "value2"), FakeModel::getData);

        FakeModel data = new FakeModel("value3");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}