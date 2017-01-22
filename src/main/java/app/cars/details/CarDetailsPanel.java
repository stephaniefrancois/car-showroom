package app.cars.details;

import app.common.details.DetailsPanel;
import core.stock.CarBuilder;
import core.stock.model.CarDetails;
import javafx.util.Pair;

public final class CarDetailsPanel extends DetailsPanel<CarDetails, CarBuilder> {


    public CarDetailsPanel() {
        super(
                new Pair<>(CarEditorPanel.class.getName(), new CarEditorPanel()),
                new Pair<>(PreviewSelectedCarPanel.class.getName(), new PreviewSelectedCarPanel())
        );
    }
}
