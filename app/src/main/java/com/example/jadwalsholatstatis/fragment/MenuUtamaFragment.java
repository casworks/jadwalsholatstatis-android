package com.example.jadwalsholatstatis.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jadwalsholatstatis.R;

public class MenuUtamaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_utama, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText inputLokasi = view.findViewById(R.id.inputLokasi);
        Button buttonSimpan = view.findViewById(R.id.buttonSimpanLokasi);
        buttonSimpan.setOnClickListener(v -> {
            String lokasi = inputLokasi.getText().toString().trim();
            if (lokasi.isEmpty()) {
                Toast.makeText(requireContext(), R.string.lokasi_empty, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(requireContext(),
                    getString(R.string.lokasi_saved, lokasi),
                    Toast.LENGTH_SHORT).show();
        });

        Button buttonJadwal = view.findViewById(R.id.buttonMenuJadwal);
        Button buttonCountdown = view.findViewById(R.id.buttonMenuCountdown);
        Button buttonPengingat = view.findViewById(R.id.buttonMenuPengingat);

        buttonJadwal.setOnClickListener(v -> navigateToTab(1));
        buttonCountdown.setOnClickListener(v -> navigateToTab(2));
        buttonPengingat.setOnClickListener(v -> navigateToTab(3));
    }

    private void navigateToTab(int index) {
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) {
            viewPager.setCurrentItem(index, true);
        }
    }
}
