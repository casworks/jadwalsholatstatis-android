package com.example.jadwalsholatstatis.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import com.example.jadwalsholatstatis.R;
import com.example.jadwalsholatstatis.adapter.PengingatAdapter;
import com.example.jadwalsholatstatis.data.PrayerData;
import com.example.jadwalsholatstatis.model.PrayerItem;
import com.example.jadwalsholatstatis.receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.List;

public class PengingatFragment extends Fragment implements PengingatAdapter.OnReminderToggleListener {

    private PrayerData prayerData;
    private PengingatAdapter pengingatAdapter;

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
            setAlarm(item, position);
        } else {
            cancelAlarm(position);
        }
    }

    private void setAlarm(PrayerItem item, int requestCode) {
        Context context = requireContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        String[] parts = item.getWaktu().split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        PendingIntent pendingIntent = buildPendingIntent(context, item, requestCode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void cancelAlarm(int requestCode) {
        Context context = requireContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }
        PendingIntent pendingIntent = buildPendingIntent(context, null, requestCode);
        alarmManager.cancel(pendingIntent);
    }

    private PendingIntent buildPendingIntent(Context context, PrayerItem item, int requestCode) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        if (item != null) {
            intent.putExtra(AlarmReceiver.EXTRA_NAMA_SHOLAT, item.getNama());
            intent.putExtra(AlarmReceiver.EXTRA_WAKTU_SHOLAT, item.getWaktu());
        }
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        return PendingIntent.getBroadcast(context, requestCode, intent, flags);
    }
}
