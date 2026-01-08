package com.example.jadwalsholatstatis.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.jadwalsholatstatis.R;

public class PrayerNotificationHelper {

    private static final String CHANNEL_ID = "jadwal_sholat_countdown";
    private static final int NOTIFICATION_ID = 1001;

    public static void showCountdown(Context context, String namaSholat, String countdownText) {
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        ensureChannel(context, manager);

        String title = context.getString(R.string.countdown_notif_title, namaSholat);
        String message = context.getString(R.string.countdown_notif_message, countdownText);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_mosque)
                .setContentTitle(title)
                .setContentText(message)
                .setOnlyAlertOnce(true)
                .setOngoing(true);

        manager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void showArrived(Context context, String namaSholat) {
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        ensureChannel(context, manager);

        String title = context.getString(R.string.countdown_notif_arrived_title, namaSholat);
        String message = context.getString(R.string.countdown_notif_arrived_message);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_mosque)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private static void ensureChannel(Context context, NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.countdown_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }
    }
}
