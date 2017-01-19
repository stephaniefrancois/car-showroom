package composition;

import core.ItemFactoryProvider;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;

public final class ObjectComposerBasedCarDealFactoryProvider implements ItemFactoryProvider<CarDealDetails, CarDealFactory> {

    @Override
    public CarDealFactory createItemFactory() {
        return ServiceLocator.getComposer().getCarDealFactory();
    }

    @Override
    public CarDealFactory createItemFactory(CarDealDetails item) {
        return ServiceLocator.getComposer().getCarDealFactory(item);
    }
}
