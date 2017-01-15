package app.common.details;

import app.RootLogger;
import app.common.BasicEventArgs;
import common.IRaiseEvents;
import common.ListenersManager;
import core.IHaveIdentifier;

import javax.swing.*;
import java.util.logging.Logger;

public abstract class PreviewSelectedItemPanel<TModel extends IHaveIdentifier> extends JPanel
        implements IRaiseEvents<ItemDetailsListener> {

    private static final Logger log = RootLogger.get();
    private final ListenersManager<ItemDetailsListener> listeners;
    private TModel item;

    public PreviewSelectedItemPanel() {
        this.listeners = new ListenersManager<>();
    }

    public void editItem(Object source) {
        if (this.item == null) {
            return;
        }
        BasicEventArgs event = new BasicEventArgs(source, this.item.getId());
        listeners.notifyListeners(l -> l.itemEditRequested(event));
    }

    public void previewItem(int id) {
        TModel item = this.getItem(id);
        if (item == null) {
            this.item = null;
            logItemNotFound(id);
            return;
        }

        this.item = item;
        populateItemInformation(item);
    }

    protected abstract TModel getItem(int id);

    protected abstract void populateItemInformation(TModel item);

    @Override
    public void addListener(ItemDetailsListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public void removeListener(ItemDetailsListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }

    private void logItemNotFound(int id) {
        log.warning(() -> String.format("Item with id '%d' was NOT found! Please check your datasource.", id));
    }
}
