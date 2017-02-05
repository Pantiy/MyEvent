package com.pantiy.myevent.Database.EventDatabase;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.pantiy.myevent.Database.EventDatabase.EventDatabase.EventTable;
import com.pantiy.myevent.model.Event;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class EventCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public EventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Event getEvent() {

        String uuidString = getString(getColumnIndex(EventTable.Lable.UUID));
        String title = getString(getColumnIndex(EventTable.Lable.TITLE));
        long date = getLong(getColumnIndex(EventTable.Lable.DATE));
        int isSolved = getInt(getColumnIndex(EventTable.Lable.SOLVED));
        String detail = getString(getColumnIndex(EventTable.Lable.DETAIL));

        Event event = new Event(UUID.fromString(uuidString));
        event.setEventTitle(title);
        event.setEventDate(new Date(date));
        event.setSolved(isSolved != 0);
        event.setEventDetail(detail);

        return event;
    }
}
