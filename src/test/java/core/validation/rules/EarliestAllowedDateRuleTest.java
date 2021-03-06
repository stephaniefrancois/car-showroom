package core.validation.rules;

import core.validation.RuleFor;
import core.validation.ValidationRule;
import core.validation.model.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.FakeModel;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class EarliestAllowedDateRuleTest {
    @Test
    public void GivenValueFieldHasThatMeetsMinimumRequirementWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.earliestAllowedDate(
                        LocalDate.of(2017, Month.JANUARY, 10),
                        "date",
                        FakeModel::getDate);

        FakeModel data = new FakeModel(LocalDate.of(2017, Month.JANUARY, 10));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasNullValueWhenValidatingThenRuleShouldPass() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.earliestAllowedDate(
                        LocalDate.of(2017, Month.JANUARY, 10),
                        "date",
                        FakeModel::getDate);

        FakeModel data = new FakeModel((LocalDate) null);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenValueFieldHasValueThatIsLessThanRequiredWhenValidatingThenRuleShouldFail() {
        // Given
        ValidationRule<FakeModel> sut =
                RuleFor.earliestAllowedDate(
                        LocalDate.of(2017, Month.JANUARY, 10),
                        "date",
                        FakeModel::getDate);

        FakeModel data = new FakeModel(LocalDate.of(2017, Month.JANUARY, 9));
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}