package com.example.mytodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TaskAdapter adapter;
    DBHelper dbHelper;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.taskListView);
        fab = findViewById(R.id.fab);

        dbHelper = new DBHelper();
        List<Task> taskList = dbHelper.getAllTasks();
        adapter = new TaskAdapter(this, taskList);
        listView.setAdapter(adapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Task> refreshedTasks = dbHelper.getAllTasks();
        adapter.updateTasks(refreshedTasks);  // 여기서 화면 새로고침됨!
    }
}
