package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_PkkDasaWisma {

    @SerializedName("id_dasa_wisma")
    @Expose
    private String id_dasa_wisma;

    @SerializedName("id_cluster")
    @Expose
    private String id_cluster;

    @SerializedName("id_kepala")
    @Expose
    private String id_kepala;

    @SerializedName("nama_dasa_wisma")
    @Expose
    private String nama_dasa_wisma;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_PkkDasaWisma> data;

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

    public List<Ent_PkkDasaWisma> getData() {
        return data;
    }

    public void setData(List<Ent_PkkDasaWisma> data) {
        this.data = data;
    }

    public String getId_dasa_wisma() {
        return id_dasa_wisma;
    }

    public void setId_dasa_wisma(String id_dasa_wisma) {
        this.id_dasa_wisma = id_dasa_wisma;
    }

    public String getId_cluster() {
        return id_cluster;
    }

    public void setId_cluster(String id_cluster) {
        this.id_cluster = id_cluster;
    }

    public String getId_kepala() {
        return id_kepala;
    }

    public void setId_kepala(String id_kepala) {
        this.id_kepala = id_kepala;
    }

    public String getNama_dasa_wisma() {
        return nama_dasa_wisma;
    }

    public void setNama_dasa_wisma(String nama_dasa_wisma) {
        this.nama_dasa_wisma = nama_dasa_wisma;
    }
}
