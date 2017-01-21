package app.cars.listing;

import app.common.listing.ItemsListPanel;
import app.common.listing.ListOptions;
import composition.ServiceLocator;
import core.stock.CarStock;
import core.stock.model.Car;

import java.util.List;

public final class CarsListPanel extends ItemsListPanel<Car> {
    private final CarStock carStock;

    public CarsListPanel(ListOptions options) {
        super(new CarTableModel(), options);
        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.carStock = ServiceLocator
                .getComposer()
                .getCarStockService();
    }

    @Override
    protected final List<Car> getAllItems() {
        return this.carStock.getAvailableCars();
    }

    @Override
    protected final String getMessageForItemDeleteDialog(Car item) {
        return String.format("Do you really want to delete '%s %s' ?", item.getMake(), item.getModel());
    }

    @Override
    protected final void removeItem(Car itemToDelete) {
        this.carStock.removeCar(itemToDelete.getId());
    }

    @Override
    protected final List<Car> findItems(String searchCriteria) {
        return this.carStock.find(searchCriteria);
    }
}
