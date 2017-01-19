package app.sales.details.wizard;

import java.util.EventListener;

public interface CarDealWizardNavigationEventListener extends EventListener {
    void navigateBack();

    void navigateForward();

    void completeWizard();

    void cancelWizard();
}
