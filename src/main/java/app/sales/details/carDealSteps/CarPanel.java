package app.sales.details.carDealSteps;

import app.cars.listing.CarsListPanel;
import app.common.BasicEventArgs;
import app.common.listing.ListEventListener;
import app.common.listing.ListOptions;
import app.common.listing.SearchableListPanel;
import app.common.search.SearchPanel;
import app.common.validation.ValidateAbleFieldDescriptor;
import app.sales.details.wizard.CarDealWizardStep;
import composition.ServiceLocator;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;
import core.deal.validation.RuleBasedCarDealValidator;
import core.stock.CarStock;
import core.stock.model.Car;
import core.stock.model.CarDetails;
import core.validation.model.ValidationSummary;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class CarPanel extends CarDealWizardStep implements ListEventListener {
    private final SearchableListPanel<Car> searchableCars;
    private final RuleBasedCarDealValidator validator;
    private final CarStock carStock;
    private final CarsListPanel carsPanel;
    private CarDealFactory carDealFactory;

    public CarPanel() {
        setLayout(new BorderLayout());

        this.carsPanel = new CarsListPanel(
                ListOptions.ReadOnly("Our excellent cars:",
                        "All cars are SOLD :)"));

        this.searchableCars = new SearchableListPanel(
                new SearchPanel(), carsPanel);
        add(this.searchableCars, BorderLayout.CENTER);
        this.validator = ServiceLocator.getComposer().getCarStepValidator();
        this.carStock = ServiceLocator.getComposer().getCarStockService();
        this.searchableCars.addListener(this);
    }

    @Override
    public String getTitle() {
        return "Car Selection";
    }

    @Override
    public ValidationSummary validateStep() {
        return this.validator.validate(CarDealDetails.of(this.carDealFactory));
    }

    @Override
    public void setCarDeal(CarDealFactory carDealFactory) {
        this.clear();
        this.carDealFactory = carDealFactory;
        if (carDealFactory.getCar() != null) {
            this.searchableCars.selectItemById(carDealFactory.getCar().getId());
        }
    }

    @Override
    public CarDealFactory getCarDeal() {
        return this.carDealFactory;
    }

    @Override
    public void clear() {
        this.carsPanel.clearSelection();
    }

    @Override
    public Map<String, ValidateAbleFieldDescriptor> getFieldsMap() {
        return new HashMap<>();
    }

    @Override
    public void itemDeleted(BasicEventArgs e) {

    }

    @Override
    public void itemSelected(BasicEventArgs e) {
        CarDetails car = this.carStock.getCarDetails(e.getId());
        this.carDealFactory.setCar(car);
    }

    @Override
    public void itemCreationRequested() {

    }
}
