package core.stock;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.stock.model.Car;
import core.stock.model.CarDetails;
import core.stock.model.UnableToUpdateCarException;
import data.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import testing.helpers.NullLogger;
import testing.helpers.TestData.Cars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public final class ShowroomTest {

    @BeforeAll
    public static void onceExecutedBeforeAll() {
        NullLogger.configure();
    }

    @Test
    public void GivenWeHaveAvailableCarsWhenWeRequestThemThenListOfCarsShouldBeReturned() {
        // Given
        int carId = 10;
        List<Car> cars = new ArrayList<>();
        Car car = Cars.getCar(carId);
        cars.add(car);

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        when(carRepositoryMock.getCars()).thenReturn(cars);

        // When
        List<Car> result = sut.getAvailableCars();

        // Then
        assertThat(result, hasSize(1));
        assertThat(result.get(0).getId(), equalTo(carId));
    }

    @Test
    public void GivenWeDecideToRemoveCarWhenWeGiveCarIdThenCarShouldBeRemoved() {
        // Given
        int carId = 10;
        CarDetails car = Cars.getCar(carId);

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        when(carRepositoryMock.getCar(carId)).thenReturn(car);

        // When
        sut.removeCar(carId);

        // Then
        verify(carRepositoryMock, times(1)).removeCar(car.getId());
    }

    @Test
    public void GivenWeDecideToRemoveCarWhenCarDoesNotExistThenWeShouldNotDoAnything() {
        // Given
        int carId = 10;
        int carToRemoveId = 20;
        Car car = Cars.getCar(carId);
        List<Car> cars = new ArrayList<>();
        cars.add(car);

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        when(carRepositoryMock.getCars()).thenReturn(cars);

        // When
        sut.removeCar(carToRemoveId);

        // Then
        verify(carRepositoryMock, never()).removeCar(anyInt());
    }

    @Test
    public void GivenNewCarWhenItsAddedThenItShouldBePersisted() throws InvalidArgumentException {
        // Given
        CarDetails car = Cars.getCar();

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
        assertThrows(NullPointerException.class, () -> sut.addCar(null));
    }

    @Test
    public void GivenCarToUpdateWhenItsUpdatedThenItShouldBePersisted() throws InvalidArgumentException, UnableToUpdateCarException {
        // Given
        CarDetails car = Cars.getCar(10);

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
        assertThrows(NullPointerException.class, () -> sut.updateCar(null));
    }

    @Test
    public void GivenCarObjectWithoutIdWhenItsUpdatedThenWeShouldThrow() {
        // Given
        CarDetails car = Cars.getCar();

        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);

        // When
        // Then
        assertThrows(UnableToUpdateCarException.class, () -> sut.updateCar(car));
    }

    @Test
    public void GivenCarSearchExecutedThenShouldFindCarFromRepository() {
        // Given
        String searchCriteria = "car1";
        List<Car> cars = Arrays.asList(Cars.getCar());
        CarRepository carRepositoryMock = Mockito.mock(CarRepository.class);
        Showroom sut = new Showroom(carRepositoryMock);
        when(carRepositoryMock.findCars(searchCriteria)).thenReturn(cars);

        // When
        List<Car> result = sut.find(searchCriteria);

        // Then
        assertThat(result, equalTo(cars));
    }
}