package com.example.tollwisedriver;

public class E_tolls {
        private String nomor_etoll, berlaku_sampai;
        private double saldo;


    public E_tolls(String nomor_etoll, String berlaku_sampai, double saldo) {
        this.nomor_etoll = nomor_etoll;
        this.berlaku_sampai = berlaku_sampai;
        this.saldo = saldo;
    }

    public E_tolls() {

    }


    public void setNomor_etoll(String nomor_etoll) {
        this.nomor_etoll = nomor_etoll;
    }

    public void setBerlaku_sampai(String berlaku_sampai) {
        this.berlaku_sampai = berlaku_sampai;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getNomor_etoll() {
        return nomor_etoll;
    }

    public String getBerlaku_sampai() {
        return berlaku_sampai;
    }

    public double getSaldo() {
        return saldo;
    }
}
