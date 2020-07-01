package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_PkkCatatanKeluargaDetail {

    @SerializedName("id_detail_cat")
    @Expose
    private String id_detail_cat;

    @SerializedName("id_cat")
    @Expose
    private String id_cat;

    @SerializedName("nik")
    @Expose
    private String nik;

    @SerializedName("berkebutuhan_khusus")
    @Expose
    private String berkebutuhan_khusus;

    @SerializedName("penghayatan_dan_pengamalan_pancasila")
    @Expose
    private String penghayatan_dan_pengamalan_pancasila;

    @SerializedName("gotong_royong")
    @Expose
    private String gotong_royong;

    @SerializedName("pendidikan_ketrampilan")
    @Expose
    private String pendidikan_ketrampilan;

    @SerializedName("pengembangan_kehidupan_berkoperasi")
    @Expose
    private String pengembangan_kehidupan_berkoperasi;

    @SerializedName("pangan")
    @Expose
    private String pangan;

    @SerializedName("sandang")
    @Expose
    private String sandang;

    @SerializedName("kesehatan")
    @Expose
    private String kesehatan;

    @SerializedName("perencanaan_sehat")
    @Expose
    private String perencanaan_sehat;

    @SerializedName("id_kelompok_umur")
    @Expose
    private String id_kelompok_umur;

    @SerializedName("usia_subur")
    @Expose
    private String usia_subur;

    @SerializedName("ibu_hamil")
    @Expose
    private String ibu_hamil;

    @SerializedName("menyusui")
    @Expose
    private String menyusui;

    @SerializedName("nifas")
    @Expose
    private String nifas;

    @SerializedName("buta_baca")
    @Expose
    private String buta_baca;

    @SerializedName("buta_tulis")
    @Expose
    private String buta_tulis;

    @SerializedName("buta_hitung")
    @Expose
    private String buta_hitung;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_PkkCatatanKeluargaDetail> data;


    public String getButa_baca() {
        return buta_baca;
    }

    public void setButa_baca(String buta_baca) {
        this.buta_baca = buta_baca;
    }

    public String getButa_tulis() {
        return buta_tulis;
    }

    public void setButa_tulis(String buta_tulis) {
        this.buta_tulis = buta_tulis;
    }

    public String getButa_hitung() {
        return buta_hitung;
    }

    public void setButa_hitung(String buta_hitung) {
        this.buta_hitung = buta_hitung;
    }

    public String getNifas() {
        return nifas;
    }

    public void setNifas(String nifas) {
        this.nifas = nifas;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData() {
        return data;
    }

    public void setData(List<Ent_PkkCatatanKeluargaDetail> data) {
        this.data = data;
    }

    public String getId_detail_cat() {
        return id_detail_cat;
    }

    public void setId_detail_cat(String id_detail_cat) {
        this.id_detail_cat = id_detail_cat;
    }

    public String getId_cat() {
        return id_cat;
    }

    public void setId_cat(String id_cat) {
        this.id_cat = id_cat;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getBerkebutuhan_khusus() {
        return berkebutuhan_khusus;
    }

    public void setBerkebutuhan_khusus(String berkebutuhan_khusus) {
        this.berkebutuhan_khusus = berkebutuhan_khusus;
    }

    public String getPenghayatan_dan_pengamalan_pancasila() {
        return penghayatan_dan_pengamalan_pancasila;
    }

    public void setPenghayatan_dan_pengamalan_pancasila(String penghayatan_dan_pengamalan_pancasila) {
        this.penghayatan_dan_pengamalan_pancasila = penghayatan_dan_pengamalan_pancasila;
    }

    public String getGotong_royong() {
        return gotong_royong;
    }

    public void setGotong_royong(String gotong_royong) {
        this.gotong_royong = gotong_royong;
    }

    public String getPendidikan_ketrampilan() {
        return pendidikan_ketrampilan;
    }

    public void setPendidikan_ketrampilan(String pendidikan_ketrampilan) {
        this.pendidikan_ketrampilan = pendidikan_ketrampilan;
    }

    public String getPengembangan_kehidupan_berkoperasi() {
        return pengembangan_kehidupan_berkoperasi;
    }

    public void setPengembangan_kehidupan_berkoperasi(String pengembangan_kehidupan_berkoperasi) {
        this.pengembangan_kehidupan_berkoperasi = pengembangan_kehidupan_berkoperasi;
    }

    public String getPangan() {
        return pangan;
    }

    public void setPangan(String pangan) {
        this.pangan = pangan;
    }

    public String getSandang() {
        return sandang;
    }

    public void setSandang(String sandang) {
        this.sandang = sandang;
    }

    public String getKesehatan() {
        return kesehatan;
    }

    public void setKesehatan(String kesehatan) {
        this.kesehatan = kesehatan;
    }

    public String getPerencanaan_sehat() {
        return perencanaan_sehat;
    }

    public void setPerencanaan_sehat(String perencanaan_sehat) {
        this.perencanaan_sehat = perencanaan_sehat;
    }

    public String getId_kelompok_umur() {
        return id_kelompok_umur;
    }

    public void setId_kelompok_umur(String id_kelompok_umur) {
        this.id_kelompok_umur = id_kelompok_umur;
    }

    public String getUsia_subur() {
        return usia_subur;
    }

    public void setUsia_subur(String usia_subur) {
        this.usia_subur = usia_subur;
    }

    public String getIbu_hamil() {
        return ibu_hamil;
    }

    public void setIbu_hamil(String ibu_hamil) {
        this.ibu_hamil = ibu_hamil;
    }

    public String getMenyusui() {
        return menyusui;
    }

    public void setMenyusui(String menyusui) {
        this.menyusui = menyusui;
    }
}
