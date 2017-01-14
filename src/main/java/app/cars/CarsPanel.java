package app.cars;

import app.cars.carListing.SearchableCarListPanel;
import app.cars.details.CarDetailsPanel;

import javax.swing.*;
import java.awt.*;

public final class CarsPanel extends JPanel {
    private final SearchableCarListPanel searchableCars;
    private final CarDetailsPanel carDetails;

    public CarsPanel() {
        setLayout(new BorderLayout());

        this.searchableCars = new SearchableCarListPanel();
        this.carDetails = new CarDetailsPanel();

        add(this.searchableCars, BorderLayout.CENTER);
        add(this.carDetails, BorderLayout.EAST);

        this.searchableCars.addListener(this.carDetails);
        this.carDetails.addListener(this.searchableCars);
    }
}
