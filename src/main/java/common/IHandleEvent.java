package common;

public interface IHandleEvent<TEvent extends EventData> {
    void handleEvent(TEvent event);
}
