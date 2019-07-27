package com.example.futsalgo.data.model;

public class Lapangan {
    Integer id;
    String nama, harga, telp, alamat, longitude, latitude, foto, bank, nama_rekening, no_rekening;

    public Lapangan(Integer id, String nama, String harga, String foto, String telp, String alamat, String latitude, String longitude, String bank, String nama_rekening, String no_rekening) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.telp = telp;
        this.alamat = alamat;
        this.longitude = longitude;
        this.latitude = latitude;
        this.foto = foto;
        this.bank = bank;
        this.nama_rekening = nama_rekening;
        this.no_rekening = no_rekening;
    }

    public Integer getId() {
        return id;
    }
    public String getNama() {
        return nama;
    }
    public String getHarga() {
        return harga;
    }
    public String getTelp() {
        return telp;
    }
    public String getAlamat() {
        return alamat;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getFoto() {
        return foto;
    }
    public String getBank() {
        return bank;
    }
    public String getNamaRekening() {
        return nama_rekening;
    }
    public String getNoRekening() {
        return no_rekening;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }
    public void setTelp(String telp) {
        this.telp = telp;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public void setNama_rekening(String nama_rekening) {
        this.nama_rekening = nama_rekening;
    }
    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }
}
