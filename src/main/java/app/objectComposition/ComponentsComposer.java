package app.objectComposition;

import core.domain.car.CarProperties;
import core.stock.CarFactory;
import core.stock.CarFactoryProvider;
import core.stock.CarStock;
import core.stock.Showroom;
import core.stock.validation.InMemoryCarValidationRulesProvider;
import core.stock.validation.RuleBasedCarValidator;
import core.validation.RuleBasedValidator;
import core.validation.ValidationRulesProvider;
import data.CarMetadataRepository;
import data.CarRepository;
import data.InMemoryCarMetadataRepository;
import data.InMemoryCarRepository;

public final class ComponentsComposer {

    private final CarRepository carRepository;

    public ComponentsComposer() {
        carRepository = new InMemoryCarRepository();
    }

    public CarStock getCarStockService() {
        return new Showroom(carRepository);
    }

    public CarFactoryProvider getCarFactoryProvider() {
        return new ObjectComposerBasedCarFactoryProvider();
    }

    public CarFactory getCarFactory() {
        return createCarFactory(null);
    }

    public CarFactory getCarFactory(CarProperties car) {
        return createCarFactory(car);
    }

    private CarFactory createCarFactory(CarProperties car) {
        ValidationRulesProvider<CarProperties> rulesProvider = new InMemoryCarValidationRulesProvider();
        RuleBasedValidator<CarProperties> validator = new RuleBasedCarValidator(rulesProvider);
        if (car == null) {
            return new CarFactory(validator);
        }
        return new CarFactory(car, validator);
    }

    public CarMetadataRepository getCarMetadataRepository() {
        return new InMemoryCarMetadataRepository();
    }
}
