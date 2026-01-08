package com.example.jadwalsholatstatis;

import java.util.HashMap;
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
}
