package com.supradesa.supradesa_pkk.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.supradesa.supradesa_pkk.Util.List_Temporary;
import com.supradesa.supradesa_pkk.Util.SharedPref;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;

import java.util.ArrayList;
import java.util.List;

public class Crud {
    Helper helper;
    SharedPref sharedPref;
    List_Temporary listTemporary;

    public Crud(Context context) {
        helper = new Helper(context);
        sharedPref = new SharedPref(context);
        listTemporary = new List_Temporary();
    }

    public long InsertData_tweb_penduduk(Ent_twebPenduduk etp)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID,etp.getId());
        contentValues.put(Helper.NAMA,etp.getNama());
        contentValues.put(Helper.NIK,etp.getNik());
        contentValues.put(Helper.ID_KK,etp.getId_kk());
        contentValues.put(Helper.KK_LEVEL,etp.getKk_level());
        contentValues.put(Helper.ID_RTM,etp.getId_rtm());
        contentValues.put(Helper.RTM_LEVEL,etp.getRtm_level());
        contentValues.put(Helper.SEX,etp.getSex());
        contentValues.put(Helper.TEMPATLAHIR,etp.getTempatlahir());
        contentValues.put(Helper.TANGGALLAHIR,etp.getTanggallahir());
        contentValues.put(Helper.AGAMA_ID,etp.getAgama_id());
        contentValues.put(Helper.PENDIDIKAN_KK_ID,etp.getPendidikan_kk_id());
        contentValues.put(Helper.PEKERJAAN_ID,etp.getPekerjaan_id());
        contentValues.put(Helper.STATUS_KAWIN,etp.getStatus_kawin());
        contentValues.put(Helper.ID_CLUSTER,etp.getId_cluster());
        contentValues.put(Helper.ALAMAT_SEKARANG,etp.getAlamat_sekarang());
        contentValues.put(Helper.CACAT_ID,etp.getCacat_id());
        contentValues.put(Helper.RT,etp.getRt());
        contentValues.put(Helper.RW,etp.getRw());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_TWEB_PENDUDUK,null,contentValues);
        return id_insert_tweb_penduduk;
    }

    public long updateData_tweb_penduduk_id_rtm(String id_rtm,String nik,String id_penduduk)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_RTM,id_rtm);
        if(listTemporary.id_penduduk.equals(id_penduduk))
        {
            contentValues.put(Helper.RTM_LEVEL,"1");
        }
        else
        {
            contentValues.put(Helper.RTM_LEVEL,"2");
        }


        long update = dbb.update(Helper.TABLE_TWEB_PENDUDUK,contentValues,Helper.NIK+"="+nik,null);
        return update;
    }

    public long updateData_tweb_penduduk_upload(String nik,String upload)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.UPLOAD,upload);


        long update = dbb.update(Helper.TABLE_TWEB_PENDUDUK,contentValues,Helper.NIK+"="+nik,null);
        return update;
    }

    public List<Ent_twebPenduduk> getData_tweb_penduduk_by_id_rtm_and_rtm_level()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID,Helper.NAMA,Helper.NIK,Helper.ID_KK,Helper.KK_LEVEL,Helper.ID_RTM,Helper.RTM_LEVEL,Helper.SEX,
                Helper.TEMPATLAHIR,Helper.TANGGALLAHIR,Helper.AGAMA_ID,Helper.PENDIDIKAN_KK_ID,Helper.PEKERJAAN_ID,Helper.STATUS_KAWIN,
                Helper.ID_CLUSTER,Helper.ALAMAT_SEKARANG,Helper.CACAT_ID};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_TWEB_PENDUDUK+" WHERE (id_rtm != 'null' OR rtm_level != 'null') AND upload = 'no'",null);
        List<Ent_twebPenduduk> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPenduduk etp = new Ent_twebPenduduk();
            etp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID)));
            etp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etp.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            etp.setId_kk(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etp.setKk_level(cursor.getString(cursor.getColumnIndex(Helper.KK_LEVEL)));
            etp.setId_rtm(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            etp.setRtm_level(cursor.getString(cursor.getColumnIndex(Helper.RTM_LEVEL)));
            etp.setSex(cursor.getString(cursor.getColumnIndex(Helper.SEX)));
            etp.setTempatlahir(cursor.getString(cursor.getColumnIndex(Helper.TEMPATLAHIR)));
            etp.setTanggallahir(cursor.getString(cursor.getColumnIndex(Helper.TANGGALLAHIR)));
            etp.setAgama_id(cursor.getString(cursor.getColumnIndex(Helper.AGAMA_ID)));
            etp.setPendidikan_kk_id(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KK_ID)));
            etp.setPekerjaan_id(cursor.getString(cursor.getColumnIndex(Helper.PEKERJAAN_ID)));
            etp.setStatus_kawin(cursor.getString(cursor.getColumnIndex(Helper.STATUS_KAWIN)));
            etp.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etp.setAlamat_sekarang(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT_SEKARANG)));
            etp.setCacat_id(cursor.getString(cursor.getColumnIndex(Helper.CACAT_ID)));
            etp.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etp.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));

            listPresence.add(etp);

        }
        return listPresence;
    }

    public List<Ent_twebPenduduk> getData_tweb_penduduk_id_kk(String id_kk)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID,Helper.NAMA,Helper.NIK,Helper.ID_KK,Helper.KK_LEVEL,Helper.ID_RTM,Helper.RTM_LEVEL,Helper.SEX,
                Helper.TEMPATLAHIR,Helper.TANGGALLAHIR,Helper.AGAMA_ID,Helper.PENDIDIKAN_KK_ID,Helper.PEKERJAAN_ID,Helper.STATUS_KAWIN,
                Helper.ID_CLUSTER,Helper.ALAMAT_SEKARANG,Helper.CACAT_ID};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_TWEB_PENDUDUK+" WHERE id_kk = '"+id_kk+"'",null);
        List<Ent_twebPenduduk> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPenduduk etp = new Ent_twebPenduduk();
            etp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID)));
            etp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etp.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            etp.setId_kk(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etp.setKk_level(cursor.getString(cursor.getColumnIndex(Helper.KK_LEVEL)));
            etp.setId_rtm(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            etp.setRtm_level(cursor.getString(cursor.getColumnIndex(Helper.RTM_LEVEL)));
            etp.setSex(cursor.getString(cursor.getColumnIndex(Helper.SEX)));
            etp.setTempatlahir(cursor.getString(cursor.getColumnIndex(Helper.TEMPATLAHIR)));
            etp.setTanggallahir(cursor.getString(cursor.getColumnIndex(Helper.TANGGALLAHIR)));
            etp.setAgama_id(cursor.getString(cursor.getColumnIndex(Helper.AGAMA_ID)));
            etp.setPendidikan_kk_id(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KK_ID)));
            etp.setPekerjaan_id(cursor.getString(cursor.getColumnIndex(Helper.PEKERJAAN_ID)));
            etp.setStatus_kawin(cursor.getString(cursor.getColumnIndex(Helper.STATUS_KAWIN)));
            etp.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etp.setAlamat_sekarang(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT_SEKARANG)));
            etp.setCacat_id(cursor.getString(cursor.getColumnIndex(Helper.CACAT_ID)));
            etp.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etp.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));

            listPresence.add(etp);

        }
        return listPresence;
    }


    public List<Ent_twebPenduduk> getData_tweb_penduduk(String id_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID,Helper.NAMA,Helper.NIK,Helper.ID_KK,Helper.KK_LEVEL,Helper.ID_RTM,Helper.RTM_LEVEL,Helper.SEX,
                            Helper.TEMPATLAHIR,Helper.TANGGALLAHIR,Helper.AGAMA_ID,Helper.PENDIDIKAN_KK_ID,Helper.PEKERJAAN_ID,Helper.STATUS_KAWIN,
                            Helper.ID_CLUSTER,Helper.ALAMAT_SEKARANG,Helper.CACAT_ID};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_TWEB_PENDUDUK+" WHERE id_rtm = '"+id_rtm+"'",null);
        List<Ent_twebPenduduk> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPenduduk etp = new Ent_twebPenduduk();
            etp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID)));
            etp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etp.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            etp.setId_kk(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etp.setKk_level(cursor.getString(cursor.getColumnIndex(Helper.KK_LEVEL)));
            etp.setId_rtm(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            etp.setRtm_level(cursor.getString(cursor.getColumnIndex(Helper.RTM_LEVEL)));
            etp.setSex(cursor.getString(cursor.getColumnIndex(Helper.SEX)));
            etp.setTempatlahir(cursor.getString(cursor.getColumnIndex(Helper.TEMPATLAHIR)));
            etp.setTanggallahir(cursor.getString(cursor.getColumnIndex(Helper.TANGGALLAHIR)));
            etp.setAgama_id(cursor.getString(cursor.getColumnIndex(Helper.AGAMA_ID)));
            etp.setPendidikan_kk_id(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KK_ID)));
            etp.setPekerjaan_id(cursor.getString(cursor.getColumnIndex(Helper.PEKERJAAN_ID)));
            etp.setStatus_kawin(cursor.getString(cursor.getColumnIndex(Helper.STATUS_KAWIN)));
            etp.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etp.setAlamat_sekarang(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT_SEKARANG)));
            etp.setCacat_id(cursor.getString(cursor.getColumnIndex(Helper.CACAT_ID)));
            etp.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etp.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));

                listPresence.add(etp);

        }
        return listPresence;
    }

    public List<Ent_twebPenduduk> getData_tweb_penduduk()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID,Helper.NAMA,Helper.NIK,Helper.ID_KK,Helper.KK_LEVEL,Helper.ID_RTM,Helper.RTM_LEVEL,Helper.SEX,
                Helper.TEMPATLAHIR,Helper.TANGGALLAHIR,Helper.AGAMA_ID,Helper.PENDIDIKAN_KK_ID,Helper.PEKERJAAN_ID,Helper.STATUS_KAWIN,
                Helper.ID_CLUSTER,Helper.ALAMAT_SEKARANG,Helper.CACAT_ID};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_TWEB_PENDUDUK,null);
        List<Ent_twebPenduduk> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPenduduk etp = new Ent_twebPenduduk();
            etp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID)));
            etp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etp.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            etp.setId_kk(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etp.setKk_level(cursor.getString(cursor.getColumnIndex(Helper.KK_LEVEL)));
            etp.setId_rtm(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            etp.setRtm_level(cursor.getString(cursor.getColumnIndex(Helper.RTM_LEVEL)));
            etp.setSex(cursor.getString(cursor.getColumnIndex(Helper.SEX)));
            etp.setTempatlahir(cursor.getString(cursor.getColumnIndex(Helper.TEMPATLAHIR)));
            etp.setTanggallahir(cursor.getString(cursor.getColumnIndex(Helper.TANGGALLAHIR)));
            etp.setAgama_id(cursor.getString(cursor.getColumnIndex(Helper.AGAMA_ID)));
            etp.setPendidikan_kk_id(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KK_ID)));
            etp.setPekerjaan_id(cursor.getString(cursor.getColumnIndex(Helper.PEKERJAAN_ID)));
            etp.setStatus_kawin(cursor.getString(cursor.getColumnIndex(Helper.STATUS_KAWIN)));
            etp.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etp.setAlamat_sekarang(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT_SEKARANG)));
            etp.setCacat_id(cursor.getString(cursor.getColumnIndex(Helper.CACAT_ID)));
            etp.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etp.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));

            listPresence.add(etp);

        }
        return listPresence;
    }

    public List<Ent_twebPenduduk> getData_tweb_penduduk_nama_kk(String no_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,nama FROM tweb_penduduk WHERE rtm_level = '1' AND id_rtm = '"+no_rtm+"'",null);
        List<Ent_twebPenduduk> listPenduduk = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPenduduk etp = new Ent_twebPenduduk();
            etp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID)));
            etp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));

            listPenduduk.add(etp);

        }
        return listPenduduk;
    }


    public List<Ent_twebPenduduk> getData_tweb_penduduk_no_rtm(String no_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID,Helper.NAMA,Helper.NIK,Helper.ID_KK,Helper.KK_LEVEL,Helper.ID_RTM,Helper.RTM_LEVEL,Helper.SEX,
                Helper.TEMPATLAHIR,Helper.TANGGALLAHIR,Helper.AGAMA_ID,Helper.PENDIDIKAN_KK_ID,Helper.PEKERJAAN_ID,Helper.STATUS_KAWIN,
                Helper.ID_CLUSTER,Helper.ALAMAT_SEKARANG,Helper.CACAT_ID};
        Cursor cursor = db.rawQuery("SELECT * FROM tweb_penduduk WHERE id_rtm = '"+no_rtm+"'",null);
        List<Ent_twebPenduduk> listPenduduk = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPenduduk etp = new Ent_twebPenduduk();
            etp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID)));
            etp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etp.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            etp.setId_kk(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etp.setKk_level(cursor.getString(cursor.getColumnIndex(Helper.KK_LEVEL)));
            etp.setId_rtm(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            etp.setRtm_level(cursor.getString(cursor.getColumnIndex(Helper.RTM_LEVEL)));
            etp.setSex(cursor.getString(cursor.getColumnIndex(Helper.SEX)));
            etp.setTempatlahir(cursor.getString(cursor.getColumnIndex(Helper.TEMPATLAHIR)));
            etp.setTanggallahir(cursor.getString(cursor.getColumnIndex(Helper.TANGGALLAHIR)));
            etp.setAgama_id(cursor.getString(cursor.getColumnIndex(Helper.AGAMA_ID)));
            etp.setPendidikan_kk_id(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KK_ID)));
            etp.setPekerjaan_id(cursor.getString(cursor.getColumnIndex(Helper.PEKERJAAN_ID)));
            etp.setStatus_kawin(cursor.getString(cursor.getColumnIndex(Helper.STATUS_KAWIN)));
            etp.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etp.setAlamat_sekarang(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT_SEKARANG)));
            etp.setCacat_id(cursor.getString(cursor.getColumnIndex(Helper.CACAT_ID)));
            etp.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etp.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));

            listPenduduk.add(etp);

        }
        return listPenduduk;
    }



    public boolean delete_penduduk_by_nik(String nik)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK,helper.NIK+"=?",new String[]{nik}) > 0;
    }

    public boolean delete_all_penduduk()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_PENDUDUK,null,null) > 0;
    }


    //RTM
    public long InsertData_tweb_rtm(Ent_twebRtm rtm)
    {

        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_RTM,rtm.getId());
        contentValues.put(Helper.NIK_KEPALA,rtm.getNik_kepala());
        contentValues.put(Helper.NO_KK,rtm.getNo_kk());
        contentValues.put(Helper.TGL_DAFTAR,rtm.getTgl_daftar());
        contentValues.put(Helper.KELAS_SOSIAL,rtm.getKelas_sosial());
        contentValues.put(Helper.UPLOAD,"no");


        if(cek_data_rtm_by_id(rtm.getNo_kk()).size() > 0)
        {
            long id_insert_tweb_penduduk = 0;
            if(dbb.delete(Helper.TABLE_TWEB_RTM, Helper.NO_KK + "=?", new String[]{rtm.getNo_kk()}) > 0)
            {
                id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_TWEB_RTM,null,contentValues);

            }
            return id_insert_tweb_penduduk;
        }
        else
        {


             long id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_TWEB_RTM,null,contentValues);
            return id_insert_tweb_penduduk;
        }



    }

    public long updateData_rtm(String no_kk,String upload)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.UPLOAD,upload);



        long update = dbb.update(Helper.TABLE_TWEB_RTM,contentValues,Helper.NO_KK+"="+no_kk,null);
        return update;
    }

    public List<Ent_twebRtm> cek_data_rtm_by_id(String no_kk)//no_kk == no_rtm
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_TWEB_RTM+" WHERE no_kk = '"+no_kk+"'",null);
        List<Ent_twebRtm> list_rtm = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebRtm rtm = new Ent_twebRtm();
            rtm.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));

            list_rtm.add(rtm);
        }
        return list_rtm;
    }


    public List<Ent_twebRtm> getData_tweb_rtm_join_penduduk()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_RTM,Helper.NIK_KEPALA,Helper.NO_KK,Helper.TGL_DAFTAR,Helper.KELAS_SOSIAL};
        Cursor cursor = db.rawQuery("SELECT tweb_rtm.*,tweb_penduduk.nama FROM tweb_penduduk JOIN tweb_rtm ON tweb_penduduk.id = tweb_rtm.nik_kepala",null);
        List<Ent_twebRtm> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebRtm rtm = new Ent_twebRtm();
            rtm.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            rtm.setNik_kepala(cursor.getString(cursor.getColumnIndex(Helper.NIK_KEPALA)));
            rtm.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));
            rtm.setTgl_daftar(cursor.getString(cursor.getColumnIndex(Helper.TGL_DAFTAR)));
            rtm.setKelas_sosial(cursor.getString(cursor.getColumnIndex(Helper.KELAS_SOSIAL)));
            rtm.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));


            listPresence.add(rtm);

        }
        return listPresence;
    }

    public List<Ent_twebRtm> getData_tweb_rtm()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_RTM,Helper.NIK_KEPALA,Helper.NO_KK,Helper.TGL_DAFTAR,Helper.KELAS_SOSIAL};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_TWEB_RTM+" WHERE upload = 'no'",null);
        List<Ent_twebRtm> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebRtm rtm = new Ent_twebRtm();
            rtm.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            rtm.setNik_kepala(cursor.getString(cursor.getColumnIndex(Helper.NIK_KEPALA)));
            rtm.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));
            rtm.setTgl_daftar(cursor.getString(cursor.getColumnIndex(Helper.TGL_DAFTAR)));
            rtm.setKelas_sosial(cursor.getString(cursor.getColumnIndex(Helper.KELAS_SOSIAL)));


            listPresence.add(rtm);

        }
        return listPresence;
    }


    public String getData_tweb_rtm_id_kk(String id_kk)  //SELECT RTM by ID_KK
    {
        String no_kk="";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT no_kk FROM "+Helper.TABLE_TWEB_KELUARGA+" WHERE id_kk = '"+id_kk+"'",null);
        while (cursor.moveToNext())
        {
            no_kk = cursor.getString(cursor.getColumnIndex(Helper.NO_KK));
        }
        return no_kk;
    }


    public boolean delete_rtm_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_RTM,helper.ID_RTM+"="+id,null) > 0;
    }

    public boolean delete_all_rtm()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_RTM,null,null) > 0;
    }


    //KELUARGA
    public long InsertData_tweb_keluarga(Ent_twebKeluarga etk)
    {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.ID_KK,etk.getId());
        contentValues.put(Helper.NO_KK,etk.getNo_kk());
        contentValues.put(Helper.NIK_KEPALA,etk.getNik_kepala());
        contentValues.put(Helper.TGL_DAFTAR,etk.getTgl_daftar());
        contentValues.put(Helper.KELAS_SOSIAL,etk.getKelas_sosial());
        contentValues.put(Helper.TGL_CETAK_KK,etk.getTgl_cetak_kk());
        contentValues.put(Helper.ALAMAT,etk.getAlamat());
        contentValues.put(Helper.ID_CLUSTER,etk.getId_cluster());
        contentValues.put(Helper.RT,etk.getRt());
        contentValues.put(Helper.RW,etk.getRw());
        contentValues.put(Helper.UPLOAD,"no");

        long id_insert_tweb_penduduk = dbb.insert(Helper.TABLE_TWEB_KELUARGA,null,contentValues);
        return id_insert_tweb_penduduk;
    }

    public List<Ent_twebKeluarga> getData_tweb_keluarga()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_KK,Helper.NO_KK,Helper.NIK_KEPALA,Helper.TGL_DAFTAR,Helper.KELAS_SOSIAL,
                            Helper.TGL_CETAK_KK,Helper.ALAMAT,Helper.ID_CLUSTER};
        Cursor cursor = db.rawQuery("SELECT tweb_penduduk.nama,tweb_penduduk.kk_level,tweb_keluarga.* FROM tweb_penduduk JOIN tweb_keluarga ON "+
                "tweb_keluarga.id_kk = tweb_penduduk.id_kk WHERE tweb_penduduk.kk_level = 1 GROUP BY tweb_keluarga.no_kk",null);

        List<Ent_twebKeluarga> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebKeluarga etk = new Ent_twebKeluarga();
            etk.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etk.setKk_level(cursor.getString(cursor.getColumnIndex(Helper.KK_LEVEL)));
            etk.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etk.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_KK)));
            etk.setNik_kepala(cursor.getString(cursor.getColumnIndex(Helper.NIK_KEPALA)));
            etk.setTgl_daftar(cursor.getString(cursor.getColumnIndex(Helper.TGL_DAFTAR)));
            etk.setKelas_sosial(cursor.getString(cursor.getColumnIndex(Helper.KELAS_SOSIAL)));
            etk.setTgl_cetak_kk(cursor.getString(cursor.getColumnIndex(Helper.TGL_CETAK_KK)));
            etk.setAlamat(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT)));
            etk.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etk.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etk.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));

            listPresence.add(etk);

        }
        return listPresence;
    }




    public boolean delete_keluarga_by_id(String id)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_KELUARGA,helper.ID+"="+id,null) > 0;
    }

    public boolean delete_all_keluarga()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(Helper.TABLE_TWEB_KELUARGA,null,null) > 0;
    }


    //Data Edit
    //Data Join
    public List<Ent_twebKeluarga> getData_tweb_rtm_keluarga()
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID_KK,Helper.NO_KK,Helper.NIK_KEPALA,Helper.TGL_DAFTAR,Helper.KELAS_SOSIAL,
                Helper.TGL_CETAK_KK,Helper.ALAMAT,Helper.ID_CLUSTER};
        Cursor cursor = db.rawQuery("SELECT tweb_rtm.no_kk as no_rtm,tweb_penduduk.nama,tweb_keluarga.* FROM tweb_penduduk JOIN tweb_keluarga ON tweb_keluarga.nik_kepala = tweb_penduduk.id " +
                "JOIN tweb_rtm ON tweb_rtm.nik_kepala = tweb_keluarga.nik_kepala",null);

        List<Ent_twebKeluarga> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebKeluarga etk = new Ent_twebKeluarga();
            etk.setNo_kk(cursor.getString(cursor.getColumnIndex(Helper.NO_RTM)));
            etk.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etk.setId(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etk.setNik_kepala(cursor.getString(cursor.getColumnIndex(Helper.NIK_KEPALA)));
            etk.setTgl_daftar(cursor.getString(cursor.getColumnIndex(Helper.TGL_DAFTAR)));
            etk.setKelas_sosial(cursor.getString(cursor.getColumnIndex(Helper.KELAS_SOSIAL)));
            etk.setTgl_cetak_kk(cursor.getString(cursor.getColumnIndex(Helper.TGL_CETAK_KK)));
            etk.setAlamat(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT)));
            etk.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etk.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etk.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));
            listPresence.add(etk);

        }
        return listPresence;
    }

    public List<Ent_twebPenduduk> getData_tweb_penduduk_edit(String no_rtm)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        String[] coloumn = {Helper.ID,Helper.NAMA,Helper.NIK,Helper.ID_KK,Helper.KK_LEVEL,Helper.ID_RTM,Helper.RTM_LEVEL,Helper.SEX,
                Helper.TEMPATLAHIR,Helper.TANGGALLAHIR,Helper.AGAMA_ID,Helper.PENDIDIKAN_KK_ID,Helper.PEKERJAAN_ID,Helper.STATUS_KAWIN,
                Helper.ID_CLUSTER,Helper.ALAMAT_SEKARANG,Helper.CACAT_ID};
        Cursor cursor = db.rawQuery("SELECT * FROM "+Helper.TABLE_TWEB_PENDUDUK+" WHERE id_rtm = '"+no_rtm+"'",null);
        List<Ent_twebPenduduk> listPresence = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Ent_twebPenduduk etp = new Ent_twebPenduduk();
            etp.setId(cursor.getString(cursor.getColumnIndex(Helper.ID)));
            etp.setNama(cursor.getString(cursor.getColumnIndex(Helper.NAMA)));
            etp.setNik(cursor.getString(cursor.getColumnIndex(Helper.NIK)));
            etp.setId_kk(cursor.getString(cursor.getColumnIndex(Helper.ID_KK)));
            etp.setKk_level(cursor.getString(cursor.getColumnIndex(Helper.KK_LEVEL)));
            etp.setId_rtm(cursor.getString(cursor.getColumnIndex(Helper.ID_RTM)));
            etp.setRtm_level(cursor.getString(cursor.getColumnIndex(Helper.RTM_LEVEL)));
            etp.setSex(cursor.getString(cursor.getColumnIndex(Helper.SEX)));
            etp.setTempatlahir(cursor.getString(cursor.getColumnIndex(Helper.TEMPATLAHIR)));
            etp.setTanggallahir(cursor.getString(cursor.getColumnIndex(Helper.TANGGALLAHIR)));
            etp.setAgama_id(cursor.getString(cursor.getColumnIndex(Helper.AGAMA_ID)));
            etp.setPendidikan_kk_id(cursor.getString(cursor.getColumnIndex(Helper.PENDIDIKAN_KK_ID)));
            etp.setPekerjaan_id(cursor.getString(cursor.getColumnIndex(Helper.PEKERJAAN_ID)));
            etp.setStatus_kawin(cursor.getString(cursor.getColumnIndex(Helper.STATUS_KAWIN)));
            etp.setId_cluster(cursor.getString(cursor.getColumnIndex(Helper.ID_CLUSTER)));
            etp.setAlamat_sekarang(cursor.getString(cursor.getColumnIndex(Helper.ALAMAT_SEKARANG)));
            etp.setCacat_id(cursor.getString(cursor.getColumnIndex(Helper.CACAT_ID)));
            etp.setRt(cursor.getString(cursor.getColumnIndex(Helper.RT)));
            etp.setRw(cursor.getString(cursor.getColumnIndex(Helper.RW)));
            listPresence.add(etp);

        }
        return listPresence;
    }

}
