package com.example.jadwalsholatstatis.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jadwalsholatstatis.R;
import com.example.jadwalsholatstatis.model.PrayerItem;

import java.util.ArrayList;
import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> {

    private final List<PrayerItem> items = new ArrayList<>();

    public void setItems(List<PrayerItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JadwalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prayer_time, parent, false);
        return new JadwalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalViewHolder holder, int position) {
        PrayerItem item = items.get(position);
        holder.tvNama.setText(item.getNama());
        holder.tvWaktu.setText(item.getWaktu());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class JadwalViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNama;
        private final TextView tvWaktu;

        JadwalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaSholat);
            tvWaktu = itemView.findViewById(R.id.tvWaktuSholat);
        }
    }
}
