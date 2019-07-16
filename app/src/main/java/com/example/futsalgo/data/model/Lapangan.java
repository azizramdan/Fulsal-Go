package com.example.futsalgo.data.model;

public class Lapangan {
    Integer id;
    String nama, harga, telp, alamat, longitude, latitude, foto, email;

    public Lapangan(Integer id, String nama, String harga, String telp, String alamat, String longitude, String latitude, String foto, String email) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.telp = telp;
        this.alamat = alamat;
        this.longitude = longitude;
        this.latitude = latitude;
        this.foto = foto;
        this.email = email;
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
    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setHarga(String harga) {
        this.nama = harga;
    }
    public void setTelp(String telp) {
        this.nama = telp;
    }
    public void setAlamat(String alamat) {
        this.nama = alamat;
    }
    public void setLongitude(String longitude) {
        this.nama = longitude;
    }
    public void setLatitude(String latitude) {
        this.nama = latitude;
    }
    public void setFoto(String foto) {
        this.nama = foto;
    }
    public void setEmail(String email) {
        this.nama = email;
    }
}
