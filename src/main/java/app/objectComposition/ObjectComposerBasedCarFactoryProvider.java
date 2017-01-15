package app.objectComposition;

import core.domain.car.CarProperties;
import core.stock.CarFactory;
import core.ItemFactoryProvider;

public class ObjectComposerBasedCarFactoryProvider implements ItemFactoryProvider<CarProperties, CarFactory> {

    @Override
    public CarFactory createItemFactory() {
        return ServiceLocator.getComposer().getCarFactory();
    }

    @Override
    public CarFactory createItemFactory(CarProperties item) {
        return ServiceLocator.getComposer().getCarFactory(item);
    }
}
