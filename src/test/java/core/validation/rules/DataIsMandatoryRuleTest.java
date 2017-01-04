package core.validation.rules;

import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import testing.helpers.DataIsMandatoryRule;
import testing.helpers.FakeModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class DataIsMandatoryRuleTest {

    @Test
    public void GivenDataFieldHasValueWhenValidatingThenRuleShouldPass() {
        // Given
        DataIsMandatoryRule sut = new DataIsMandatoryRule();
        FakeModel data = new FakeModel("data");
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenDataFieldIsNullWhenValidatingThenRuleShouldFail() {
        // Given
        DataIsMandatoryRule sut = new DataIsMandatoryRule();
        String value = null;
        FakeModel data = new FakeModel(value);
        // When
        ValidationSummary result = sut.validate(data);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
    }
}