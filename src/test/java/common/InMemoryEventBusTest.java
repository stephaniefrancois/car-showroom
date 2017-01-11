package common;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import testing.helpers.EvenFakerEvent;
import testing.helpers.FakeEvent;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public final class InMemoryEventBusTest {

    @Test
    public void GivenSubscriberWhenEventRaisedThenSubscriberShouldReceiveMessage() {
        // Given
        FakeEvent data = new FakeEvent();
        IHandleEvent<FakeEvent> subscriberMock = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        sut.subscribeTo(subscriberMock);
        // When
        sut.raiseEvent(data);

        // Then
        verify(subscriberMock, times(1)).handleEvent(data);
    }

    @Test
    public void GivenSubscriberWhenMultipleEventsRaisedThenSubscriberShouldReceiveMultipleMessages() {
        // Given
        FakeEvent data1 = new FakeEvent();
        FakeEvent data2 = new FakeEvent();
        IHandleEvent<FakeEvent> subscriberMock = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        sut.subscribeTo(subscriberMock);
        // When
        sut.raiseEvent(data1);
        sut.raiseEvent(data2);

        // Then
        verify(subscriberMock, times(1)).handleEvent(data1);
        verify(subscriberMock, times(1)).handleEvent(data2);
    }

    @Test
    public void GivenSubscriberWhenUnsubscribesThenShouldReceiveNoMoreMessages() {
        // Given
        FakeEvent data = new FakeEvent();
        IHandleEvent<FakeEvent> subscriberMock = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        sut.subscribeTo(subscriberMock);
        // When

        sut.unsubscribeFrom(subscriberMock);
        sut.raiseEvent(data);

        // Then
        verify(subscriberMock, never()).handleEvent(any());
    }

    @Test
    public void GivenNoSubscriptionsWhenSubscriberTriesToUnsubscribeThenWeShouldReturn() {
        // Given
        FakeEvent data = new FakeEvent();
        IHandleEvent<FakeEvent> subscriberMock = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        // When

        sut.unsubscribeFrom(subscriberMock);
        // Then
    }

    @Test
    public void GivenMultipleSubscribersWhenEventRaisedThenAllMatchingSubscribersShouldReceiveMessage() {
        // Given
        FakeEvent data = new FakeEvent();
        IHandleEvent<FakeEvent> subscriber1Mock = Mockito.mock(IHandleEvent.class);
        IHandleEvent<FakeEvent> subscriber2Mock = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        sut.subscribeTo(subscriber1Mock);
        sut.subscribeTo(subscriber2Mock);

        // When
        sut.raiseEvent(data);

        // Then
        verify(subscriber1Mock, times(1)).handleEvent(data);
        verify(subscriber2Mock, times(1)).handleEvent(data);
    }

    @Test
    public void GivenMultipleSubscribersWhenOneUnsubscribesThenRemainingSubscriberShouldReceiveMessage() {
        // Given
        FakeEvent data1 = new FakeEvent();
        FakeEvent data2 = new FakeEvent();
        IHandleEvent<FakeEvent> subscriber1Mock = Mockito.mock(IHandleEvent.class);
        IHandleEvent<FakeEvent> subscriber2Mock = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        sut.subscribeTo(subscriber1Mock);
        sut.subscribeTo(subscriber2Mock);

        // When
        sut.raiseEvent(data1);
        sut.unsubscribeFrom(subscriber1Mock);
        sut.raiseEvent(data2);

        // Then
        verify(subscriber1Mock, never()).handleEvent(data2);
        verify(subscriber2Mock, times(1)).handleEvent(data2);
    }

    @Test
    public void GivenSubscriberWhenSubscribesMultipleTimesToSameEventThenShouldReceiveMessageOnce() {
        // Given
        FakeEvent data = new FakeEvent();
        IHandleEvent<FakeEvent> subscriberMock = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        sut.subscribeTo(subscriberMock);
        sut.subscribeTo(subscriberMock);

        // When
        sut.raiseEvent(data);

        // Then
        verify(subscriberMock, times(1)).handleEvent(data);
    }

    @Test
    public void GivenSubscribersWhenSubscribesToDifferentEventsThenMessagesShouldBeSendToTheCorrectSubscriber() {
        // Given
        FakeEvent data1 = new FakeEvent();
        EvenFakerEvent data2 = new EvenFakerEvent();
        IHandleEvent<FakeEvent> subscriberMock1 = Mockito.mock(IHandleEvent.class);
        IHandleEvent<EvenFakerEvent> subscriberMock2 = Mockito.mock(IHandleEvent.class);
        EventBus sut = new InMemoryEventBus();
        sut.subscribeTo(subscriberMock1);
        sut.subscribeTo(subscriberMock2);

        // When
        sut.raiseEvent(data1);
        sut.raiseEvent(data2);

        // Then
        verify(subscriberMock1, times(1)).handleEvent(data1);
        verify(subscriberMock1, times(1)).handleEvent(any());
        verify(subscriberMock2, times(1)).handleEvent(data2);
        verify(subscriberMock2, times(1)).handleEvent(any());
    }

    // TODO: test if we are trying to unsubscribe, but we were never subscribed in the first place, we should simply return
    // TODO: test multiple subscribers like 3 or 4
    // TODO: test single subscriber with multiple event types

}