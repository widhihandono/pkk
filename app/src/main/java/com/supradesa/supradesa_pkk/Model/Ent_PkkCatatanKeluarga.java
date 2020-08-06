package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_PkkCatatanKeluarga {

    @SerializedName("id_cat")
    @Expose
    private String id_cat;

    @SerializedName("id_kk")
    @Expose
    private String id_kk;

    @SerializedName("id_dk")
    @Expose
    private String id_dk;

    @SerializedName("tanggal_cat")
    @Expose
    private String tanggal_cat;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_PkkCatatanKeluarga> data;

    @SerializedName("upload")
    @Expose
    private String upload;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }


    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public List<Ent_PkkCatatanKeluarga> getData() {
        return data;
    }

    public void setData(List<Ent_PkkCatatanKeluarga> data) {
        this.data = data;
    }

    public String getId_cat() {
        return id_cat;
    }

    public void setId_cat(String id_cat) {
        this.id_cat = id_cat;
    }

    public String getId_kk() {
        return id_kk;
    }

    public void setId_kk(String id_kk) {
        this.id_kk = id_kk;
    }

    public String getId_dk() {
        return id_dk;
    }

    public void setId_dk(String id_dk) {
        this.id_dk = id_dk;
    }

    public String getTanggal_cat() {
        return tanggal_cat;
    }

    public void setTanggal_cat(String tanggal_cat) {
        this.tanggal_cat = tanggal_cat;
    }
}
