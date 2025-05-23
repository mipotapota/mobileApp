package com.example.calendarapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "EventActivity";

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

        // Intent에서 날짜 정보가 있는지 확인
        if (getIntent().hasExtra("selected_date")) {
            long dateMillis = getIntent().getLongExtra("selected_date", System.currentTimeMillis());
            selectedDate.setTimeInMillis(dateMillis);
        }

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
            titleEditText.requestFocus();
            return;
        }

        // 과거 시간 체크
        Calendar eventTime = Calendar.getInstance();
        eventTime.setTime(selectedDate.getTime());
        eventTime.set(Calendar.HOUR_OF_DAY, selectedHour);
        eventTime.set(Calendar.MINUTE, selectedMinute);
        eventTime.set(Calendar.SECOND, 0);
        eventTime.set(Calendar.MILLISECOND, 0);

        if (eventTime.getTimeInMillis() <= System.currentTimeMillis()) {
            if (notificationCheckBox.isChecked()) {
                new AlertDialog.Builder(this)
                        .setTitle("과거 시간 알림")
                        .setMessage("선택한 시간이 이미 지났습니다. 알림을 해제하시겠습니까?")
                        .setPositiveButton("알림 해제", (dialog, which) -> {
                            notificationCheckBox.setChecked(false);
                            proceedWithSaving(title, description);
                        })
                        .setNegativeButton("취소", null)
                        .show();
                return;
            }
        }

        proceedWithSaving(title, description);
    }

    private void proceedWithSaving(String title, String description) {
        Event event = new Event(title, description, selectedDate.getTime(),
                selectedHour, selectedMinute, notificationCheckBox.isChecked());

        long eventId;
        boolean success;

        if (isEditMode) {
            event.setId(editingEvent.getId());
            // 기존 알림 취소
            if (editingEvent.hasNotification()) {
                cancelNotification(editingEvent.getId());
            }
            success = databaseHelper.updateEvent(event);
            eventId = editingEvent.getId();
        } else {
            eventId = databaseHelper.addEvent(event);
            success = eventId != -1;
        }

        if (success) {
            // 새로운 알림 설정
            if (notificationCheckBox.isChecked()) {
                scheduleNotification(event, (int) eventId);
            }

            String message = isEditMode ? "일정이 수정되었습니다" : "일정이 저장되었습니다";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            String message = isEditMode ? "일정 수정에 실패했습니다" : "일정 저장에 실패했습니다";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmation() {
        if (editingEvent == null) return;

        new AlertDialog.Builder(this)
                .setTitle("삭제 확인")
                .setMessage("이 일정을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    boolean success = databaseHelper.deleteEvent(editingEvent.getId());
                    if (success) {
                        // 알림도 함께 취소
                        if (editingEvent.hasNotification()) {
                            cancelNotification(editingEvent.getId());
                        }
                        Toast.makeText(this, "일정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(this, "일정 삭제에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(Event event, int requestCode) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(event.getDate());
            calendar.set(Calendar.HOUR_OF_DAY, event.getHour());
            calendar.set(Calendar.MINUTE, event.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // 과거 시간이면 알림 설정하지 않음
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                Log.w(TAG, "Cannot schedule notification for past time");
                return;
            }

            Intent intent = new Intent(this, NotificationReceiver.class);
            intent.putExtra("title", event.getTitle());
            intent.putExtra("description", event.getDescription());
            intent.putExtra("event_id", requestCode);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, requestCode, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                try {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    Log.d(TAG, "Notification scheduled for: " + calendar.getTime());
                } catch (SecurityException e) {
                    Log.e(TAG, "Permission denied for exact alarm", e);
                    // 정확한 알람 권한이 없는 경우 일반 알람 사용
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error scheduling notification", e);
            Toast.makeText(this, "알림 설정에 실패했습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelNotification(int requestCode) {
        try {
            Intent intent = new Intent(this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, requestCode, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Log.d(TAG, "Notification cancelled for request code: " + requestCode);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error cancelling notification", e);
        }
    }
}