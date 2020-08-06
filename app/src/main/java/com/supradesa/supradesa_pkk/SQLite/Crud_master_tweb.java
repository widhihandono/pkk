package com.supradesa.supradesa_pkk.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.supradesa.supradesa_pkk.Util.SharedPref;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukAgama;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukHubungan;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukPekerjaan;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukPendidikanKK;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukUmur;

import java.util.ArrayList;
import java.util.List;

public class Crud_master_tweb {
    Helper helper;
    SharedPref sharedPref;

    public Crud_master_tweb(Context context) {
        helper = new Helper(context);
        sharedPref = new SharedPref(context);
    }

    public long InsertData_tweb_penduduk_pendidikan_kk(Ent_twebPendudukPendidikanKK ep)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_PENDUDUK_PENDIDIKAN_KK,ep.getId());
        contentValues.put(Helper.NAMA,ep.getNama());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK,null,contentValues);
        return id_insert_tweb_penduduk;
    }


    public List<Ent_twebPendudukPendidikanKK> getData_tweb_penduduk_pendidikan_kk()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_PENDUDUK_PENDIDIKAN_KK,Helper.NAMA};
        Cursor cursor = db.query(Helper.TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK,coloumn,null,null,null,null,null);
        List<Ent_twebPendudukPendidikanKK> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPendudukPendidikanKK ep = new Ent_twebPendudukPendidikanKK();
            ep.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_PENDUDUK_PENDIDIKAN_KK)));
            ep.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            ep.setUpload(cursor.getString(cursor.getColumnIndex(Helper.UPLOAD)));

            listPresence.add(ep);

        }
        return listPresence;
    }


    public boolean delete_penduduk_pendidikan_kk_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK,helper.ID_PENDUDUK_PENDIDIKAN_KK+"="+id,null) > 0;
    }

    public boolean delete_all_penduduk_pendidikan_kk()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK,null,null) > 0;
    }


    //TWEB PENDUDUK PEKERJAAN
    public long InsertData_tweb_penduduk_pekerjaan(Ent_twebPendudukPekerjaan epp)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_PENDUDUK_PEKERJAAN,epp.getId());
        contentValues.put(Helper.NAMA,epp.getNama());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_TWEB_PENDUDUK_PEKERJAAN,null,contentValues);
        return id_insert_tweb_penduduk;
    }

    public List<Ent_twebPendudukPekerjaan> getData_tweb_penduduk_pekerjaan()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_PENDUDUK_PEKERJAAN,Helper.NAMA};
        Cursor cursor = db.query(Helper.TABLE_TWEB_RTM,coloumn,null,null,null,null,null);
        List<Ent_twebPendudukPekerjaan> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPendudukPekerjaan epp = new Ent_twebPendudukPekerjaan();
            epp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_PENDUDUK_PEKERJAAN)));
            epp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));


            listPresence.add(epp);

        }
        return listPresence;
    }


    public boolean delete_penduduk_pekerjaan_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_PEKERJAAN,helper.ID_PENDUDUK_PEKERJAAN+"="+id,null) > 0;
    }

    public boolean delete_all_penduduk_pekerjaan()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_PEKERJAAN,null,null) > 0;
    }

    //TWEB PENDUDUK UMUR
    public long InsertData_tweb_penduduk_umur(Ent_twebPendudukUmur epu)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_PENDUDUK_UMUR,epu.getId());
        contentValues.put(Helper.NAMA,epu.getNama());
        contentValues.put(Helper.DARI,epu.getDari());
        contentValues.put(Helper.SAMPAI,epu.getSampai());
        contentValues.put(Helper.STATUS,epu.getStatus());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk_umur = dbb.insert(Helper.TABLE_TWEB_PENDUDUK_UMUR,null,contentValues);
        return id_insert_tweb_penduduk_umur;
    }

    public List<Ent_twebPendudukUmur> getData_tweb_penduduk_umur()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_PENDUDUK_UMUR,Helper.NAMA,Helper.DARI,Helper.SAMPAI,Helper.STATUS};
        Cursor cursor = db.query(Helper.TABLE_TWEB_PENDUDUK_UMUR,coloumn,null,null,null,null,null);
        List<Ent_twebPendudukUmur> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPendudukUmur epu = new Ent_twebPendudukUmur();
            epu.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_PENDUDUK_UMUR)));
            epu.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            epu.setDari(cursor.getString(cursor.getColumnIndex(Helper.DARI)));
            epu.setSampai(cursor.getString(cursor.getColumnIndex(Helper.SAMPAI)));
            epu.setStatus(cursor.getString(cursor.getColumnIndex(Helper.STATUS)));


            listPresence.add(epu);

        }
        return listPresence;
    }


    public boolean delete_penduduk_umur_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_UMUR,helper.ID_PENDUDUK_UMUR+"="+id,null) > 0;
    }

    public boolean delete_all_penduduk_umur()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_UMUR,null,null) > 0;
    }



    //TWEB PENDUDUK AGAMA
    public long InsertData_tweb_penduduk_agama(Ent_twebPendudukAgama epa)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_PENDUDUK_AGAMA,epa.getId());
        contentValues.put(Helper.NAMA,epa.getNama());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk_agama = dbb.insert(Helper.TABLE_TWEB_PENDUDUK_AGAMA,null,contentValues);
        return id_insert_tweb_penduduk_agama;
    }

    public List<Ent_twebPendudukAgama> getData_tweb_penduduk_agama()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_PENDUDUK_AGAMA,Helper.NAMA};
        Cursor cursor = db.query(Helper.TABLE_TWEB_PENDUDUK_AGAMA,coloumn,null,null,null,null,null);
        List<Ent_twebPendudukAgama> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPendudukAgama epa = new Ent_twebPendudukAgama();
            epa.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_PENDUDUK_AGAMA)));
            epa.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));


            listPresence.add(epa);

        }
        return listPresence;
    }


    public boolean delete_penduduk_agama_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_AGAMA,helper.ID_PENDUDUK_AGAMA+"="+id,null) > 0;
    }

    public boolean delete_all_penduduk_agama()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_AGAMA,null,null) > 0;
    }


    //TWEB PENDUDUK HUBUNGAN
    public long InsertData_tweb_penduduk_hubungan(Ent_twebPendudukHubungan eph)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_PENDUDUK_HUBUNGAN,eph.getId());
        contentValues.put(Helper.NAMA,eph.getNama());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk_hubungan = dbb.insert(Helper.TABLE_TWEB_PENDUDUK_HUBUNGAN,null,contentValues);
        return id_insert_tweb_penduduk_hubungan;
    }

    public List<Ent_twebPendudukHubungan> getData_tweb_penduduk_hubungan()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_PENDUDUK_HUBUNGAN,Helper.NAMA};
        Cursor cursor = db.query(Helper.TABLE_TWEB_PENDUDUK_HUBUNGAN,coloumn,null,null,null,null,null);
        List<Ent_twebPendudukHubungan> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPendudukHubungan eph = new Ent_twebPendudukHubungan();
            eph.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_PENDUDUK_HUBUNGAN)));
            eph.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));


            listPresence.add(eph);

        }
        return listPresence;
    }


    public boolean delete_penduduk_hubungan_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_HUBUNGAN,helper.ID_PENDUDUK_HUBUNGAN+"="+id,null) > 0;
    }

    public boolean delete_all_penduduk_hubungan()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK_HUBUNGAN,null,null) > 0;
    }


}
