package app.sales.details.wizard;

import java.util.EventObject;

public final class StepChangedEventArgs extends EventObject {

    private final CarDealWizardStep previousSteps;
    private final CarDealWizardStep activeStep;
    private final int totalNumberOfSteps;

    public int getTotalNumberOfSteps() {
        return totalNumberOfSteps;
    }

    public int getCurrentStepNumber() {
        return currentStepNumber;
    }

    private final int currentStepNumber;
    private final boolean canGoBack;
    private final boolean canGoForward;

    public StepChangedEventArgs(Object source,
                                CarDealWizardStep previousStep,
                                CarDealWizardStep activeStep,
                                int totalNumberOfSteps,
                                int currentStepNumber,
                                boolean canGoBack,
                                boolean canGoForward) {
        super(source);
        this.previousSteps = previousStep;
        this.activeStep = activeStep;
        this.totalNumberOfSteps = totalNumberOfSteps;
        this.currentStepNumber = currentStepNumber;
        this.canGoBack = canGoBack;
        this.canGoForward = canGoForward;
    }

    public CarDealWizardStep getPreviousStep() {
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

    public boolean isLastStep() {
        return totalNumberOfSteps == currentStepNumber;
    }

}
