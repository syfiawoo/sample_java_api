package com.callia.test;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.UUID;

public class Event {

    private UUID eventId;
    private double orderAmount;
    private long timestamp;

    public Event(UUID id, double amount, long currentTimestamp){
        eventId = id;
        orderAmount = amount;
        timestamp = currentTimestamp;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return eventId + "" + orderAmount + timestamp;
    }
}
