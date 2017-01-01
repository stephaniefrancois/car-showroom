package dataAccessLayer;

import core.domain.car.Car;
import core.domain.car.CarProperties;

import java.util.List;

public interface CarRepository {
    List<Car> getCars();

    CarProperties getCar();

    void saveCar(CarProperties car);
}
