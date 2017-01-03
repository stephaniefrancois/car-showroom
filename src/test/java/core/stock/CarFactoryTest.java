package core.stock;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.domain.car.*;
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

        FuelType fuelType = new FuelType("Petrol");
        BodyStyle bodyStyle = new BodyStyle("Sedan");
        Transmission transmission = new Transmission("Automatic");
        BigDecimal price = new BigDecimal(500000);

        CarDetails car = Cars.createCar(features, fuelType, bodyStyle, transmission, price);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);

        // When
        CarFactory sut = new CarFactory(car, validatorMock);

        // Then
        assertThat(sut.getCarId(), equalTo(10));
        assertThat(sut.getMake(), equalTo("MB"));
        assertThat(sut.getModel(), equalTo("S600"));
        assertThat(sut.getYear(), equalTo(2017));
        assertThat(sut.getColor(), equalTo("Black"));
        assertThat(sut.getFuelType(), equalTo(fuelType));
        assertThat(sut.getBodyStyle(), equalTo(bodyStyle));
        assertThat(sut.getTransmission(), equalTo(transmission));
        assertThat(sut.getNumberOfSeats(), equalTo(4));
        assertThat(sut.getPrice(), equalTo(price));
        assertThat(sut.getMileage(), equalTo(100));
        assertThat(sut.getCondition().getDescription(), equalTo("USED"));
        assertThat(sut.getFeatures(), equalTo(features));
    }


    @Test
    public void GivenCorrectlySetPropertiesWhenWeBuildCarWeShouldHaveCarPropertiesSet() throws ValidationException {
        // Given
        List<CarFeature> features = new ArrayList<>();
        features.add(new CarFeature("Luxury Massage Seats"));

        FuelType fuelType = new FuelType("Petrol");
        BodyStyle bodyStyle = new BodyStyle("Sedan");
        Transmission transmission = new Transmission("Automatic");
        BigDecimal price = new BigDecimal(500000);

        CarDetails carToUpdate = Cars.createCar(features, fuelType, bodyStyle, transmission, price);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(carToUpdate, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        CarProperties car = sut.build();

        // Then
        assertThat(car.getCarId(), equalTo(10));
        assertThat(car.getMake(), equalTo("MB"));
        assertThat(car.getModel(), equalTo("S600"));
        assertThat(car.getYear(), equalTo(2017));
        assertThat(car.getColor(), equalTo("Black"));
        assertThat(car.getFuelType(), equalTo(fuelType));
        assertThat(car.getBodyStyle(), equalTo(bodyStyle));
        assertThat(car.getTransmission(), equalTo(transmission));
        assertThat(car.getNumberOfSeats(), equalTo(4));
        assertThat(car.getPrice(), equalTo(price));
        assertThat(car.getMileage(), equalTo(100));
        assertThat(car.getCondition().getDescription(), equalTo("USED"));
        assertThat(car.getFeatures(), equalTo(features));
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
    public void GivenInvalidCarSetupWhenWeBuildCarThenExeptionShouldBeThrown() {
        // Given
        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(validatorMock);

        List<ValidationError> errors = new ArrayList<>();
        errors.add(new ValidationError("make", "Car MAKE must be supplied!"));
        errors.add(new ValidationError("model", "Car MODEL must be supplied!"));
        ValidationSummary summary = new ValidationSummary(errors);

        when(validatorMock.validate(any())).thenReturn(summary);

        // When
        Exception thrown = assertThrows(ValidationException.class, () -> sut.build());

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

        CarDetails car = Cars.createCar(features);

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

        CarDetails car = Cars.createCar(features);

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

        CarDetails car = Cars.createCar(features);

        Validator<CarProperties> validatorMock = Mockito.mock(Validator.class);
        CarFactory sut = new CarFactory(car, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.removeCarFeature(manualWindows);

        // Then
        assertThat(sut.getFeatures(), hasSize(2));
    }
}