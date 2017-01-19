package composition;

import app.sales.details.carDealSteps.InMemoryFourStepsCarDealWizard;
import app.sales.details.wizard.CarDealWizardStepsProvider;
import core.ItemFactoryProvider;
import core.authentication.AuthenticationContextBasedSalesRepresentativeProvider;
import core.authentication.PasswordBasedUserAuthenticator;
import core.authentication.UserAuthenticator;
import core.authentication.UserRepository;
import core.authentication.model.AuthenticationContext;
import core.authentication.model.SimpleAuthenticationContext;
import core.authentication.model.UserIdentity;
import core.customer.CustomerFactory;
import core.customer.model.Customer;
import core.customer.validation.InMemoryCustomerValidationRulesProvider;
import core.customer.validation.RuleBasedCustomerValidator;
import core.deal.CarDealFactory;
import core.deal.PaymentScheduleCalculator;
import core.deal.SalesRepresentativeProvider;
import core.deal.SimplePaymentScheduleCalculator;
import core.deal.model.CarDealDetails;
import core.deal.validation.*;
import core.stock.CarFactory;
import core.stock.CarStock;
import core.stock.Showroom;
import core.stock.model.CarDetails;
import core.stock.validation.InMemoryCarValidationRulesProvider;
import core.stock.validation.RuleBasedCarValidator;
import core.validation.RuleBasedValidator;
import core.validation.TreeLikeValidationErrorsFormatter;
import core.validation.ValidationErrorsFormatter;
import core.validation.ValidationRulesProvider;
import data.*;

public final class ComponentsComposer {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CarDealRepository carDealRepository;
    private final AuthenticationContext authenticationContext;

    public ComponentsComposer() {
        carRepository = new InMemoryCarRepository();
        customerRepository = new InMemoryCustomerRepository();
        carDealRepository = new InMemoryCarDealRepository();
        userRepository = new InMemoryUserRepository();
        authenticationContext = new SimpleAuthenticationContext();
    }

    public UserIdentity getUserIdentity() {
        return this.authenticationContext;
    }

    public UserAuthenticator getUserAuthenticator() {
        return new PasswordBasedUserAuthenticator(this.userRepository, this.authenticationContext);
    }

    public CarStock getCarStockService() {
        return new Showroom(carRepository);
    }

    public ItemFactoryProvider<CarDetails, CarFactory> getCarFactoryProvider() {
        return new ObjectComposerBasedCarFactoryProvider();
    }

    public CarFactory getCarFactory() {
        return createCarFactory(null);
    }

    public CarFactory getCarFactory(CarDetails car) {
        return createCarFactory(car);
    }

    private CarFactory createCarFactory(CarDetails car) {
        ValidationRulesProvider<CarDetails> rulesProvider = new InMemoryCarValidationRulesProvider();
        RuleBasedValidator<CarDetails> validator = new RuleBasedCarValidator(rulesProvider);
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

    public CustomerFactory getCustomerFactory(Customer customer) {
        return createCustomerFactory(customer);
    }

    private CustomerFactory createCustomerFactory(Customer customer) {
        ValidationRulesProvider<Customer> rulesProvider = new InMemoryCustomerValidationRulesProvider();
        RuleBasedValidator<Customer> validator = new RuleBasedCustomerValidator(rulesProvider);
        if (customer == null) {
            return new CustomerFactory(validator);
        }
        return new CustomerFactory(customer, validator);
    }

    public ItemFactoryProvider<Customer, CustomerFactory> getCustomerFactoryProvider() {
        return new ObjectComposerBasedCustomerFactoryProvider();
    }

    public CarDealRepository getCarDealRepository() {
        return carDealRepository;
    }

    public CarDealFactory getCarDealFactory(CarDealDetails carDeal) {
        return createCarDealFactory(carDeal);
    }

    public CarDealFactory getCarDealFactory() {
        return createCarDealFactory(null);
    }

    private CarDealFactory createCarDealFactory(CarDealDetails carDeal) {
        ValidationRulesProvider<CarDealDetails> rulesProvider = new InMemoryCarDealValidationRulesProvider();
        RuleBasedValidator<CarDealDetails> validator = new RuleBasedCarDealValidator(rulesProvider);
        PaymentScheduleCalculator scheduleCalculator = new SimplePaymentScheduleCalculator();

        if (carDeal == null) {
            return new CarDealFactory(validator, scheduleCalculator);
        }
        return new CarDealFactory(carDeal, validator, scheduleCalculator);
    }

    public ItemFactoryProvider<CarDealDetails, CarDealFactory> getCarDealFactoryProvider() {
        return new ObjectComposerBasedCarDealFactoryProvider();
    }

    public CarDealWizardStepsProvider getCarDealWizardStepsProvider() {
        return new InMemoryFourStepsCarDealWizard();
    }

    public RuleBasedCarDealValidator getCarStepValidator() {
        return new RuleBasedCarDealValidator(new InMemoryCarStepValidationRulesProvider());
    }

    public RuleBasedCarDealValidator getCustomerStepValidator() {
        return new RuleBasedCarDealValidator(new InMemoryCustomerStepValidationRulesProvider());
    }

    public RuleBasedPaymentOptionsValidator getPaymentOptionsStepValidator() {
        return new RuleBasedPaymentOptionsValidator(new InMemoryPaymentOptionsStepValidationRulesProvider());
    }

    public SalesRepresentativeProvider getSalesRepresentativeProvider() {
        return new AuthenticationContextBasedSalesRepresentativeProvider(
                this.getUserIdentity()
        );
    }
}
