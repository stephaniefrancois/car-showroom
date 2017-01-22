package app.sales.details.wizard;

import app.RootLogger;
import app.common.BasicEventArgs;
import app.common.validation.ValidationEventArgs;
import common.IRaiseEvents;
import common.ListenersManager;
import core.deal.CarDealBuilder;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class CarDealWizard implements IRaiseEvents<CarDealWizardEventListener> {
    private final static Logger log = RootLogger.get();
    private final List<CarDealWizardStep> steps;
    private CarDealWizardStep activeStep;
    private ListenersManager<CarDealWizardEventListener> listenersManager;

    public CarDealWizard(CarDealBuilder carDealBuilder, CarDealWizardStepsProvider stepsProvider) {
        Objects.requireNonNull(carDealBuilder);
        Objects.requireNonNull(stepsProvider);

        this.listenersManager = new ListenersManager<>();
        this.steps = stepsProvider.getSteps();
        this.activeStep = this.goToStart();
        this.activeStep.setCarDeal(carDealBuilder);
    }

    private CarDealWizardStep goToStart() {
        if (this.steps.isEmpty()) {
            logNoWizardStepsFound();
        }
        return this.steps.get(0);
    }

    public final void goForward() throws ValidationException {
        int currentStepIndex = getCurrentStepIndex();

        ValidationSummary validation = this.activeStep.validateStep();

        if (!validation.getIsValid()) {
            ValidationEventArgs args = new ValidationEventArgs(this.activeStep,
                    validation,
                    this.activeStep.getFieldsMap());

            this.listenersManager.notifyListeners(l -> l.validationFailedOnNavigating(args));
            return;
        }

        if (atLastStep()) {
            CarDealCompletedEventArgs args = new CarDealCompletedEventArgs(this,
                    this.activeStep.getCarDeal().build());
            this.listenersManager.notifyListeners(l -> l.completed(args));
            return;
        }

        CarDealWizardStep previousStep = this.activeStep;
        this.activeStep = this.steps.get(currentStepIndex + 1);
        this.raiseStepChanged(previousStep, activeStep);
    }

    private int getCurrentStepIndex() {
        return this.steps.indexOf(this.activeStep);
    }

    private void raiseStepChanged(CarDealWizardStep previousStep, CarDealWizardStep activeStep) {
        boolean canGoForward = !this.atLastStep();
        boolean canGoBack = !this.atFirstStep();
        int stepNumber = this.getCurrentStepIndex() + 1;

        StepChangedEventArgs args = new StepChangedEventArgs(this, previousStep, activeStep,
                this.steps.size(),
                stepNumber,
                canGoBack,
                canGoForward);

        activeStep.setCarDeal(previousStep.getCarDeal());
        this.listenersManager.notifyListeners(l -> l.stepChanged(args));
    }

    private boolean atLastStep() {
        int currentStepIndex = getCurrentStepIndex();
        return currentStepIndex == this.steps.size() - 1;
    }

    public final void goBack() {
        int currentStepIndex = getCurrentStepIndex();
        if (atFirstStep()) {
            return;
        }

        CarDealWizardStep previousStep = this.activeStep;
        this.activeStep = this.steps.get(currentStepIndex - 1);
        this.raiseStepChanged(previousStep, activeStep);
    }

    private boolean atFirstStep() {
        int currentStepIndex = getCurrentStepIndex();
        return currentStepIndex == 0;
    }

    public CarDealWizardStep activeStep() {
        return this.activeStep;
    }

    @Override
    public void addListener(CarDealWizardEventListener listenerToAdd) {
        this.listenersManager.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarDealWizardEventListener listenerToRemove) {
        this.listenersManager.removeListener(listenerToRemove);
    }

    public void cancel() {
        logCancellingDeal();
        BasicEventArgs args = new BasicEventArgs(this, this.activeStep.getCarDeal().getId());
        this.activeStep = this.goToStart();
        this.steps.forEach(CarDealWizardStep::clear);
        logDealCancelled(args.getId());
    }

    private void logCancellingDeal() {
        log.info(() -> "Cancelling Car Deal ...");
    }

    private void logDealCancelled(int carDealId) {
        log.info(() -> String.format("Car deal with id '%d' has been cancelled!", carDealId));
    }

    private void logNoWizardStepsFound() {
        log.severe(() -> "No wizard steps have been supplied! Please properly configure steps provider!");
    }
}