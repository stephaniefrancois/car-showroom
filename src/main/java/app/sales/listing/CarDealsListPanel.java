package app.sales.listing;

import app.common.listing.ItemsListPanel;
import app.objectComposition.ServiceLocator;
import core.deal.model.CarDeal;
import data.CarDealRepository;

import java.util.List;

public final class CarDealsListPanel extends ItemsListPanel<CarDeal> {
    private final CarDealRepository carDealRepository;

    public CarDealsListPanel() {
        super(new CarDealTableModel(),
                "Our deals:",
                "We have no deals!!! Go get some customers :)");

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.carDealRepository = ServiceLocator
                .getComposer()
                .getCarDealRepository();
        this.refresh();
    }

    @Override
    protected List<CarDeal> getAllItems() {
        return carDealRepository.getDeals();
    }

    @Override
    protected String getMessageForItemDeleteDialog(CarDeal item) {
        return String.format("Do you really want to delete '%s from %s' ?", item.getCarTitle(), item.getCustomerFullName());
    }

    @Override
    protected void removeItem(CarDeal itemToDelete) {
        this.carDealRepository.removeDeal(itemToDelete.getId());
    }

    @Override
    protected List<CarDeal> findItems(String searchCriteria) {
        return this.carDealRepository.findDeals(searchCriteria);
    }
}
