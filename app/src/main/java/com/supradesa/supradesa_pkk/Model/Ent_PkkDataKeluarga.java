package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_PkkDataKeluarga {

    @SerializedName("id_dk")
    @Expose
    private String id_dk;

    @SerializedName("no_kk")
    @Expose
    private String no_kk;

    @SerializedName("makanan_pokok")
    @Expose
    private String makanan_pokok;

    @SerializedName("jml_makanan_pokok")
    @Expose
    private String jml_makanan_pokok;

    @SerializedName("jamban")
    @Expose
    private String jamban;

    @SerializedName("jml_jamban")
    @Expose
    private String jml_jamban;

    @SerializedName("sumber_air")
    @Expose
    private String sumber_air;

    @SerializedName("jml_sumber_air")
    @Expose
    private String jml_sumber_air;

    @SerializedName("tempat_sampah")
    @Expose
    private String tempat_sampah;

    @SerializedName("jml_tempat_sampah")
    @Expose
    private String jml_tempat_sampah;

    @SerializedName("saluran_pembuangan_air")
    @Expose
    private String saluran_pembuangan_air;

    @SerializedName("jml_saluran_pembuangan_air")
    @Expose
    private String jml_saluran_pembuangan_air;

    @SerializedName("stiker_p4k")
    @Expose
    private String stiker_p4k;

    @SerializedName("jml_stiker_p4k")
    @Expose
    private String jml_stiker_p4k;

    @SerializedName("kriteria_rumah")
    @Expose
    private String kriteria_rumah;

    @SerializedName("jml_kriteria_rumah")
    @Expose
    private String jml_kriteria_rumah;

    @SerializedName("up2k")
    @Expose
    private String up2k;

    @SerializedName("jml_up2k")
    @Expose
    private String jml_up2k;

    @SerializedName("keg_sehat_lingkungan")
    @Expose
    private String keg_sehat_lingkungan;

    @SerializedName("jml_keg_sehat_lingkungan")
    @Expose
    private String jml_keg_sehat_lingkungan;

    @SerializedName("ptp")
    @Expose
    private String ptp;

    @SerializedName("Industri_rt")
    @Expose
    private String Industri_rt;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_PkkDataKeluarga> data;

    public String getPtp() {
        return ptp;
    }

    public void setPtp(String ptp) {
        this.ptp = ptp;
    }

    public String getIndustri_rt() {
        return Industri_rt;
    }

    public void setIndustri_rt(String industri_rt) {
        Industri_rt = industri_rt;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public List<Ent_PkkDataKeluarga> getData() {
        return data;
    }

    public void setData(List<Ent_PkkDataKeluarga> data) {
        this.data = data;
    }

    public String getId_dk() {
        return id_dk;
    }

    public void setId_dk(String id_dk) {
        this.id_dk = id_dk;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getMakanan_pokok() {
        return makanan_pokok;
    }

    public void setMakanan_pokok(String makanan_pokok) {
        this.makanan_pokok = makanan_pokok;
    }

    public String getJml_makanan_pokok() {
        return jml_makanan_pokok;
    }

    public void setJml_makanan_pokok(String jml_makanan_pokok) {
        this.jml_makanan_pokok = jml_makanan_pokok;
    }

    public String getJamban() {
        return jamban;
    }

    public void setJamban(String jamban) {
        this.jamban = jamban;
    }

    public String getJml_jamban() {
        return jml_jamban;
    }

    public void setJml_jamban(String jml_jamban) {
        this.jml_jamban = jml_jamban;
    }

    public String getSumber_air() {
        return sumber_air;
    }

    public void setSumber_air(String sumber_air) {
        this.sumber_air = sumber_air;
    }

    public String getJml_sumber_air() {
        return jml_sumber_air;
    }

    public void setJml_sumber_air(String jml_sumber_air) {
        this.jml_sumber_air = jml_sumber_air;
    }

    public String getTempat_sampah() {
        return tempat_sampah;
    }

    public void setTempat_sampah(String tempat_sampah) {
        this.tempat_sampah = tempat_sampah;
    }

    public String getJml_tempat_sampah() {
        return jml_tempat_sampah;
    }

    public void setJml_tempat_sampah(String jml_tempat_sampah) {
        this.jml_tempat_sampah = jml_tempat_sampah;
    }

    public String getSaluran_pembuangan_air() {
        return saluran_pembuangan_air;
    }

    public void setSaluran_pembuangan_air(String saluran_pembuangan_air) {
        this.saluran_pembuangan_air = saluran_pembuangan_air;
    }

    public String getJml_saluran_pembuangan_air() {
        return jml_saluran_pembuangan_air;
    }

    public void setJml_saluran_pembuangan_air(String jml_saluran_pembuangan_air) {
        this.jml_saluran_pembuangan_air = jml_saluran_pembuangan_air;
    }

    public String getStiker_p4k() {
        return stiker_p4k;
    }

    public void setStiker_p4k(String stiker_p4k) {
        this.stiker_p4k = stiker_p4k;
    }

    public String getJml_stiker_p4k() {
        return jml_stiker_p4k;
    }

    public void setJml_stiker_p4k(String jml_stiker_p4k) {
        this.jml_stiker_p4k = jml_stiker_p4k;
    }

    public String getKriteria_rumah() {
        return kriteria_rumah;
    }

    public void setKriteria_rumah(String kriteria_rumah) {
        this.kriteria_rumah = kriteria_rumah;
    }

    public String getJml_kriteria_rumah() {
        return jml_kriteria_rumah;
    }

    public void setJml_kriteria_rumah(String jml_kriteria_rumah) {
        this.jml_kriteria_rumah = jml_kriteria_rumah;
    }

    public String getUp2k() {
        return up2k;
    }

    public void setUp2k(String up2k) {
        this.up2k = up2k;
    }

    public String getJml_up2k() {
        return jml_up2k;
    }

    public void setJml_up2k(String jml_up2k) {
        this.jml_up2k = jml_up2k;
    }

    public String getKeg_sehat_lingkungan() {
        return keg_sehat_lingkungan;
    }

    public void setKeg_sehat_lingkungan(String keg_sehat_lingkungan) {
        this.keg_sehat_lingkungan = keg_sehat_lingkungan;
    }

    public String getJml_keg_sehat_lingkungan() {
        return jml_keg_sehat_lingkungan;
    }

    public void setJml_keg_sehat_lingkungan(String jml_keg_sehat_lingkungan) {
        this.jml_keg_sehat_lingkungan = jml_keg_sehat_lingkungan;
    }
}
