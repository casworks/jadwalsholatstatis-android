package com.example.jadwalsholatstatis.data;

import com.example.jadwalsholatstatis.model.PrayerItem;
import com.example.jadwalsholatstatis.model.PrayerTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrayerData {
    private final Map<String, PrayerTime> jadwal = new HashMap<>();
    private final Map<String, Map<LocalDate, PrayerTime>> jadwalHarian = new HashMap<>();

    public PrayerData() {
        jadwal.put("Surabaya", new PrayerTime("04:18", "11:38", "14:58", "17:33", "18:43"));
        jadwal.put("Jakarta", new PrayerTime("04:20", "11:40", "15:00", "17:35", "18:45"));
        jadwal.put("Bandung", new PrayerTime("04:24", "11:45", "15:05", "17:40", "18:50"));

        seedJanuariData();
    }

    public PrayerTime getPrayerTime(String kota) {
        return jadwal.get(kota);
    }

    public List<PrayerItem> getPrayerItems(String kota) {
        return getPrayerItemsForDate(kota, LocalDate.now());
    }

    public List<PrayerItem> getPrayerItemsForDate(String kota, LocalDate tanggal) {
        PrayerTime waktu = getPrayerTimeForDate(kota, tanggal);
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
        return getPrayerItemsForDate("Surabaya", LocalDate.now());
    }

    public PrayerTime getPrayerTimeForDate(String kota, LocalDate tanggal) {
        Map<LocalDate, PrayerTime> map = jadwalHarian.get(kota);
        if (map != null && map.containsKey(tanggal)) {
            return map.get(tanggal);
        }
        return getPrayerTime(kota);
    }

    private void seedJanuariData() {
        int year = LocalDate.now().getYear();
        Map<LocalDate, PrayerTime> surabaya = new HashMap<>();
        surabaya.put(LocalDate.of(year, 1, 7), new PrayerTime("03:54", "11:35", "15:01", "17:51", "19:07"));
        surabaya.put(LocalDate.of(year, 1, 8), new PrayerTime("03:55", "11:35", "15:02", "17:51", "19:07"));
        surabaya.put(LocalDate.of(year, 1, 9), new PrayerTime("03:55", "11:36", "15:02", "17:52", "19:07"));
        surabaya.put(LocalDate.of(year, 1, 10), new PrayerTime("03:56", "11:36", "15:02", "17:52", "19:08"));
        surabaya.put(LocalDate.of(year, 1, 11), new PrayerTime("03:57", "11:37", "15:02", "17:52", "19:08"));
        surabaya.put(LocalDate.of(year, 1, 12), new PrayerTime("03:57", "11:37", "15:02", "17:52", "19:08"));
        surabaya.put(LocalDate.of(year, 1, 13), new PrayerTime("03:58", "11:37", "15:03", "17:53", "19:08"));
        jadwalHarian.put("Surabaya", surabaya);

        Map<LocalDate, PrayerTime> jakarta = new HashMap<>();
        jakarta.put(LocalDate.of(year, 1, 7), new PrayerTime("04:20", "11:59", "15:25", "18:13", "19:28"));
        jakarta.put(LocalDate.of(year, 1, 8), new PrayerTime("04:21", "11:59", "15:25", "18:13", "19:29"));
        jakarta.put(LocalDate.of(year, 1, 9), new PrayerTime("04:21", "11:59", "15:26", "18:13", "19:29"));
        jakarta.put(LocalDate.of(year, 1, 10), new PrayerTime("04:22", "12:00", "15:26", "18:14", "19:29"));
        jakarta.put(LocalDate.of(year, 1, 11), new PrayerTime("04:22", "12:00", "15:26", "18:14", "19:29"));
        jakarta.put(LocalDate.of(year, 1, 12), new PrayerTime("04:23", "12:01", "15:26", "18:14", "19:30"));
        jakarta.put(LocalDate.of(year, 1, 13), new PrayerTime("04:24", "12:01", "15:27", "18:15", "19:30"));
        jadwalHarian.put("Jakarta", jakarta);

        Map<LocalDate, PrayerTime> bandung = new HashMap<>();
        bandung.put(LocalDate.of(year, 1, 7), new PrayerTime("04:15", "11:55", "15:22", "18:11", "19:27"));
        bandung.put(LocalDate.of(year, 1, 8), new PrayerTime("04:16", "11:56", "15:22", "18:11", "19:27"));
        bandung.put(LocalDate.of(year, 1, 9), new PrayerTime("04:17", "11:56", "15:22", "18:12", "19:27"));
        bandung.put(LocalDate.of(year, 1, 10), new PrayerTime("04:17", "11:57", "15:23", "18:12", "19:27"));
        bandung.put(LocalDate.of(year, 1, 11), new PrayerTime("04:18", "11:57", "15:23", "18:12", "19:28"));
        bandung.put(LocalDate.of(year, 1, 12), new PrayerTime("04:18", "11:58", "15:23", "18:12", "19:28"));
        bandung.put(LocalDate.of(year, 1, 13), new PrayerTime("04:19", "11:58", "15:23", "18:13", "19:28"));
        jadwalHarian.put("Bandung", bandung);
    }
}
