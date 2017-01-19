package app.sales.details.carDealSteps;

import app.common.validation.ValidateAbleFieldDescriptor;
import app.sales.details.wizard.CarDealWizardStep;
import app.styles.LabelStyles;
import common.NumberExtensions;
import composition.ServiceLocator;
import core.deal.CarDealFactory;
import core.deal.model.PaymentOptions;
import core.deal.validation.RuleBasedPaymentOptionsValidator;
import core.validation.model.ValidationSummary;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class PaymentDetailsPanel extends CarDealWizardStep {

    private final UtilDateModel dateModel;
    private final RuleBasedPaymentOptionsValidator validator;
    private final GridBagConstraints formGridConfig;
    private final JTextField depositField;
    private final JDatePickerImpl firstPaymentDatePicker;
    private final JTextField durationInMonthsField;
    private final Insets controlsPadding;
    private CarDealFactory carDealFactory;
    private Map<String, ValidateAbleFieldDescriptor> fieldsMap;

    public PaymentDetailsPanel() {
        setLayout(new GridBagLayout());

        this.validator = ServiceLocator.getComposer().getPaymentOptionsStepValidator();
        this.controlsPadding = new Insets(5, 0, 4, 5);
        this.fieldsMap = new HashMap<>();
        formGridConfig = new GridBagConstraints();
        formGridConfig.fill = GridBagConstraints.NONE;

        dateModel = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, new Properties());
        firstPaymentDatePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        depositField = new JTextField();
        durationInMonthsField = new JTextField();

        addControlWithLabel(durationInMonthsField, "Duration In Months", 0);
        addControlWithLabel(firstPaymentDatePicker, "First Payment", 1);
        addControlWithLabel(depositField, "Deposit", 2);

        formGridConfig.gridy = 10;
        formGridConfig.weightx = 1;
        formGridConfig.weighty = 2.0;

        JPanel placeholder = new JPanel();
        add(placeholder, formGridConfig);
    }

    @Override
    public String getTitle() {
        return "Payment details";
    }

    @Override
    public ValidationSummary validateStep() {
        this.carDealFactory.setPaymentOptions(this.getPaymentOptionsFromForm());

        return this.validator.validate(this.carDealFactory.getPaymentOptions());
    }

    @Override
    public CarDealFactory getCarDeal() {
        this.carDealFactory.setPaymentOptions(this.getPaymentOptionsFromForm());
        return this.carDealFactory;
    }

    @Override
    public void setCarDeal(CarDealFactory carDealFactory) {
        this.carDealFactory = carDealFactory;
        this.clear();
        if (carDealFactory.getPaymentOptions() != null) {
            PaymentOptions payment = carDealFactory.getPaymentOptions();
            LocalDate paymentDate = payment.getFirstPaymentDay();
            this.dateModel.setDate(paymentDate.getYear(), paymentDate.getMonthValue(), paymentDate.getDayOfMonth());
            this.dateModel.setSelected(true);
            this.depositField.setText(String.valueOf(payment.getDeposit()));
            this.durationInMonthsField.setText(String.valueOf(payment.getDurationInMonths()));
        }
    }

    private PaymentOptions getPaymentOptionsFromForm() {
        Integer durationInMonths = NumberExtensions.tryParseNumber(this.durationInMonthsField.getText(), (Integer) null);
        Date date = (Date) this.firstPaymentDatePicker.getModel().getValue();
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDate firstPaymentDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        Integer deposit = NumberExtensions.tryParseNumber(this.depositField.getText(), (Integer) null);

        return new PaymentOptions(durationInMonths,
                firstPaymentDate,
                deposit
        );
    }

    @Override
    public void clear() {
        this.dateModel.setValue(new Date());
        this.dateModel.setSelected(true);
        this.depositField.setText("");
        this.durationInMonthsField.setText("");
    }

    @Override
    public Map<String, ValidateAbleFieldDescriptor> getFieldsMap() {
        return this.fieldsMap;
    }

    private void addControlWithLabel(Component componentToAdd, String label, int rowIndex) {
        JLabel componentLabel = new JLabel(String.format("%s:", label));
        componentLabel.setLabelFor(componentToAdd);
        componentLabel.setFont(LabelStyles.getFontForFieldLabel());
        componentToAdd.setFont(LabelStyles.getFontForFieldLabel());

        formGridConfig.gridy = rowIndex;
        formGridConfig.gridx = 0;
        formGridConfig.weightx = 0.1;
        formGridConfig.weighty = 0.1;

        formGridConfig.fill = GridBagConstraints.NONE;
        formGridConfig.anchor = GridBagConstraints.LINE_END;
        formGridConfig.insets = this.controlsPadding;
        add(componentLabel, formGridConfig);

        formGridConfig.gridx = 1;
        formGridConfig.weightx = 0.4;
        formGridConfig.insets = this.controlsPadding;
        formGridConfig.anchor = GridBagConstraints.LINE_START;
        formGridConfig.fill = GridBagConstraints.HORIZONTAL;
        add(componentToAdd, formGridConfig);
        this.fieldsMap.put(label, new ValidateAbleFieldDescriptor(componentLabel, componentToAdd));
    }
}
