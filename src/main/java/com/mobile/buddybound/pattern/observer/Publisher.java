package com.mobile.buddybound.pattern.observer;

import java.util.List;

public class Publisher {
    private List<Subscriber> subscribers;

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }
}
