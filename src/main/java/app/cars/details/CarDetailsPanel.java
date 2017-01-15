package app.cars.details;

import app.common.details.DetailsPanel;
import core.domain.car.CarProperties;
import core.stock.CarFactory;
import javafx.util.Pair;

public final class CarDetailsPanel extends DetailsPanel<CarProperties, CarFactory> {


    public CarDetailsPanel() {
        super(
                new Pair<>(CarEditorPanel.class.getName(), new CarEditorPanel()),
                new Pair<>(PreviewSelectedCarPanel.class.getName(), new PreviewSelectedCarPanel())
        );
    }
}
