package com.supradesa.supradesa_pkk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Model.Ent_ConfigCode;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_master_tweb;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.Util.Get_Data_From_Server;
import com.supradesa.supradesa_pkk.Util.JSONParser;
import com.supradesa.supradesa_pkk.Util.List_Temporary;
import com.supradesa.supradesa_pkk.Util.MyHttpEntity;
import com.supradesa.supradesa_pkk.Util.MySSLSocketFactory;
import com.supradesa.supradesa_pkk.Util.SharedPref;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDasaWisma;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDataKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkKelompokDasawisma;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukAgama;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukHubungan;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukPekerjaan;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukPendidikanKK;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukUmur;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ambil_DataActivity extends AppCompatActivity {
    SharedPref sharedPref;
    Crud crudSqlite;
    Crud_master_tweb crudMasterTweb;
    Crud_pkk crudPkk;
    Api_Interface apiInterface;
    List_Temporary list_temporary;
    private LinearLayout lnKeluarga,lnPenduduk,lnRtm,lnPendudukPendidikanKk,lnPendudukPekerjaan,lnPendudukUmur,
            lnPendudukAgama,lnPendudukHubungan,lnPkkCatatanKeluarga,lnPkkCatatanKeluargaDetail,
            lnPkkDataKeluarga,lnPkkKelompokDasawisma,lnPkkDasawisma,lnLogOut,lnConfigCode;
    private TextView tvSyncAll;

    private ProgressDialog dialog;

    Get_Data_From_Server get_data_from_server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil__data);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(this);
        crudSqlite = new Crud(this);
        crudMasterTweb = new Crud_master_tweb(this);
        crudPkk = new Crud_pkk(this);
        list_temporary = new List_Temporary();
        apiInterface = Api_Client.getClient().create(Api_Interface.class);
        get_data_from_server = new Get_Data_From_Server(Ambil_DataActivity.this);


        if(!sharedPref.getSPSudahLogin())
        {
            startActivity(new Intent(this,Login_Activity.class));
            finish();
        }

        lnKeluarga = findViewById(R.id.lnKeluarga);
        lnRtm = findViewById(R.id.lnRtm);
        lnPenduduk = findViewById(R.id.lnDataPenduduk);
        lnPkkCatatanKeluarga = findViewById(R.id.lnPkkCatatanKeluarga);
        lnPkkCatatanKeluargaDetail = findViewById(R.id.lnPkkCatatanKeluargaDetail);
        lnPkkDataKeluarga = findViewById(R.id.lnPkkDataKeluarga);
        lnPkkKelompokDasawisma = findViewById(R.id.lnPkkKelompokDasawisma);
        lnPkkDasawisma = findViewById(R.id.lnPkkDasawisma);
        lnLogOut = findViewById(R.id.lnLogOut);
        tvSyncAll = findViewById(R.id.tvSyncAll);
        lnConfigCode = findViewById(R.id.lnConfigCode);


        lnConfigCode.setOnClickListener(l->{

            get_data_from_server.getConfig_Code();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });

        lnPenduduk.setOnClickListener(l->{
//            penduduk();


//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("kd_desa","1243");
//                jsonObject.put("dusun","jasdff");
            get_data_from_server.getPenduduk();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
//
//                Log.v("Data",jsonObject.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//


//                postPenduduk();
//            Get_Penduduk_Async getAsync = new Get_Penduduk_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPenduduk");
//            getAsync.execute();
        });

        lnRtm.setOnClickListener(l->{
            get_data_from_server.getRtm();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });

        lnKeluarga.setOnClickListener(l->{
            get_data_from_server.getKeluarga();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });


        lnPkkCatatanKeluarga.setOnClickListener(l->{
            get_data_from_server.getPkkCatatanKeluarga();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });

        lnPkkCatatanKeluargaDetail.setOnClickListener(l->{
            get_data_from_server.getPkkCatatanKeluargaDetail();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });

        lnPkkDataKeluarga.setOnClickListener(l->{
            get_data_from_server.getPkkDataKeluarga();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });

        lnPkkKelompokDasawisma.setOnClickListener(l->{
            get_data_from_server.getPkkKelompokDasaWisma();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });

        lnPkkDasawisma.setOnClickListener(l->{
            get_data_from_server.getPkkDasaWisma();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
        });

        tvSyncAll.setOnClickListener(l->{

            get_data_from_server.getAll();

            list_temporary.listAllAnggota_sementara.clear();
            list_temporary.no_rtm = "";
            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();


        });

        lnLogOut.setOnClickListener(l->{
            crudSqlite.delete_all_penduduk();
            crudSqlite.delete_all_keluarga();
            crudSqlite.delete_all_rtm();

            crudPkk.delete_all_pkk_catatan_keluarga();
            crudPkk.delete_all_pkk_catatan_keluarga_detail();
            crudPkk.delete_all_pkk_dasa_wisma();
            crudPkk.delete_all_pkk_data_keluarga();
            crudPkk.delete_all_pkk_kelompok_dasa_wisma();

            crudMasterTweb.delete_all_penduduk_agama();
            crudMasterTweb.delete_all_penduduk_hubungan();
            crudMasterTweb.delete_all_penduduk_pekerjaan();
            crudMasterTweb.delete_all_penduduk_pendidikan_kk();
            crudMasterTweb.delete_all_penduduk_umur();

            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAllAnggota.clear();
            list_temporary.listPenduduk_Detail.clear();
            list_temporary.listSub.clear();
            list_temporary.dasawismaPosition = -1;
            list_temporary.kepalaRtm = -1;
            list_temporary.id_dasawisma = "";
            list_temporary.id_penduduk = "";
            list_temporary.id_kk = "";
            list_temporary.nik = "";

            Toast.makeText(this,"Sukses Logout",Toast.LENGTH_LONG).show();
            sharedPref.saveSPBoolean(sharedPref.SP_SUDAH_LOGIN,false);
            sharedPref.saveSPString("nik","");
            sharedPref.saveSPString("kode_desa","");
            sharedPref.saveSPString("dusun","");
            sharedPref.saveSPString("nama_desa","");
            sharedPref.saveSPString("nama_kecamatan","");
            sharedPref.saveSPString("no_hp","");
            sharedPref.saveSPString("email","");

            startActivity(new Intent(this,Login_Activity.class));
            finish();
        });

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}