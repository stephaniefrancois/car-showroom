package app.sales.details.carDealSteps;

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
