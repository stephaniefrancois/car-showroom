package app.cars.listing;

import app.common.BasicEventArgs;
import app.common.details.ItemDetailsListener;
import app.common.listing.ListEventListener;
import app.common.search.SearchPanel;
import common.IRaiseEvents;

import javax.swing.*;
import java.awt.*;

public final class SearchableCarListPanel extends JPanel implements
        ItemDetailsListener,
        IRaiseEvents<ListEventListener> {
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
    public void addListener(ListEventListener listenerToAdd) {
        carsList.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(ListEventListener listenerToRemove) {
        carsList.removeListener(listenerToRemove);
    }

    @Override
    public void itemEditRequested(BasicEventArgs e) {

    }

    @Override
    public void itemSaved(BasicEventArgs e) {
        this.carsList.refresh();
    }

    @Override
    public void itemEditCancelled(BasicEventArgs e) {

    }
}
