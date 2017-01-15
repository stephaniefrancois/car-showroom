package core.stock;

import core.stock.model.Car;
import core.stock.model.CarDetails;
import core.stock.model.UnableToUpdateCarException;

import java.util.List;

public interface CarStock {
    List<Car> getAvailableCars();

    List<Car> find(String searchCriteria);

    CarDetails getCarDetails(int carId);

    void removeCar(int carId);

    CarDetails addCar(CarDetails car);

    void updateCar(CarDetails car) throws UnableToUpdateCarException;
}
