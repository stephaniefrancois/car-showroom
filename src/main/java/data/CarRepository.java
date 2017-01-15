package data;

import core.stock.model.Car;
import core.stock.model.CarDetails;

import java.util.List;

public interface CarRepository {
    List<Car> getCars();

    CarDetails getCar(int carId);

    CarDetails saveCar(CarDetails car);

    void removeCar(int carId);

    List<Car> findCars(String searchCriteria);
}
