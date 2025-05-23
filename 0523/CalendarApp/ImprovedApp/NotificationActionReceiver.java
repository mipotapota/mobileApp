package com.example.calendarapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationActionReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationActionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int notificationId = intent.getIntExtra("notification_id", 0);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if ("DISMISS".equals(action)) {
            // 알림 닫기
            if (notificationManager != null) {
                notificationManager.cancel(notificationId);
            }
            Log.d(TAG, "Notification dismissed: " + notificationId);

        } else if ("SNOOZE".equals(action)) {
            // 5분 후 다시 알림
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");

            // 현재 알림 닫기
            if (notificationManager != null) {
                notificationManager.cancel(notificationId);
            }

            // 5분 후 알림 예약
            scheduleSnoozeNotification(context, title, description, notificationId);

            // 사용자에게 피드백
            Toast.makeText(context, "5분 후 다시 알림됩니다", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Notification snoozed for 5 minutes: " + notificationId);
        }
    }

    private void scheduleSnoozeNotification(Context context, String title, String description, int notificationId) {
        try {
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            intent.putExtra("event_id", notificationId);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId + 10000, // 다른 ID 사용
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                long triggerTime = System.currentTimeMillis() + (5 * 60 * 1000); // 5분 후

                try {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                } catch (SecurityException e) {
                    Log.e(TAG, "Permission denied for exact alarm", e);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                }

                Log.d(TAG, "Snooze notification scheduled for 5 minutes later");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error scheduling snooze notification", e);
        }
    }
}