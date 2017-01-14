package app.cars.carListing;

import app.cars.CarEventArgs;
import app.cars.details.CarDetailsListener;
import app.cars.search.SearchPanel;
import common.IRaiseEvents;

import javax.swing.*;
import java.awt.*;

public final class SearchableCarListPanel extends JPanel implements
        CarDetailsListener,
        IRaiseEvents<CarListener> {
    private final SearchPanel searchPanel;
    private final CarsListPanel carsList;

    public SearchableCarListPanel() {
        setLayout(new BorderLayout());

        this.searchPanel = new SearchPanel();
        this.carsList = new CarsListPanel();

        add(this.searchPanel, BorderLayout.NORTH);
        add(this.carsList, BorderLayout.CENTER);

        this.searchPanel.addListener(this.carsList);
    }

    @Override
    public void addListener(CarListener listenerToAdd) {
        carsList.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(CarListener listenerToRemove) {
        carsList.removeListener(listenerToRemove);
    }

    @Override
    public void carEditRequested(CarEventArgs e) {

    }

    @Override
    public void carSaved(CarEventArgs e) {
        this.carsList.refresh();
    }

    @Override
    public void carEditCancelled(CarEventArgs e) {

    }
}
