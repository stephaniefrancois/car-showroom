package app.cars.listing;

import app.common.listing.ItemsListPanel;
import app.objectComposition.ServiceLocator;
import core.stock.CarStock;
import core.stock.model.Car;

import java.util.List;

public final class CarsListPanel extends ItemsListPanel<Car> {
    private final CarStock carStock;

    public CarsListPanel() {
        super(new CarTableModel(),
                "Available cars:",
                "No cars available in the showroom!");

        // TODO: pass the data from the main controller or app bootstrapper service ???
        this.carStock = ServiceLocator
                .getComposer()
                .getCarStockService();
        this.refresh();
    }

    @Override
    protected List<Car> getAllItems() {
        return this.carStock.getAvailableCars();
    }

    @Override
    protected String getMessageForItemDeleteDialog(Car item) {
        return String.format("Do you really want to delete '%s %s' ?", item.getMake(), item.getModel());
    }

    @Override
    protected void removeItem(Car itemToDelete) {
        this.carStock.removeCar(itemToDelete.getId());
    }

    @Override
    protected List<Car> findItems(String searchCriteria) {
        return this.carStock.find(searchCriteria);
    }
}
