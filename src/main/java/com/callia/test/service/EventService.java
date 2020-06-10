package com.callia.test.service;

import com.callia.test.Event;
import com.callia.test.EventList;
import org.springframework.stereotype.Service;

public interface EventService{
    EventList getLastSixtySeconds(long currentTime);
    double eventMeanAmount(EventList eventList);
    boolean addEvent(Event event);
}
