package app.sales.details;

import java.util.EventListener;

public interface CarDealWizardNavigationEventListener extends EventListener {
    void navigateBack();

    void navigateForward();

    void completeWizard();
}
