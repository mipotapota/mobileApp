package com.example.calendarapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationReceiver";
    private static final String CHANNEL_ID = "calendar_notifications";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Notification received");

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int eventId = intent.getIntExtra("event_id", 0);

        if (title == null) {
            title = "캘린더 알림";
        }
        if (description == null || description.trim().isEmpty()) {
            description = "예정된 일정입니다.";
        }

        createNotificationChannel(context);
        showHeadsUpNotification(context, title, description, eventId);
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "캘린더 알림";
            String description = "캘린더 이벤트 알림";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            // 채널 설정 - Heads-up notification을 위한 설정
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.setShowBadge(true);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

            // 기본 알림음 설정
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(defaultSoundUri, audioAttributes);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showHeadsUpNotification(Context context, String title, String description, int eventId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                eventId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 액션 버튼 추가
        Intent dismissIntent = new Intent(context, NotificationActionReceiver.class);
        dismissIntent.setAction("DISMISS");
        dismissIntent.putExtra("notification_id", eventId);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(
                context,
                eventId * 1000,
                dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent snoozeIntent = new Intent(context, NotificationActionReceiver.class);
        snoozeIntent.setAction("SNOOZE");
        snoozeIntent.putExtra("notification_id", eventId);
        snoozeIntent.putExtra("title", title);
        snoozeIntent.putExtra("description", description);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(
                context,
                eventId * 1001,
                snoozeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("🔔 " + title)
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(description))
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 높은 우선순위
                .setCategory(NotificationCompat.CATEGORY_ALARM) // 알람 카테고리
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setShowWhen(true)
                .setWhen(System.currentTimeMillis())
                .setOnlyAlertOnce(false) // 매번 알림음 재생
                .setFullScreenIntent(pendingIntent, true) // Heads-up notification을 위한 설정
                // 액션 버튼 추가
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "닫기", dismissPendingIntent)
                .addAction(android.R.drawable.ic_media_pause, "5분 후", snoozePendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(eventId, builder.build());
            Log.d(TAG, "Heads-up notification shown for event ID: " + eventId);
        }
    }
}