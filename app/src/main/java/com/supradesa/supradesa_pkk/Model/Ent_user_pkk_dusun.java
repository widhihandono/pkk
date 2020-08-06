package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_user_pkk_dusun {

    @SerializedName("id_user")
    @Expose
    private int id_user;

    @SerializedName("nik")
    @Expose
    private String nik;

    @SerializedName("kode_desa")
    @Expose
    private String kode_desa;

    @SerializedName("dusun")
    @Expose
    private String dusun;

    @SerializedName("nama_desa")
    @Expose
    private String nama_desa;

    @SerializedName("nama_kecamatan")
    @Expose
    private String nama_kecamatan;

    @SerializedName("no_hp")
    @Expose
    private String no_hp;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_user_pkk_dusun> data;

    @SerializedName("upload")
    @Expose
    private String upload;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }


    public String getNama_desa() {
        return nama_desa;
    }

    public void setNama_desa(String nama_desa) {
        this.nama_desa = nama_desa;
    }

    public String getNama_kecamatan() {
        return nama_kecamatan;
    }

    public void setNama_kecamatan(String nama_kecamatan) {
        this.nama_kecamatan = nama_kecamatan;
    }

    public List<Ent_user_pkk_dusun> getData() {
        return data;
    }

    public void setData(List<Ent_user_pkk_dusun> data) {
        this.data = data;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getKode_desa() {
        return kode_desa;
    }

    public void setKode_desa(String kode_desa) {
        this.kode_desa = kode_desa;
    }

    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
