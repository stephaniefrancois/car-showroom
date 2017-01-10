package app.cars.carListing;

import app.cars.search.SearchPanel;
import common.IRaiseEvents;

import javax.swing.*;
import java.awt.*;

public final class SearchableCarListPanel extends JPanel implements IRaiseEvents<CarListener> {
    private final SearchPanel searchPanel;
    private final CarsListPanel carsList;

    public SearchableCarListPanel() {
        setLayout(new BorderLayout());

        this.searchPanel = new SearchPanel();
        this.carsList = new CarsListPanel();

        add(this.searchPanel, BorderLayout.NORTH);
        add(this.carsList, BorderLayout.CENTER);
    }

    @Override
    public void addListener(CarListener listenerToAdd) {
        carsList.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarListener listenerToRemove) {
        carsList.removeListener(listenerToRemove);
    }
}
