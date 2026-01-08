package com.example.jadwalsholatstatis;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvTanggal;
    private TextView tvNamaKota;
    private TextView tvSubuh;
    private TextView tvDzuhur;
    private TextView tvAshar;
    private TextView tvMaghrib;
    private TextView tvIsya;
    private Spinner spinnerKota;
    private Button btnRefresh;

    private PrayerData prayerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTanggal = findViewById(R.id.tvTanggal);
        tvNamaKota = findViewById(R.id.tvNamaKota);
        tvSubuh = findViewById(R.id.tvSubuh);
        tvDzuhur = findViewById(R.id.tvDzuhur);
        tvAshar = findViewById(R.id.tvAshar);
        tvMaghrib = findViewById(R.id.tvMaghrib);
        tvIsya = findViewById(R.id.tvIsya);
        spinnerKota = findViewById(R.id.spinnerKota);
        btnRefresh = findViewById(R.id.btnRefresh);

        prayerData = new PrayerData();

        tampilkanTanggal();
        setupSpinner();
        tampilkanJadwal(spinnerKota.getSelectedItem().toString());

        btnRefresh.setOnClickListener(v ->
                tampilkanJadwal(spinnerKota.getSelectedItem().toString()));
    }

    private void tampilkanTanggal() {
        String tanggal = new SimpleDateFormat(
                "EEEE, dd MMMM yyyy",
                new Locale("id", "ID")
        ).format(new Date());
        tvTanggal.setText(tanggal);
    }

    private void setupSpinner() {
        String[] kota = getResources().getStringArray(R.array.kota_list);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        kota);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinnerKota.setAdapter(adapter);
    }

    private void tampilkanJadwal(String namaKota) {
        PrayerTime waktu = prayerData.getPrayerTime(namaKota);
        if (waktu == null) {
            return;
        }

        tvNamaKota.setText(namaKota);
        tvSubuh.setText(getString(R.string.label_subuh, waktu.getSubuh()));
        tvDzuhur.setText(getString(R.string.label_dzuhur, waktu.getDzuhur()));
        tvAshar.setText(getString(R.string.label_ashar, waktu.getAshar()));
        tvMaghrib.setText(getString(R.string.label_maghrib, waktu.getMaghrib()));
        tvIsya.setText(getString(R.string.label_isya, waktu.getIsya()));
    }
}
