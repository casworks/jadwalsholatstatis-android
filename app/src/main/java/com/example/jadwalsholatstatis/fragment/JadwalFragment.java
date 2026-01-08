package com.example.jadwalsholatstatis.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jadwalsholatstatis.R;
import com.example.jadwalsholatstatis.adapter.JadwalAdapter;
import com.example.jadwalsholatstatis.data.PrayerData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JadwalFragment extends Fragment {

    private TextView tvTanggal;
    private Spinner spinnerKota;
    private JadwalAdapter jadwalAdapter;
    private PrayerData prayerData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jadwal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTanggal = view.findViewById(R.id.tvTanggal);
        spinnerKota = view.findViewById(R.id.spinnerKota);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerJadwal);

        prayerData = new PrayerData();
        jadwalAdapter = new JadwalAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(jadwalAdapter);

        tampilkanTanggal();
        setupSpinner();
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
                new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item,
                        kota);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKota.setAdapter(adapter);

        spinnerKota.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String namaKota = kota[position];
                jadwalAdapter.setItems(prayerData.getPrayerItems(namaKota));
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        if (kota.length > 0) {
            jadwalAdapter.setItems(prayerData.getPrayerItems(kota[0]));
        }
    }
}
