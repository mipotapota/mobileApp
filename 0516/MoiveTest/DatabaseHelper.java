package com.example.movietest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MovieDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIES = "movies";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_DIRECTOR = "director";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_COUNTRY = "country";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_DIRECTOR + " TEXT,"
                + COLUMN_RATING + " REAL,"
                + COLUMN_COUNTRY + " TEXT" + ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    // 영화 추가
    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_DIRECTOR, movie.getDirector());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_COUNTRY, movie.getCountry());

        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }

    // 모든 영화 조회
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(0));
                movie.setTitle(cursor.getString(1));
                movie.setYear(cursor.getInt(2));
                movie.setDirector(cursor.getString(3));
                movie.setRating(cursor.getDouble(4));
                movie.setCountry(cursor.getString(5));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movieList;
    }

    // 영화 업데이트
    public int updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_DIRECTOR, movie.getDirector());
        values.put(COLUMN_RATING, movie.getRating());
        values.put(COLUMN_COUNTRY, movie.getCountry());

        return db.update(TABLE_MOVIES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
    }

    // 영화 삭제
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
        db.close();
    }
}