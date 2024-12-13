package com.mobile.buddybound.pattern.observer;

import com.mobile.buddybound.model.dto.LocationDto;

public interface Subscriber {
    void updateLocation(LocationDto location);
    void turnOffLocation();
}
