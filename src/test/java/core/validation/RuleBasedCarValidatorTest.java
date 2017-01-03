package core.validation;

import core.TestData.Cars;
import core.domain.car.CarProperties;
import core.domain.validation.ValidationError;
import core.domain.validation.ValidationSummary;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

public final class RuleBasedCarValidatorTest {

    @Test
    public void GivenCarWhenNoValidationRulesExistsShouldReturnPositiveValidationResult() {
        // Given
        CarProperties car = Cars.createCar();
        CarValidator sut = new RuleBasedCarValidator(new ArrayList<>());

        // When
        ValidationSummary result = sut.validate(car);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenCarWhenAllValidationRulesPassShouldReturnPositiveValidationResult() {
        // Given
        CarProperties car = Cars.createCar();
        ValidationRule passingRule1 = Mockito.mock(ValidationRule.class);
        ValidationRule passingRule2 = Mockito.mock(ValidationRule.class);

        List<ValidationRule> rules = new ArrayList<>();
        rules.add(passingRule1);
        rules.add(passingRule2);

        CarValidator sut = new RuleBasedCarValidator(rules);

        when(passingRule1.validate(car)).thenReturn(new ValidationSummary());
        when(passingRule2.validate(car)).thenReturn(new ValidationSummary());

        // When
        ValidationSummary result = sut.validate(car);

        // Then
        assertThat(result.getIsValid(), equalTo(true));
    }

    @Test
    public void GivenCarWhenOneValidationRuleFailedThenValidationResultShouldContainErrorMessage() {
        // Given
        CarProperties car = Cars.createCar();

        ValidationRule passingRule = Mockito.mock(ValidationRule.class);
        ValidationRule failingRule = Mockito.mock(ValidationRule.class);

        List<ValidationRule> rules = new ArrayList<>();
        rules.add(passingRule);
        rules.add(failingRule);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("field1", "error1"));

        when(passingRule.validate(car)).thenReturn(new ValidationSummary());
        when(failingRule.validate(car)).thenReturn(new ValidationSummary(errors));

        CarValidator sut = new RuleBasedCarValidator(rules);

        // When
        ValidationSummary result = sut.validate(car);

        // Then
        assertThat(result.getIsValid(), equalTo(false));
        assertThat(result.getValidationErrors(), hasSize(1));
        assertThat(result.getValidationErrors().get(0).getErrorMessage(),
                containsString("error1"));
    }

    @Test
    public void GivenCarWhenMultipleValidationRuleFailedThenValidationResultShouldContainAggregatedErrorMessages() {
        // Given
        CarProperties car = Cars.createCar();

        ValidationRule passingRule = Mockito.mock(ValidationRule.class);
        ValidationRule failingRule1 = Mockito.mock(ValidationRule.class);
        ValidationRule failingRule2 = Mockito.mock(ValidationRule.class);

        List<ValidationRule> rules = new ArrayList<>();
        rules.add(passingRule);
        rules.add(failingRule1);
        rules.add(failingRule2);

        List<ValidationError> errorsForRule1 = new ArrayList<>();
        errorsForRule1.add(new ValidationError("field1", "error1"));

        List<ValidationError> errorsForRule2 = new ArrayList<>();
        errorsForRule2.add(new ValidationError("field2", "error2"));
        errorsForRule2.add(new ValidationError("field2", "error3"));

        when(passingRule.validate(car)).thenReturn(new ValidationSummary());
        when(failingRule1.validate(car)).thenReturn(new ValidationSummary(errorsForRule1));
        when(failingRule2.validate(car)).thenReturn(new ValidationSummary(errorsForRule2));

        CarValidator sut = new RuleBasedCarValidator(rules);

        // When
        ValidationSummary result = sut.validate(car);

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