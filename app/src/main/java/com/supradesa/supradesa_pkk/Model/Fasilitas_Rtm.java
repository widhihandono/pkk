package com.supradesa.supradesa_pkk.Model;

public class Fasilitas_Rtm {
    private String id,nama;

    public Fasilitas_Rtm(String id, String nama) {
        this.id = id;
        this.nama = nama;
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
