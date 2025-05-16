package com.example.memoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class MemoDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MEMOS = "memos";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public MemoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_MEMOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);
        onCreate(db);
    }

    public long insertMemo(Memo memo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, memo.getTitle());
        values.put(COLUMN_CONTENT, memo.getContent());
        values.put(COLUMN_TIMESTAMP, memo.getTimestamp());

        long id = db.insert(TABLE_MEMOS, null, values);
        db.close();
        return id;
    }

    public boolean updateMemo(Memo memo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, memo.getTitle());
        values.put(COLUMN_CONTENT, memo.getContent());
        values.put(COLUMN_TIMESTAMP, memo.getTimestamp());

        int rowsAffected = db.update(TABLE_MEMOS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(memo.getId())});
        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteMemo(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(TABLE_MEMOS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    public Memo getMemoById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEMOS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        Memo memo = null;
        if (cursor.moveToFirst()) {
            memo = new Memo();
            memo.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            memo.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            memo.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
            memo.setTimestamp(cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
        }

        cursor.close();
        db.close();
        return memo;
    }

    public List<Memo> getAllMemos() {
        List<Memo> memoList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEMOS, null, null, null, null, null,
                COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Memo memo = new Memo();
                memo.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                memo.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                memo.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
                memo.setTimestamp(cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                memoList.add(memo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return memoList;
    }
}