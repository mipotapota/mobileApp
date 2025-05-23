package com.example.calendarapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventActivity extends AppCompatActivity {
    private EditText titleEditText, descriptionEditText;
    private TextView dateTextView, timeTextView;
    private CheckBox notificationCheckBox;
    private Button saveButton, deleteButton;

    private DatabaseHelper databaseHelper;
    private Calendar selectedDate;
    private int selectedHour = 9, selectedMinute = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());

    private Event editingEvent = null;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initViews();
        setupListeners();

        databaseHelper = new DatabaseHelper(this);
        selectedDate = Calendar.getInstance();

        if (getIntent().hasExtra("event")) {
            editingEvent = (Event) getIntent().getSerializableExtra("event");
            isEditMode = true;
            setupEditMode();
        }

        updateDateDisplay();
        updateTimeDisplay();
    }

    private void initViews() {
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        notificationCheckBox = findViewById(R.id.notificationCheckBox);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void setupListeners() {
        dateTextView.setOnClickListener(v -> showDatePicker());
        timeTextView.setOnClickListener(v -> showTimePicker());
        saveButton.setOnClickListener(v -> saveEvent());
        deleteButton.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void setupEditMode() {
        setTitle("일정 수정");
        saveButton.setText("수정");
        deleteButton.setVisibility(View.VISIBLE);

        titleEditText.setText(editingEvent.getTitle());
        descriptionEditText.setText(editingEvent.getDescription());
        selectedDate.setTime(editingEvent.getDate());
        selectedHour = editingEvent.getHour();
        selectedMinute = editingEvent.getMinute();
        notificationCheckBox.setChecked(editingEvent.hasNotification());
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate.set(year, month, dayOfMonth);
                    updateDateDisplay();
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedHour = hourOfDay;
                    selectedMinute = minute;
                    updateTimeDisplay();
                },
                selectedHour,
                selectedMinute,
                true
        );
        timePickerDialog.show();
    }

    private void updateDateDisplay() {
        dateTextView.setText(dateFormat.format(selectedDate.getTime()));
    }

    private void updateTimeDisplay() {
        timeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
    }

    private void saveEvent() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty()) {
            titleEditText.setError("제목을 입력하세요");
            return;
        }

        Event event = new Event(title, description, selectedDate.getTime(),
                selectedHour, selectedMinute, notificationCheckBox.isChecked());

        long eventId;
        if (isEditMode) {
            event.setId(editingEvent.getId());
            databaseHelper.updateEvent(event);
            eventId = editingEvent.getId();
        } else {
            eventId = databaseHelper.addEvent(event);
        }

        if (notificationCheckBox.isChecked()) {
            scheduleNotification(event, (int) eventId);
        }

        Toast.makeText(this, "이벤트가 저장되었습니다", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteConfirmation() {
        if (editingEvent == null) return;

        new AlertDialog.Builder(this)
                .setTitle("삭제 확인")
                .setMessage("이 일정을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    databaseHelper.deleteEvent(editingEvent.getId());
                    Toast.makeText(this, "이벤트가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(Event event, int requestCode) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, event.getHour());
        calendar.set(Calendar.MINUTE, event.getMinute());
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("title", event.getTitle());
        intent.putExtra("description", event.getDescription());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
