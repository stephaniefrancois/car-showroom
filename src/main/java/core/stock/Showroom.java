package core.stock;

import core.domain.car.Car;
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
}
