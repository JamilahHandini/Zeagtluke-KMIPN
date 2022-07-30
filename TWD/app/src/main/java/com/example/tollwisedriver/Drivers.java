package com.example.tollwisedriver;

public class Drivers {
    private E_tolls e_tolls;
    private String key;
    private String nama, nomor_hp, nomor_e_toll, nomor_sim, plat_nomor, email, password;

    public Drivers(E_tolls e_tolls, String nama, String nomor_hp,
                   String nomor_e_toll, String nomor_sim, String plat_nomor,
                   String email, String password) {
        this.e_tolls = e_tolls;
        this.nama = nama;
        this.nomor_hp = nomor_hp;
        this.nomor_e_toll = nomor_e_toll;
        this.nomor_sim = nomor_sim;
        this.plat_nomor = plat_nomor;
        this.email = email;
        this.password = password;
    }

    public Drivers() {

    }

    public void setE_tolls(E_tolls e_tolls) {
        this.e_tolls = e_tolls;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNomor_hp(String nomor_hp) {
        this.nomor_hp = nomor_hp;
    }

    public void setNomor_e_toll(String nomor_e_toll) {
        this.nomor_e_toll = nomor_e_toll;
    }

    public void setNomor_sim(String nomor_sim) {
        this.nomor_sim = nomor_sim;
    }

    public void setPlat_nomor(String plat_nomor) {
        this.plat_nomor = plat_nomor;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public E_tolls getE_tolls() {
        return e_tolls;
    }

    public String getNama() {
        return nama;
    }

    public String getNomor_hp() {
        return nomor_hp;
    }

    public String getNomor_e_toll() {
        return nomor_e_toll;
    }

    public String getNomor_sim() {
        return nomor_sim;
    }

    public String getPlat_nomor() {
        return plat_nomor;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getKey() {
        return key;
    }
}
