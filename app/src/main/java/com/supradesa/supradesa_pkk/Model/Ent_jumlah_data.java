package com.supradesa.supradesa_pkk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ent_jumlah_data {

    @SerializedName("data_balita")
    @Expose
    private int data_balita;

    @SerializedName("data_wus")
    @Expose
    private int data_wus;

    @SerializedName("data_lansia")
    @Expose
    private int data_lansia;

    @SerializedName("data_menyusui")
    @Expose
    private int data_menyusui;

    @SerializedName("data_buta")
    @Expose
    private int data_buta;

    @SerializedName("data_hamil")
    @Expose
    private int data_hamil;

    @SerializedName("data_pus")
    @Expose
    private int data_pus;

    @SerializedName("response")
    @Expose
    private int response;


    public int getData_pus() {
        return data_pus;
    }

    public void setData_pus(int data_pus) {
        this.data_pus = data_pus;
    }

    public int getData_balita() {
        return data_balita;
    }

    public void setData_balita(int data_balita) {
        this.data_balita = data_balita;
    }

    public int getData_wus() {
        return data_wus;
    }

    public void setData_wus(int data_wus) {
        this.data_wus = data_wus;
    }

    public int getData_lansia() {
        return data_lansia;
    }

    public void setData_lansia(int data_lansia) {
        this.data_lansia = data_lansia;
    }

    public int getData_menyusui() {
        return data_menyusui;
    }

    public void setData_menyusui(int data_menyusui) {
        this.data_menyusui = data_menyusui;
    }

    public int getData_buta() {
        return data_buta;
    }

    public void setData_buta(int data_buta) {
        this.data_buta = data_buta;
    }

    public int getData_hamil() {
        return data_hamil;
    }

    public void setData_hamil(int data_hamil) {
        this.data_hamil = data_hamil;
    }


    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
