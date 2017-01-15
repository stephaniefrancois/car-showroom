package app.cars.details;

import app.common.details.EditorPanel;
import app.common.validation.ValidationSummaryPanel;
import app.objectComposition.ServiceLocator;
import core.domain.car.CarProperties;
import core.domain.car.UnableToUpdateCarException;
import core.stock.CarFactory;
import core.stock.CarStock;

public final class CarEditorPanel extends EditorPanel<CarProperties, CarFactory> {
    private final CarStock carStock;

    public CarEditorPanel() {
        super(ServiceLocator.getComposer().getCarFactoryProvider(),
                new CarEditorInputsPanel(),
                new ValidationSummaryPanel()
                );
        this.carStock = ServiceLocator.getComposer().getCarStockService();
    }

    @Override
    protected CarProperties saveItem(CarProperties itemToSave) throws UnableToUpdateCarException {
        if (itemToSave.getId() == 0) {
            itemToSave = carStock.addCar(itemToSave);
        } else {
            carStock.updateCar(itemToSave);
        }
        return itemToSave;
    }

    @Override
    protected CarProperties getItem(int id) {
        return this.carStock.getCarDetails(id);
    }
}
