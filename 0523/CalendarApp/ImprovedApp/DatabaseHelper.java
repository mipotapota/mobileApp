package com.example.calendarapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
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
                COLUMN_TITLE + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_DATE + " TEXT NOT NULL," +
                COLUMN_HOUR + " INTEGER NOT NULL," +
                COLUMN_MINUTE + " INTEGER NOT NULL," +
                COLUMN_NOTIFICATION + " INTEGER DEFAULT 0" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public long addEvent(Event event) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(COLUMN_TITLE, event.getTitle());
            values.put(COLUMN_DESCRIPTION, event.getDescription());
            values.put(COLUMN_DATE, dateFormat.format(event.getDate()));
            values.put(COLUMN_HOUR, event.getHour());
            values.put(COLUMN_MINUTE, event.getMinute());
            values.put(COLUMN_NOTIFICATION, event.hasNotification() ? 1 : 0);

            long id = db.insert(TABLE_EVENTS, null, values);
            Log.d(TAG, "Event added with ID: " + id);
            return id;
        } catch (Exception e) {
            Log.e(TAG, "Error adding event", e);
            return -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Event> getEventsForDate(Date date) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            String selection = COLUMN_DATE + " = ?";
            String[] selectionArgs = {dateFormat.format(date)};

            cursor = db.query(TABLE_EVENTS, null, selection, selectionArgs, null, null,
                    COLUMN_HOUR + ", " + COLUMN_MINUTE);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int titleIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE);
                int descIndex = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION);
                int hourIndex = cursor.getColumnIndexOrThrow(COLUMN_HOUR);
                int minuteIndex = cursor.getColumnIndexOrThrow(COLUMN_MINUTE);
                int notificationIndex = cursor.getColumnIndexOrThrow(COLUMN_NOTIFICATION);

                do {
                    Event event = new Event();
                    event.setId(cursor.getInt(idIndex));
                    event.setTitle(cursor.getString(titleIndex));
                    event.setDescription(cursor.getString(descIndex));
                    event.setDate(date);
                    event.setHour(cursor.getInt(hourIndex));
                    event.setMinute(cursor.getInt(minuteIndex));
                    event.setHasNotification(cursor.getInt(notificationIndex) == 1);

                    events.add(event);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting events for date", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return events;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_EVENTS, null, null, null, null, null,
                    COLUMN_DATE + ", " + COLUMN_HOUR + ", " + COLUMN_MINUTE);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int titleIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE);
                int descIndex = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION);
                int dateIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE);
                int hourIndex = cursor.getColumnIndexOrThrow(COLUMN_HOUR);
                int minuteIndex = cursor.getColumnIndexOrThrow(COLUMN_MINUTE);
                int notificationIndex = cursor.getColumnIndexOrThrow(COLUMN_NOTIFICATION);

                do {
                    Event event = new Event();
                    event.setId(cursor.getInt(idIndex));
                    event.setTitle(cursor.getString(titleIndex));
                    event.setDescription(cursor.getString(descIndex));

                    try {
                        Date eventDate = dateFormat.parse(cursor.getString(dateIndex));
                        event.setDate(eventDate != null ? eventDate : new Date());
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing date", e);
                        event.setDate(new Date());
                    }

                    event.setHour(cursor.getInt(hourIndex));
                    event.setMinute(cursor.getInt(minuteIndex));
                    event.setHasNotification(cursor.getInt(notificationIndex) == 1);

                    events.add(event);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting all events", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return events;
    }

    public boolean updateEvent(Event event) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(COLUMN_TITLE, event.getTitle());
            values.put(COLUMN_DESCRIPTION, event.getDescription());
            values.put(COLUMN_DATE, dateFormat.format(event.getDate()));
            values.put(COLUMN_HOUR, event.getHour());
            values.put(COLUMN_MINUTE, event.getMinute());
            values.put(COLUMN_NOTIFICATION, event.hasNotification() ? 1 : 0);

            int rowsAffected = db.update(TABLE_EVENTS, values, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(event.getId())});

            Log.d(TAG, "Event updated, rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error updating event", e);
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean deleteEvent(int eventId) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            int rowsAffected = db.delete(TABLE_EVENTS, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(eventId)});

            Log.d(TAG, "Event deleted, rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error deleting event", e);
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public Event getEventById(int eventId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            String selection = COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(eventId)};

            cursor = db.query(TABLE_EVENTS, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int titleIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE);
                int descIndex = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION);
                int dateIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE);
                int hourIndex = cursor.getColumnIndexOrThrow(COLUMN_HOUR);
                int minuteIndex = cursor.getColumnIndexOrThrow(COLUMN_MINUTE);
                int notificationIndex = cursor.getColumnIndexOrThrow(COLUMN_NOTIFICATION);

                Event event = new Event();
                event.setId(cursor.getInt(idIndex));
                event.setTitle(cursor.getString(titleIndex));
                event.setDescription(cursor.getString(descIndex));

                try {
                    Date eventDate = dateFormat.parse(cursor.getString(dateIndex));
                    event.setDate(eventDate != null ? eventDate : new Date());
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing date", e);
                    event.setDate(new Date());
                }

                event.setHour(cursor.getInt(hourIndex));
                event.setMinute(cursor.getInt(minuteIndex));
                event.setHasNotification(cursor.getInt(notificationIndex) == 1);

                return event;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting event by ID", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }
}