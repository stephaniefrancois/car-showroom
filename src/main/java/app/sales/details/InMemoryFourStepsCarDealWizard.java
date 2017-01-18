package app.sales.details;

import app.sales.details.carDealSteps.CarPanel;
import app.sales.details.carDealSteps.CustomerPanel;
import app.sales.details.carDealSteps.DealReviewPanel;
import app.sales.details.carDealSteps.PaymentDetailsPanel;
import app.sales.details.wizard.CarDealWizardStep;
import app.sales.details.wizard.CarDealWizardStepsProvider;

import java.util.Arrays;
import java.util.List;

public final class InMemoryFourStepsCarDealWizard implements CarDealWizardStepsProvider {
    private final List<CarDealWizardStep> steps;

    public InMemoryFourStepsCarDealWizard() {
        steps = Arrays.asList(
                new CarPanel(),
                new CustomerPanel(),
                new PaymentDetailsPanel(),
                new DealReviewPanel()
        );
    }

    @Override
    public List<CarDealWizardStep> getSteps() {
        return this.steps;
    }
}
