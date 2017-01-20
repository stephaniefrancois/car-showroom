package app.sales.details.carDealSteps;

import app.RootLogger;
import app.common.ControlsHelper;
import app.common.validation.ValidateAbleFieldDescriptor;
import app.sales.details.ScheduledPaymentsPanel;
import app.sales.details.wizard.CarDealWizardStep;
import app.styles.BorderStyles;
import composition.ServiceLocator;
import core.authentication.model.NotAuthenticatedException;
import core.deal.CarDealFactory;
import core.deal.SalesRepresentativeProvider;
import core.deal.model.CarDealDetails;
import core.deal.model.SalesRepresentative;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DealReviewPanel extends CarDealWizardStep {
    private static final Logger log = RootLogger.get();

    private final GridBagConstraints formGridConfig;
    private final JLabel customerFirstNameValueLabel;
    private final JLabel customerLastNameValueLabel;
    private final JLabel customerCityValueLabel;

    private final JLabel makeValueLabel;
    private final JLabel modelValueLabel;
    private final JLabel yearValueLabel;
    private final JLabel colorValueLabel;
    private final JLabel priceValueLabel;

    private final JLabel salesNameValueLabel;

    private final JLabel paymentAdvanceAmountValueLabel;
    private final JLabel paymentFirstPaymentDateValueLabel;
    private final JLabel paymentDurationValueLabel;

    private final ScheduledPaymentsPanel scheduledPayments;

    private final Insets controlsPadding;
    private final SalesRepresentativeProvider salesRepresentativeProvider;

    private CarDealFactory carDealFactory;

    public DealReviewPanel() {
        setLayout(new GridBagLayout());

        this.salesRepresentativeProvider = ServiceLocator.getComposer().getSalesRepresentativeProvider();
        this.controlsPadding = new Insets(5, 0, 4, 5);

        this.formGridConfig = new GridBagConstraints();
        this.formGridConfig.fill = GridBagConstraints.NONE;

        this.customerFirstNameValueLabel = new JLabel();
        this.customerLastNameValueLabel = new JLabel();
        this.customerCityValueLabel = new JLabel();

        this.makeValueLabel = new JLabel();
        this.modelValueLabel = new JLabel();
        this.yearValueLabel = new JLabel();
        this.colorValueLabel = new JLabel();
        this.priceValueLabel = new JLabel();

        this.salesNameValueLabel = new JLabel();

        this.paymentAdvanceAmountValueLabel = new JLabel();
        this.paymentFirstPaymentDateValueLabel = new JLabel();
        this.paymentDurationValueLabel = new JLabel();

        formGridConfig.fill = GridBagConstraints.HORIZONTAL;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.gridy = 0;
        add(this.buildCustomerDataPanel(), formGridConfig);
        formGridConfig.gridy = 1;
        add(this.buildCarDataPanel(), formGridConfig);
        formGridConfig.gridy = 2;
        add(this.buildSalesDataPanel(), formGridConfig);
        formGridConfig.gridy = 3;
        add(this.buildPaymentDataPanel(), formGridConfig);
        formGridConfig.gridy = 4;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 50.0;
        add(new JPanel(), formGridConfig);
        this.scheduledPayments = buildPaymentsPanel();
    }

    @Override
    public String getTitle() {
        return "Deal Review";
    }

    @Override
    public ValidationSummary validateStep() {
        return this.carDealFactory.validate();
    }

    private JPanel buildCustomerDataPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderStyles.getTitleBorderNarrow("Customer:"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridConfig = new GridBagConstraints();
        gridConfig.fill = GridBagConstraints.NONE;
        ControlsHelper.addControlWithLabel(customerFirstNameValueLabel, "First Name:", 0, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(customerLastNameValueLabel, "Last Name:", 1, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(customerCityValueLabel, "City:", 2, this.controlsPadding, gridConfig, panel);

        return panel;
    }

    private JPanel buildCarDataPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderStyles.getTitleBorderNarrow("Car:"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridConfig = new GridBagConstraints();
        gridConfig.fill = GridBagConstraints.NONE;
        ControlsHelper.addControlWithLabel(makeValueLabel, "Make:", 0, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(modelValueLabel, "Model:", 1, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(yearValueLabel, "Year:", 2, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(colorValueLabel, "Color:", 3, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(priceValueLabel, "Price:", 4, this.controlsPadding, gridConfig, panel);

        return panel;
    }

    private JPanel buildSalesDataPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderStyles.getTitleBorderNarrow("Sales man:"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridConfig = new GridBagConstraints();
        gridConfig.fill = GridBagConstraints.NONE;
        ControlsHelper.addControlWithLabel(salesNameValueLabel, "Name:", 0, this.controlsPadding, gridConfig, panel);

        return panel;
    }

    private JPanel buildPaymentDataPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderStyles.getTitleBorderNarrow("Payment Options:"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridConfig = new GridBagConstraints();
        gridConfig.fill = GridBagConstraints.NONE;
        ControlsHelper.addControlWithLabel(paymentAdvanceAmountValueLabel, "Advance Amount:", 0, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(paymentFirstPaymentDateValueLabel, "First Payment Date:", 1, this.controlsPadding, gridConfig, panel);
        ControlsHelper.addControlWithLabel(paymentDurationValueLabel, "Duration in months:", 2, this.controlsPadding, gridConfig, panel);

        return panel;
    }

    private ScheduledPaymentsPanel buildPaymentsPanel() {
        ScheduledPaymentsPanel scheduledPaymentsPanel = new ScheduledPaymentsPanel();

        formGridConfig.gridy = 0;
        formGridConfig.gridx = 1;
        formGridConfig.weighty = 1;
        formGridConfig.insets = this.controlsPadding;
        formGridConfig.anchor = GridBagConstraints.NORTHWEST;
        formGridConfig.gridheight = 4;
        add(scheduledPaymentsPanel, formGridConfig);

        return scheduledPaymentsPanel;
    }

    @Override
    public CarDealFactory getCarDeal() {
        return this.carDealFactory;
    }

    @Override
    public void setCarDeal(CarDealFactory carDealFactory) {
        this.carDealFactory = carDealFactory;

        if (this.carDealFactory.getSalesRepresentative() == null) {
            try {
                SalesRepresentative salesRepresentative =
                        this.salesRepresentativeProvider.getActiveSalesRepresentative();
                this.carDealFactory.setSalesRepresentative(salesRepresentative);
            } catch (NotAuthenticatedException e) {
                logFailedToRetrieveSalesRepresentative(e);
            }
        }

        try {
            this.populateItemInformation(this.carDealFactory.build());
        } catch (ValidationException e) {
            logFailedToPopulateFormWithCarDeal(e);
        }
    }

    private void populateItemInformation(CarDealDetails item) {
        customerFirstNameValueLabel.setText(item.getCustomer().getFirstName());
        customerLastNameValueLabel.setText(item.getCustomer().getLastName());
        customerCityValueLabel.setText(item.getCustomer().getCity());

        makeValueLabel.setText(item.getCar().getMake());
        modelValueLabel.setText(item.getCar().getModel());
        yearValueLabel.setText(item.getCar().getYear().toString());
        colorValueLabel.setText(item.getCar().getColor());
        priceValueLabel.setText(item.getCar().getPrice().toString());

        salesNameValueLabel.setText(item.getSalesRepresentative().getFullName());

        paymentAdvanceAmountValueLabel.setText(Integer.toString(item.getPaymentOptions().getDeposit()));
        paymentFirstPaymentDateValueLabel.setText(item.getPaymentOptions().getFirstPaymentDay().toString());
        paymentDurationValueLabel.setText(Integer.toString(item.getPaymentOptions().getDurationInMonths()));

        scheduledPayments.setScheduledPayments(item.getPaymentSchedule());
    }

    @Override
    public void clear() {

    }

    @Override
    public Map<String, ValidateAbleFieldDescriptor> getFieldsMap() {
        return new HashMap<>();
    }

    private void logFailedToRetrieveSalesRepresentative(NotAuthenticatedException e) {
        log.log(Level.SEVERE, e, () -> "Can't find ACTIVE sales representative, user is NOT authenticated!");
    }

    private void logFailedToPopulateFormWithCarDeal(ValidationException e) {
        log.log(Level.SEVERE, e, () -> "Failed to populate CAR DEAL summary due to invalid CAR DEAL. " +
                "At this stage, CAR DEAL should have all required data.");
    }
}
