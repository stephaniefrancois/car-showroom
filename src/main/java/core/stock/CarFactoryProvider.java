package core.stock;

import core.domain.car.CarProperties;

public interface CarFactoryProvider {
    CarFactory createCarFactory();

    CarFactory createCarFactory(CarProperties car);
}
