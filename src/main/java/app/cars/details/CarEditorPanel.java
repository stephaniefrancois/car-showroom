package app.cars.details;

import app.common.details.SimpleEditorPanel;
import app.common.validation.ValidationSummaryPanel;
import app.objectComposition.ServiceLocator;
import core.stock.CarFactory;
import core.stock.CarStock;
import core.stock.model.CarDetails;
import core.stock.model.UnableToUpdateCarException;

public final class CarEditorPanel extends SimpleEditorPanel<CarDetails, CarFactory> {
    private final CarStock carStock;

    public CarEditorPanel() {
        super(ServiceLocator.getComposer().getCarFactoryProvider(),
                new CarEditorInputsPanel(),
                new ValidationSummaryPanel()
                );
        this.carStock = ServiceLocator.getComposer().getCarStockService();
    }

    @Override
    protected CarDetails saveItem(CarDetails itemToSave) throws UnableToUpdateCarException {
        if (itemToSave.getId() == 0) {
            itemToSave = carStock.addCar(itemToSave);
        } else {
            carStock.updateCar(itemToSave);
        }
        return itemToSave;
    }

    @Override
    protected CarDetails getItem(int id) {
        return this.carStock.getCarDetails(id);
    }
}
