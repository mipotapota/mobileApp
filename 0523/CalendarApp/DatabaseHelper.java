package com.example.calendarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calendar.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_HOUR = "hour";
    private static final String COLUMN_MINUTE = "minute";
    private static final String COLUMN_NOTIFICATION = "notification";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_EVENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_HOUR + " INTEGER," +
                COLUMN_MINUTE + " INTEGER," +
                COLUMN_NOTIFICATION + " INTEGER" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public long addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_DATE, dateFormat.format(event.getDate()));
        values.put(COLUMN_HOUR, event.getHour());
        values.put(COLUMN_MINUTE, event.getMinute());
        values.put(COLUMN_NOTIFICATION, event.hasNotification() ? 1 : 0);

        long id = db.insert(TABLE_EVENTS, null, values);
        db.close();
        return id;
    }

    public List<Event> getEventsForDate(Date date) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_DATE + " = ?";
        String[] selectionArgs = {dateFormat.format(date)};

        Cursor cursor = db.query(TABLE_EVENTS, null, selection, selectionArgs, null, null,
                COLUMN_HOUR + ", " + COLUMN_MINUTE);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                event.setDate(date);
                event.setHour(cursor.getInt(cursor.getColumnIndex(COLUMN_HOUR)));
                event.setMinute(cursor.getInt(cursor.getColumnIndex(COLUMN_MINUTE)));
                event.setHasNotification(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATION)) == 1);

                events.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null,
                COLUMN_DATE + ", " + COLUMN_HOUR + ", " + COLUMN_MINUTE);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));

                try {
                    event.setDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE))));
                } catch (Exception e) {
                    event.setDate(new Date());
                }

                event.setHour(cursor.getInt(cursor.getColumnIndex(COLUMN_HOUR)));
                event.setMinute(cursor.getInt(cursor.getColumnIndex(COLUMN_MINUTE)));
                event.setHasNotification(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATION)) == 1);

                events.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

    public boolean updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_DATE, dateFormat.format(event.getDate()));
        values.put(COLUMN_HOUR, event.getHour());
        values.put(COLUMN_MINUTE, event.getMinute());
        values.put(COLUMN_NOTIFICATION, event.hasNotification() ? 1 : 0);

        int rowsAffected = db.update(TABLE_EVENTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_EVENTS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(eventId)});
        db.close();
        return rowsAffected > 0;
    }
}