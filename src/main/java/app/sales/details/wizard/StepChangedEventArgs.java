package app.sales.details.wizard;

import java.util.EventObject;

public final class StepChangedEventArgs extends EventObject {

    private final CarDealWizardStep previousSteps;
    private final CarDealWizardStep activeStep;
    private final boolean canGoBack;
    private final boolean canGoForward;

    public StepChangedEventArgs(Object source,
                                CarDealWizardStep previousSteps,
                                CarDealWizardStep activeStep,
                                boolean canGoBack,
                                boolean canGoForward) {
        super(source);
        this.previousSteps = previousSteps;
        this.activeStep = activeStep;
        this.canGoBack = canGoBack;
        this.canGoForward = canGoForward;
    }

    public CarDealWizardStep getPreviousSteps() {
        return previousSteps;
    }

    public CarDealWizardStep getActiveStep() {
        return activeStep;
    }

    public boolean canGoBack() {
        return canGoBack;
    }

    public boolean canGoForward() {
        return canGoForward;
    }

}
