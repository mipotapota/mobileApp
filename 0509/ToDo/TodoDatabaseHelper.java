package com.example.todoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todoapp.model.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_db";
    private static final int DATABASE_VERSION = 1;

    // 테이블 이름
    private static final String TABLE_TODO = "todos";

    // 컬럼 이름
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DUE_DATE = "due_date";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_COMPLETED = "is_completed";

    // 날짜 포맷
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DUE_DATE + " TEXT,"
                + KEY_PRIORITY + " INTEGER,"
                + KEY_COMPLETED + " INTEGER" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    // Todo 추가하기
    public long addTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, todo.getTitle());
        values.put(KEY_DESCRIPTION, todo.getDescription());
        values.put(KEY_DUE_DATE, dateFormat.format(todo.getDueDate()));
        values.put(KEY_PRIORITY, todo.getPriority());
        values.put(KEY_COMPLETED, todo.isCompleted() ? 1 : 0);

        long id = db.insert(TABLE_TODO, null, values);
        db.close();
        return id;
    }

    // Todo 하나 가져오기
    public Todo getTodo(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TODO, new String[]{
                        KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_DUE_DATE, KEY_PRIORITY, KEY_COMPLETED},
                KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Todo todo = null;
        if (cursor != null && cursor.getCount() > 0) {
            try {
                todo = new Todo(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        dateFormat.parse(cursor.getString(3)),
                        cursor.getInt(4),
                        cursor.getInt(5) == 1
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.close();
        }

        db.close();
        return todo;
    }

    // 모든 Todo 가져오기
    public List<Todo> getAllTodos() {
        List<Todo> todoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    Todo todo = new Todo(
                            cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            dateFormat.parse(cursor.getString(3)),
                            cursor.getInt(4),
                            cursor.getInt(5) == 1
                    );
                    todoList.add(todo);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoList;
    }

    // Todo 업데이트
    public int updateTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, todo.getTitle());
        values.put(KEY_DESCRIPTION, todo.getDescription());
        values.put(KEY_DUE_DATE, dateFormat.format(todo.getDueDate()));
        values.put(KEY_PRIORITY, todo.getPriority());
        values.put(KEY_COMPLETED, todo.isCompleted() ? 1 : 0);

        int result = db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
        db.close();
        return result;
    }

    // Todo 완료 상태 토글
    public int toggleTodoCompletion(long id) {
        Todo todo = getTodo(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
            return updateTodo(todo);
        }
        return 0;
    }

    // Todo 삭제
    public void deleteTodo(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // 모든 Todo 개수 가져오기
    public int getTodoCount() {
        String countQuery = "SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}