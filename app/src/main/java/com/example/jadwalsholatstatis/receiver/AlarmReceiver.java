package com.example.jadwalsholatstatis.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.jadwalsholatstatis.R;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_NAMA_SHOLAT = "extra_nama_sholat";
    public static final String EXTRA_WAKTU_SHOLAT = "extra_waktu_sholat";
    private static final String CHANNEL_ID = "jadwal_sholat_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String namaSholat = intent.getStringExtra(EXTRA_NAMA_SHOLAT);
        String waktuSholat = intent.getStringExtra(EXTRA_WAKTU_SHOLAT);
        String title = context.getString(R.string.notif_title, namaSholat);
        String message = context.getString(R.string.notif_message, waktuSholat);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.notif_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_mosque)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
