package app.sales.details.carDealSteps;

import app.common.validation.ValidateAbleFieldDescriptor;
import app.sales.details.wizard.CarDealWizardStep;
import app.styles.LabelStyles;
import core.deal.CarDealFactory;
import core.validation.model.ValidationSummary;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class CustomerPanel extends CarDealWizardStep {
    private CarDealFactory carDealFactory;

    public CustomerPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel(getTitle());
        label.setFont(LabelStyles.getFontForHeaderLevelOne());
        add(label, BorderLayout.CENTER);
    }

    @Override
    public String getTitle() {
        return "Select customer";
    }

    @Override
    public ValidationSummary validateStep() {
        return new ValidationSummary();
    }

    @Override
    public void setCarDeal(CarDealFactory carDealFactory) {
        this.carDealFactory = carDealFactory;

    }

    @Override
    public CarDealFactory getCarDeal() {
        return this.carDealFactory;
    }

    @Override
    public void clear() {

    }

    @Override
    public Map<String, ValidateAbleFieldDescriptor> getFieldsMap() {
        return new HashMap<>();
    }
}
