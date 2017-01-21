package app.cars;

import app.ContentPanel;
import app.cars.details.CarDetailsPanel;
import app.cars.listing.CarsListPanel;
import app.common.listing.ListOptions;
import app.common.listing.SearchableListPanel;
import app.common.search.SearchPanel;
import core.stock.model.Car;

import java.awt.*;

public final class CarsPanel extends ContentPanel {
    private final SearchableListPanel<Car> searchableCars;
    private final CarDetailsPanel carDetails;

    public CarsPanel() {
        setLayout(new BorderLayout());

        this.searchableCars = new SearchableListPanel(
                new SearchPanel(),
                new CarsListPanel(
                        ListOptions.AllowEditingItems("Available cars:",
                                "No cars available in the showroom!"))
        );

        this.carDetails = new CarDetailsPanel();

        add(this.searchableCars, BorderLayout.CENTER);
        add(this.carDetails, BorderLayout.EAST);

        this.searchableCars.addListener(this.carDetails);
        this.carDetails.addListener(this.searchableCars);
    }

    @Override
    public void activated() {
        this.searchableCars.refresh();
    }
}
