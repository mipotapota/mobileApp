package com.example.calendarapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventAdapter.OnEventClickListener {
    private CalendarView calendarView;
    private ListView eventListView;
    private FloatingActionButton addEventFab;

    private DatabaseHelper databaseHelper;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();

        databaseHelper = new DatabaseHelper(this);
        loadEventsForToday();
    }

    private void initViews() {
        calendarView = findViewById(R.id.calendarView);
        eventListView = findViewById(R.id.eventListView);
        addEventFab = findViewById(R.id.addEventFab);
    }

    private void setupListeners() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            loadEventsForDate(calendar.getTime());
        });

        addEventFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        });
    }

    private void loadEventsForToday() {
        Date today = new Date();
        loadEventsForDate(today);
    }

    private void loadEventsForDate(Date date) {
        List<Event> events = databaseHelper.getEventsForDate(date);
        eventAdapter = new EventAdapter(this, events);
        eventAdapter.setOnEventClickListener(this);
        eventListView.setAdapter(eventAdapter);
    }

    @Override
    public void onEventClick(Event event) {
        // 일정 수정 화면으로 이동
        Intent intent = new Intent(MainActivity.this, EventActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

    @Override
    public void onEventLongClick(Event event) {
        // 길게 누르면 삭제 확인 다이얼로그
        new android.app.AlertDialog.Builder(this)
                .setTitle("일정 삭제")
                .setMessage("'" + event.getTitle() + "' 일정을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    boolean success = databaseHelper.deleteEvent(event.getId());
                    if (success) {
                        cancelNotification(event.getId());
                        Toast.makeText(this, "일정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        loadEventsForToday(); // 목록 새로고침
                    } else {
                        Toast.makeText(this, "일정 삭제에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void cancelNotification(int requestCode) {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEventsForToday();
    }
}