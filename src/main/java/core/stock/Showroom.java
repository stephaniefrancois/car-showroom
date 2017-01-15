package core.stock;

import app.RootLogger;
import core.stock.model.Car;
import core.stock.model.CarDetails;
import core.stock.model.UnableToUpdateCarException;
import data.CarRepository;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class Showroom implements CarStock {

    private static final Logger log = RootLogger.get();
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
    public CarDetails getCarDetails(int carId) {
        return carRepository.getCar(carId);
    }

    @Override
    public void removeCar(int carId) {
        CarDetails car = carRepository.getCar(carId);
        if (car != null) {
            logDeletingCar(carId);
            carRepository.removeCar(car.getId());
        } else {
            logCantDeleteCar(carId);
        }
    }

    @Override
    public CarDetails addCar(CarDetails car) {
        Objects.requireNonNull(car,
                "'car' must be supplied!");
        return carRepository.saveCar(car);
    }

    @Override
    public void updateCar(CarDetails car) throws UnableToUpdateCarException {
        Objects.requireNonNull(car,
                "'car' must be supplied!");

        if (car.getId() <= 0) {
            throw new UnableToUpdateCarException(car.getId());
        }
        carRepository.saveCar(car);
    }

    @Override
    public List<Car> find(String searchCriteria) {
        return this.carRepository.findCars(searchCriteria);
    }

    private void logDeletingCar(int carId) {
        log.info(() -> String.format("Deleting car with id '%d' ...", carId));
    }

    private void logCantDeleteCar(int carId) {
        log.warning(() -> String.format("Car with id '%d' was NOT found and CAN'T be deleted!", carId));
    }
}
