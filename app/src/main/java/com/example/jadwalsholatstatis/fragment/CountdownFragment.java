package com.example.jadwalsholatstatis.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jadwalsholatstatis.R;
import com.example.jadwalsholatstatis.data.PrayerData;
import com.example.jadwalsholatstatis.model.PrayerItem;
import com.example.jadwalsholatstatis.notification.PrayerNotificationHelper;
import com.example.jadwalsholatstatis.util.TimeUtil;

import java.time.LocalDate;
import java.util.List;

public class CountdownFragment extends Fragment {

    private TextView tvTarget;
    private TextView tvCountdown;
    private Spinner spinnerKota;
    private PrayerData prayerData;
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_countdown, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTarget = view.findViewById(R.id.tvTargetSholat);
        tvCountdown = view.findViewById(R.id.tvCountdown);
        spinnerKota = view.findViewById(R.id.spinnerCountdownKota);

        prayerData = new PrayerData();
        setupSpinner();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopTimer();
    }

    private void setupSpinner() {
        String[] kota = getResources().getStringArray(R.array.kota_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                kota);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKota.setAdapter(adapter);
        spinnerKota.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                startCountdown(kota[position]);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        if (kota.length > 0) {
            startCountdown(kota[0]);
        }
    }

    private void startCountdown(String kota) {
        stopTimer();
        LocalDate today = LocalDate.now();
        List<PrayerItem> items = prayerData.getPrayerItemsForDate(kota, today);
        TimeUtil.NextPrayer nextPrayer = TimeUtil.getNextPrayer(items, today);

        tvTarget.setText(getString(R.string.countdown_target, nextPrayer.getNama(), nextPrayer.getWaktu()));
        updateCountdown(nextPrayer.getNama(), nextPrayer.getMillisUntil());

        countDownTimer = new CountDownTimer(nextPrayer.getMillisUntil(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdown(nextPrayer.getNama(), millisUntilFinished);
            }

            @Override
            public void onFinish() {
                PrayerNotificationHelper.showArrived(requireContext(), nextPrayer.getNama());
                startCountdown(kota);
            }
        }.start();
    }

    private void updateCountdown(String namaSholat, long millisUntil) {
        String countdownText = TimeUtil.formatCountdown(millisUntil);
        tvCountdown.setText(getString(R.string.countdown_remaining, countdownText));
        PrayerNotificationHelper.showCountdown(requireContext(), namaSholat, countdownText);
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
