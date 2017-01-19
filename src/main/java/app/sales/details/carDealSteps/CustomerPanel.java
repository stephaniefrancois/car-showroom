package app.sales.details.carDealSteps;

import app.common.BasicEventArgs;
import app.common.listing.ListEventListener;
import app.common.listing.ListOptions;
import app.common.listing.SearchableListPanel;
import app.common.search.SearchPanel;
import app.common.validation.ValidateAbleFieldDescriptor;
import app.customers.listing.CustomersListPanel;
import app.sales.details.wizard.CarDealWizardStep;
import composition.ServiceLocator;
import core.customer.model.Customer;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;
import core.deal.validation.RuleBasedCarDealValidator;
import core.validation.model.ValidationSummary;
import data.CustomerRepository;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class CustomerPanel extends CarDealWizardStep implements ListEventListener {
    private final SearchableListPanel<Customer> searchableCustomers;
    private final RuleBasedCarDealValidator validator;
    private final CustomerRepository customerRepository;
    private final CustomersListPanel customersPanel;
    private CarDealFactory carDealFactory;

    public CustomerPanel() {
        setLayout(new BorderLayout());

        this.customersPanel = new CustomersListPanel(
                ListOptions.ReadOnly("Customers:",
                        "No customers! Go get some! :)"));

        this.searchableCustomers = new SearchableListPanel(
                new SearchPanel(), customersPanel);
        add(this.searchableCustomers, BorderLayout.CENTER);
        this.validator = ServiceLocator.getComposer().getCustomerStepValidator();
        this.customerRepository = ServiceLocator.getComposer().getCustomerRepository();
        this.searchableCustomers.addListener(this);
    }

    @Override
    public String getTitle() {
        return "Customer";
    }

    @Override
    public ValidationSummary validateStep() {
        return this.validator.validate(CarDealDetails.of(this.carDealFactory));
    }

    @Override
    public void setCarDeal(CarDealFactory carDealFactory) {
        this.clear();
        this.carDealFactory = carDealFactory;
        if (carDealFactory.getCustomer() != null) {
            this.searchableCustomers.selectItemById(carDealFactory.getCustomer().getId());
        }
    }

    @Override
    public CarDealFactory getCarDeal() {
        return this.carDealFactory;
    }

    @Override
    public void clear() {
        this.customersPanel.clearSelection();
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
        Customer customer = this.customerRepository.getCustomer(e.getId());
        this.carDealFactory.setCustomer(customer);
    }

    @Override
    public void itemCreationRequested() {

    }
}
