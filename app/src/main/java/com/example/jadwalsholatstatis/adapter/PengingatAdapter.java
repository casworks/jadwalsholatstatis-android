package com.example.jadwalsholatstatis.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jadwalsholatstatis.R;
import com.example.jadwalsholatstatis.model.PrayerItem;

import java.util.ArrayList;
import java.util.List;

public class PengingatAdapter extends RecyclerView.Adapter<PengingatAdapter.PengingatViewHolder> {

    public interface OnReminderToggleListener {
        void onToggle(PrayerItem item, boolean isChecked, int position);
    }

    private final List<PrayerItem> items = new ArrayList<>();
    private final List<Boolean> states = new ArrayList<>();
    private final OnReminderToggleListener listener;

    public PengingatAdapter(OnReminderToggleListener listener) {
        this.listener = listener;
    }

    public void setItems(List<PrayerItem> newItems) {
        items.clear();
        items.addAll(newItems);
        states.clear();
        for (int i = 0; i < newItems.size(); i++) {
            states.add(false);
        }
        notifyDataSetChanged();
    }

    public void setChecked(int position, boolean isChecked) {
        if (position < 0 || position >= states.size()) {
            return;
        }
        states.set(position, isChecked);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public PengingatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reminder, parent, false);
        return new PengingatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengingatViewHolder holder, int position) {
        PrayerItem item = items.get(position);
        holder.tvNama.setText(item.getNama());
        holder.tvWaktu.setText(item.getWaktu());
        holder.switchReminder.setOnCheckedChangeListener(null);
        holder.switchReminder.setChecked(states.get(position));
        holder.switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            states.set(position, isChecked);
            if (listener != null) {
                listener.onToggle(item, isChecked, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PengingatViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNama;
        private final TextView tvWaktu;
        private final SwitchCompat switchReminder;

        PengingatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvReminderNama);
            tvWaktu = itemView.findViewById(R.id.tvReminderWaktu);
            switchReminder = itemView.findViewById(R.id.switchReminder);
        }
    }
}
