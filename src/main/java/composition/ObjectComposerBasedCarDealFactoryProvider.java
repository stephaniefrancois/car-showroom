package composition;

import core.ItemFactoryProvider;
import core.deal.CarDealBuilder;
import core.deal.model.CarDealDetails;

public final class ObjectComposerBasedCarDealFactoryProvider implements ItemFactoryProvider<CarDealDetails, CarDealBuilder> {

    @Override
    public CarDealBuilder createItemFactory() {
        return ServiceLocator.getComposer().getCarDealFactory();
    }

    @Override
    public CarDealBuilder createItemFactory(CarDealDetails item) {
        return ServiceLocator.getComposer().getCarDealFactory(item);
    }
}
