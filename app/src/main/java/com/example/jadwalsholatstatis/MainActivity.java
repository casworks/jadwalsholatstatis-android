package com.example.jadwalsholatstatis;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView tvTanggal, tvSubuh, tvDzuhur, tvAshar, tvMaghrib, tvIsya;
    Spinner spinnerKota;

    HashMap<String, String[]> jadwalSholat = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTanggal = findViewById(R.id.tvTanggal);
        tvSubuh = findViewById(R.id.tvSubuh);
        tvDzuhur = findViewById(R.id.tvDzuhur);
        tvAshar = findViewById(R.id.tvAshar);
        tvMaghrib = findViewById(R.id.tvMaghrib);
        tvIsya = findViewById(R.id.tvIsya);
        spinnerKota = findViewById(R.id.spinnerKota);

        tampilkanTanggal();
        setupData();
        setupSpinner();
    }

    void tampilkanTanggal() {
        String tanggal = new SimpleDateFormat(
                "EEEE, dd MMMM yyyy",
                new Locale("id", "ID")
        ).format(new Date());
        tvTanggal.setText(tanggal);
    }

    void setupData() {
        jadwalSholat.put("Surabaya",
                new String[]{"04:18", "11:38", "14:58", "17:33", "18:43"});

        jadwalSholat.put("Jakarta",
                new String[]{"04:20", "11:40", "15:00", "17:35", "18:45"});
    }

    void setupSpinner() {
        String[] kota = {"Surabaya", "Jakarta"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        kota);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinnerKota.setAdapter(adapter);

        spinnerKota.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        String namaKota = kota[position];
                        String[] waktu = jadwalSholat.get(namaKota);

                        tvSubuh.setText("Subuh: " + waktu[0]);
                        tvDzuhur.setText("Dzuhur: " + waktu[1]);
                        tvAshar.setText("Ashar: " + waktu[2]);
                        tvMaghrib.setText("Maghrib: " + waktu[3]);
                        tvIsya.setText("Isya: " + waktu[4]);
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                });
    }
}
