package com.example.bencanalytical.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class responsePengaduanData {
    @SerializedName("nama_bencana")
    @Expose
    private String namaBencana;
    @SerializedName("deskripsi_kejadian")
    @Expose
    private String deskripsiKejadian;
    @SerializedName("deskripsi_lokasi")
    @Expose
    private String deskripsiLokasi;
    @SerializedName("koordinat")
    @Expose
    private String koordinat;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status_bencana")
    @Expose
    private String statusBencana;

    public String getNamaBencana() {
        return namaBencana;
    }

    public void setNamaBencana(String namaBencana) {
        this.namaBencana = namaBencana;
    }

    public String getDeskripsiKejadian() {
        return deskripsiKejadian;
    }

    public void setDeskripsiKejadian(String deskripsiKejadian) {
        this.deskripsiKejadian = deskripsiKejadian;
    }

    public String getDeskripsiLokasi() {
        return deskripsiLokasi;
    }

    public void setDeskripsiLokasi(String deskripsiLokasi) {
        this.deskripsiLokasi = deskripsiLokasi;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatusBencana() {
        return statusBencana;
    }

    public void setStatusBencana(String statusBencana) {
        this.statusBencana = statusBencana;
    }
}
