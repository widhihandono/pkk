package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_twebPendudukHubungan {
    
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_twebPendudukHubungan> data;

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

    public List<Ent_twebPendudukHubungan> getData() {
        return data;
    }

    public void setData(List<Ent_twebPendudukHubungan> data) {
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

}
