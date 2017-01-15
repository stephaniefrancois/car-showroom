package core.validation.rules;

import core.validation.RuleFor;
import core.validation.ValidationRule;
import core.validation.model.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MinRequiredNumericValueRuleTest {
    @Test
    public void GivenValueFieldHasThatMeetsMinimumRequirementWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut = RuleFor.minValue(new BigDecimal(0),
                "value",
                FakeModel::getValue);

        FakeModel data = new FakeModel(new BigDecimal(1));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut = RuleFor.minValue(new BigDecimal(0),
                "value",
                FakeModel::getValue);

        FakeModel data = new FakeModel((BigDecimal) null);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasValueThatIsLessThanRequiredWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut = RuleFor.minValue(new BigDecimal(0),
                "value",
                FakeModel::getValue);

        FakeModel data = new FakeModel(new BigDecimal(-1));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}