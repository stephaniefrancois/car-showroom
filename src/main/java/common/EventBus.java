package common;

public interface EventBus {
    <TEvent extends EventData> void subscribeTo(IHandleEvent<TEvent> subscriber);

    <TEvent extends EventData> void unsubscribeFrom(IHandleEvent<TEvent> subscriber);

    <TEvent extends EventData> void raiseEvent(TEvent eventData);
}

