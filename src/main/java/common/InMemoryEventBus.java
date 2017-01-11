package common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class InMemoryEventBus implements EventBus {

    private final class Subscription<TEvent extends EventData> {
        private List<IHandleEvent<TEvent>> subscribers;

        public Subscription() {
            this.subscribers = new ArrayList<>();
        }

        public void addSubscriber(IHandleEvent<TEvent> subscriber) {
            if (subscriber == null) {
                return;
            }

            List<IHandleEvent<TEvent>> matchingSubscribers = findSubscribers(subscriber);
            if (matchingSubscribers.isEmpty()) {
                this.subscribers.add(subscriber);
            }
        }

        public void unsubscribeFrom(IHandleEvent<TEvent> subscriber) {
            List<IHandleEvent<TEvent>> matchingSubscribers = findSubscribers(subscriber);

            if (matchingSubscribers.size() > 0) {
                this.subscribers.removeAll(matchingSubscribers);
            }
        }

        private List<IHandleEvent<TEvent>> findSubscribers(IHandleEvent<TEvent> subscriber) {
            return this.subscribers
                    .stream()
                    .filter(s -> s.equals(subscriber))
                    .collect(Collectors.toList());
        }
    }

    private List<Subscription<?>> subscriptions;

    public InMemoryEventBus() {
        this.subscriptions = new ArrayList<>();
    }

    @Override
    public <TEvent extends EventData> void subscribeTo(IHandleEvent<TEvent> subscriber) {

        Subscription<TEvent> subscription = new Subscription<TEvent>();
        subscription.addSubscriber(subscriber);

        this.subscriptions.add(subscription);

        Subscription<TEvent> dummySubscription = new Subscription<TEvent>();
        dummySubscription.getClass().getTypeName();

//        this.subscriptions.stream()
//                .filter(s -> s.getClass()
//                        .getTypeName() == "")

        List<IHandleEvent<EventData>> matchingSubscribers = findExistingSubscription(subscriber);
        if (matchingSubscribers.isEmpty()) {
            // this.subscriptions.add((IHandleEvent<EventData>) subscriber);
        }
    }

    @Override
    public <TEvent extends EventData> void unsubscribeFrom(IHandleEvent<TEvent> subscriber) {
        List<IHandleEvent<EventData>> matchingSubscribers = findExistingSubscription(subscriber);

        if (matchingSubscribers.size() > 0) {
            this.subscriptions.removeAll(matchingSubscribers);
        }
    }

    // TODO: remove this
    private <TEvent extends EventData> List<IHandleEvent<EventData>> findExistingSubscription(IHandleEvent<TEvent> subscriber) {
//        return this.subscriptions
//                .stream()
//                .filter(s -> s.equals(subscriber))
//                .collect(Collectors.toList());

        return null;
    }


    @Override
    public <TEvent extends EventData> void raiseEvent(TEvent eventData) {
        if (this.subscriptions.isEmpty() == false) {
            this.subscriptions.forEach(s -> {
                //s.handleEvent(eventData);
            });
        }
    }
}
