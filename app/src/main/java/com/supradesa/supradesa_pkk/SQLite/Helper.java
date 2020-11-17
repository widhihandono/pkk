package com.supradesa.supradesa_pkk.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

public class Helper extends SQLiteOpenHelper {
    public static final String UPLOAD = "upload";

    public static final String DATABASE_NAME = "pkk_db";
    public static final String TABLE_TWEB_PENDUDUK = "tweb_penduduk";
    public static final String TABLE_TWEB_RTM = "tweb_rtm";
    public static final String TABLE_TWEB_KELUARGA = "tweb_keluarga";
    public static final String TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK = "tweb_penduduk_pendidikan_kk";
    public static final String TABLE_TWEB_PENDUDUK_PEKERJAAN = "tweb_penduduk_pekerjaan";
    public static final String TABLE_TWEB_PENDUDUK_UMUR = "tweb_penduduk_umur";
    public static final String TABLE_TWEB_PENDUDUK_AGAMA = "tweb_penduduk_agama";
    public static final String TABLE_TWEB_PENDUDUK_HUBUNGAN = "tweb_penduduk_hubungan";
    public static final String TABLE_PKK_CATATAN_KELUARGA = "pkk_catatan_keluarga";
    public static final String TABLE_PKK_CATATAN_KELUARGA_DETAIL = "pkk_catatan_keluarga_detail";
    public static final String TABLE_PKK_DATA_KELUARGA = "pkk_data_keluarga";
    public static final String TABLE_PKK_KELOMPOK_DASAWISMA = "pkk_kelompok_dasawisma";
    public static final String TABLE_PKK_DASA_WISMA = "pkk_dasa_wisma";
    public static final String TABLE_CONFIG_CODE = "config_code";
    public static final int DATABASE_Version = 1;
//    public static final String UID = "id";
    //Penduduk
    public static final String RT = "rt";
    public static final String RW = "rw";
    public static final String ID = "id";
    public static final String NAMA = "nama";
    public static final String NIK = "nik";
    public static final String ID_KK = "id_kk";
    public static final String KK_LEVEL = "kk_level";
    public static final String ID_RTM = "id_rtm";
    public static final String RTM_LEVEL = "rtm_level";
    public static final String SEX = "sex";
    public static final String TEMPATLAHIR = "tempatlahir";
    public static final String TANGGALLAHIR = "tanggallahir";
    public static final String AGAMA_ID = "agama_id";
    public static final String PENDIDIKAN_KK_ID = "pendidikan_kk_id";
    public static final String PENDIDIKAN_SEDANG_ID = " pendidikan_sedang_id";
    public static final String PEKERJAAN_ID = "pekerjaan_id";
    public static final String STATUS_KAWIN = "status_kawin";
    public static final String ID_CLUSTER = "id_cluster";
    public static final String ALAMAT_SEKARANG = "alamat_sekarang";
    public static final String CACAT_ID = "cacat_id";
    public static final String HAPUS_ID_RTM = "hapus_id_rtm";

    //RTM
    public static final String NIK_KEPALA = "nik_kepala";
    public static final String NO_KK = "no_kk";
    public static final String NO_RTM = "no_rtm";
    public static final String TGL_DAFTAR = "tgl_daftar";
    public static final String KELAS_SOSIAL = "kelas_sosial";

    //KELUARGA
    public static final String TGL_CETAK_KK = "tgl_cetak_kk";
    public static final String ALAMAT = "alamat";

    //TWEB_PENDUDUK_PENDIDIKAN_KK
    public static final String ID_PENDUDUK_PENDIDIKAN_KK = "id_penduduk_pendidikan_kk";

    //TWEB_PENDUDUK_PEKERJAAN
    public static final String ID_PENDUDUK_PEKERJAAN = "id_penduduk_pekerjaan";

    //TWEB_PENDUDUK_UMUR
    public static final String ID_PENDUDUK_UMUR = "id_penduduk_umur";
    public static final String DARI = "dari";
    public static final String SAMPAI = "sampai";
    public static final String STATUS = "status";

    //TWEB_PENDUDUK_AGAMA
    public static final String ID_PENDUDUK_AGAMA = "id_penduduk_agama";

    //TWEB_PENDUDUK_HUBUNGAN
    public static final String ID_PENDUDUK_HUBUNGAN = "id_penduduk_hubungan";


    //PKK_CATATAN_KELUARGA
    public static final String ID_CAT = "id_cat";
    public static final String ID_DK = "id_dk";
    public static final String TANGGAL_CAT = "tanggal_cat";

    //PKK_CATATAN_KELUARGA_DETAIL
    public static final String ID_DETAIL_CAT = "id_detail_cat";
    public static final String HAPUS = "hapus";
//    public static final String ID_PENDUDUK = "id_penduduk";
    public static final String BERKEBUTUHAN_KHUSUS = "berkebutuhan_khusus";
    public static final String PENGHAYATAN_DAN_PENGAMALAN_PANCASILA = "penghayatan_dan_pengamalan_pancasila";
    public static final String GOTONG_ROYONG = "gotong_royong";
    public static final String PENDIDIKAN_KETRAMPILAN = "pendidikan_ketrampilan";
    public static final String PENGEMBANGAN_KEHIDUPAN_BERKOPERASI = "pengembangan_kehidupan_berkoperasi";
    public static final String PANGAN = "pangan";
    public static final String SANDANG = "sandang";
    public static final String KESEHATAN = "kesehatan";
    public static final String PERENCANAAN_SEHAT = "perencanaan_sehat";
    public static final String ID_KELOMPOK_UMUR = "id_kelompok_umur";
    public static final String USIA_SUBUR = "usia_subur";
    public static final String IBU_HAMIL = "ibu_hamil";
    public static final String MENYUSUI = "menyusui";
    public static final String NIFAS = "nifas";
    public static final String BUTA_BACA = "buta_baca";
    public static final String BUTA_TULIS = "buta_tulis";
    public static final String BUTA_HITUNG = "buta_hitung";
    public static final String STUNTING = "stunting";


    //PKK_DATA_KELUARGA
    public static final String MAKANAN_POKOK = "makanan_pokok";
    public static final String JML_MAKANAN_POKOK = "jml_makanan_pokok";
    public static final String JAMBAN = "jamban";
    public static final String JML_JAMBAN = "jml_jamban";
    public static final String SUMBER_AIR = "sumber_air";
    public static final String JML_SUMBER_AIR = "jml_sumber_air";
    public static final String TEMPAT_SAMPAH = "tempat_sampah";
    public static final String JML_TEMPAT_SAMPAH = "jml_tempat_sampah";
    public static final String SALURAN_PEMBUANGAN_AIR = "saluran_pembuangan_air";
    public static final String JML_SALURAN_PEMBUANGAN_AIR = "jml_saluran_pembuangan_air";
    public static final String STIKER_P4K = "stiker_p4k";
    public static final String JML_STIKER_P4K = "jml_stiker_p4k";
    public static final String KRITERIA_RUMAH = "kriteria_rumah";
    public static final String JML_KRITERIA_RUMAH = "jml_kriteria_rumah";
    public static final String UP2K = "up2k";
    public static final String JML_UP2K = "jml_up2k";
    public static final String KEG_SEHAT_LINGKUNGAN = "keg_sehat_lingkungan";
    public static final String JML_KEG_SEHAT_LINGKUNGAN = "jml_keg_sehat_lingkungan";
    public static final String PTP = "ptp";
    public static final String INDUSTRI_RT = "Industri_rt";

    //PKK_KELOMPOK_DASAWISMA
    public static final String ID_KELOMPOK_DASAWISMA = "id_kelompok_dasawisma";
    public static final String ID_DASAWISMA = "id_dasa_wisma";

    //PKK_DASA_WISMA
    public static final String ID_KEPALA = "id_kepala"; // atau id_penduduk
    public static final String NAMA_DASA_WISMA = "nama_dasa_wisma";

    //CONFIG CODE
    public static final String KODE_DESA = "kode_desa";
    public static final String KODE_KECAMATAN = "kode_kecamatan";
    public static final String KODE_KABUPATEN = "kode_kabupaten";


    //Config code
    public static final String CREATE_TABLE_CONFIG_CODE = "CREATE TABLE "+TABLE_CONFIG_CODE+
            "("+KODE_DESA+" VARCHAR(50) PRIMARY KEY, "+KODE_KECAMATAN+" VARCHAR(30), "+
            KODE_KABUPATEN+" VARCHAR(5));";
    public static final String DROP_CONFIG_CODE = "DROP TABLE IF EXISTS "+TABLE_CONFIG_CODE;

    //Penduduk
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_TWEB_PENDUDUK+
            "("+ID+" VARCHAR(11) PRIMARY KEY, "+NAMA+" VARCHAR(100), "+
            NIK+" VARCHAR(20), "+ID_KK+" VARCHAR(11), "+KK_LEVEL+" VARCHAR(5),"+ID_RTM+" VARCHAR(50)," +
            RTM_LEVEL+" VARCHAR(8), "+SEX +" VARCHAR(4), "+TEMPATLAHIR+" VARCHAR(50), "+
            TANGGALLAHIR+" VARCHAR(30), "+AGAMA_ID +" VARCHAR(11), "+PENDIDIKAN_KK_ID+" VARCHAR(11), "+
            PEKERJAAN_ID +" VARCHAR(11), "+STATUS_KAWIN+" VARCHAR(11), "+
            ID_CLUSTER+" VARCHAR(11), "+ALAMAT_SEKARANG +" VARCHAR(100), "+CACAT_ID+" VARCHAR(11), "+UPLOAD+" VARCHAR(8),"+RT +" VARCHAR(5),"+RW +" VARCHAR(5),"+HAPUS_ID_RTM+" VARCHAR(10));";
    public static final String DROP_TABLE_TWEB_PENDUDUK = "DROP TABLE IF EXISTS "+TABLE_TWEB_PENDUDUK;

    //RTM
    public static final String CREATE_TABLE_RTM = "CREATE TABLE "+TABLE_TWEB_RTM+
            "("+ID_RTM+" VARCHAR(50) PRIMARY KEY, "+NIK_KEPALA+" VARCHAR(30), "+
            NO_KK+" VARCHAR(30), "+TGL_DAFTAR+" VARCHAR(30), "+KELAS_SOSIAL+" VARCHAR(5), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_TWEB_RTM = "DROP TABLE IF EXISTS "+TABLE_TWEB_RTM;

    //KELUARGA
    public static final String CREATE_TABLE_KELUARGA = "CREATE TABLE "+TABLE_TWEB_KELUARGA+
            "("+ID_KK+" VARCHAR(11) PRIMARY KEY, "+NO_KK+" VARCHAR(30), "+
            NIK_KEPALA+" VARCHAR(30), "+TGL_DAFTAR+" VARCHAR(30), "+KELAS_SOSIAL+" VARCHAR(5),"+TGL_CETAK_KK+" VARCHAR(30)," +
            ALAMAT+" VARCHAR(100), "+ID_CLUSTER +" VARCHAR(11), "+UPLOAD+" VARCHAR(8),"+RT +" VARCHAR(5),"+RW +" VARCHAR(5));";
    public static final String DROP_TABLE_TWEB_KELUARGA = "DROP TABLE IF EXISTS "+TABLE_TWEB_KELUARGA;

    //tweb_penduduk_pendidikan_kk
    public static final String CREATE_TABLE_PENDUDUK_PENDIDIKAN_KK= "CREATE TABLE "+TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK+
            "("+ID_PENDUDUK_PENDIDIKAN_KK+" VARCHAR(11) PRIMARY KEY, "+NAMA+" VARCHAR(100), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK = "DROP TABLE IF EXISTS "+TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK;

    //tweb_penduduk_pekerjaan
    public static final String CREATE_TABLE_PENDUDUK_PEKERJAAN= "CREATE TABLE "+TABLE_TWEB_PENDUDUK_PEKERJAAN+
            "("+ID_PENDUDUK_PEKERJAAN+" VARCHAR(11) PRIMARY KEY, "+NAMA+" VARCHAR(100), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_TWEB_PENDUDUK_PEKERJAAN = "DROP TABLE IF EXISTS "+TABLE_TWEB_PENDUDUK_PEKERJAAN;

    //tweb_penduduk_umur
    public static final String CREATE_TABLE_PENDUDUK_UMUR= "CREATE TABLE "+TABLE_TWEB_PENDUDUK_UMUR+
            "("+ID_PENDUDUK_UMUR+" VARCHAR(11) PRIMARY KEY, "+NAMA+" VARCHAR(50),"+
                DARI+" VARCHAR(5), "+SAMPAI+" VARCHAR(5), "+STATUS+" VARCHAR(5), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_TWEB_PENDUDUK_UMUR = "DROP TABLE IF EXISTS "+TABLE_TWEB_PENDUDUK_UMUR;

    //tweb_penduduk_agama
    public static final String CREATE_TABLE_PENDUDUK_AGAMA= "CREATE TABLE "+TABLE_TWEB_PENDUDUK_AGAMA+
            "("+ID_PENDUDUK_AGAMA+" VARCHAR(11) PRIMARY KEY, "+NAMA+" VARCHAR(75), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_TWEB_PENDUDUK_AGAMA = "DROP TABLE IF EXISTS "+TABLE_TWEB_PENDUDUK_AGAMA;

    //tweb_penduduk_hubungan
    public static final String CREATE_TABLE_PENDUDUK_HUBUNGAN= "CREATE TABLE "+TABLE_TWEB_PENDUDUK_HUBUNGAN+
            "("+ID_PENDUDUK_HUBUNGAN+" VARCHAR(11) PRIMARY KEY, "+NAMA+" VARCHAR(50), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_TWEB_PENDUDUK_HUBUNGAN = "DROP TABLE IF EXISTS "+TABLE_TWEB_PENDUDUK_HUBUNGAN;

    //pkk_catatan_keluarga
    public static final String CREATE_TABLE_PKK_CATATAN_KELUARGA= "CREATE TABLE "+TABLE_PKK_CATATAN_KELUARGA+
            "("+ID_CAT+" VARCHAR(11) PRIMARY KEY, "+ID_KK+" VARCHAR(11),"+
            ID_DK+" VARCHAR(11), "+TANGGAL_CAT+" VARCHAR(25), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_PKK_CATATAN_KELUARGA = "DROP TABLE IF EXISTS "+TABLE_PKK_CATATAN_KELUARGA;


    //pkk_catatan_keluarga_detail
    public static final String CREATE_TABLE_CATATAN_KELUARGA_DETAIL = "CREATE TABLE "+TABLE_PKK_CATATAN_KELUARGA_DETAIL+
            "("+ID_DETAIL_CAT+" VARCHAR(11) PRIMARY KEY, "+
            NIK+" VARCHAR(20), "+BERKEBUTUHAN_KHUSUS+" VARCHAR(3), "+PENGHAYATAN_DAN_PENGAMALAN_PANCASILA+" VARCHAR(3),"+
            GOTONG_ROYONG+" VARCHAR(3)," +PENDIDIKAN_KETRAMPILAN+" VARCHAR(3), "+PENGEMBANGAN_KEHIDUPAN_BERKOPERASI +" VARCHAR(3), "+
            PANGAN+" VARCHAR(3), "+SANDANG+" VARCHAR(3), "+KESEHATAN +" VARCHAR(3), "+PERENCANAAN_SEHAT+" VARCHAR(3), "+
            ID_KELOMPOK_UMUR+" VARCHAR(3), "+USIA_SUBUR+" VARCHAR(15), "+
            IBU_HAMIL+" VARCHAR(15), "+MENYUSUI+" VARCHAR(15), "+NIFAS+" VARCHAR(15), "+BUTA_BACA+" VARCHAR(15)," +
            BUTA_TULIS+" VARCHAR(15),"+BUTA_HITUNG+" VARCHAR(15), "+STUNTING+" VARCHAR(15),"+HAPUS+" VARCHAR(15), "+
            UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_PKK_CATATAN_KELUARGA_DETAIL = "DROP TABLE IF EXISTS "+TABLE_PKK_CATATAN_KELUARGA_DETAIL;

    //pkk_data_keluarga
    //NO KK == NO RTM
    public static final String CREATE_TABLE_PKK_DATA_KELUARGA = "CREATE TABLE "+TABLE_PKK_DATA_KELUARGA+
            "("+ID_DK+" VARCHAR(11) PRIMARY KEY, "+NO_KK+" VARCHAR(30), "+
            MAKANAN_POKOK+" VARCHAR(20), "+JML_MAKANAN_POKOK+" VARCHAR(3), "+JAMBAN+" VARCHAR(20),"+
            JML_JAMBAN+" VARCHAR(3)," +SUMBER_AIR+" VARCHAR(20), "+JML_SUMBER_AIR +" VARCHAR(3), "+
            TEMPAT_SAMPAH+" VARCHAR(10), "+JML_TEMPAT_SAMPAH+" VARCHAR(3), "+SALURAN_PEMBUANGAN_AIR +" VARCHAR(10), "+JML_SALURAN_PEMBUANGAN_AIR+" VARCHAR(3), "+
            STIKER_P4K+" VARCHAR(10), "+JML_STIKER_P4K+" VARCHAR(3), "+KRITERIA_RUMAH+" VARCHAR(20), "+
            JML_KRITERIA_RUMAH+" VARCHAR(3), "+UP2K+" VARCHAR(10), "+JML_UP2K+" VARCHAR(3), "+KEG_SEHAT_LINGKUNGAN+" VARCHAR(20), "+JML_KEG_SEHAT_LINGKUNGAN+" VARCHAR(3), "+PTP+" VARCHAR(20), "+INDUSTRI_RT+" VARCHAR(20), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_PKK_DATA_KELUARGA = "DROP TABLE IF EXISTS "+TABLE_PKK_DATA_KELUARGA;

    //pkk_kelompok_dasawisma
    //NO KK == NO RTM
    public static final String CREATE_TABLE_PKK_KELOMPOK_DASAWISMA= "CREATE TABLE "+TABLE_PKK_KELOMPOK_DASAWISMA+
            "("+ID_KELOMPOK_DASAWISMA+" VARCHAR(50) PRIMARY KEY, "+NO_KK+" VARCHAR(30), "+
            ID_DASAWISMA+" VARCHAR(11), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_PKK_KELOMPOK_DASAWISMA = "DROP TABLE IF EXISTS "+TABLE_PKK_KELOMPOK_DASAWISMA;

    //pkk_dasawisma
    public static final String CREATE_TABLE_PKK_DASA_WISMA= "CREATE TABLE "+TABLE_PKK_DASA_WISMA+
            "("+ID_DASAWISMA+" VARCHAR(11) PRIMARY KEY, "+ID_CLUSTER+" VARCHAR(11), "+
            ID_KEPALA+" VARCHAR(11), "+NAMA_DASA_WISMA+" VARCHAR(100), "+UPLOAD+" VARCHAR(8));";
    public static final String DROP_TABLE_PKK_DASA_WISMA = "DROP TABLE IF EXISTS "+TABLE_PKK_DASA_WISMA;

    public Context context;

    public Helper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE_RTM);
            db.execSQL(CREATE_TABLE_KELUARGA);
            db.execSQL(CREATE_TABLE_PENDUDUK_PENDIDIKAN_KK);
            db.execSQL(CREATE_TABLE_PENDUDUK_PEKERJAAN);
            db.execSQL(CREATE_TABLE_PENDUDUK_UMUR);
            db.execSQL(CREATE_TABLE_PENDUDUK_AGAMA);
            db.execSQL(CREATE_TABLE_PENDUDUK_HUBUNGAN);
            db.execSQL(CREATE_TABLE_PKK_CATATAN_KELUARGA);
            db.execSQL(CREATE_TABLE_CATATAN_KELUARGA_DETAIL);
            db.execSQL(CREATE_TABLE_PKK_DATA_KELUARGA);
            db.execSQL(CREATE_TABLE_PKK_KELOMPOK_DASAWISMA);
            db.execSQL(CREATE_TABLE_PKK_DASA_WISMA);
            db.execSQL(CREATE_TABLE_CONFIG_CODE);

        }catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            Toast.makeText(context,"OnUpgrade",Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE_TWEB_PENDUDUK);
            db.execSQL(DROP_TABLE_TWEB_RTM);
            db.execSQL(DROP_TABLE_TWEB_KELUARGA);
            db.execSQL(DROP_TABLE_TWEB_PENDUDUK_PENDIDIKAN_KK);
            db.execSQL(DROP_TABLE_TWEB_PENDUDUK_PEKERJAAN);
            db.execSQL(DROP_TABLE_TWEB_PENDUDUK_UMUR);
            db.execSQL(DROP_TABLE_TWEB_PENDUDUK_AGAMA);
            db.execSQL(DROP_TABLE_TWEB_PENDUDUK_HUBUNGAN);
            db.execSQL(DROP_TABLE_PKK_CATATAN_KELUARGA);
            db.execSQL(DROP_TABLE_PKK_CATATAN_KELUARGA_DETAIL);
            db.execSQL(DROP_TABLE_PKK_DATA_KELUARGA);
            db.execSQL(DROP_TABLE_PKK_KELOMPOK_DASAWISMA);
            db.execSQL(DROP_TABLE_PKK_DASA_WISMA);
            onCreate(db);
        }catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

}
