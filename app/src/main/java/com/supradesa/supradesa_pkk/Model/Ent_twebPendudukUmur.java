package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_twebPendudukUmur {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("dari")
    @Expose
    private String dari;

    @SerializedName("sampai")
    @Expose
    private String sampai;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_twebPendudukUmur> data;

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

    public List<Ent_twebPendudukUmur> getData() {
        return data;
    }

    public void setData(List<Ent_twebPendudukUmur> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public String getSampai() {
        return sampai;
    }

    public void setSampai(String sampai) {
        this.sampai = sampai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
