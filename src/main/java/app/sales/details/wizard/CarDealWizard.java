package app.sales.details.wizard;

import app.common.validation.ValidationEventArgs;
import com.sun.javaws.exceptions.InvalidArgumentException;
import common.IRaiseEvents;
import common.ListenersManager;
import core.deal.CarDealFactory;
import core.validation.model.ValidationException;
import core.validation.model.ValidationSummary;

import java.util.List;
import java.util.Objects;

public final class CarDealWizard implements IRaiseEvents<CarDealWizardEventListener> {
    private final List<CarDealWizardStep> steps;
    private CarDealWizardStep activeStep;
    private ListenersManager<CarDealWizardEventListener> listenersManager;

    public CarDealWizard(CarDealFactory carDealFactory, List<CarDealWizardStep> steps) throws InvalidArgumentException {
        Objects.requireNonNull(carDealFactory);
        Objects.requireNonNull(steps);
        if (steps.isEmpty()) {
            throw new InvalidArgumentException(new String[]{"'steps' must always contain at least 2 steps to be a valid wizard!"});
        }
        this.listenersManager = new ListenersManager<>();
        this.steps = steps;
        this.activeStep = this.goToStart();
        this.activeStep.setCarDeal(carDealFactory);
    }

    private CarDealWizardStep goToStart() {
        return this.steps.get(0);
    }

    public final void goForward() throws ValidationException {
        int currentStepIndex = getCurrentStepIndex();

        ValidationSummary validation = this.activeStep.validateStep();

        if (validation.getIsValid() == false) {
            ValidationEventArgs args = new ValidationEventArgs(this.activeStep, validation);
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
        StepChangedEventArgs args = new StepChangedEventArgs(this, previousStep, activeStep, canGoBack,
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
        this.activeStep = this.goToStart();
        this.steps.forEach(CarDealWizardStep::clear);
    }
}