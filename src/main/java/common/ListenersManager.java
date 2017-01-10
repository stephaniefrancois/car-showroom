package common;

import java.util.ArrayList;
import java.util.function.Consumer;

public final class ListenersManager<TListener> implements IRaiseEvents<TListener> {

    private final java.util.List<TListener> listeners;

    public ListenersManager() {
        this.listeners = new ArrayList<>();
    }

    public void notifyListeners(Consumer<TListener> listenerNotifier) {
        if (listeners.size() > 0) {
            listeners.forEach(listener -> listenerNotifier.accept(listener));
        }
    }

    @Override
    public void addListener(TListener listenerToAdd) {
        if (!listeners.contains(listenerToAdd)) {
            listeners.add(listenerToAdd);
        }
    }

    @Override
    public void removeListener(TListener listenerToRemove) {
        if (listeners.contains(listenerToRemove)) {
            listeners.remove(listenerToRemove);
        }
    }
}
