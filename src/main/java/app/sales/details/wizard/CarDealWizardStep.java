package app.sales.details.wizard;

import core.deal.CarDealFactory;
import core.validation.model.ValidationSummary;

import javax.swing.*;

public abstract class CarDealWizardStep extends JPanel {
    public abstract ValidationSummary validateStep();

    public abstract void setCarDeal(CarDealFactory carDealFactory);

    public abstract CarDealFactory getCarDeal();

    public abstract void clear();
}
