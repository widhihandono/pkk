package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ent_ConfigCode {
    @SerializedName("kode_desa")
    @Expose
    private String kode_desa;

    @SerializedName("kode_kecamatan")
    @Expose
    private String kode_kecamatan;

    @SerializedName("kode_kabupaten")
    @Expose
    private String kode_kabupaten;

    @SerializedName("upload")
    @Expose
    private String upload;

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getKode_desa() {
        return kode_desa;
    }

    public void setKode_desa(String kode_desa) {
        this.kode_desa = kode_desa;
    }

    public String getKode_kecamatan() {
        return kode_kecamatan;
    }

    public void setKode_kecamatan(String kode_kecamatan) {
        this.kode_kecamatan = kode_kecamatan;
    }

    public String getKode_kabupaten() {
        return kode_kabupaten;
    }

    public void setKode_kabupaten(String kode_kabupaten) {
        this.kode_kabupaten = kode_kabupaten;
    }
}
