package com.example.jadwalsholatstatis.data;

import com.example.jadwalsholatstatis.model.PrayerItem;
import com.example.jadwalsholatstatis.model.PrayerTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrayerData {
    private final Map<String, PrayerTime> jadwal = new HashMap<>();

    public PrayerData() {
        jadwal.put("Surabaya", new PrayerTime("04:18", "11:38", "14:58", "17:33", "18:43"));
        jadwal.put("Jakarta", new PrayerTime("04:20", "11:40", "15:00", "17:35", "18:45"));
        jadwal.put("Bandung", new PrayerTime("04:24", "11:45", "15:05", "17:40", "18:50"));
    }

    public PrayerTime getPrayerTime(String kota) {
        return jadwal.get(kota);
    }

    public List<PrayerItem> getPrayerItems(String kota) {
        PrayerTime waktu = getPrayerTime(kota);
        List<PrayerItem> items = new ArrayList<>();
        if (waktu == null) {
            return items;
        }
        items.add(new PrayerItem("Subuh", waktu.getSubuh()));
        items.add(new PrayerItem("Dzuhur", waktu.getDzuhur()));
        items.add(new PrayerItem("Ashar", waktu.getAshar()));
        items.add(new PrayerItem("Maghrib", waktu.getMaghrib()));
        items.add(new PrayerItem("Isya", waktu.getIsya()));
        return items;
    }

    public List<PrayerItem> getDefaultReminderItems() {
        return getPrayerItems("Surabaya");
    }
}
