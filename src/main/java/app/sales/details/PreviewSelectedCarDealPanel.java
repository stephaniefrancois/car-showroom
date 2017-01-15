package app.sales.details;

import app.common.ControlsHelper;
import app.common.details.PreviewSelectedItemPanel;
import app.objectComposition.ServiceLocator;
import app.styles.BorderStyles;
import core.deal.model.CarDealDetails;
import data.CarDealRepository;

import javax.swing.*;
import java.awt.*;

public class PreviewSelectedCarDealPanel extends PreviewSelectedItemPanel<CarDealDetails> {

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

    private final JButton editCarDealBtn;
    private final CarDealRepository carDealRepository;
    private final Insets controlsPadding;

    public PreviewSelectedCarDealPanel() {
        setLayout(new GridBagLayout());

        this.carDealRepository = ServiceLocator.getComposer().getCarDealRepository();
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

        this.editCarDealBtn = new JButton("Edit deal ...");

        formGridConfig.fill = GridBagConstraints.HORIZONTAL;
        formGridConfig.gridy = 0;
        add(this.buildCustomerDataPanel(), formGridConfig);
        formGridConfig.gridy = 1;
        add(this.buildCarDataPanel(), formGridConfig);
        formGridConfig.gridy = 2;
        add(this.buildSalesDataPanel(), formGridConfig);
        formGridConfig.gridy = 3;
        add(this.buildPaymentDataPanel(), formGridConfig);

        this.scheduledPayments = buildPaymentsPanel();

        formGridConfig.gridy = 4;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        formGridConfig.gridx = 1;
        formGridConfig.anchor = GridBagConstraints.FIRST_LINE_START;
        formGridConfig.insets = new Insets(0, 0, 0, 0);
        add(editCarDealBtn, formGridConfig);

        editCarDealBtn.addActionListener(e -> {
            this.editItem(e.getSource());
        });
    }

    private JPanel buildCustomerDataPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderStyles.getTitleBorder("Customer:"));
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
        panel.setBorder(BorderStyles.getTitleBorder("Car:"));
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
        panel.setBorder(BorderStyles.getTitleBorder("Sales man:"));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gridConfig = new GridBagConstraints();
        gridConfig.fill = GridBagConstraints.NONE;
        ControlsHelper.addControlWithLabel(salesNameValueLabel, "Name:", 0, this.controlsPadding, gridConfig, panel);

        return panel;
    }

    private JPanel buildPaymentDataPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderStyles.getTitleBorder("Payment Options:"));
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
    protected CarDealDetails getItem(int id) {
        return this.carDealRepository.getDeal(id);
    }

    @Override
    protected void populateItemInformation(CarDealDetails item) {
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
}
