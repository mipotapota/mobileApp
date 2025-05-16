package com.example.stylishtodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "tasks";
    private static final String COL_ID = "id";
    private static final String COL_TASK = "task";
    private static final String COL_DATE = "date";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TASK + " TEXT, " +
                COL_DATE + " TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(String task, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK, task);
        values.put(COL_DATE, date);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COL_ID + " DESC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String task = cursor.getString(cursor.getColumnIndexOrThrow(COL_TASK));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
            tasks.add(new Task(id, task, date));
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
