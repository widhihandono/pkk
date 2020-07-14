package com.supradesa.supradesa_pkk.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.supradesa.supradesa_pkk.Util.SharedPref;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDasaWisma;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDataKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkKelompokDasawisma;

import java.util.ArrayList;
import java.util.List;

public class Crud_pkk {
    Helper helper;
    SharedPref sharedPref;

    public Crud_pkk(Context context) {
        helper = new Helper(context);
        sharedPref = new SharedPref(context);
    }

    public long InsertData_pkk_catatan_keluarga(Ent_PkkCatatanKeluarga eck)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_CAT,eck.getId_cat());
        contentValues.put(Helper.ID_KK,eck.getId_kk());
        contentValues.put(Helper.ID_DK,eck.getId_dk());
        contentValues.put(Helper.TANGGAL_CAT,eck.getTanggal_cat());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_pkk_cat_keluarga = dbb.insert(Helper.TABLE_PKK_CATATAN_KELUARGA,null,contentValues);
        return id_insert_pkk_cat_keluarga;
    }


    public List<Ent_PkkCatatanKeluarga> getData_pkkCatatanKeluarga()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_CAT,Helper.ID_KK,Helper.ID_DK,Helper.TANGGAL_CAT};
        Cursor cursor = db.query(Helper.TABLE_PKK_CATATAN_KELUARGA,coloumn,null,null,null,null,null);
        List<Ent_PkkCatatanKeluarga> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluarga eck = new Ent_PkkCatatanKeluarga();
            eck.setId_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_CAT)));
            eck.setId_kk(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            eck.setId_dk(cursor.getString(cursor.getColumnIndex(Helper.ID_DK)));
            eck.setTanggal_cat(cursor.getString(cursor.getColumnIndex(Helper.TANGGAL_CAT)));

            listPresence.add(eck);

        }
        return listPresence;
    }


    public boolean delete_pkk_catatan_keluarga_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_CATATAN_KELUARGA,helper.ID_CAT+"="+id,null) > 0;
    }

    public boolean delete_all_pkk_catatan_keluarga()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_CATATAN_KELUARGA,null,null) > 0;
    }

    //Pkk Catatan keluarga detail
    public long InsertData_pkk_catatan_keluarga_detail(Ent_PkkCatatanKeluargaDetail ckd)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_DETAIL_CAT,ckd.getId_detail_cat());
        contentValues.put(Helper.NIK,ckd.getNik());
        contentValues.put(Helper.BERKEBUTUHAN_KHUSUS,ckd.getBerkebutuhan_khusus());
        contentValues.put(Helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,ckd.getPenghayatan_dan_pengamalan_pancasila());
        contentValues.put(Helper.GOTONG_ROYONG,ckd.getGotong_royong());
        contentValues.put(Helper.PENDIDIKAN_KETRAMPILAN,ckd.getPendidikan_ketrampilan());
        contentValues.put(Helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,ckd.getPengembangan_kehidupan_berkoperasi());
        contentValues.put(Helper.PANGAN,ckd.getPangan());
        contentValues.put(Helper.SANDANG,ckd.getSandang());
        contentValues.put(Helper.KESEHATAN,ckd.getKesehatan());
        contentValues.put(Helper.PERENCANAAN_SEHAT,ckd.getPerencanaan_sehat());
        contentValues.put(Helper.ID_KELOMPOK_UMUR,ckd.getId_kelompok_umur());
        contentValues.put(Helper.USIA_SUBUR,ckd.getUsia_subur());
        contentValues.put(Helper.IBU_HAMIL,ckd.getIbu_hamil());
        contentValues.put(Helper.MENYUSUI,ckd.getMenyusui());
        contentValues.put(Helper.NIFAS,ckd.getNifas());
        contentValues.put(Helper.BUTA_BACA,ckd.getButa_baca());
        contentValues.put(Helper.BUTA_TULIS,ckd.getButa_tulis());
        contentValues.put(Helper.BUTA_HITUNG,ckd.getButa_hitung());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_pkk_catatan_keluarga_detail = dbb.insertWithOnConflict(Helper.TABLE_PKK_CATATAN_KELUARGA_DETAIL,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        if(id_insert_pkk_catatan_keluarga_detail > 0)
        {
            return id_insert_pkk_catatan_keluarga_detail;
        }
        else
        {
            long update_pkk_catatan_keluarga_detail = dbb.update(Helper.TABLE_PKK_CATATAN_KELUARGA_DETAIL,contentValues,helper.ID_DETAIL_CAT+"="+ckd.getId_detail_cat(),null);
            return update_pkk_catatan_keluarga_detail;
        }

    }

    public long update_pkk_catatan_keluarga_detail(String key,String value,String id_detail_cat)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key,value);

        long update_pkk_catatan_keluarga_detail = dbb.update(Helper.TABLE_PKK_CATATAN_KELUARGA_DETAIL,contentValues,helper.ID_DETAIL_CAT+"="+id_detail_cat,null);
        return update_pkk_catatan_keluarga_detail;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail_by_id_kelompok_umur(String id)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_detail_cat,id_penduduk FROM "+Helper.TABLE_PKK_CATATAN_KELUARGA_DETAIL+" WHERE id_kelompok_umur = '"+id+"'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));

            listPresence.add(ckd);

        }
        return listPresence;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail_by_param(String param,String value)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_detail_cat,id_penduduk FROM "+Helper.TABLE_PKK_CATATAN_KELUARGA_DETAIL+" WHERE "+param+" = '"+value+"'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));

            listPresence.add(ckd);

        }
        return listPresence;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail_WUS()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_detail_cat,id_penduduk FROM pkk_catatan_keluarga_detail JOIN tweb_penduduk ON tweb_penduduk.id = pkk_catatan_keluarga_detail.id_penduduk " +
                                    "WHERE tweb_penduduk.sex = '2' AND pkk_catatan_keluarga_detail.usia_subur = 'ya'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));

            listPresence.add(ckd);

        }
        return listPresence;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail_PUS()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_detail_cat,id_penduduk FROM pkk_catatan_keluarga_detail JOIN tweb_penduduk ON tweb_penduduk.id = pkk_catatan_keluarga_detail.id_penduduk " +
                "WHERE tweb_penduduk.sex = '2' AND pkk_catatan_keluarga_detail.usia_subur = 'ya' AND tweb_penduduk.status_kawin = '2'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));

            listPresence.add(ckd);

        }
        return listPresence;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail_tiga_buta()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_detail_cat,id_penduduk FROM pkk_catatan_keluarga_detail " +
                "WHERE buta_baca = 'ya' OR buta_tulis = 'ya' OR buta_hitung = 'ya'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));

            listPresence.add(ckd);

        }
        return listPresence;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail_by_no_rtm(String no_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT tweb_penduduk.id_rtm,pkk_catatan_keluarga_detail.* FROM pkk_catatan_keluarga_detail JOIN tweb_penduduk on "+
                                        "tweb_penduduk.nik = pkk_catatan_keluarga_detail.nik " +
                                        "WHERE pkk_catatan_keluarga_detail.upload = 'no' AND tweb_penduduk.id_rtm = '"+no_rtm+"'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            ckd.setBerkebutuhan_khusus(cursor.getString(cursor.getColumnIndex(Helper.BERKEBUTUHAN_KHUSUS)));
            ckd.setPenghayatan_dan_pengamalan_pancasila(cursor.getString(cursor.getColumnIndex(Helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA)));
            ckd.setGotong_royong(cursor.getString(cursor.getColumnIndex(Helper.GOTONG_ROYONG)));
            ckd.setPendidikan_ketrampilan(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KETRAMPILAN)));
            ckd.setPengembangan_kehidupan_berkoperasi(cursor.getString(cursor.getColumnIndex(Helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI)));
            ckd.setPangan(cursor.getString(cursor.getColumnIndex(Helper.PANGAN)));
            ckd.setSandang(cursor.getString(cursor.getColumnIndex(Helper.SANDANG)));
            ckd.setKesehatan(cursor.getString(cursor.getColumnIndex(Helper.KESEHATAN)));
            ckd.setPerencanaan_sehat(cursor.getString(cursor.getColumnIndex(Helper.PERENCANAAN_SEHAT)));
            ckd.setId_kelompok_umur(cursor.getString(cursor.getColumnIndex(Helper.ID_KELOMPOK_UMUR)));
            ckd.setUsia_subur(cursor.getString(cursor.getColumnIndex(Helper.USIA_SUBUR)));
            ckd.setIbu_hamil(cursor.getString(cursor.getColumnIndex(Helper.IBU_HAMIL)));
            ckd.setMenyusui(cursor.getString(cursor.getColumnIndex(Helper.MENYUSUI)));
            ckd.setNifas(cursor.getString(cursor.getColumnIndex(Helper.NIFAS)));
            ckd.setButa_baca(cursor.getString(cursor.getColumnIndex(Helper.BUTA_BACA)));
            ckd.setButa_tulis(cursor.getString(cursor.getColumnIndex(Helper.BUTA_TULIS)));
            ckd.setButa_hitung(cursor.getString(cursor.getColumnIndex(Helper.BUTA_HITUNG)));
            ckd.setStunting(cursor.getString(cursor.getColumnIndex(Helper.STUNTING)));


            listPresence.add(ckd);

        }
        return listPresence;
    }

    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail(String nik)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pkk_catatan_keluarga_detail WHERE upload = 'no' AND nik = '"+nik+"'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            ckd.setBerkebutuhan_khusus(cursor.getString(cursor.getColumnIndex(Helper.BERKEBUTUHAN_KHUSUS)));
            ckd.setPenghayatan_dan_pengamalan_pancasila(cursor.getString(cursor.getColumnIndex(Helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA)));
            ckd.setGotong_royong(cursor.getString(cursor.getColumnIndex(Helper.GOTONG_ROYONG)));
            ckd.setPendidikan_ketrampilan(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KETRAMPILAN)));
            ckd.setPengembangan_kehidupan_berkoperasi(cursor.getString(cursor.getColumnIndex(Helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI)));
            ckd.setPangan(cursor.getString(cursor.getColumnIndex(Helper.PANGAN)));
            ckd.setSandang(cursor.getString(cursor.getColumnIndex(Helper.SANDANG)));
            ckd.setKesehatan(cursor.getString(cursor.getColumnIndex(Helper.KESEHATAN)));
            ckd.setPerencanaan_sehat(cursor.getString(cursor.getColumnIndex(Helper.PERENCANAAN_SEHAT)));
            ckd.setId_kelompok_umur(cursor.getString(cursor.getColumnIndex(Helper.ID_KELOMPOK_UMUR)));
            ckd.setUsia_subur(cursor.getString(cursor.getColumnIndex(Helper.USIA_SUBUR)));
            ckd.setIbu_hamil(cursor.getString(cursor.getColumnIndex(Helper.IBU_HAMIL)));
            ckd.setMenyusui(cursor.getString(cursor.getColumnIndex(Helper.MENYUSUI)));
            ckd.setNifas(cursor.getString(cursor.getColumnIndex(Helper.NIFAS)));
            ckd.setButa_baca(cursor.getString(cursor.getColumnIndex(Helper.BUTA_BACA)));
            ckd.setButa_tulis(cursor.getString(cursor.getColumnIndex(Helper.BUTA_TULIS)));
            ckd.setButa_hitung(cursor.getString(cursor.getColumnIndex(Helper.BUTA_HITUNG)));


            listPresence.add(ckd);

        }
        return listPresence;
    }


    public List<Ent_PkkCatatanKeluargaDetail> getData_pkk_catatan_keluarga_detail()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_DETAIL_CAT,Helper.ID_CAT,Helper.NIK,Helper.BERKEBUTUHAN_KHUSUS,Helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,
                            Helper.GOTONG_ROYONG,Helper.PENDIDIKAN_KETRAMPILAN,Helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,
                            Helper.PANGAN,Helper.SANDANG,Helper.KESEHATAN,Helper.PERENCANAAN_SEHAT,Helper.ID_KELOMPOK_UMUR,Helper.USIA_SUBUR,
                            Helper.IBU_HAMIL,Helper.MENYUSUI,Helper.NIFAS,Helper.BUTA_BACA,Helper.BUTA_TULIS,Helper.BUTA_HITUNG};
        Cursor cursor = db.rawQuery("SELECT * FROM pkk_catatan_keluarga_detail WHERE upload = 'no'",null);
        List<Ent_PkkCatatanKeluargaDetail> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkCatatanKeluargaDetail ckd = new Ent_PkkCatatanKeluargaDetail();
            ckd.setId_detail_cat(cursor.getString(cursor.getColumnIndex(Helper.ID_DETAIL_CAT)));
            ckd.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            ckd.setBerkebutuhan_khusus(cursor.getString(cursor.getColumnIndex(Helper.BERKEBUTUHAN_KHUSUS)));
            ckd.setPenghayatan_dan_pengamalan_pancasila(cursor.getString(cursor.getColumnIndex(Helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA)));
            ckd.setGotong_royong(cursor.getString(cursor.getColumnIndex(Helper.GOTONG_ROYONG)));
            ckd.setPendidikan_ketrampilan(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KETRAMPILAN)));
            ckd.setPengembangan_kehidupan_berkoperasi(cursor.getString(cursor.getColumnIndex(Helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI)));
            ckd.setPangan(cursor.getString(cursor.getColumnIndex(Helper.PANGAN)));
            ckd.setSandang(cursor.getString(cursor.getColumnIndex(Helper.SANDANG)));
            ckd.setKesehatan(cursor.getString(cursor.getColumnIndex(Helper.KESEHATAN)));
            ckd.setPerencanaan_sehat(cursor.getString(cursor.getColumnIndex(Helper.PERENCANAAN_SEHAT)));
            ckd.setId_kelompok_umur(cursor.getString(cursor.getColumnIndex(Helper.ID_KELOMPOK_UMUR)));
            ckd.setUsia_subur(cursor.getString(cursor.getColumnIndex(Helper.USIA_SUBUR)));
            ckd.setIbu_hamil(cursor.getString(cursor.getColumnIndex(Helper.IBU_HAMIL)));
            ckd.setMenyusui(cursor.getString(cursor.getColumnIndex(Helper.MENYUSUI)));
            ckd.setNifas(cursor.getString(cursor.getColumnIndex(Helper.NIFAS)));
            ckd.setButa_baca(cursor.getString(cursor.getColumnIndex(Helper.BUTA_BACA)));
            ckd.setButa_tulis(cursor.getString(cursor.getColumnIndex(Helper.BUTA_TULIS)));
            ckd.setButa_hitung(cursor.getString(cursor.getColumnIndex(Helper.BUTA_HITUNG)));
            ckd.setStunting(cursor.getString(cursor.getColumnIndex(Helper.STUNTING)));


            listPresence.add(ckd);

        }
        return listPresence;
    }


    public boolean delete_pkk_catatan_keluarga_detail_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_CATATAN_KELUARGA_DETAIL,helper.ID_DETAIL_CAT+"="+id,null) > 0;
    }

    public boolean delete_all_pkk_catatan_keluarga_detail()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_CATATAN_KELUARGA_DETAIL,null,null) > 0;
    }


    //PKK Data KELUARGA

    public long Insert_pkk_data_keluarga(Ent_PkkDataKeluarga pdk)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_DK,pdk.getId_dk());
        contentValues.put(Helper.NO_KK,pdk.getNo_kk());
        contentValues.put(Helper.MAKANAN_POKOK,pdk.getMakanan_pokok());
        contentValues.put(Helper.JML_MAKANAN_POKOK,pdk.getJml_makanan_pokok());
        contentValues.put(Helper.JAMBAN,pdk.getJamban());
        contentValues.put(Helper.JML_JAMBAN,pdk.getJml_jamban());
        contentValues.put(Helper.SUMBER_AIR,pdk.getSumber_air());
        contentValues.put(Helper.JML_SUMBER_AIR,pdk.getJml_sumber_air());
        contentValues.put(Helper.TEMPAT_SAMPAH,pdk.getTempat_sampah());
        contentValues.put(Helper.JML_TEMPAT_SAMPAH,pdk.getJml_tempat_sampah());
        contentValues.put(Helper.SALURAN_PEMBUANGAN_AIR,pdk.getSaluran_pembuangan_air());
        contentValues.put(Helper.JML_SALURAN_PEMBUANGAN_AIR,pdk.getJml_saluran_pembuangan_air());
        contentValues.put(Helper.STIKER_P4K,pdk.getStiker_p4k());
        contentValues.put(Helper.JML_STIKER_P4K,pdk.getJml_stiker_p4k());
        contentValues.put(Helper.KRITERIA_RUMAH,pdk.getKriteria_rumah());
        contentValues.put(Helper.JML_KRITERIA_RUMAH,pdk.getJml_kriteria_rumah());
        contentValues.put(Helper.UP2K,pdk.getUp2k());
        contentValues.put(Helper.JML_UP2K,pdk.getJml_up2k());
        contentValues.put(Helper.KEG_SEHAT_LINGKUNGAN,pdk.getKeg_sehat_lingkungan());
        contentValues.put(Helper.JML_KEG_SEHAT_LINGKUNGAN,pdk.getJml_keg_sehat_lingkungan());
        contentValues.put(Helper.PTP,pdk.getPtp());
        contentValues.put(Helper.INDUSTRI_RT,pdk.getIndustri_rt());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_PKK_DATA_KELUARGA,null,contentValues);
        return id_insert_tweb_penduduk;
    }

    public long update_pkk_data_keluarga(String key,String value, String no_rtm) {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("UPDATE "+Helper.TABLE_PKK_DATA_KELUARGA+" SET "+key+" = "+"'"+value+"' "+ "WHERE no_kk = "+"'"+no_rtm+"'");

        if(getAllDataKeluarga().size() > 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }

    }

    public ArrayList getAllDataKeluarga() {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select changes() as cek from " + Helper.TABLE_PKK_DATA_KELUARGA, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            if ((res != null) && (res.getCount() > 0))
                array_list.add(res.getString(res.getColumnIndex("cek")));
            res.moveToNext();
        }
        return array_list;
    }

    public long Input_pkk_data_keluarga(String key,String value,String no_rtm) //no_rtm atau no_kk
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_dk",no_rtm);
        contentValues.put(key,value);
        contentValues.put(Helper.UPLOAD,"no");

        if(getPkk_DataKeluarga_by_id(no_rtm).size() > 0)
        {
            return update_pkk_data_keluarga(key,value,no_rtm);
        }
        else
        {
            contentValues.put(Helper.NO_KK,no_rtm);
            long id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_PKK_DATA_KELUARGA,null,contentValues);

            return id_insert_tweb_penduduk;
        }

    }

    public List<Ent_PkkDataKeluarga> getPkk_DataKeluarga_by_id(String no_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_PKK_DATA_KELUARGA+" WHERE no_kk = '"+no_rtm+"'",null);
        List<Ent_PkkDataKeluarga> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkDataKeluarga pkd = new Ent_PkkDataKeluarga();
            pkd.setId_dk(cursor.getString(cursor.getColumnIndex(Helper.ID_DK)));
            pkd.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));
            pkd.setMakanan_pokok(cursor.getString(cursor.getColumnIndex(Helper.MAKANAN_POKOK)));
            pkd.setJml_makanan_pokok(cursor.getString(cursor.getColumnIndex(Helper.JML_MAKANAN_POKOK)));
            pkd.setJamban(cursor.getString(cursor.getColumnIndex(Helper.JAMBAN)));
            pkd.setJml_jamban(cursor.getString(cursor.getColumnIndex(Helper.JML_JAMBAN)));
            pkd.setSumber_air(cursor.getString(cursor.getColumnIndex(Helper.SUMBER_AIR)));
            pkd.setJml_sumber_air(cursor.getString(cursor.getColumnIndex(Helper.JML_SUMBER_AIR)));
            pkd.setTempat_sampah(cursor.getString(cursor.getColumnIndex(Helper.TEMPAT_SAMPAH)));
            pkd.setJml_tempat_sampah(cursor.getString(cursor.getColumnIndex(Helper.JML_TEMPAT_SAMPAH)));
            pkd.setSaluran_pembuangan_air(cursor.getString(cursor.getColumnIndex(Helper.SALURAN_PEMBUANGAN_AIR)));
            pkd.setJml_saluran_pembuangan_air(cursor.getString(cursor.getColumnIndex(Helper.JML_SALURAN_PEMBUANGAN_AIR)));
            pkd.setStiker_p4k(cursor.getString(cursor.getColumnIndex(Helper.STIKER_P4K)));
            pkd.setJml_stiker_p4k(cursor.getString(cursor.getColumnIndex(Helper.JML_STIKER_P4K)));
            pkd.setKriteria_rumah(cursor.getString(cursor.getColumnIndex(Helper.KRITERIA_RUMAH)));
            pkd.setJml_kriteria_rumah(cursor.getString(cursor.getColumnIndex(Helper.JML_KRITERIA_RUMAH)));
            pkd.setUp2k(cursor.getString(cursor.getColumnIndex(Helper.UP2K)));
            pkd.setJml_up2k(cursor.getString(cursor.getColumnIndex(Helper.JML_UP2K)));
            pkd.setKeg_sehat_lingkungan(cursor.getString(cursor.getColumnIndex(Helper.KEG_SEHAT_LINGKUNGAN)));
            pkd.setJml_keg_sehat_lingkungan(cursor.getString(cursor.getColumnIndex(Helper.JML_KEG_SEHAT_LINGKUNGAN)));
            pkd.setPtp(cursor.getString(cursor.getColumnIndex(Helper.PTP)));
            pkd.setIndustri_rt(cursor.getString(cursor.getColumnIndex(Helper.INDUSTRI_RT)));

            listPresence.add(pkd);

        }
        return listPresence;
    }

    public List<Ent_PkkDataKeluarga> getPkk_data_keluarga()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_DK,Helper.NO_KK,Helper.MAKANAN_POKOK,Helper.JML_MAKANAN_POKOK,Helper.JAMBAN,
                            Helper.JML_JAMBAN,Helper.SUMBER_AIR,Helper.JML_SUMBER_AIR,Helper.TEMPAT_SAMPAH,Helper.JML_TEMPAT_SAMPAH,
                            Helper.SALURAN_PEMBUANGAN_AIR,Helper.JML_SALURAN_PEMBUANGAN_AIR,Helper.STIKER_P4K,Helper.JML_STIKER_P4K,
                            Helper.KRITERIA_RUMAH,Helper.JML_KRITERIA_RUMAH,Helper.UP2K,Helper.JML_UP2K,Helper.KEG_SEHAT_LINGKUNGAN,Helper.JML_KEG_SEHAT_LINGKUNGAN};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_PKK_DATA_KELUARGA+" WHERE upload = 'no'",null);
        List<Ent_PkkDataKeluarga> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkDataKeluarga pkd = new Ent_PkkDataKeluarga();
            pkd.setId_dk(cursor.getString(cursor.getColumnIndex(Helper.ID_DK)));
            pkd.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));
            pkd.setMakanan_pokok(cursor.getString(cursor.getColumnIndex(Helper.MAKANAN_POKOK)));
            pkd.setJml_makanan_pokok(cursor.getString(cursor.getColumnIndex(Helper.JML_MAKANAN_POKOK)));
            pkd.setJamban(cursor.getString(cursor.getColumnIndex(Helper.JAMBAN)));
            pkd.setJml_jamban(cursor.getString(cursor.getColumnIndex(Helper.JML_JAMBAN)));
            pkd.setSumber_air(cursor.getString(cursor.getColumnIndex(Helper.SUMBER_AIR)));
            pkd.setJml_sumber_air(cursor.getString(cursor.getColumnIndex(Helper.JML_SUMBER_AIR)));
            pkd.setTempat_sampah(cursor.getString(cursor.getColumnIndex(Helper.TEMPAT_SAMPAH)));
            pkd.setJml_tempat_sampah(cursor.getString(cursor.getColumnIndex(Helper.JML_TEMPAT_SAMPAH)));
            pkd.setSaluran_pembuangan_air(cursor.getString(cursor.getColumnIndex(Helper.SALURAN_PEMBUANGAN_AIR)));
            pkd.setJml_saluran_pembuangan_air(cursor.getString(cursor.getColumnIndex(Helper.JML_SALURAN_PEMBUANGAN_AIR)));
            pkd.setStiker_p4k(cursor.getString(cursor.getColumnIndex(Helper.STIKER_P4K)));
            pkd.setJml_stiker_p4k(cursor.getString(cursor.getColumnIndex(Helper.JML_STIKER_P4K)));
            pkd.setKriteria_rumah(cursor.getString(cursor.getColumnIndex(Helper.KRITERIA_RUMAH)));
            pkd.setJml_kriteria_rumah(cursor.getString(cursor.getColumnIndex(Helper.JML_KRITERIA_RUMAH)));
            pkd.setUp2k(cursor.getString(cursor.getColumnIndex(Helper.UP2K)));
            pkd.setJml_up2k(cursor.getString(cursor.getColumnIndex(Helper.JML_UP2K)));
            pkd.setKeg_sehat_lingkungan(cursor.getString(cursor.getColumnIndex(Helper.KEG_SEHAT_LINGKUNGAN)));
            pkd.setJml_keg_sehat_lingkungan(cursor.getString(cursor.getColumnIndex(Helper.JML_KEG_SEHAT_LINGKUNGAN)));
            pkd.setPtp(cursor.getString(cursor.getColumnIndex(Helper.PTP)));
            pkd.setIndustri_rt(cursor.getString(cursor.getColumnIndex(Helper.INDUSTRI_RT)));



            listPresence.add(pkd);

        }
        return listPresence;
    }


    public boolean delete_pkk_data_keluarga_by_No_KK(String no_kk) //no_kk
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_DATA_KELUARGA,helper.NO_KK+"=?",new String[]{no_kk}) > 0;
    }

    public boolean delete_pkk_data_keluarga_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_DATA_KELUARGA,helper.ID_DK+"="+id,null) > 0;
    }

    public boolean delete_all_pkk_data_keluarga()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_DATA_KELUARGA,null,null) > 0;
    }


    //PKK Dasa wisma
    public long Insert_pkk_dasa_wisma(Ent_PkkDasaWisma pdw)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_DASAWISMA,pdw.getId_dasa_wisma());
        contentValues.put(Helper.ID_CLUSTER,pdw.getId_cluster());
        contentValues.put(Helper.ID_KEPALA,pdw.getId_kepala());
        contentValues.put(Helper.NAMA_DASA_WISMA,pdw.getNama_dasa_wisma());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_pkk_dasa_wisma = dbb.insert(Helper.TABLE_PKK_DASA_WISMA,null,contentValues);
        return id_insert_pkk_dasa_wisma;
    }

    public List<Ent_PkkDasaWisma> getPkk_dasa_wisma()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_DASAWISMA,Helper.ID_CLUSTER,Helper.ID_KEPALA,Helper.NAMA_DASA_WISMA};
        Cursor cursor = db.query(Helper.TABLE_PKK_DASA_WISMA,coloumn,null,null,null,null,null);
        List<Ent_PkkDasaWisma> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkDasaWisma pdw = new Ent_PkkDasaWisma();
            pdw.setId_dasa_wisma(cursor.getString(cursor.getColumnIndex(Helper.ID_DASAWISMA)));
            pdw.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            pdw.setId_kepala(cursor.getString(cursor.getColumnIndex(Helper.ID_KEPALA)));
            pdw.setNama_dasa_wisma(cursor.getString(cursor.getColumnIndex(Helper.NAMA_DASA_WISMA)));

            listPresence.add(pdw);

        }
        return listPresence;
    }


    public boolean delete_pkk_dasa_wisma_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_DASA_WISMA,helper.ID_DASAWISMA+"="+id,null) > 0;
    }

    public boolean delete_all_pkk_dasa_wisma()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_DASA_WISMA,null,null) > 0;
    }


    //PKK Kelompok Dasa Wisma
    public long Insert_pkk_kelompok_dasa_wisma(Ent_PkkKelompokDasawisma pkd)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_KELOMPOK_DASAWISMA,pkd.getId_kelompok());
        contentValues.put(Helper.NO_KK,pkd.getNo_kk());
        contentValues.put(Helper.ID_DASAWISMA,pkd.getId_dasa_wisma());
        contentValues.put(Helper.UPLOAD,"no");


        long id_insert_pkk_kelompok_dasa_wisma = dbb.insert(Helper.TABLE_PKK_KELOMPOK_DASAWISMA,null,contentValues);
        return id_insert_pkk_kelompok_dasa_wisma;
    }


    public long Input_kelompok_dasawisma(String key,String value,String no_rtm) //no_rtm atau no_kk
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(helper.ID_KELOMPOK_DASAWISMA,no_rtm);
        contentValues.put(helper.NO_KK,no_rtm);
        contentValues.put(helper.UPLOAD,"no");
        contentValues.put(key,value);

        if(getDataKelompokDaswisma_by_id(no_rtm).size() > 0)
        {
            return update_pkk_kelompok_dasawisma(key,value,no_rtm);
        }
        else
        {
            long id_insert_kelompok_daswisma = dbb.insert(Helper.TABLE_PKK_KELOMPOK_DASAWISMA,null,contentValues);

            return id_insert_kelompok_daswisma;
        }

    }

    public long update_pkk_kelompok_dasawisma(String key,String value, String no_rtm) {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("UPDATE "+Helper.TABLE_PKK_KELOMPOK_DASAWISMA+" SET "+key+" = "+"'"+value+"' "+ "WHERE no_kk = "+"'"+no_rtm+"'");

        if(getAllKelompokDasawisma().size() > 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }

    }

    public ArrayList getAllKelompokDasawisma() {
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select changes() as cek from " + Helper.TABLE_PKK_KELOMPOK_DASAWISMA, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            if ((res != null) && (res.getCount() > 0))
                array_list.add(res.getString(res.getColumnIndex("cek")));
            res.moveToNext();
        }
        return array_list;
    }


    public List<Ent_PkkKelompokDasawisma> getDataKelompokDaswisma_by_id(String no_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_KELOMPOK_DASAWISMA,Helper.NO_KK,Helper.ID_DASAWISMA};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_PKK_KELOMPOK_DASAWISMA+" WHERE no_kk = '"+no_rtm+"'",null);
        List<Ent_PkkKelompokDasawisma> listdasawisma = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkKelompokDasawisma pkd = new Ent_PkkKelompokDasawisma();
            pkd.setId_kelompok(cursor.getString(cursor.getColumnIndex(Helper.ID_KELOMPOK_DASAWISMA)));
            pkd.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));

            listdasawisma.add(pkd);

        }
        return listdasawisma;
    }

    public List<Ent_PkkKelompokDasawisma> getPkk_kelompok_dasa_wisma_no_rtm(String no_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_KELOMPOK_DASAWISMA,Helper.NO_KK,Helper.ID_DASAWISMA};
        Cursor cursor = db.rawQuery("SELECT * FROM pkk_kelompok_dasawisma WHERE upload = 'no' AND no_kk = '"+no_rtm+"'",null);
        List<Ent_PkkKelompokDasawisma> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkKelompokDasawisma pdw = new Ent_PkkKelompokDasawisma();
            pdw.setId_kelompok(cursor.getString(cursor.getColumnIndex(Helper.ID_KELOMPOK_DASAWISMA)));
            pdw.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));
            pdw.setId_dasa_wisma(cursor.getString(cursor.getColumnIndex(Helper.ID_DASAWISMA)));

            listPresence.add(pdw);

        }
        return listPresence;
    }

    public List<Ent_PkkKelompokDasawisma> getPkk_kelompok_dasa_wisma()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_KELOMPOK_DASAWISMA,Helper.NO_KK,Helper.ID_DASAWISMA};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_PKK_KELOMPOK_DASAWISMA+" WHERE upload = 'no'",null);
        List<Ent_PkkKelompokDasawisma> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_PkkKelompokDasawisma pdw = new Ent_PkkKelompokDasawisma();
            pdw.setId_kelompok(cursor.getString(cursor.getColumnIndex(Helper.ID_KELOMPOK_DASAWISMA)));
            pdw.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));
            pdw.setId_dasa_wisma(cursor.getString(cursor.getColumnIndex(Helper.ID_DASAWISMA)));

            listPresence.add(pdw);

        }
        return listPresence;
    }


    public boolean delete_pkk_kelompok_dasa_wisma_by_no_rtm(String no_rtm)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_KELOMPOK_DASAWISMA,helper.NO_KK+"=?",new String[]{no_rtm}) > 0;
    }

    public boolean delete_all_pkk_kelompok_dasa_wisma()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_PKK_KELOMPOK_DASAWISMA,null,null) > 0;
    }
}
