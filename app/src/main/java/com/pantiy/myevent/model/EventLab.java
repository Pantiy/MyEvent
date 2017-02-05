package com.pantiy.myevent.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pantiy.myevent.Database.EventDatabase.EventBasedateHelper;
import com.pantiy.myevent.Database.EventDatabase.EventCursorWrapper;
import com.pantiy.myevent.Database.EventDatabase.EventDatabase.EventTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Pantiy on 2016/10/19.
 */

public class EventLab {

    public static EventLab sEventLab;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    private EventLab(Context context){
        mContext = context;
        mSQLiteDatabase = new EventBasedateHelper(mContext).getWritableDatabase();
    }

    public static EventLab getEventLab(Context context) {
        if (sEventLab == null) {
            sEventLab = new EventLab(context);
        }
        return sEventLab;
    }

    public List<Event> getSolvedEvents() {

        EventCursorWrapper cursor = queryEvent(null,null);
        List<Event> events = new ArrayList<>();
        try {
            if (cursor.getCount() == 0) {
                return events;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (cursor.getEvent().isSolved()){
                    events.add(cursor.getEvent());
                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        sortEvents(events,false);
        return events;
    }

    public List<Event> getUnsolvedEvents() {

        EventCursorWrapper cursor = queryEvent(null,null);
        List<Event> events = new ArrayList<>();
        try {
            if (cursor.getCount() == 0) {
                return events;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (!cursor.getEvent().isSolved()){
                    events.add(cursor.getEvent());
                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        sortEvents(events,true);
        return events;
    }

    private void sortEvents(List<Event> events, boolean isAscending) {

        for (int i = 0;i < events.size() - 1;i++) {
            for (int j = i + 1;j < events.size();j++) {
                if (isAscending) {
                    if (events.get(i).getEventDate().after(events.get(j).getEventDate())) {
                        Event event = events.get(j);
                        events.set(j,events.get(i));
                        events.set(i,event);
                    }
                } else {
                    if (events.get(i).getEventDate().before(events.get(j).getEventDate())) {
                        Event event = events.get(j);
                        events.set(j,events.get(i));
                        events.set(i,event);
                    }
                }
            }
        }
    }

    public Event getEvent(UUID eventId) {

        EventCursorWrapper cursor = queryEvent(EventTable.Lable.UUID + "=?",
                new String[] {eventId.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEvent();
        } finally {
            cursor.close();
        }
    }

    public void addEvent(Event event) {
        ContentValues values = getContentValues(event);
        mSQLiteDatabase.insert(EventTable.NAME,null,values);
    }

    public void updateEvent(Event event) {
        ContentValues values = getContentValues(event);
        mSQLiteDatabase.update(EventTable.NAME,values, EventTable.Lable.UUID + "=?",
                new String[] {event.getEventId().toString()});
    }

    public void deleteEvent(Event event) {
        mSQLiteDatabase.delete(EventTable.NAME,EventTable.Lable.UUID + "=?",
                new String[] {event.getEventId().toString()});
    }

    private static ContentValues getContentValues(Event event) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventTable.Lable.UUID,event.getEventId().toString());
        contentValues.put(EventTable.Lable.TITLE,event.getEventTitle());
        contentValues.put(EventTable.Lable.DATE,event.getEventDate().getTime());
        contentValues.put(EventTable.Lable.SOLVED,event.isSolved() ? 1 : 0);
        contentValues.put(EventTable.Lable.DETAIL,event.getEventDetail());

        return contentValues;
    }

    private EventCursorWrapper queryEvent(String whereClause, String[] whereArgs) {

        Cursor cursor = mSQLiteDatabase.query(
                EventTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);

        return new EventCursorWrapper(cursor);
    }
}
