package dataAccessLayer;

import core.domain.car.Car;
import core.domain.car.CarProperties;

import java.util.List;

public interface CarRepository {
    List<Car> getCars();

    CarProperties getCar(int carId);

    CarProperties saveCar(CarProperties car);

    void removeCar(int carId);
}
