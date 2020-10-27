package com.supradesa.supradesa_pkk.Util;

import android.content.Context;

import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Sub_Catatan_keluarga;

import java.util.ArrayList;
import java.util.List;

public class List_Temporary {
    public static final List<Ent_twebPenduduk> listCekAnggotaRtm = new ArrayList<>();

    public static final List<Ent_twebPenduduk> listAnggotaRtm = new ArrayList<>();
    public static final List<Ent_twebPenduduk> listAnggotaRtm_Edit = new ArrayList<>();
    public static final List<Ent_twebPenduduk> listAnggotaRtm_Edit_sementara = new ArrayList<>();

    public static final List<Ent_twebPenduduk> listAnggotaRtm_Edit_tampung = new ArrayList<>();
    public static final List<Ent_twebPenduduk> list_AmbilAnggotaRtm_Edit = new ArrayList<>();
    public static final List<Ent_twebPenduduk> listAllAnggota = new ArrayList<>();
    public static final List<Ent_twebPenduduk> listPenduduk_Detail = new ArrayList<>();
    public static final List<Ent_twebPenduduk> listAllAnggota_sementara = new ArrayList<>();
    public static final List<Ent_twebPenduduk> listAllAnggota_edit_sementara = new ArrayList<>();
    public static final List<String> list_no_kk = new ArrayList<>();
    public static final List<String> list_no_kk_edit = new ArrayList<>();
    public static final List<Sub_Catatan_keluarga> listSub = new ArrayList<>();


    public static String id_kk = "",id_penduduk="",nik="",id_dasawisma="",no_rtm="",id_rtm="",no_rtm_edit = "no",kepalaRtm_edit = "";
    public static int dasawismaPosition = -1,kepalaRtm = -1;

    public static String getKepalaRtm_edit() {
        return kepalaRtm_edit;
    }

    public static void setKepalaRtm_edit(String kepalaRtm_edit) {
        List_Temporary.kepalaRtm_edit = kepalaRtm_edit;
    }

    public static String getNo_rtm_edit() {
        return no_rtm_edit;
    }

    public static void setNo_rtm_edit(String no_rtm_edit) {
        List_Temporary.no_rtm_edit = no_rtm_edit;
    }

    public static List<Ent_twebPenduduk> getListAnggotaRtm_Edit_sementara() {
        return listAnggotaRtm_Edit_sementara;
    }

    public static String getId_rtm() {
        return id_rtm;
    }

    public static void setId_rtm(String id_rtm) {
        List_Temporary.id_rtm = id_rtm;
    }

    public static List<Ent_twebPenduduk> getListCekAnggotaRtm() {
        return listCekAnggotaRtm;
    }

    public static List<String> getList_no_kk_edit() {
        return list_no_kk_edit;
    }

    public static List<String> getList_no_kk() {
        return list_no_kk;
    }

    public static List<Ent_twebPenduduk> getListAllAnggota_edit_sementara() {
        return listAllAnggota_edit_sementara;
    }

    public static List<Ent_twebPenduduk> getListAllAnggota_sementara() {
        return listAllAnggota_sementara;
    }

    public static String getNo_rtm() {
        return no_rtm;
    }

    public static void setNo_rtm(String no_rtm) {
        List_Temporary.no_rtm = no_rtm;
    }

    public static List<Ent_twebPenduduk> getListAnggotaRtm_Edit_tampung() {
        return listAnggotaRtm_Edit_tampung;
    }

    public static List<Ent_twebPenduduk> getList_AmbilAnggotaRtm_Edit() {
        return list_AmbilAnggotaRtm_Edit;
    }

    public static String getId_dasawisma() {
        return id_dasawisma;
    }

    public static void setId_dasawisma(String id_dasawisma) {
        List_Temporary.id_dasawisma = id_dasawisma;
    }

    public List_Temporary() {
    }

    public static List<Ent_twebPenduduk> getListAnggotaRtm_Edit() {
        return listAnggotaRtm_Edit;
    }

    public static int getKepalaRtm() {
        return kepalaRtm;
    }

    public static void setKepalaRtm(int kepalaRtm) {
        List_Temporary.kepalaRtm = kepalaRtm;
    }

    public static int getDasawismaPosition() {
        return dasawismaPosition;
    }

    public static void setDasawismaPosition(int dasawismaPosition) {
        List_Temporary.dasawismaPosition = dasawismaPosition;
    }

    public static String getNik() {
        return nik;
    }

    public static void setNik(String nik) {
        List_Temporary.nik = nik;
    }

    public static String getId_penduduk() {
        return id_penduduk;
    }

    public static void setId_penduduk(String id_penduduk) {
        List_Temporary.id_penduduk = id_penduduk;
    }

    public static String getId_kk() {
        return id_kk;
    }

    public static void setId_kk(String id_kk) {
        List_Temporary.id_kk = id_kk;
    }

    public static List<Sub_Catatan_keluarga> getListSub() {
        return listSub;
    }

    public static List<Ent_twebPenduduk> getListPenduduk_Detail() {
        return listPenduduk_Detail;
    }

    public static List<Ent_twebPenduduk> getListAllAnggota() {
        return listAllAnggota;
    }

    public static List<Ent_twebPenduduk> getListAnggotaRtm() {
        return listAnggotaRtm;
    }
}
