package core.validation.rules;

import core.validation.RuleFor;
import core.validation.ValidationRule;
import core.validation.model.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MandatoryFieldRuleTest {

    @Test
    public void GivenDataFieldHasValueWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.mandatory("data", FakeModel::getData);

        FakeModel data = new FakeModel("data");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldIsNullWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.mandatory("data", FakeModel::getData);

        FakeModel data = new FakeModel((String) null);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}