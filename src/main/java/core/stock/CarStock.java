package core.stock;

import core.domain.car.Car;
import core.domain.car.CarProperties;
import core.domain.car.UnableToUpdateCarException;

import java.util.List;

public interface CarStock {
    List<Car> getAvailableCars();

    List<Car> find(String searchCriteria);

    CarProperties getCarDetails(int carId);

    void removeCar(int carId);

    CarProperties addCar(CarProperties car);

    void updateCar(CarProperties car) throws UnableToUpdateCarException;
}
