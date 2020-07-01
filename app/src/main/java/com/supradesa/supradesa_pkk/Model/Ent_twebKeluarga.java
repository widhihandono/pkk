package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_twebKeluarga {

    @SerializedName("rt")
    @Expose
    private String rt;

    @SerializedName("rw")
    @Expose
    private String rw;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("kk_level")
    @Expose
    private String kk_level;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("no_kk")
    @Expose
    private String no_kk;

    @SerializedName("id_rtm")
    @Expose
    private String id_rtm;

    @SerializedName("nik_kepala")
    @Expose
    private String nik_kepala;

    @SerializedName("tgl_daftar")
    @Expose
    private String tgl_daftar;

    @SerializedName("kelas_sosial")
    @Expose
    private String kelas_sosial;

    @SerializedName("tgl_cetak_kk")
    @Expose
    private String tgl_cetak_kk;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("id_cluster")
    @Expose
    private String id_cluster;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_twebKeluarga> data;


    public String getNama() {
        return nama;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKk_level() {
        return kk_level;
    }

    public void setKk_level(String kk_level) {
        this.kk_level = kk_level;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public List<Ent_twebKeluarga> getData() {
        return data;
    }

    public void setData(List<Ent_twebKeluarga> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getId_rtm() {
        return id_rtm;
    }

    public void setId_rtm(String id_rtm) {
        this.id_rtm = id_rtm;
    }

    public String getNik_kepala() {
        return nik_kepala;
    }

    public void setNik_kepala(String nik_kepala) {
        this.nik_kepala = nik_kepala;
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

    public String getTgl_cetak_kk() {
        return tgl_cetak_kk;
    }

    public void setTgl_cetak_kk(String tgl_cetak_kk) {
        this.tgl_cetak_kk = tgl_cetak_kk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getId_cluster() {
        return id_cluster;
    }

    public void setId_cluster(String id_cluster) {
        this.id_cluster = id_cluster;
    }
}
