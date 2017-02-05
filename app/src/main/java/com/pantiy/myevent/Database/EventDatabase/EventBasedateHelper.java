package com.pantiy.myevent.Database.EventDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pantiy.myevent.Database.EventDatabase.EventDatabase.EventTable;

/**
 * Created by Pantiy on 2016/10/20.
 */

public class EventBasedateHelper extends SQLiteOpenHelper {

    private static final String DATEBASE_NAME = "eventDatabase.db";
    private static final int VERSION = 1;

    public EventBasedateHelper(Context context) {
        super(context,DATEBASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + EventTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                EventTable.Lable.UUID + ", " +
                EventTable.Lable.TITLE + ", " +
                EventTable.Lable.DATE + ", " +
                EventTable.Lable.SOLVED + ", " +
                EventTable.Lable.DETAIL + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
