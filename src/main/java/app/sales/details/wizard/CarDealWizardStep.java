package app.sales.details.wizard;

import app.common.validation.ValidateAbleFieldDescriptor;
import core.deal.CarDealFactory;
import core.validation.model.ValidationSummary;

import javax.swing.*;
import java.util.Map;

public abstract class CarDealWizardStep extends JPanel {
    public final String getStepKey() {
        return this.getClass().getName();
    }

    public abstract String getTitle();

    public abstract ValidationSummary validateStep();

    public abstract void setCarDeal(CarDealFactory carDealFactory);

    public abstract CarDealFactory getCarDeal();

    public abstract void clear();

    public abstract Map<String, ValidateAbleFieldDescriptor> getFieldsMap();
}
