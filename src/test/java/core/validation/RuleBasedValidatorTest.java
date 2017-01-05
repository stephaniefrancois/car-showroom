package core.validation;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import testing.helpers.FakeModel;
import testing.helpers.FakeRuleBasedModelValidator;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

public final class RuleBasedValidatorTest {

    @Test
    public void GivenCarWhenNoValidationRulesExistsShouldReturnPositiveValidationResult() {
        // Given
        ValidationRulesProvider<FakeModel> rulesProvider =
                Mockito.mock(ValidationRulesProvider.class);

        Validator sut = new FakeRuleBasedModelValidator(rulesProvider);

        when(rulesProvider.getValidationRules()).thenReturn(Collections.emptyList());

        // When
        ValidationSummary result = sut.validate(new FakeModel());

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenCarWhenAllValidationRulesPassShouldReturnPositiveValidationResult() {
        // Given
        FakeModel modelToValidate = new FakeModel();

        ValidationRule<FakeModel> passingRule1 = Mockito.mock(ValidationRule.class);
        ValidationRule<FakeModel> passingRule2 = Mockito.mock(ValidationRule.class);

        ValidationRulesProvider<FakeModel> rulesProvider =
                Mockito.mock(ValidationRulesProvider.class);

        Validator sut = new FakeRuleBasedModelValidator(rulesProvider);

        when(rulesProvider.getValidationRules()).thenReturn(Arrays.asList(passingRule1, passingRule2));
        when(passingRule1.validate(modelToValidate)).thenReturn(new ValidationSummary());
        when(passingRule2.validate(modelToValidate)).thenReturn(new ValidationSummary());

        // When
        ValidationSummary result = sut.validate(modelToValidate);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenCarWhenOneValidationRuleFailedThenValidationResultShouldContainErrorMessage() {
        // Given
        FakeModel modelToValidate = new FakeModel();

        ValidationRule<FakeModel> passingRule = Mockito.mock(ValidationRule.class);
        ValidationRule<FakeModel> failingRule = Mockito.mock(ValidationRule.class);

        when(passingRule.validate(modelToValidate)).thenReturn(new ValidationSummary());
        when(failingRule.validate(modelToValidate)).thenReturn(new ValidationSummary(
                Collections.singletonList(new ValidationError("field1", "error1"))
        ));

        ValidationRulesProvider<FakeModel> rulesProvider =
                Mockito.mock(ValidationRulesProvider.class);

        Validator sut = new FakeRuleBasedModelValidator(rulesProvider);

        when(rulesProvider.getValidationRules()).thenReturn(Arrays.asList(passingRule, failingRule));

        // When
        ValidationSummary result = sut.validate(modelToValidate);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
        assertThat(result.getValidationErrors(), hasSize(1));
        assertThat(result.getValidationErrors().get(0).getErrorMessage(),
                containsString("error1"));
    }

    @Test
    public void GivenCarWhenMultipleValidationRuleFailedThenValidationResultShouldContainAggregatedErrorMessages() {
        // Given
        FakeModel modelToValidate = new FakeModel();

        ValidationRule<FakeModel> passingRule = Mockito.mock(ValidationRule.class);
        ValidationRule<FakeModel> failingRule1 = Mockito.mock(ValidationRule.class);
        ValidationRule<FakeModel> failingRule2 = Mockito.mock(ValidationRule.class);

        when(passingRule.validate(modelToValidate)).thenReturn(new ValidationSummary());
        when(failingRule1.validate(modelToValidate)).thenReturn(
                new ValidationSummary(Collections.singletonList(
                        new ValidationError("field1", "error1")
                )));
        when(failingRule2.validate(modelToValidate)).thenReturn(
                new ValidationSummary(Arrays.asList(
                        new ValidationError("field2", "error2"),
                        new ValidationError("field2", "error3")
                )));

        ValidationRulesProvider<FakeModel> rulesProvider =
                Mockito.mock(ValidationRulesProvider.class);

        Validator sut = new FakeRuleBasedModelValidator(rulesProvider);

        when(rulesProvider.getValidationRules()).thenReturn(Arrays.asList(passingRule,
                failingRule1, failingRule2));

        // When
        ValidationSummary result = sut.validate(modelToValidate);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
        assertThat(result.getValidationErrors(), hasSize(3));
        assertThat(result.getValidationErrors().get(0).getErrorMessage(),
                containsString("error1"));
        assertThat(result.getValidationErrors().get(1).getErrorMessage(),
                containsString("error2"));
        assertThat(result.getValidationErrors().get(2).getErrorMessage(),
                containsString("error3"));
    }
}