package app.objectComposition;

import core.customer.CustomerFactory;
import core.customer.CustomerFactoryProvider;
import core.customer.validation.InMemoryCustomerValidationRulesProvider;
import core.customer.validation.RuleBasedCustomerValidator;
import core.domain.car.CarProperties;
import core.domain.deal.CustomerProperties;
import core.stock.CarFactory;
import core.stock.CarFactoryProvider;
import core.stock.CarStock;
import core.stock.Showroom;
import core.stock.validation.InMemoryCarValidationRulesProvider;
import core.stock.validation.RuleBasedCarValidator;
import core.validation.RuleBasedValidator;
import core.validation.TreeLikeValidationErrorsFormatter;
import core.validation.ValidationErrorsFormatter;
import core.validation.ValidationRulesProvider;
import data.*;

public final class ComponentsComposer {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public ComponentsComposer() {
        carRepository = new InMemoryCarRepository();
        customerRepository = new InMemoryCustomerRepository();
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

    public ValidationErrorsFormatter getValidationErrorsFormatter() {
        return new TreeLikeValidationErrorsFormatter();
    }

    public CustomerRepository getCustomerRepository() {
        return this.customerRepository;
    }

    public CustomerFactory getCustomerFactory() {
        return createCustomerFactory(null);
    }

    public CustomerFactory getCustomerFactory(CustomerProperties customer) {
        return createCustomerFactory(customer);
    }

    private CustomerFactory createCustomerFactory(CustomerProperties customer) {
        ValidationRulesProvider<CustomerProperties> rulesProvider = new InMemoryCustomerValidationRulesProvider();
        RuleBasedValidator<CustomerProperties> validator = new RuleBasedCustomerValidator(rulesProvider);
        if (customer == null) {
            return new CustomerFactory(validator);
        }
        return new CustomerFactory(customer, validator);
    }

    public CustomerFactoryProvider getCustomerFactoryProvider() {
        return new ObjectComposerBasedCustomerFactoryProvider();
    }
}
