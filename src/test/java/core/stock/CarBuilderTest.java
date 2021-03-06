package core.stock;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.stock.model.CarDetails;
import core.stock.model.CarFeature;
import core.stock.model.CarMetadata;
import core.validation.Validator;
import core.validation.model.ValidationError;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static testing.helpers.TestData.Cars;

public final class CarBuilderTest {

    @Test
    public void GivenCarFactoryWhenUpdatingExistingCarThenAllCarFactoryPropertiesShouldBeSetCorrectly() {
        // Given
        List<CarFeature> features = new ArrayList<>();
        features.add(new CarFeature(1, "Luxury Massage Seats"));

        CarDetails car = Cars.getCar(features);

        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);

        // When
        CarBuilder sut = new CarBuilder(car, validatorMock);

        // Then
        assertThat(sut.getId(), equalTo(car.getId()));
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
        CarFeature luxurySeats = new CarFeature(1, "Luxury Massage Seats");
        BigDecimal price = new BigDecimal(500000);

        CarMetadata fuelType = new CarMetadata(1, "Petrol");
        CarMetadata bodyStyle = new CarMetadata(1, "Sedan");
        CarMetadata transmission = new CarMetadata(1, "Automatic");

        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.setMake("MB");
        sut.setModel("S600");
        sut.setYear(2017);
        sut.setColor("Black");
        sut.setFuelType(fuelType);
        sut.setBodyStyle(bodyStyle);
        sut.setTransmission(transmission);
        sut.setNumberOfSeats(4);
        sut.setPrice(price);
        sut.setMileage(100);
        sut.addCarFeature(luxurySeats);


        CarDetails car = sut.build();

        // Then
        assertThat(car.getId(), equalTo(0));
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
        assertThat(car.getFeatures(), hasItem(luxurySeats));
    }

    @Test
    public void GivenWeHaveMissingDataWhenBuildingCarThenValidationErrorsShouldBeReturned() {
        // Given
        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(validatorMock);

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
        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(validatorMock);

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
    public void GivenAnyCarWhenAddingFeatureThenWeShouldBeAbleToRetrieveFeature() {
        // Given
        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        CarFeature featureToAdd = new CarFeature(1, "Feature 1");

        // When
        sut.addCarFeature(featureToAdd);

        // Then
        assertThat(sut.getFeatures(), hasItem(featureToAdd));
    }

    @Test
    public void GivenAnyCarWhenAddingFeatureButPassingNullThenNoFeatureWillBeAdded() {
        // Given
        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        // Then
        assertThrows(NullPointerException.class, () -> sut.addCarFeature(null));
    }

    @Test
    public void GivenAnyCarWhenAddingDuplicateFeatureThenOnlySingleInstanceOfFeatureShouldExist() {
        // Given
        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        CarFeature featureToAdd = new CarFeature(1, "Feature 1");
        CarFeature featureToAddDuplicate = new CarFeature(1,"Feature 1");

        // When
        sut.addCarFeature(featureToAdd);
        sut.addCarFeature(featureToAddDuplicate);

        // Then
        assertThat(sut.getFeatures(), hasSize(1));
    }

    @Test
    public void GivenAnyCarWhenRemovingFeatureThenFeatureShouldBeRemoved() {
        // Given
        CarFeature luxurySeatsFeature = new CarFeature(1,"Luxury Massage Seats");
        CarFeature cooledChampagne = new CarFeature(1,"Cooled Champagne");
        List<CarFeature> features = new ArrayList<>();
        features.add(luxurySeatsFeature);
        features.add(cooledChampagne);

        CarDetails car = Cars.getCar(features);

        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(car, validatorMock);
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
        CarFeature luxurySeatsFeature = new CarFeature(1,"Luxury Massage Seats");
        CarFeature cooledChampagne = new CarFeature(1,"Cooled Champagne");
        List<CarFeature> features = new ArrayList<>();
        features.add(luxurySeatsFeature);
        features.add(cooledChampagne);

        CarDetails car = Cars.getCar(features);

        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(car, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        // Then
        assertThrows(NullPointerException.class, () -> sut.removeCarFeature(null));
    }

    @Test
    public void GivenAnyCarWhenRemovingFeatureThatDoesntExistThenNoFeatureWillBeRemoved() {
        // Given
        CarFeature luxurySeatsFeature = new CarFeature(1,"Luxury Massage Seats");
        CarFeature cooledChampagne = new CarFeature(1,"Cooled Champagne");
        CarFeature manualWindows = new CarFeature(1,"Manual Windows");
        List<CarFeature> features = new ArrayList<>();
        features.add(luxurySeatsFeature);
        features.add(cooledChampagne);

        CarDetails car = Cars.getCar(features);

        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(car, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.removeCarFeature(manualWindows);

        // Then
        assertThat(sut.getFeatures(), hasSize(2));
    }

    @Test
    public void GivenCarWithFeaturesWhenRemoveAllFeaturesThenCarShouldHaveNoFeatures() {
        // Given
        CarFeature luxurySeatsFeature = new CarFeature(1,"Luxury Massage Seats");
        CarFeature cooledChampagne = new CarFeature(1,"Cooled Champagne");
        List<CarFeature> features = new ArrayList<>();
        features.add(luxurySeatsFeature);
        features.add(cooledChampagne);

        CarDetails car = Cars.getCar(features);

        Validator<CarDetails> validatorMock = Mockito.mock(Validator.class);
        CarBuilder sut = new CarBuilder(car, validatorMock);
        when(validatorMock.validate(any())).thenReturn(new ValidationSummary());

        // When
        sut.removeAllFeatures();

        // Then
        assertThat(sut.getFeatures(), empty());
    }
}