package com.example.futsalgo.data.model;

public class PesananSaya {
    Integer id;
    String waktu_pilih_tanggal, waktu_pilih_jam, metode_bayar, status, nama_lapangan, harga, alamat, bank, nama_rekening, no_rekening, telp, latitude, longitude;

    public PesananSaya(Integer id, String waktu_pilih_tanggal, String waktu_pilih_jam, String metode_bayar, String status, String nama_lapangan, String harga, String alamat, String bank, String nama_rekening, String no_rekening, String telp, String latitude, String longitude) {
        this.id = id;
        this.waktu_pilih_tanggal = waktu_pilih_tanggal;
        this.waktu_pilih_jam = waktu_pilih_jam;
        this.metode_bayar = metode_bayar;
        this.status = status;
        this.nama_lapangan = nama_lapangan;
        this.harga = harga;
        this.alamat = alamat;
        this.bank = bank;
        this.nama_rekening = nama_rekening;
        this.no_rekening = no_rekening;
        this.telp = telp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }
    public String getWaktuPilihTanggal() {
        return waktu_pilih_tanggal;
    }
    public String getWaktuPilihJam() {
        return waktu_pilih_jam;
    }
    public String getMetodeBayar() {
        return metode_bayar;
    }
    public String getStatus() {
        return status;
    }
    public String getNamaLapangan() {
        return nama_lapangan;
    }
    public String getHarga() {
        return harga;
    }
    public String getAlamat() {
        return alamat;
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
    public String getTelp() {
        return telp;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setWaktuPilihTanggal(String waktu_pilih_tanggal) {
        this.waktu_pilih_tanggal = waktu_pilih_tanggal;
    }
    public void setWaktuPilihJam(String waktu_pilih_jam) {
        this.waktu_pilih_jam = waktu_pilih_jam;
    }
    public void setMetodeBayar(String metode_bayar) {
        this.metode_bayar = metode_bayar;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setNamaLapangan(String nama_lapangan) {
        this.nama_lapangan = nama_lapangan;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public void setNamaRekening(String nama_rekening) {
        this.nama_rekening = nama_rekening;
    }
    public void setNoRekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }
    public void setTelp(String telp) {
        this.telp = telp;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
