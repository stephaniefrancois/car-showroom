package app.sales.details.wizard;

import app.common.validation.ValidationEventArgs;

import java.util.EventListener;

public interface CarDealWizardEventListener extends EventListener {
    void stepChanged(StepChangedEventArgs args);

    void validationFailedOnNavigating(ValidationEventArgs args);

    void completed(CarDealCompletedEventArgs args);
}
