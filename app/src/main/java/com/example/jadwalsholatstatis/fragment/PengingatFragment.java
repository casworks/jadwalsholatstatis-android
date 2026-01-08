package com.example.jadwalsholatstatis.fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;

import com.example.jadwalsholatstatis.R;
import com.example.jadwalsholatstatis.adapter.PengingatAdapter;
import com.example.jadwalsholatstatis.data.PrayerData;
import com.example.jadwalsholatstatis.model.PrayerItem;
import com.example.jadwalsholatstatis.receiver.AlarmReceiver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class PengingatFragment extends Fragment implements PengingatAdapter.OnReminderToggleListener {

    private static final int MAX_COUNTDOWN_MINUTES = 30;
    private static final int ARRIVAL_OFFSET = 1500;
    private static final int REQUEST_NOTIFICATIONS = 100;
    private PrayerData prayerData;
    private PengingatAdapter pengingatAdapter;
    private PrayerItem pendingItem;
    private int pendingPosition = RecyclerView.NO_POSITION;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pengingat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerPengingat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        prayerData = new PrayerData();
        pengingatAdapter = new PengingatAdapter(this);
        recyclerView.setAdapter(pengingatAdapter);

        List<PrayerItem> items = prayerData.getDefaultReminderItems();
        pengingatAdapter.setItems(items);
    }

    @Override
    public void onToggle(PrayerItem item, boolean isChecked, int position) {
        if (isChecked) {
            if (!hasNotificationPermission()) {
                pendingItem = item;
                pendingPosition = position;
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATIONS);
                pengingatAdapter.setChecked(position, false);
                return;
            }
            setReminderAlarms(item, position);
        } else {
            pendingItem = null;
            pendingPosition = RecyclerView.NO_POSITION;
            cancelReminderAlarms(position);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATIONS) {
            boolean granted = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (granted && pendingItem != null && pendingPosition != RecyclerView.NO_POSITION) {
                pengingatAdapter.setChecked(pendingPosition, true);
                setReminderAlarms(pendingItem, pendingPosition);
            }
            pendingItem = null;
            pendingPosition = RecyclerView.NO_POSITION;
        }
    }

    private boolean hasNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true;
        }
        return ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    private void setReminderAlarms(PrayerItem item, int position) {
        if (item == null || item.getWaktu() == null || item.getWaktu().isEmpty()) {
            return;
        }
        Context context = requireContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDate targetDate = LocalDate.now();
        LocalTime targetTime = LocalTime.parse(item.getWaktu());
        LocalDateTime targetDateTime = LocalDateTime.of(targetDate, targetTime);
        if (!targetDateTime.isAfter(now)) {
            targetDateTime = targetDateTime.plusDays(1);
        }

        long minutesUntil = Duration.between(now, targetDateTime).toMinutes();
        if (minutesUntil <= 0) {
            return;
        }
        int countdownMinutes = (int) Math.min(MAX_COUNTDOWN_MINUTES, minutesUntil);

        for (int minutesLeft = countdownMinutes; minutesLeft >= 1; minutesLeft--) {
            LocalDateTime notifyTime = targetDateTime.minusMinutes(minutesLeft);
            scheduleExact(alarmManager,
                    notifyTime,
                    buildPendingIntent(context, item, getRequestCode(position, minutesLeft),
                            AlarmReceiver.TYPE_COUNTDOWN, minutesLeft));
        }

        scheduleExact(alarmManager,
                targetDateTime,
                buildPendingIntent(context, item, getRequestCode(position, ARRIVAL_OFFSET),
                        AlarmReceiver.TYPE_ARRIVED, 0));
    }

    private void cancelReminderAlarms(int position) {
        Context context = requireContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }
        for (int minutesLeft = 1; minutesLeft <= MAX_COUNTDOWN_MINUTES; minutesLeft++) {
            PendingIntent pendingIntent = buildPendingIntent(context, null,
                    getRequestCode(position, minutesLeft),
                    AlarmReceiver.TYPE_COUNTDOWN, minutesLeft);
            alarmManager.cancel(pendingIntent);
        }
        PendingIntent arrivalIntent = buildPendingIntent(context, null,
                getRequestCode(position, ARRIVAL_OFFSET),
                AlarmReceiver.TYPE_ARRIVED, 0);
        alarmManager.cancel(arrivalIntent);
    }

    private PendingIntent buildPendingIntent(Context context, PrayerItem item, int requestCode,
                                             String type, int minutesLeft) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        if (item != null) {
            intent.putExtra(AlarmReceiver.EXTRA_NAMA_SHOLAT, item.getNama());
            intent.putExtra(AlarmReceiver.EXTRA_WAKTU_SHOLAT, item.getWaktu());
        }
        intent.putExtra(AlarmReceiver.EXTRA_TYPE, type);
        intent.putExtra(AlarmReceiver.EXTRA_MINUTES_LEFT, minutesLeft);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        return PendingIntent.getBroadcast(context, requestCode, intent, flags);
    }

    private void scheduleExact(AlarmManager alarmManager, LocalDateTime dateTime, PendingIntent intent) {
        long triggerAtMillis = toMillis(dateTime);
        if (triggerAtMillis <= System.currentTimeMillis()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, intent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, intent);
        }
    }

    private int getRequestCode(int position, int offset) {
        return position * 2000 + offset;
    }

    private long toMillis(LocalDateTime dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, dateTime.getYear());
        calendar.set(Calendar.MONTH, dateTime.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dateTime.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, dateTime.getHour());
        calendar.set(Calendar.MINUTE, dateTime.getMinute());
        calendar.set(Calendar.SECOND, dateTime.getSecond());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
