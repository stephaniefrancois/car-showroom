package app.objectComposition;

import core.domain.car.CarProperties;
import core.stock.CarFactory;
import core.stock.CarFactoryProvider;

public class ObjectComposerBasedCarFactoryProvider implements CarFactoryProvider {

    @Override
    public CarFactory createCarFactory() {
        return ServiceLocator.getComposer().getCarFactory();
    }

    @Override
    public CarFactory createCarFactory(CarProperties car) {
        return ServiceLocator.getComposer().getCarFactory(car);
    }
}
