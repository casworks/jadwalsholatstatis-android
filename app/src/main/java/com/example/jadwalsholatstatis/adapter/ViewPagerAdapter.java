package com.example.jadwalsholatstatis.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jadwalsholatstatis.fragment.JadwalFragment;
import com.example.jadwalsholatstatis.fragment.PengingatFragment;
import com.example.jadwalsholatstatis.fragment.TentangFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new PengingatFragment();
        }
        if (position == 2) {
            return new TentangFragment();
        }
        return new JadwalFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
