package com.example.jadwalsholatstatis;

public class PrayerTime {
    private final String subuh;
    private final String dzuhur;
    private final String ashar;
    private final String maghrib;
    private final String isya;

    public PrayerTime(String subuh, String dzuhur, String ashar, String maghrib, String isya) {
        this.subuh = subuh;
        this.dzuhur = dzuhur;
        this.ashar = ashar;
        this.maghrib = maghrib;
        this.isya = isya;
    }

    public String getSubuh() {
        return subuh;
    }

    public String getDzuhur() {
        return dzuhur;
    }

    public String getAshar() {
        return ashar;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public String getIsya() {
        return isya;
    }
}
