package core.customer;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.domain.deal.Customer;
import core.domain.deal.CustomerProperties;
import core.domain.validation.ValidationError;
import core.domain.validation.ValidationException;
import core.domain.validation.ValidationSummary;
import core.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static testing.helpers.TestData.Customers;

public final class CustomerFactoryTest {

    @Test
    public void GivenCustomerFactoryWhenUpdatingExistingCustomerThenAllCustomerFactoryPropertiesShouldBeSetCorrectly() {
        // Given
        Customer customer = Customers.getCustomer();

        Validator<CustomerProperties> validatorMock = Mockito.mock(Validator.class);

        // When
        CustomerFactory sut = new CustomerFactory(customer, validatorMock);

        // Then
        assertThat(sut.getId(), equalTo(customer.getId()));
        assertThat(sut.getFirstName(), equalTo(customer.getFirstName()));
        assertThat(sut.getLastName(), equalTo(customer.getLastName()));
        assertThat(sut.getCity(), equalTo(customer.getCity()));
        assertThat(sut.getCustomerSince(), equalTo(customer.getCustomerSince()));
    }

    @Test
    public void GivenCorrectlySetPropertiesWhenWeBuildCustomerWeShouldHaveCustomerPropertiesSet() throws ValidationException, InvalidArgumentException {
        // Given
        Validator<CustomerProperties> validatorMock = Mockito.mock(Validator.class);
        CustomerFactory sut = new CustomerFactory(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.setFirstName("Stephanie");
        sut.setLastName("Francois");
        sut.setCity("Trou aux biches");

        CustomerProperties customer = sut.build();

        // Then
        assertThat(customer.getId(), equalTo(0));
        assertThat(customer.getFirstName(), equalTo("Stephanie"));
        assertThat(customer.getLastName(), equalTo("Francois"));
        assertThat(customer.getCity(), equalTo("Trou aux biches"));
    }

    @Test
    public void GivenWeHaveMissingDataWhenBuildingCustomerThenValidationErrorsShouldBeReturned() {
        // Given
        Validator<CustomerProperties> validatorMock = Mockito.mock(Validator.class);
        CustomerFactory sut = new CustomerFactory(validatorMock);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("firstName", "Customer FIRSTNAME must be supplied!"));
        errors.add(new ValidationError("lastName", "Customer LASTNAME must be supplied!"));
        ValidationSummary summary = new ValidationSummary(errors);

        when(validatorMock.validate(any())).thenReturn(summary);

        // When
        ValidationSummary result = sut.validate();

        // Then
        assertThat(result.getIsValid(), equalTo(false));
        assertThat(result.getValidationErrors(), hasSize(2));
    }

    @Test
    public void GivenInvalidCustomerSetupWhenWeBuildCustomerThenExceptionShouldBeThrown() {
        // Given
        Validator<CustomerProperties> validatorMock = Mockito.mock(Validator.class);
        CustomerFactory sut = new CustomerFactory(validatorMock);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("firstName", "Customer FIRSTNAME must be supplied!"));
        errors.add(new ValidationError("lastName", "Customer LASTNAME must be supplied!"));
        ValidationSummary summary = new ValidationSummary(errors);

        when(validatorMock.validate(any())).thenReturn(summary);

        // When
        Exception thrown = assertThrows(ValidationException.class, sut::build);

        // Then
        assertThat(thrown.getMessage(), containsString("firstName"));
        assertThat(thrown.getMessage(), containsString("lastName"));
    }
}