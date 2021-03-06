package composition;

import app.sales.details.carDealSteps.InMemoryFourStepsCarDealWizard;
import app.sales.details.wizard.CarDealWizardStepsProvider;
import common.ApplicationSettingsBasedConnectionStringProvider;
import common.PreferencesBasedStore;
import common.SettingsStore;
import core.ItemFactoryProvider;
import core.authentication.*;
import core.authentication.model.AuthenticationContext;
import core.authentication.model.SimpleAuthenticationContext;
import core.authentication.model.UserIdentity;
import core.customer.CustomerBuilder;
import core.customer.model.Customer;
import core.customer.validation.InMemoryCustomerValidationRulesProvider;
import core.customer.validation.RuleBasedCustomerValidator;
import core.deal.CarDealBuilder;
import core.deal.PaymentScheduleCalculator;
import core.deal.SalesRepresentativeProvider;
import core.deal.SimplePaymentScheduleCalculator;
import core.deal.model.CarDealDetails;
import core.deal.validation.*;
import core.stock.CarBuilder;
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
import data.inMemory.InMemoryCarMetadataRepository;
import data.sql.MsSqlCarDealRepository;
import data.sql.MsSqlCarRepository;
import data.sql.MsSqlCustomerRepository;
import data.sql.MsSqlUserRepository;

public final class ComponentsComposer {

    private final SettingsStore settingsStore;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CarDealRepository carDealRepository;
    private final AuthenticationContext authenticationContext;

    public ComponentsComposer() {
        settingsStore = new PreferencesBasedStore();
        ConnectionStringProvider connectionStringProvider =
                new ApplicationSettingsBasedConnectionStringProvider(getSettingsStore());

//        carRepository = new InMemoryCarRepository();
//        customerRepository = new InMemoryCustomerRepository();
//        carDealRepository = new InMemoryCarDealRepository();
//        userRepository = new InMemoryUserRepository();

        carRepository = new MsSqlCarRepository(connectionStringProvider, getSettingsStore(), getCarMetadataRepository());
        customerRepository = new MsSqlCustomerRepository(connectionStringProvider, getSettingsStore());
        carDealRepository = new MsSqlCarDealRepository(connectionStringProvider, settingsStore, carRepository, customerRepository);
        userRepository = new MsSqlUserRepository(connectionStringProvider, getSettingsStore());

        authenticationContext = new SimpleAuthenticationContext();
    }

    public PasswordHasher getPasswordHasher() {
        return new MD5PasswordHasher();
    }

    public UserIdentity getUserIdentity() {
        return this.authenticationContext;
    }

    public UserAuthenticator getUserAuthenticator() {
        return new PasswordBasedUserAuthenticator(this.userRepository,
                getPasswordHasher(), this.authenticationContext);
    }

    public CarStock getCarStockService() {
        return new Showroom(carRepository);
    }

    public ItemFactoryProvider<CarDetails, CarBuilder> getCarFactoryProvider() {
        return new ObjectComposerBasedCarFactoryProvider();
    }

    public CarBuilder getCarFactory() {
        return createCarFactory(null);
    }

    public CarBuilder getCarFactory(CarDetails car) {
        return createCarFactory(car);
    }

    private CarBuilder createCarFactory(CarDetails car) {
        ValidationRulesProvider<CarDetails> rulesProvider = new InMemoryCarValidationRulesProvider();
        RuleBasedValidator<CarDetails> validator = new RuleBasedCarValidator(rulesProvider);
        if (car == null) {
            return new CarBuilder(validator);
        }
        return new CarBuilder(car, validator);
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

    public CustomerBuilder getCustomerFactory() {
        return createCustomerFactory(null);
    }

    public CustomerBuilder getCustomerFactory(Customer customer) {
        return createCustomerFactory(customer);
    }

    private CustomerBuilder createCustomerFactory(Customer customer) {
        ValidationRulesProvider<Customer> rulesProvider = new InMemoryCustomerValidationRulesProvider();
        RuleBasedValidator<Customer> validator = new RuleBasedCustomerValidator(rulesProvider);
        if (customer == null) {
            return new CustomerBuilder(validator);
        }
        return new CustomerBuilder(customer, validator);
    }

    public ItemFactoryProvider<Customer, CustomerBuilder> getCustomerFactoryProvider() {
        return new ObjectComposerBasedCustomerFactoryProvider();
    }

    public CarDealRepository getCarDealRepository() {
        return carDealRepository;
    }

    public CarDealBuilder getCarDealFactory(CarDealDetails carDeal) {
        return createCarDealFactory(carDeal);
    }

    public CarDealBuilder getCarDealFactory() {
        return createCarDealFactory(null);
    }

    private CarDealBuilder createCarDealFactory(CarDealDetails carDeal) {
        ValidationRulesProvider<CarDealDetails> rulesProvider = new InMemoryCarDealValidationRulesProvider();
        RuleBasedValidator<CarDealDetails> validator = new RuleBasedCarDealValidator(rulesProvider);
        PaymentScheduleCalculator scheduleCalculator = new SimplePaymentScheduleCalculator();

        if (carDeal == null) {
            return new CarDealBuilder(validator, scheduleCalculator);
        }
        return new CarDealBuilder(carDeal, validator, scheduleCalculator);
    }

    public ItemFactoryProvider<CarDealDetails, CarDealBuilder> getCarDealFactoryProvider() {
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

    public SettingsStore getSettingsStore() {
        return this.settingsStore;
    }
}
