package core.validation.rules;

import core.domain.validation.ValidationSummary;
import core.validation.RuleFor;
import core.validation.ValidationRule;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MaxAllowedNumericValueRuleTest {
    @Test
    public void GivenValueFieldHasThatMeetsMaxAllowedRequirementWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut = RuleFor.maxValue(new BigDecimal(10),
                "value",
                FakeModel::getValue);

        FakeModel data = new FakeModel(new BigDecimal(10));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut = RuleFor.maxValue(new BigDecimal(10),
                "value",
                FakeModel::getValue);

        FakeModel data = new FakeModel((BigDecimal) null);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasValueThatIsMoreThanAllowedWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut = RuleFor.maxValue(new BigDecimal(10),
                "value",
                FakeModel::getValue);

        FakeModel data = new FakeModel(new BigDecimal(11));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}