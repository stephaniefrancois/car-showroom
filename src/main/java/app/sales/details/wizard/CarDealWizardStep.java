package app.sales.details.wizard;

import app.common.validation.ValidateAbleFieldDescriptor;
import core.deal.CarDealBuilder;
import core.validation.model.ValidationSummary;

import javax.swing.*;
import java.util.Map;

public abstract class CarDealWizardStep extends JPanel {
    public final String getStepKey() {
        return this.getClass().getName();
    }

    public abstract String getTitle();

    public abstract ValidationSummary validateStep();

    public abstract void setCarDeal(CarDealBuilder carDealBuilder);

    public abstract CarDealBuilder getCarDeal();

    public abstract void clear();

    public abstract Map<String, ValidateAbleFieldDescriptor> getFieldsMap();
}
