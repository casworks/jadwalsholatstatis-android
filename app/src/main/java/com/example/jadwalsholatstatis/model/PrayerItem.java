package com.example.jadwalsholatstatis.model;

public class PrayerItem {
    private final String nama;
    private final String waktu;

    public PrayerItem(String nama, String waktu) {
        this.nama = nama;
        this.waktu = waktu;
    }

    public String getNama() {
        return nama;
    }

    public String getWaktu() {
        return waktu;
    }
}
