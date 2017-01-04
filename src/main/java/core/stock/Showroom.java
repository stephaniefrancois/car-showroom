package core.stock;

import core.domain.car.Car;
import core.domain.car.CarProperties;
import core.domain.car.UnableToUpdateCarException;
import dataAccessLayer.CarRepository;

import java.util.List;
import java.util.Objects;

public final class Showroom {

    private final CarRepository carRepository;

    public Showroom(CarRepository carRepository) {
        Objects.requireNonNull(carRepository,
                "'carRepository' must be supplied!");

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

    public void addCar(CarProperties car) {
        Objects.requireNonNull(car,
                "'car' must be supplied!");
        carRepository.saveCar(car);
    }

    public void updateCar(CarProperties car) throws UnableToUpdateCarException {
        Objects.requireNonNull(car,
                "'car' must be supplied!");

        if (car.getCarId() <= 0) {
            throw new UnableToUpdateCarException(car.getCarId());
        }
        carRepository.saveCar(car);
    }
}
