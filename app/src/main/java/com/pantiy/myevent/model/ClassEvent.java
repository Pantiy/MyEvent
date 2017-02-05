package com.pantiy.myevent.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Pantiy on 2016/11/16.
 * Copyright © 2016 All rights Reserved by Pantiy
 */

public class ClassEvent {

    private UUID mClassEventId;
    private String mClassEventTitle;
    private Date mClassEventDate;
    private boolean mSolved;

    public ClassEvent() {
        mClassEventId = UUID.randomUUID();
        mClassEventDate = new Date();
    }

    public String getClassEventTitle() {
        return mClassEventTitle;
    }

    public void setClassEventTitle(String classEventTitle) {
        mClassEventTitle = classEventTitle;
    }

    public Date getClassEventDate() {
        return mClassEventDate;
    }

    public void setClassEventDate(Date classEventDate) {
        mClassEventDate = classEventDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
