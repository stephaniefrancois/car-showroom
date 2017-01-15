package app.common.listing;

import app.common.BasicEventArgs;
import app.common.details.ItemDetailsListener;
import app.common.search.SearchPanel;
import common.IRaiseEvents;
import core.IHaveIdentifier;

import javax.swing.*;
import java.awt.*;

public final class SearchableListPanel<TModel extends IHaveIdentifier> extends JPanel implements
        ItemDetailsListener,
        IRaiseEvents<ListEventListener> {
    private final SearchPanel searchPanel;
    private final ItemsListPanel<TModel> itemsList;

    public SearchableListPanel(SearchPanel searchPanel, ItemsListPanel<TModel> itemsListPanel) {
        setLayout(new BorderLayout());

        this.searchPanel = searchPanel;
        this.itemsList = itemsListPanel;

        add(this.searchPanel, BorderLayout.NORTH);
        add(this.itemsList, BorderLayout.CENTER);

        this.searchPanel.addListener(this.itemsList);
    }

    @Override
    public final void addListener(ListEventListener listenerToAdd) {
        itemsList.addListener(listenerToAdd);
    }

    @Override
    public final void removeListener(ListEventListener listenerToRemove) {
        itemsList.removeListener(listenerToRemove);
    }

    @Override
    public final void itemEditRequested(BasicEventArgs e) {

    }

    @Override
    public final void itemSaved(BasicEventArgs e) {
        this.itemsList.refresh();
    }

    @Override
    public final void itemEditCancelled(BasicEventArgs e) {

    }
}
