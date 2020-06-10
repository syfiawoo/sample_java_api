package com.callia.test;

import com.callia.test.service.EventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EventList implements EventService{
    public static final int SIXTY_SECONDS = 60 * 1000;  // 60s in milliseconds
    private ArrayList<Event> events;  // keep track of all events

    // getter for events
    public ArrayList<Event> getEvents() {
        return events;
    }

    // default constructor
    public EventList() {
        this.events = new ArrayList<>();  // initialise events
    }

    @Override
    public EventList getLastSixtySeconds(long currentTime) {
        EventList temp = new EventList();  // temporary events list to store events that match criteria
        long lastSixtySecs = currentTime - SIXTY_SECONDS;  // smallest within
        for (Event e: events) {
            if(e.getTimestamp() > lastSixtySecs && e.getTimestamp() <= currentTime)
                // try to add an event to the list
                try{
                    temp.addEvent(e);
                }
                catch(Exception error) {
                    // failed
                    System.out.println(error.getMessage());
                }
        }
        return temp;
    }

    @Override
    public double eventMeanAmount(EventList eventList) {
        double mean = 0.0;
        ArrayList<Event> allEvents = eventList.getEvents();
        // check to make sure list contains elements
        if (allEvents.size() > 0) {
            double sumEvents = 0.0;
            // find the sum of all events in list
            for (Event e : allEvents) {
                sumEvents += e.getOrderAmount();
            }
            // calculate the mean
            mean = sumEvents / allEvents.size();
        }
        return mean;
    }

    @Override
    public boolean addEvent(Event event) {
        boolean added = events.add(event);
        if(! added)
            throw new ArrayStoreException("There was a problem adding the event");
        return true;
    }

    @Override
    public String toString() {
        StringBuilder data = new StringBuilder();
        for (Event e: events) {
            data.append(e);
            data.append('\n');
        }
        return data.toString();
    }
}
