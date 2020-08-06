package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ent_twebwilCluster {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("rt")
    @Expose
    private String rt;

    @SerializedName("rw")
    @Expose
    private String rw;

    @SerializedName("dusun")
    @Expose
    private String dusun;

    @SerializedName("id_kepala")
    @Expose
    private String id_kepala;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("zoom")
    @Expose
    private String zoom;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("map_tipe")
    @Expose
    private String map_tipe;

    @SerializedName("response")
    @Expose
    private boolean response;

    @SerializedName("data")
    @Expose
    private List<Ent_twebwilCluster> data;

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

    public List<Ent_twebwilCluster> getData() {
        return data;
    }

    public void setData(List<Ent_twebwilCluster> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    public String getId_kepala() {
        return id_kepala;
    }

    public void setId_kepala(String id_kepala) {
        this.id_kepala = id_kepala;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMap_tipe() {
        return map_tipe;
    }

    public void setMap_tipe(String map_tipe) {
        this.map_tipe = map_tipe;
    }
}
