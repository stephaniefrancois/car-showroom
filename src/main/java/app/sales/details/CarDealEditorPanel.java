package app.sales.details;

import app.common.details.EditorPanel;
import app.common.validation.ValidationSummaryPanel;
import app.objectComposition.ServiceLocator;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;
import core.stock.model.UnableToUpdateCarException;
import data.CarDealRepository;

public final class CarDealEditorPanel extends EditorPanel<CarDealDetails, CarDealFactory> {
    private final CarDealRepository carDetailsRepository;

    public CarDealEditorPanel() {
        super(ServiceLocator.getComposer().getCarDealFactoryProvider(),
                new CarDealEditorInputsPanel(),
                new ValidationSummaryPanel()
        );
        this.carDetailsRepository = ServiceLocator.getComposer().getCarDealRepository();
    }

    @Override
    protected CarDealDetails saveItem(CarDealDetails itemToSave) throws UnableToUpdateCarException {
        return carDetailsRepository.saveDeal(itemToSave);
    }

    @Override
    protected CarDealDetails getItem(int id) {
        return this.carDetailsRepository.getDeal(id);
    }
}
