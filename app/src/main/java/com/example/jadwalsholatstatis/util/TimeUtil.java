package com.example.jadwalsholatstatis.util;

import com.example.jadwalsholatstatis.model.PrayerItem;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TimeUtil {

    public static class NextPrayer {
        private final String nama;
        private final String waktu;
        private final long millisUntil;

        public NextPrayer(String nama, String waktu, long millisUntil) {
            this.nama = nama;
            this.waktu = waktu;
            this.millisUntil = millisUntil;
        }

        public String getNama() {
            return nama;
        }

        public String getWaktu() {
            return waktu;
        }

        public long getMillisUntil() {
            return millisUntil;
        }
    }

    public static NextPrayer getNextPrayer(List<PrayerItem> items, LocalDate tanggal) {
        LocalDateTime now = LocalDateTime.now();
        for (PrayerItem item : items) {
            LocalDateTime target = LocalDateTime.of(tanggal, parseTime(item.getWaktu()));
            if (target.isAfter(now)) {
                long millis = Duration.between(now, target).toMillis();
                return new NextPrayer(item.getNama(), item.getWaktu(), millis);
            }
        }
        if (!items.isEmpty()) {
            PrayerItem first = items.get(0);
            LocalDateTime target = LocalDateTime.of(tanggal.plusDays(1), parseTime(first.getWaktu()));
            long millis = Duration.between(now, target).toMillis();
            return new NextPrayer(first.getNama(), first.getWaktu(), millis);
        }
        return new NextPrayer("-", "-", 0);
    }

    public static String formatCountdown(long millis) {
        long totalSeconds = Math.max(0, millis / 1000);
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("%02d:%02d", minutes, seconds);
    }

    private static LocalTime parseTime(String time) {
        return LocalTime.parse(time);
    }
}
