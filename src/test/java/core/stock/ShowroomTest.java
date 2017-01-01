package core.stock;

import core.domain.car.Car;
import core.domain.car.CarDescription;
import core.domain.car.FuelType;
import core.domain.car.Transmission;
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
import static org.mockito.Mockito.when;

public final class ShowroomTest {
    @Test
    public void GivenCorrectUserCredentialsShouldAuthenticateUser() {
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


}