package core.validation;

import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import testing.helpers.FakeModel;
import testing.helpers.FakeRuleBasedModelValidator;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

public final class RuleBasedValidatorTest {

    @Test
    public void GivenCarWhenNoValidationRulesExistsShouldReturnPositiveValidationResult() {
        // Given
        Validator sut = new FakeRuleBasedModelValidator(new ArrayList<>());

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

        List<ValidationRule<FakeModel>> rules = new ArrayList<>();
        rules.add(passingRule1);
        rules.add(passingRule2);

        Validator sut = new FakeRuleBasedModelValidator(rules);

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

        List<ValidationRule<FakeModel>> rules = new ArrayList<>();
        rules.add(passingRule);
        rules.add(failingRule);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("field1", "error1"));

        when(passingRule.validate(modelToValidate)).thenReturn(new ValidationSummary());
        when(failingRule.validate(modelToValidate)).thenReturn(new ValidationSummary(errors));

        Validator sut = new FakeRuleBasedModelValidator(rules);

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

        List<ValidationRule<FakeModel>> rules = new ArrayList<>();
        rules.add(passingRule);
        rules.add(failingRule1);
        rules.add(failingRule2);

        List<ValidationError> errorsForRule1 = new ArrayList<>();
        errorsForRule1.add(new ValidationError("field1", "error1"));

        List<ValidationError> errorsForRule2 = new ArrayList<>();
        errorsForRule2.add(new ValidationError("field2", "error2"));
        errorsForRule2.add(new ValidationError("field2", "error3"));

        when(passingRule.validate(modelToValidate)).thenReturn(new ValidationSummary());
        when(failingRule1.validate(modelToValidate)).thenReturn(new ValidationSummary(errorsForRule1));
        when(failingRule2.validate(modelToValidate)).thenReturn(new ValidationSummary(errorsForRule2));

        Validator sut = new FakeRuleBasedModelValidator(rules);

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