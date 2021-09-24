package com.proper.transport;

import com.proper.events.Event;

import java.util.LinkedList;
import java.util.Queue;

public class EventPublisherImpl implements EventPublisher<Event> {
    Queue<Event> eventQueue = new LinkedList<>();
    @Override
    public void put(Event element) {
        eventQueue.add(element);
    }

    @Override
    public Event poll() {
        return eventQueue.poll();
    }
}
