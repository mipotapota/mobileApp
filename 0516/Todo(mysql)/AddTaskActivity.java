package com.example.mytodoapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText titleInput, dateInput;
    Button saveBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        saveBtn = findViewById(R.id.saveBtn);
        dbHelper = new DBHelper();

        dateInput.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        dateInput.setText(year1 + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }, year, month, day);
            datePickerDialog.show();
        });

        saveBtn.setOnClickListener(view -> {
            String title = titleInput.getText().toString();
            String date = dateInput.getText().toString();
            if (title.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            dbHelper.insertTask(title, date);
            finish();
        });
    }
}