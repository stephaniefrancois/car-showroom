package core.validation.rules;

import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;
import testing.helpers.MinRequiredFakeModelValueRule;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MinRequiredValueRuleTest {
    @Test
    public void GivenValueFieldHasThatMeetsMinimumRequirementWhenValidatingThenRuleShouldPass() {
        // Given
        MinRequiredFakeModelValueRule sut = new MinRequiredFakeModelValueRule(new BigDecimal(0));
        FakeModel data = new FakeModel(new BigDecimal(1));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        MinRequiredFakeModelValueRule sut = new MinRequiredFakeModelValueRule(new BigDecimal(0));
        BigDecimal value = null;
        FakeModel data = new FakeModel(value);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasValueThatsLessThanRequiredWhenValidatingThenRuleShouldFail() {
        // Given
        MinRequiredFakeModelValueRule sut = new MinRequiredFakeModelValueRule(new BigDecimal(0));
        FakeModel data = new FakeModel(new BigDecimal(-1));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}