package core.stock;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.domain.car.CarDetails;
import core.domain.car.CarFeature;
import core.domain.car.CarProperties;
import core.domain.validation.ValidationError;
import core.domain.validation.ValidationException;
import core.domain.validation.ValidationSummary;
import core.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static testing.helpers.TestData.Cars;

public final class CarFactoryTest {

    @Test
    public void GivenCarFactoryWhenUpdatingExistingCarThenAllCarFactoryPropertiesShouldBeSetCorrectly() {
        // Given
        List<CarFeature> features = new ArrayList<>();
        features.add(new CarFeature("Luxury Massage Seats"));

        CarDetails car = Cars.getCar(features);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);

        // When
        CarFactory sut = new CarFactory(car, validatorMock);

        // Then
        assertThat(sut.getCarId(), equalTo(car.getCarId()));
        assertThat(sut.getMake(), equalTo(car.getMake()));
        assertThat(sut.getModel(), equalTo(car.getModel()));
        assertThat(sut.getYear(), equalTo(car.getYear()));
        assertThat(sut.getColor(), equalTo(car.getColor()));
        assertThat(sut.getFuelType(), equalTo(car.getFuelType()));
        assertThat(sut.getBodyStyle(), equalTo(car.getBodyStyle()));
        assertThat(sut.getTransmission(), equalTo(car.getTransmission()));
        assertThat(sut.getNumberOfSeats(), equalTo(car.getNumberOfSeats()));
        assertThat(sut.getPrice(), equalTo(car.getPrice()));
        assertThat(sut.getMileage(), equalTo(car.getMileage()));
        assertThat(sut.getCondition().getDescription(), equalTo("USED"));
        assertThat(sut.getFeatures(), equalTo(features));
    }

    @Test
    public void GivenCorrectlySetPropertiesWhenWeBuildCarWeShouldHaveCarPropertiesSet() throws ValidationException, InvalidArgumentException {
        // Given
        CarFeature luxurySeats = new CarFeature("Luxury Massage Seats");
        BigDecimal price = new BigDecimal(500000);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.setMake("MB");
        sut.setModel("S600");
        sut.setYear(2017);
        sut.setColor("Black");
        sut.setFuelType("Petrol");
        sut.setBodyStyle("Sedan");
        sut.setTransmission("Automatic");
        sut.setNumberOfSeats(4);
        sut.setPrice(price);
        sut.setMileage(100);
        sut.addCarFeature(luxurySeats);


        CarProperties car = sut.build();

        // Then
        assertThat(car.getCarId(), equalTo(0));
        assertThat(car.getMake(), equalTo("MB"));
        assertThat(car.getModel(), equalTo("S600"));
        assertThat(car.getYear(), equalTo(2017));
        assertThat(car.getColor(), equalTo("Black"));
        assertThat(car.getFuelType(), equalTo("Petrol"));
        assertThat(car.getBodyStyle(), equalTo("Sedan"));
        assertThat(car.getTransmission(), equalTo("Automatic"));
        assertThat(car.getNumberOfSeats(), equalTo(4));
        assertThat(car.getPrice(), equalTo(price));
        assertThat(car.getMileage(), equalTo(100));
        assertThat(car.getCondition().getDescription(), equalTo("USED"));
        assertThat(car.getFeatures(), hasItem(luxurySeats));
    }

    @Test
    public void GivenWeHaveMissingDataWhenBuildingCarThenValidationErrorsShouldBeReturned() {
        // Given
        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(validatorMock);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("make", "Car MAKE must be supplied!"));
        errors.add(new ValidationError("model", "Car MODEL must be supplied!"));
        ValidationSummary summary = new ValidationSummary(errors);

        when(validatorMock.validate(any())).thenReturn(summary);

        // When
        ValidationSummary result = sut.validate();

        // Then
        assertThat(result.getIsValid(), equalTo(false));
        assertThat(result.getValidationErrors(), hasSize(2));
    }

    @Test
    public void GivenInvalidCarSetupWhenWeBuildCarThenExceptionShouldBeThrown() {
        // Given
        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(validatorMock);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("make", "Car MAKE must be supplied!"));
        errors.add(new ValidationError("model", "Car MODEL must be supplied!"));
        ValidationSummary summary = new ValidationSummary(errors);

        when(validatorMock.validate(any())).thenReturn(summary);

        // When
        Exception thrown = assertThrows(ValidationException.class, sut::build);

        // Then
        assertThat(thrown.getMessage(), containsString("make"));
        assertThat(thrown.getMessage(), containsString("model"));
    }

    @Test
    public void GivenAnyCarWhenAddingFeatureThenWeShouldBeAbleToRetrieveFeature() throws InvalidArgumentException {
        // Given
        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        CarFeature featureToAdd = new CarFeature("Feature 1");

        // When
        sut.addCarFeature(featureToAdd);

        // Then
        assertThat(sut.getFeatures(), hasItem(featureToAdd));
    }

    @Test
    public void GivenAnyCarWhenAddingFeatureButPassingNullThenNoFeatureWillBeAdded() throws InvalidArgumentException {
        // Given
        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        // Then
        assertThrows(InvalidArgumentException.class, () -> sut.addCarFeature(null));
    }

    @Test
    public void GivenAnyCarWhenAddingDuplicateFeatureThenOnlySingleInstanceOfFeatureShouldExist() throws InvalidArgumentException {
        // Given
        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        CarFeature featureToAdd = new CarFeature("Feature 1");
        CarFeature featureToAddDuplicate = new CarFeature("Feature 1");

        // When
        sut.addCarFeature(featureToAdd);
        sut.addCarFeature(featureToAddDuplicate);

        // Then
        assertThat(sut.getFeatures(), hasSize(1));
    }

    @Test
    public void GivenAnyCarWhenRemovingFeatureThenFeatureShouldBeRemoved() throws InvalidArgumentException {
        // Given
        CarFeature luxurySeatsFeature = new CarFeature("Luxury Massage Seats");
        CarFeature cooledChampagne = new CarFeature("Cooled Champagne");
        List<CarFeature> features = new ArrayList<>();
        features.add(luxurySeatsFeature);
        features.add(cooledChampagne);

        CarDetails car = Cars.getCar(features);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(car, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.removeCarFeature(luxurySeatsFeature);

        // Then
        assertThat(sut.getFeatures(), hasSize(1));
        assertThat(sut.getFeatures(), hasItem(cooledChampagne));
    }

    @Test
    public void GivenAnyCarWhenRemovingFeatureButPassingNullThenNoFeatureWillBeRemoved() {
        // Given
        CarFeature luxurySeatsFeature = new CarFeature("Luxury Massage Seats");
        CarFeature cooledChampagne = new CarFeature("Cooled Champagne");
        List<CarFeature> features = new ArrayList<>();
        features.add(luxurySeatsFeature);
        features.add(cooledChampagne);

        CarDetails car = Cars.getCar(features);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(car, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        // Then
        assertThrows(InvalidArgumentException.class, () -> sut.removeCarFeature(null));
    }

    @Test
    public void GivenAnyCarWhenRemovingFeatureThatDoesntExistThenNoFeatureWillBeRemoved() throws InvalidArgumentException {
        // Given
        CarFeature luxurySeatsFeature = new CarFeature("Luxury Massage Seats");
        CarFeature cooledChampagne = new CarFeature("Cooled Champagne");
        CarFeature manualWindows = new CarFeature("Manual Windows");
        List<CarFeature> features = new ArrayList<>();
        features.add(luxurySeatsFeature);
        features.add(cooledChampagne);

        CarDetails car = Cars.getCar(features);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(car, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.removeCarFeature(manualWindows);

        // Then
        assertThat(sut.getFeatures(), hasSize(2));
    }
}