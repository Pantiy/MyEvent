package com.pantiy.myevent.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Pantiy on 2016/10/19.
 */

public class Event {

    private UUID mEventId;
    private String mEventTitle;
    private Date mEventDate;
    private boolean mSolved;
    private String mEventDetail;

    public Event() {
        this(UUID.randomUUID());
    }

    public Event(UUID eventId) {
        mEventId = eventId;
        mEventDate = new Date();
    }

    public UUID getEventId() {
        return mEventId;
    }

    public Date getEventDate() {
        return mEventDate;
    }

    public void setEventDate(Date eventDate) {
        mEventDate = eventDate;
    }

    public String getEventTitle() {
        return mEventTitle;
    }

    public void setEventTitle(String eventTitle) {
        mEventTitle = eventTitle;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getEventDetail() {
        return mEventDetail;
    }

    public void setEventDetail(String eventDetail) {
        mEventDetail = eventDetail;
    }

}
