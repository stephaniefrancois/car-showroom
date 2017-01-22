package app.sales.details;

import app.common.details.DetailsPanel;
import app.sales.details.wizard.CarDealWizardEditorPanel;
import core.deal.CarDealBuilder;
import core.deal.model.CarDealDetails;
import javafx.util.Pair;

public final class CarDealDetailsPanel extends DetailsPanel<CarDealDetails, CarDealBuilder> {
    public CarDealDetailsPanel() {
        super(
                new Pair<>(CarDealWizardEditorPanel.class.getName(), new CarDealWizardEditorPanel()),
                new Pair<>(PreviewSelectedCarDealPanel.class.getName(), new PreviewSelectedCarDealPanel())
        );
    }
}
