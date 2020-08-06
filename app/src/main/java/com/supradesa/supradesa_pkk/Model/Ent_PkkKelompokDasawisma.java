package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_PkkKelompokDasawisma {

    @SerializedName("id_kelompok")
    @Expose
    private String id_kelompok;

    @SerializedName("no_kk")
    @Expose
    private String no_kk;

    @SerializedName("id_dasa_wisma")
    @Expose
    private String id_dasa_wisma;

    @SerializedName("response")
    @Expose
    private int response;

    @SerializedName("data")
    @Expose
    private List<Ent_PkkKelompokDasawisma> data;

    @SerializedName("upload")
    @Expose
    private String upload;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }


    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public List<Ent_PkkKelompokDasawisma> getData() {
        return data;
    }

    public void setData(List<Ent_PkkKelompokDasawisma> data) {
        this.data = data;
    }

    public String getId_kelompok() {
        return id_kelompok;
    }


    public void setId_kelompok(String id_kelompok) {
        this.id_kelompok = id_kelompok;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getId_dasa_wisma() {
        return id_dasa_wisma;
    }

    public void setId_dasa_wisma(String id_dasa_wisma) {
        this.id_dasa_wisma = id_dasa_wisma;
    }
}
