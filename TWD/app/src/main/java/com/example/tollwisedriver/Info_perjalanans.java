package com.example.tollwisedriver;

public class Info_perjalanans {
    private String id, tanggal;
    private  boolean status_perjalanan;
    private  int jumlah_penumpang;

    public Info_perjalanans(String id, String tanggal, boolean status_perjalanan, int jumlah_penumpang) {
        this.id = id;
        this.tanggal = tanggal;
        this.status_perjalanan = status_perjalanan;
        this.jumlah_penumpang = jumlah_penumpang;
    }

    public  Info_perjalanans(){

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setStatus_perjalanan(boolean status_perjalanan) {
        this.status_perjalanan = status_perjalanan;
    }

    public void setJumlah_penumpang(int jumlah_penumpang) {
        this.jumlah_penumpang = jumlah_penumpang;
    }

    public String getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public boolean isStatus_perjalanan() {
        return status_perjalanan;
    }

    public int getJumlah_penumpang() {
        return jumlah_penumpang;
    }
}
