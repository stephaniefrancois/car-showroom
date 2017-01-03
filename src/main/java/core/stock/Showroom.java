package core.stock;

import com.sun.javaws.exceptions.InvalidArgumentException;
import core.domain.car.Car;
import core.domain.car.CarProperties;
import core.domain.car.UnableToUpdateCarException;
import dataAccessLayer.CarRepository;

import java.util.List;

public final class Showroom {

    private final CarRepository carRepository;

    public Showroom(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAvailableCars() {
        return carRepository.getCars();
    }

    public void removeCar(int carId) {
        CarProperties car = carRepository.getCar(carId);
        if (car != null) {
            carRepository.removeCar(car);
        } else {
            // TODO: log car not found
        }
    }

    public void addCar(CarProperties car) throws InvalidArgumentException {
        if (car == null) {
            throw new InvalidArgumentException(new String[]{"'car' must be supplied!"});
        }
        carRepository.saveCar(car);
    }

    public void updateCar(CarProperties car) throws InvalidArgumentException,
            UnableToUpdateCarException {
        if (car == null) {
            throw new InvalidArgumentException(new String[]{"'car' must be supplied!"});
        }
        if (car.getCarId() <= 0) {
            throw new UnableToUpdateCarException(car.getCarId());
        }
        carRepository.saveCar(car);
    }
}
