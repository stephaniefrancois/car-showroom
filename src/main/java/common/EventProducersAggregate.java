package common;

import java.util.EventListener;
import java.util.List;
import java.util.Objects;

public final class EventProducersAggregate<TListener extends EventListener>
        implements IRaiseEvents<TListener> {

    private final List<IRaiseEvents> eventProducers;

    public EventProducersAggregate(List<IRaiseEvents> eventProducers) {
        Objects.requireNonNull(eventProducers);
        this.eventProducers = eventProducers;
    }

    @Override
    public void addListener(TListener listenerToAdd) {
        this.eventProducers.forEach(p -> p.addListener(listenerToAdd));
    }

    @Override
    public void removeListener(TListener listenerToRemove) {
        this.eventProducers.forEach(p -> p.removeListener(listenerToRemove));
    }
}
