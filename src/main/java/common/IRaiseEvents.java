package common;

public interface IRaiseEvents<TListener> {
    void addListener(TListener listenerToAdd);

    void removeListener(TListener listenerToRemove);
}
