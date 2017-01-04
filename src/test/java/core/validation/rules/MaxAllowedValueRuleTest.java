package core.validation.rules;

import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;
import testing.helpers.MaxAllowedFakeModelValueRule;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MaxAllowedValueRuleTest {
    @Test
    public void GivenValueFieldHasThatMeetsMaxAllowedRequirementWhenValidatingThenRuleShouldPass() {
        // Given
        MaxAllowedFakeModelValueRule sut = new MaxAllowedFakeModelValueRule(new BigDecimal(10));
        FakeModel data = new FakeModel(new BigDecimal(10));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        MaxAllowedFakeModelValueRule sut = new MaxAllowedFakeModelValueRule(new BigDecimal(10));
        BigDecimal value = null;
        FakeModel data = new FakeModel(value);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasValueThatsMoreThanAllowedWhenValidatingThenRuleShouldFail() {
        // Given
        MaxAllowedFakeModelValueRule sut = new MaxAllowedFakeModelValueRule(new BigDecimal(10));
        FakeModel data = new FakeModel(new BigDecimal(11));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}