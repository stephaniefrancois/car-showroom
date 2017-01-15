package app.objectComposition;

import core.ItemFactoryProvider;
import core.stock.CarFactory;
import core.stock.model.CarDetails;

public class ObjectComposerBasedCarFactoryProvider implements ItemFactoryProvider<CarDetails, CarFactory> {

    @Override
    public CarFactory createItemFactory() {
        return ServiceLocator.getComposer().getCarFactory();
    }

    @Override
    public CarFactory createItemFactory(CarDetails item) {
        return ServiceLocator.getComposer().getCarFactory(item);
    }
}
