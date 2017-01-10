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

        add(this.searchableCars, BorderLayout.WEST);
        add(this.carDetails, BorderLayout.EAST);

        this.searchableCars.addListener(this.carDetails);
        // TODO: so that the list would refresh its cars!
        this.carDetails.addListener(this.carDetails);
        //this.carDetails.addListener(this.searchableCars);
    }
}
