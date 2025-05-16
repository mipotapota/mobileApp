package com.example.stylishtodo;

import android.app.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayList<Task> taskList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        taskList = dbHelper.getAllTasks();
        adapter = new TaskAdapter(this, taskList);

        ListView listView = findViewById(R.id.task_list);
        listView.setAdapter(adapter);

        findViewById(R.id.add_btn).setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("할 일 추가");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText taskInput = new EditText(this);
        taskInput.setHint("할 일 입력");
        layout.addView(taskInput);

        final EditText dateInput = new EditText(this);
        dateInput.setHint("날짜 선택 (예: 2025-05-20)");
        dateInput.setFocusable(false);
        layout.addView(dateInput);

        dateInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
                dateInput.setText(date);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        builder.setView(layout);

        builder.setPositiveButton("추가", (dialog, which) -> {
            String task = taskInput.getText().toString().trim();
            String date = dateInput.getText().toString().trim();
            if (!task.isEmpty() && !date.isEmpty()) {
                dbHelper.insertTask(task, date);
                taskList.clear();
                taskList.addAll(dbHelper.getAllTasks());
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "할 일과 날짜를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
