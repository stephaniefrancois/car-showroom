package core.stock;

import core.domain.car.Car;
import core.domain.car.CarProperties;
import core.domain.car.UnableToUpdateCarException;
import dataAccessLayer.CarRepository;

import java.util.List;
import java.util.Objects;

public final class Showroom implements CarStock {

    private final CarRepository carRepository;

    public Showroom(CarRepository carRepository) {
        Objects.requireNonNull(carRepository,
                "'carRepository' must be supplied!");

        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAvailableCars() {
        return carRepository.getCars();
    }

    @Override
    public CarProperties getCarDetails(int carId) {
        return carRepository.getCar(carId);
    }

    @Override
    public void removeCar(int carId) {
        CarProperties car = carRepository.getCar(carId);
        if (car != null) {
            carRepository.removeCar(car.getCarId());
        } else {
            // TODO: log car not found
        }
    }

    @Override
    public CarProperties addCar(CarProperties car) {
        Objects.requireNonNull(car,
                "'car' must be supplied!");
        return carRepository.saveCar(car);
    }

    @Override
    public void updateCar(CarProperties car) throws UnableToUpdateCarException {
        Objects.requireNonNull(car,
                "'car' must be supplied!");

        if (car.getCarId() <= 0) {
            throw new UnableToUpdateCarException(car.getCarId());
        }
        carRepository.saveCar(car);
    }
}
