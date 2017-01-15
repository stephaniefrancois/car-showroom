package app.cars;

import app.cars.details.CarDetailsPanel;
import app.cars.listing.CarsListPanel;
import app.common.listing.SearchableListPanel;
import app.common.search.SearchPanel;
import core.stock.model.Car;

import javax.swing.*;
import java.awt.*;

public final class CarsPanel extends JPanel {
    private final SearchableListPanel<Car> searchableCars;
    private final CarDetailsPanel carDetails;

    public CarsPanel() {
        setLayout(new BorderLayout());

        this.searchableCars = new SearchableListPanel(
                new SearchPanel(),
                new CarsListPanel()
        );

        this.carDetails = new CarDetailsPanel();

        add(this.searchableCars, BorderLayout.CENTER);
        add(this.carDetails, BorderLayout.EAST);

        this.searchableCars.addListener(this.carDetails);
        this.carDetails.addListener(this.searchableCars);
    }
}
