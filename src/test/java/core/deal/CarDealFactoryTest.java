package core.deal;

import core.domain.car.CarProperties;
import core.domain.deal.*;
import core.domain.validation.ValidationError;
import core.domain.validation.ValidationException;
import core.domain.validation.ValidationSummary;
import core.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import testing.helpers.TestData.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public final class CarDealFactoryTest {

    @Test
    public void GivenDealFactoryWhenUpdatingExistingDealThenAllDealFactoryPropertiesShouldBeSetCorrectly() {

        // Given
        CarDealProperties deal = Deals.getDeal();
        Validator<CarDealProperties> validatorMock = Mockito.mock(Validator.class);
        PaymentScheduleCalculator calculatorMock = Mockito.mock(PaymentScheduleCalculator.class);

        // When
        CarDealFactory sut = new CarDealFactory(deal, validatorMock, calculatorMock);

        // Then
        assertThat(sut.getCar(), equalTo(deal.getCar()));
        assertThat(sut.getCustomer(), equalTo(deal.getCustomer()));
        assertThat(sut.getDealDate(), equalTo(deal.getDealDate()));
        assertThat(sut.getPaymentOptions(), equalTo(deal.getPaymentOptions()));
        assertThat(sut.getSalesRepresentative(), equalTo(deal.getSalesRepresentative()));
    }

    @Test
    public void GivenCorrectlySetPropertiesWhenWeBuildDealWeShouldHaveDealPropertiesSet() throws ValidationException {
        // Given
        CarProperties car = Cars.getCar();
        PaymentOptions paymentOptions = PaymentOptionsData.getPaymentOptions();
        SalesRepresentative salesMan = SalesPeople.getSalesMan();
        Date dealDate = new Date(2017, 1, 1);
        Customer customer = Customers.getCustomer();

        Validator<CarDealProperties> validatorMock = Mockito.mock(Validator.class);
        PaymentScheduleCalculator calculatorMock = Mockito.mock(PaymentScheduleCalculator.class);
        CarDealFactory sut = new CarDealFactory(validatorMock, calculatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.setCar(car);
        sut.setCustomer(customer);
        sut.setDealDate(dealDate);
        sut.setPaymentOptions(paymentOptions);
        sut.setSalesRepresentative(salesMan);

        CarDealProperties deal = sut.build();

        // Then
        assertThat(deal.getCar(), equalTo(car));
        assertThat(deal.getPaymentOptions(), equalTo(paymentOptions));
        assertThat(deal.getSalesRepresentative(), equalTo(salesMan));
        assertThat(deal.getDealDate(), equalTo(dealDate));
        assertThat(deal.getCustomer(), equalTo(customer));
    }

    @Test
    public void GivenWeHaveMissingDataWhenBuildingDealThenValidationErrorsShouldBeReturned() {
        // Given
        Validator<CarDealProperties> validatorMock = Mockito.mock(Validator.class);
        PaymentScheduleCalculator calculatorMock = Mockito.mock(PaymentScheduleCalculator.class);
        CarDealFactory sut = new CarDealFactory(validatorMock, calculatorMock);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("salesman", "Sales Representative must be supplied!"));
        errors.add(new ValidationError("price", "Valid Price must be supplied!"));
        ValidationSummary summary = new ValidationSummary(errors);

        when(validatorMock.validate(any())).thenReturn(summary);

        // When
        ValidationSummary result = sut.validate();

        // Then
        assertThat(result.getIsValid(), equalTo(false));
        assertThat(result.getValidationErrors(), hasSize(2));
    }

    @Test
    public void GivenInvalidDealSetupWhenWeBuildDealThenExeptionShouldBeThrown() {
        // Given
        Validator<CarDealProperties> validatorMock = Mockito.mock(Validator.class);
        PaymentScheduleCalculator calculatorMock = Mockito.mock(PaymentScheduleCalculator.class);
        CarDealFactory sut = new CarDealFactory(validatorMock, calculatorMock);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("salesman", "Sales Representative must be supplied!"));
        errors.add(new ValidationError("price", "Valid Price must be supplied!"));
        ValidationSummary summary = new ValidationSummary(errors);

        when(validatorMock.validate(any())).thenReturn(summary);

        // When
        Exception thrown = assertThrows(ValidationException.class, sut::build);

        // Then
        assertThat(thrown.getMessage(), containsString("salesman"));
        assertThat(thrown.getMessage(), containsString("price"));
    }

    @Test
    public void GivenCorrectPaymentOptionsWhenBuildingCarDealThenCalculatePaymentSchedule() throws ValidationException {
        // Given
        Validator<CarDealProperties> validatorMock = Mockito.mock(Validator.class);
        PaymentScheduleCalculator calculatorMock = Mockito.mock(PaymentScheduleCalculator.class);
        PaymentSchedule schedule = PaymentSchedules.getSchedule();
        PaymentOptions paymentOptions = PaymentOptionsData.getPaymentOptions();
        CarProperties car = Cars.getCar();
        BigDecimal totalAmountToPay = car.getPrice();

        CarDealFactory sut = new CarDealFactory(validatorMock, calculatorMock);

        sut.setPaymentOptions(paymentOptions);
        sut.setCar(car);

        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());
        when(calculatorMock.calculatePaymentSchedule(totalAmountToPay,
                paymentOptions)).thenReturn(schedule);

        // When
        CarDeal deal = sut.build();

        // Then
        assertThat(deal.getPaymentSchedule(), equalTo(schedule));
    }
}