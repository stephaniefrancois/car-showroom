package core.stock;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.domain.car.*;
import core.domain.car.conditions.NewCar;
import dataAccessLayer.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public final class ShowroomTest {
    @Test
    public void GivenWeHaveAvailableCarsWhenWeRequestThemThenListOfCarsShouldBeReturned() {
        // Given
        List<Car> cars = new ArrayList<>();
        cars.add(new CarDescription(10,
                "MB",
                "S600",
                2017,
                new FuelType("Gasoline"),
                new Transmission("Automatic"),
                new NewCar(),
                new BigDecimal(300000)));

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        when(carRepositoryMock.getCars()).thenReturn(cars);

        // When
        List<Car> result = sut.getAvailableCars();

        // Then
        assertThat(result, hasSize(1));
        assertThat(result.get(0).getCarId(), equalTo(10));
    }

    @Test
    public void GivenWeDecideToRemoveCarWhenWeGiveCarIdThenCarShouldBeRemoved() {
        // Given
        int carId = 10;
        CarDetails car = new CarDetails(10,
                "MB",
                "S600",
                2017,
                "Black",
                new FuelType("Petrol"),
                new BodyStyle("Sedan"),
                new Transmission("Automatic"),
                4,
                new BigDecimal(500000),
                100,
                new ArrayList<>());

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        when(carRepositoryMock.getCar(carId)).thenReturn(car);

        // When
        sut.removeCar(carId);

        // Then
        verify(carRepositoryMock, times(1)).removeCar(car);
    }

    @Test
    public void GivenWeDecideToRemoveCarWhenCarDoesntExistThenWeShouldNotDoAnything() {
        // Given
        int carId = 10;
        int carToRemoveId = 20;
        CarDescription car = new CarDescription(carId,
                "MB",
                "S600",
                2017,
                new FuelType("Gasoline"),
                new Transmission("Automatic"),
                new NewCar(),
                new BigDecimal(300000));
        List<Car> cars = new ArrayList<>();
        cars.add(car);

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        when(carRepositoryMock.getCars()).thenReturn(cars);

        // When
        sut.removeCar(carToRemoveId);

        // Then
        verify(carRepositoryMock, never()).removeCar(any());
    }

    @Test
    public void GivenNewCarWhenItsAddedThenItShouldBePersisted() throws InvalidArgumentException {
        // Given
        CarDetails car = new CarDetails(
                "MB",
                "S600",
                2017,
                "Black",
                new FuelType("Petrol"),
                new BodyStyle("Sedan"),
                new Transmission("Automatic"),
                4,
                new BigDecimal(500000),
                100,
                new ArrayList<>());

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        // When
        sut.addCar(car);

        // Then
        verify(carRepositoryMock, times(1)).saveCar(car);
    }

    @Test
    public void GivenNullCarObjectWhenItsAddedThenWeShouldThrow() {
        // Given
        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        // When
        // Then
        assertThrows(InvalidArgumentException.class, () -> sut.addCar(null));
    }

    @Test
    public void GivenCarToUpdateWhenItsUpdatedThenItShouldBePersisted() throws InvalidArgumentException, UnableToUpdateCarException {
        // Given
        CarDetails car = new CarDetails(10,
                "MB",
                "S600",
                2017,
                "Black",
                new FuelType("Petrol"),
                new BodyStyle("Sedan"),
                new Transmission("Automatic"),
                4,
                new BigDecimal(500000),
                100,
                new ArrayList<>());

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        // When
        sut.updateCar(car);

        // Then
        verify(carRepositoryMock, times(1)).saveCar(car);
    }

    @Test
    public void GivenNullCarObjectWhenItsUpdatedThenWeShouldThrow() {
        // Given
        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        // When
        // Then
        assertThrows(InvalidArgumentException.class, () -> sut.updateCar(null));
    }

    @Test
    public void GivenCarObjectWithoutIdWhenItsUpdatedThenWeShouldThrow() {
        // Given
        CarDetails car = new CarDetails(
                "MB",
                "S600",
                2017,
                "Black",
                new FuelType("Petrol"),
                new BodyStyle("Sedan"),
                new Transmission("Automatic"),
                4,
                new BigDecimal(500000),
                100,
                new ArrayList<>());

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        // When
        // Then
        assertThrows(UnableToUpdateCarException.class, () -> sut.updateCar(car));
    }
}