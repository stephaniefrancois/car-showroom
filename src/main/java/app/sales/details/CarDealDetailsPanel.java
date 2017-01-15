package app.sales.details;

import app.common.details.DetailsPanel;
import core.deal.CarDealFactory;
import core.deal.model.CarDealDetails;
import javafx.util.Pair;

public final class CarDealDetailsPanel extends DetailsPanel<CarDealDetails, CarDealFactory> {
    public CarDealDetailsPanel() {
        super(
                new Pair<>(CarDealEditorPanel.class.getName(), new CarDealEditorPanel()),
                new Pair<>(PreviewSelectedCarDealPanel.class.getName(), new PreviewSelectedCarDealPanel())
        );
    }
}
