package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_twebPenduduk {

    @SerializedName("rt")
    @Expose
    private String rt;

    @SerializedName("rw")
    @Expose
    private String rw;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("nik")
    @Expose
    private String nik;

    @SerializedName("id_kk")
    @Expose
    private String id_kk;

    @SerializedName("rtm_level")
    @Expose
    private String rtm_level;

    @SerializedName("id_rtm")
    @Expose
    private String id_rtm;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("tempatlahir")
    @Expose
    private String tempatlahir;

    @SerializedName("tanggallahir")
    @Expose
    private String tanggallahir;

    @SerializedName("agama_id")
    @Expose
    private String agama_id;

    @SerializedName("pendidikan_kk_id")
    @Expose
    private String pendidikan_kk_id;


    @SerializedName("pekerjaan_id")
    @Expose
    private String pekerjaan_id;

    @SerializedName("status_kawin")
    @Expose
    private String status_kawin;

    @SerializedName("id_cluster")
    @Expose
    private String id_cluster;

    @SerializedName("alamat_sekarang")
    @Expose
    private String alamat_sekarang;

    @SerializedName("cacat_id")
    @Expose
    private String cacat_id;

    @SerializedName("kk_level")
    @Expose
    private String kk_level;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_twebPenduduk> data;


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

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public List<Ent_twebPenduduk> getData() {
        return data;
    }

    public void setData(List<Ent_twebPenduduk> data) {
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

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getId_kk() {
        return id_kk;
    }

    public void setId_kk(String id_kk) {
        this.id_kk = id_kk;
    }

    public String getRtm_level() {
        return rtm_level;
    }

    public void setRtm_level(String rtm_level) {
        this.rtm_level = rtm_level;
    }

    public String getId_rtm() {
        return id_rtm;
    }

    public void setId_rtm(String id_rtm) {
        this.id_rtm = id_rtm;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTempatlahir() {
        return tempatlahir;
    }

    public void setTempatlahir(String tempatlahir) {
        this.tempatlahir = tempatlahir;
    }

    public String getTanggallahir() {
        return tanggallahir;
    }

    public void setTanggallahir(String tanggallahir) {
        this.tanggallahir = tanggallahir;
    }

    public String getAgama_id() {
        return agama_id;
    }

    public void setAgama_id(String agama_id) {
        this.agama_id = agama_id;
    }

    public String getPendidikan_kk_id() {
        return pendidikan_kk_id;
    }

    public void setPendidikan_kk_id(String pendidikan_kk_id) {
        this.pendidikan_kk_id = pendidikan_kk_id;
    }

    public String getPekerjaan_id() {
        return pekerjaan_id;
    }

    public void setPekerjaan_id(String pekerjaan_id) {
        this.pekerjaan_id = pekerjaan_id;
    }

    public String getStatus_kawin() {
        return status_kawin;
    }

    public void setStatus_kawin(String status_kawin) {
        this.status_kawin = status_kawin;
    }

    public String getId_cluster() {
        return id_cluster;
    }

    public void setId_cluster(String id_cluster) {
        this.id_cluster = id_cluster;
    }

    public String getAlamat_sekarang() {
        return alamat_sekarang;
    }

    public void setAlamat_sekarang(String alamat_sekarang) {
        this.alamat_sekarang = alamat_sekarang;
    }

    public String getCacat_id() {
        return cacat_id;
    }

    public void setCacat_id(String cacat_id) {
        this.cacat_id = cacat_id;
    }

    public String getKk_level() {
        return kk_level;
    }

    public void setKk_level(String kk_level) {
        this.kk_level = kk_level;
    }
}
