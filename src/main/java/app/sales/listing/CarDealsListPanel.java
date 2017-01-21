package app.sales.listing;

import app.common.listing.ItemsListPanel;
import app.common.listing.ListOptions;
import composition.ServiceLocator;
import core.deal.model.CarDeal;
import data.CarDealRepository;

import java.util.List;

public final class CarDealsListPanel extends ItemsListPanel<CarDeal> {
    private final CarDealRepository carDealRepository;

    public CarDealsListPanel(ListOptions options) {
        super(new CarDealTableModel(),
                options);

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.carDealRepository = ServiceLocator
                .getComposer()
                .getCarDealRepository();
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
