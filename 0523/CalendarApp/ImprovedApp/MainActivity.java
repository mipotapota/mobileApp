package com.example.calendarapp;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements EventAdapter.OnEventClickListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_EVENT = 1;
    private static final int REQUEST_CODE_NOTIFICATION_PERMISSION = 2;
    private static final int REQUEST_CODE_EXACT_ALARM_PERMISSION = 3;

    private CalendarView calendarView;
    private ListView eventListView;
    private FloatingActionButton addEventFab;

    private DatabaseHelper databaseHelper;
    private EventAdapter eventAdapter;
    private Date currentSelectedDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();

        databaseHelper = new DatabaseHelper(this);
        currentSelectedDate = new Date(); // 오늘 날짜로 초기화

        // 제목 설정
        setTitle("캘린더 앱");

        // 권한 요청
        requestNecessaryPermissions();

        loadEventsForDate(currentSelectedDate);
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
            currentSelectedDate = calendar.getTime();
            Log.d(TAG, "Selected date: " + dateFormat.format(currentSelectedDate));
            loadEventsForDate(currentSelectedDate);
        });

        addEventFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            // 현재 선택된 날짜를 전달
            intent.putExtra("selected_date", currentSelectedDate.getTime());
            startActivityForResult(intent, REQUEST_CODE_EVENT);
        });
    }

    private void requestNecessaryPermissions() {
        // Android 13+ 알림 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_CODE_NOTIFICATION_PERMISSION);
            } else {
                checkExactAlarmPermission();
            }
        } else {
            checkExactAlarmPermission();
        }
    }

    private void checkExactAlarmPermission() {
        // Android 12+ 정확한 알람 권한 확인
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                showExactAlarmPermissionDialog();
            }
        }
    }

    private void showExactAlarmPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("정확한 알람 권한 필요")
                .setMessage("일정 알림을 정확한 시간에 받으려면 '정확한 알람' 권한이 필요합니다.\n\n설정에서 권한을 허용해주세요.")
                .setPositiveButton("설정으로 이동", (dialog, which) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, REQUEST_CODE_EXACT_ALARM_PERMISSION);
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "알림 권한이 허용되었습니다", Toast.LENGTH_SHORT).show();
                checkExactAlarmPermission();
            } else {
                Toast.makeText(this, "알림 권한이 거부되어 알림을 받을 수 없습니다", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void loadEventsForDate(Date date) {
        try {
            List<Event> events = databaseHelper.getEventsForDate(date);
            eventAdapter = new EventAdapter(this, events);
            eventAdapter.setOnEventClickListener(this);
            eventListView.setAdapter(eventAdapter);

            Log.d(TAG, "Loaded " + events.size() + " events for date: " + dateFormat.format(date));

            // 사용자에게 선택된 날짜와 일정 개수 알림
            if (events.isEmpty()) {
                Toast.makeText(this, dateFormat.format(date) + "에 등록된 일정이 없습니다", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, dateFormat.format(date) + "에 " + events.size() + "개의 일정이 있습니다", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading events", e);
            Toast.makeText(this, "일정을 불러오는데 실패했습니다", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEventClick(Event event) {
        // 일정 수정 화면으로 이동
        Intent intent = new Intent(MainActivity.this, EventActivity.class);
        intent.putExtra("event", event);
        startActivityForResult(intent, REQUEST_CODE_EVENT);
    }

    @Override
    public void onEventLongClick(Event event) {
        // 길게 누르면 삭제 확인 다이얼로그
        new AlertDialog.Builder(this)
                .setTitle("일정 삭제")
                .setMessage("'" + event.getTitle() + "' 일정을 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    boolean success = databaseHelper.deleteEvent(event.getId());
                    if (success) {
                        // 알림도 함께 취소
                        if (event.hasNotification()) {
                            cancelNotification(event.getId());
                        }
                        Toast.makeText(this, "일정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        loadEventsForDate(currentSelectedDate); // 현재 선택된 날짜의 목록 새로고침
                    } else {
                        Toast.makeText(this, "일정 삭제에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EVENT && resultCode == RESULT_OK) {
            // EventActivity에서 돌아왔을 때 목록 새로고침
            loadEventsForDate(currentSelectedDate);
        } else if (requestCode == REQUEST_CODE_EXACT_ALARM_PERMISSION) {
            // 정확한 알람 권한 설정에서 돌아왔을 때
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null && alarmManager.canScheduleExactAlarms()) {
                    Toast.makeText(this, "정확한 알람 권한이 허용되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "정확한 알람 권한이 거부되어 알림이 정확하지 않을 수 있습니다", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 앱이 다시 활성화될 때 목록 새로고침
        loadEventsForDate(currentSelectedDate);
    }
}