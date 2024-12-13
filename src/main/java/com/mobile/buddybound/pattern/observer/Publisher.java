package com.mobile.buddybound.pattern.observer;

import com.mobile.buddybound.model.dto.LocationDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class Publisher {
    private List<Subscriber> subscribers;

    public Publisher() {
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notify(LocationDto dto) {
        for (Subscriber subscriber : subscribers) {
            subscriber.updateLocation(dto);
        }
    }
}
