package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_twebRtm {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nik_kepala")
    @Expose
    private String nik_kepala;

    @SerializedName("id_penduduk")
    @Expose
    private String id_penduduk;

    @SerializedName("no_kk")
    @Expose
    private String no_kk;

    @SerializedName("tgl_daftar")
    @Expose
    private String tgl_daftar;

    @SerializedName("kelas_sosial")
    @Expose
    private String kelas_sosial;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("response")
    @Expose
    private int response;

    @SerializedName("data")
    @Expose
    private List<Ent_twebRtm> data;

    @SerializedName("upload")
    @Expose
    private String upload;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public List<Ent_twebRtm> getData() {
        return data;
    }

    public void setData(List<Ent_twebRtm> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik_kepala() {
        return nik_kepala;
    }

    public void setNik_kepala(String nik_kepala) {
        this.nik_kepala = nik_kepala;
    }

    public String getId_penduduk() {
        return id_penduduk;
    }

    public void setId_penduduk(String id_penduduk) {
        this.id_penduduk = id_penduduk;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getTgl_daftar() {
        return tgl_daftar;
    }

    public void setTgl_daftar(String tgl_daftar) {
        this.tgl_daftar = tgl_daftar;
    }

    public String getKelas_sosial() {
        return kelas_sosial;
    }

    public void setKelas_sosial(String kelas_sosial) {
        this.kelas_sosial = kelas_sosial;
    }
}
