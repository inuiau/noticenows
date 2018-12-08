package com.devdeloop.noticenows.Model;

import java.io.Serializable;

public class Pengumuman implements Serializable{
    private static  final long id = 1L;

    private String id_artikel;
    private String judul;
    private String kategori;
    private String konten;
    private String tanggal_dibuat;

    public Pengumuman(){

    }
    public String getId_artikel() {
        return id_artikel;
    }

    public void setId_artikel(String id_artikel) {
        this.id_artikel = id_artikel;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public String getTanggal_dibuat() {
        return tanggal_dibuat;
    }

    public void setTanggal_dibuat(String tanggal_dibuat) {
        this.tanggal_dibuat = tanggal_dibuat;
    }
}
