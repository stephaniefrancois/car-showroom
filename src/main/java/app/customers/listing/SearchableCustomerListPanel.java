package app.customers.listing;

import app.common.BasicEventArgs;
import app.common.details.ItemDetailsListener;
import app.common.listing.ListEventListener;
import app.common.search.SearchPanel;
import common.IRaiseEvents;

import javax.swing.*;
import java.awt.*;

public final class SearchableCustomerListPanel extends JPanel implements
        ItemDetailsListener,
        IRaiseEvents<ListEventListener> {
    private final SearchPanel searchPanel;
    private final CustomersListPanel customersList;

    public SearchableCustomerListPanel() {
        setLayout(new BorderLayout());

        this.searchPanel = new SearchPanel();
        this.customersList = new CustomersListPanel();

        add(this.searchPanel, BorderLayout.NORTH);
        add(this.customersList, BorderLayout.CENTER);

        this.searchPanel.addListener(this.customersList);
    }

    @Override
    public void addListener(ListEventListener listenerToAdd) {
        customersList.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(ListEventListener listenerToRemove) {
        customersList.removeListener(listenerToRemove);
    }

    @Override
    public void itemEditRequested(BasicEventArgs e) {

    }

    @Override
    public void itemSaved(BasicEventArgs e) {
        this.customersList.refresh();
    }

    @Override
    public void itemEditCancelled(BasicEventArgs e) {

    }
}
