package composition;

import core.ItemFactoryProvider;
import core.stock.CarBuilder;
import core.stock.model.CarDetails;

public class ObjectComposerBasedCarFactoryProvider implements ItemFactoryProvider<CarDetails, CarBuilder> {

    @Override
    public CarBuilder createItemFactory() {
        return ServiceLocator.getComposer().getCarFactory();
    }

    @Override
    public CarBuilder createItemFactory(CarDetails item) {
        return ServiceLocator.getComposer().getCarFactory(item);
    }
}
