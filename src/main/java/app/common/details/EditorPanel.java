package app.common.details;

import common.IRaiseEvents;
import common.ListenersManager;

import javax.swing.*;

public abstract class EditorPanel
        extends JPanel implements
        IRaiseEvents<ItemDetailsListener> {
    protected final ListenersManager<ItemDetailsListener> listeners;

    public EditorPanel() {
        this.listeners = new ListenersManager<>();
    }

    public abstract void createItem();

    public abstract void editItem(int id);

    @Override
    public final void addListener(ItemDetailsListener listenerToAdd) {
        this.listeners.addListener(listenerToAdd);
    }

    @Override
    public final void removeListener(ItemDetailsListener listenerToRemove) {
        this.listeners.removeListener(listenerToRemove);
    }
}
