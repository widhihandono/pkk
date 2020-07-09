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
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_master_tweb;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
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
            lnPkkDataKeluarga,lnPkkKelompokDasawisma,lnPkkDasawisma,lnLogOut;

    JSONParser jsonParser = new JSONParser();

    private ProgressDialog dialog;


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


        lnPenduduk.setOnClickListener(l->{
//            penduduk();


//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("kd_desa","1243");
//                jsonObject.put("dusun","jasdff");
                GetPenduduk productTask = new GetPenduduk();
                productTask.execute();
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
            getRtm();
        });

        lnKeluarga.setOnClickListener(l->{
            getKeluarga();
        });


        lnPkkCatatanKeluarga.setOnClickListener(l->{
            getPkkCatatanKeluarga();
        });

        lnPkkCatatanKeluargaDetail.setOnClickListener(l->{
            getPkkCatatanKeluargaDetail();
        });

        lnPkkDataKeluarga.setOnClickListener(l->{
            getPkkDataKeluarga();
        });

        lnPkkKelompokDasawisma.setOnClickListener(l->{
            getPkkKelompokDasaWisma();
        });

        lnPkkDasawisma.setOnClickListener(l->{
            getPkkDasaWisma();
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

    private void penduduk()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream os = null;
                InputStream is = null;
                HttpURLConnection conn = null;
                try {
                    //constants
                    URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getPenduduk");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("kd_desa", "3308012011");
                    jsonObject.put("dusun", "JEBENGAN");
                    String message = jsonObject.toString();

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout( 10000 /*milliseconds*/ );
                    conn.setConnectTimeout( 15000 /* milliseconds */ );
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(message.getBytes().length);

                    //make some HTTP header nicety
                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                    conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                    //open
                    conn.connect();

                    //setup send
                    os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    //clean up
                    os.flush();

                    //do somehting with response
                    is = conn.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

                    String output;
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        response.append(line);
                    }

//                    Log.v("Response","Kode : "+new JSONObject(response.toString()).getString("data"));
//                    Log.v("Response","Kode : "+new JSONObject(br.readLine()).getString("data"));
                    JSONObject myJ = new JSONObject(response.toString());
                    JSONArray Jarray = myJ.getJSONArray("data");

                    Ent_twebPenduduk etp = new Ent_twebPenduduk();
                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jOb = Jarray.getJSONObject(a);
                        etp.setRt(jOb.getString("rt"));
                        etp.setRw(jOb.getString("rw"));
                        etp.setId(jOb.getString("id"));
                        etp.setNama(jOb.getString("nama"));
                        etp.setNik(jOb.getString("nik"));
                        etp.setId_kk(jOb.getString("id_kk"));
                        etp.setKk_level(jOb.getString("kk_level"));
                        etp.setId_rtm(jOb.getString("id_rtm"));
                        etp.setRtm_level(jOb.getString("rtm_level"));
                        etp.setSex(jOb.getString("sex"));
                        etp.setTempatlahir(jOb.getString("tempatlahir"));
                        etp.setTanggallahir(jOb.getString("tanggallahir"));
                        etp.setAgama_id(jOb.getString("agama_id"));
                        etp.setPendidikan_kk_id(jOb.getString("pendidikan_kk_id"));
                        etp.setPekerjaan_id(jOb.getString("pekerjaan_id"));
                        etp.setStatus_kawin(jOb.getString("status_kawin"));
                        etp.setId_cluster(jOb.getString("id_cluster"));
                        etp.setAlamat_sekarang(jOb.getString("alamat_sekarang"));
                        etp.setCacat_id(jOb.getString("cacat_id"));

                        crudSqlite.InsertData_tweb_penduduk(etp);

                    }


                    Log.v("Response","Kode : "+myJ.getInt("response"));

                    //String contentAsString = readIt(is,len);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    //clean up
                    try {
                        os.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    conn.disconnect();
                }
            }
        }).start();
    }


    private void getPenduduk()
    {
        if(crudSqlite.getData_tweb_penduduk().size() > 0)
        {
            if(crudSqlite.delete_all_penduduk())
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("kd_desa", "3308012011"));
                params.add(new BasicNameValuePair("dusun", "JEBENGAN"));

                GetPenduduk productTask = new GetPenduduk();
                productTask.execute();

//                sendPost(sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPenduduk");
//                Get_Penduduk_Async getAsync = new Get_Penduduk_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPenduduk");
//                getAsync.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("kd_desa", "3308012011"));
            params.add(new BasicNameValuePair("dusun", "JEBENGAN"));

            GetPenduduk productTask = new GetPenduduk();
            productTask.execute();

//            sendPost(sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPenduduk");
//            Get_Penduduk_Async getAsync = new Get_Penduduk_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPenduduk");
//            getAsync.execute();
        }

    }

    private void getKeluarga()
    {
        if(crudSqlite.getData_tweb_keluarga().size() > 0)
        {
            if(crudSqlite.delete_all_keluarga())
            {
                Get_Keluarga_Async getAsync = new Get_Keluarga_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getKeluarga");
                getAsync.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Get_Keluarga_Async getAsync = new Get_Keluarga_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getKeluarga");
            getAsync.execute();
        }


    }

    private void getRtm()
    {
        if(crudSqlite.getData_tweb_rtm().size() > 0)
        {
            if(crudSqlite.delete_all_rtm())
            {
                Get_Rtm_Async getUseAsyncTask = new Get_Rtm_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getRtm");
                getUseAsyncTask.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Get_Rtm_Async getUseAsyncTask = new Get_Rtm_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getRtm");
            getUseAsyncTask.execute();
        }

    }


    private void getPkkCatatanKeluarga()
    {
        if(crudPkk.getData_pkkCatatanKeluarga().size() > 0)
        {
            if(crudPkk.delete_all_pkk_catatan_keluarga())
            {
                Get_PkkCatatanKeluarga_Async getUseAsyncTask = new Get_PkkCatatanKeluarga_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkCatatanKeluarga");
                getUseAsyncTask.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Get_PkkCatatanKeluarga_Async getUseAsyncTask = new Get_PkkCatatanKeluarga_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkCatatanKeluarga");
            getUseAsyncTask.execute();
        }


    }



    private void getPkkCatatanKeluargaDetail()
    {
        if(crudPkk.getData_pkk_catatan_keluarga_detail().size() > 0)
        {
            if(crudPkk.delete_all_pkk_catatan_keluarga_detail())
            {
                GV9Jm2u7rmsCe65wKzPTw5jtS38n2tVEGic getUseAsyncTask = new GV9Jm2u7rmsCe65wKzPTw5jtS38n2tVEGic(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkCatatanKeluargaDetail");
                getUseAsyncTask.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            GV9Jm2u7rmsCe65wKzPTw5jtS38n2tVEGic getUseAsyncTask = new GV9Jm2u7rmsCe65wKzPTw5jtS38n2tVEGic(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkCatatanKeluargaDetail");
            getUseAsyncTask.execute();
        }


    }


    private void getPkkDataKeluarga()
    {
        if(crudPkk.getPkk_data_keluarga().size() > 0)
        {
            if(crudPkk.delete_all_pkk_data_keluarga())
            {
                Get_PkkDataKeluarga getUseAsyncTask = new Get_PkkDataKeluarga(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkDataKeluarga");
                getUseAsyncTask.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Get_PkkDataKeluarga getUseAsyncTask = new Get_PkkDataKeluarga(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkDataKeluarga");
            getUseAsyncTask.execute();
        }


    }


    private void getPkkDasaWisma()
    {
        if(crudPkk.getPkk_dasa_wisma().size() > 0)
        {
            if(crudPkk.delete_all_pkk_dasa_wisma())
            {
                Pkk_Dasawisma_Async getUseAsyncTask = new Pkk_Dasawisma_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkDasaWisma");
                getUseAsyncTask.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Pkk_Dasawisma_Async getUseAsyncTask = new Pkk_Dasawisma_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkDasaWisma");
            getUseAsyncTask.execute();
        }


//        Call<Ent_PkkDasaWisma> callDataKeluarga = apiInterface.getPkkDasaWisma(sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""));
//        callDataKeluarga.enqueue(new Callback<Ent_PkkDasaWisma>() {
//            @Override
//            public void onResponse(Call<Ent_PkkDasaWisma> call, Response<Ent_PkkDasaWisma> response) {
//                if(response.body().isResponse())
//                {
//                    List<Ent_PkkDasaWisma> data = response.body().getData();
//                    Ent_PkkDasaWisma ep = new Ent_PkkDasaWisma();
//
//                    for (int a=0;a<data.size();a++)
//                    {
//                        ep.setId_dasa_wisma(data.get(a).getId_dasa_wisma());
//                        ep.setId_cluster(data.get(a).getId_cluster());
//                        ep.setId_kepala(data.get(a).getId_kepala());
//                        ep.setNama_dasa_wisma(data.get(a).getNama_dasa_wisma());
//
//                        crudPkk.Insert_pkk_dasa_wisma(ep);
//
//                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    Toast.makeText(Ambil_DataActivity.this,"Gagal Ambil data",Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Ent_PkkDasaWisma> call, Throwable t) {
//                Toast.makeText(Ambil_DataActivity.this,"Network Failed",Toast.LENGTH_LONG).show();
//            }
//        });
    }


    private void getPkkKelompokDasaWisma()
    {
        if(crudPkk.getPkk_kelompok_dasa_wisma().size() > 0)
        {
            if(crudPkk.delete_all_pkk_kelompok_dasa_wisma())
            {
                Pkk_kelomDasawisma_Async getUseAsyncTask = new Pkk_kelomDasawisma_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkKelompokDasaWisma");
                getUseAsyncTask.execute();
            }
            else
            {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Pkk_kelomDasawisma_Async getUseAsyncTask = new Pkk_kelomDasawisma_Async(this,sharedPref.sp.getString("kode_desa",""),sharedPref.sp.getString("dusun",""),"https://pkk.magelangkab.go.id/Api_pkk/getPkkKelompokDasaWisma");
            getUseAsyncTask.execute();
        }


    }


    //Async
    private class Pkk_kelomDasawisma_Async extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun,id_dasawisma;

        private Pkk_kelomDasawisma_Async(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            List<Ent_PkkKelompokDasawisma> data = new ArrayList<>();
            Ent_PkkKelompokDasawisma ep = new Ent_PkkKelompokDasawisma();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("dusun", dusun);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
                    JSONArray Jarray = myObject.getJSONArray("data");


                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jsonObject = Jarray.getJSONObject(a);
                        ep.setId_kelompok(jsonObject.getString("id_kelompok"));
                        ep.setNo_kk(jsonObject.getString("no_kk"));
                        ep.setId_dasa_wisma(jsonObject.getString("id_dasa_wisma"));

                        crudPkk.Insert_pkk_kelompok_dasa_wisma(ep);


                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"response : ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }

    //Async PKK Dasawisma
    private class Pkk_Dasawisma_Async extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun;

        private Pkk_Dasawisma_Async(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            List<Ent_PkkKelompokDasawisma> data = new ArrayList<>();
            Ent_PkkDasaWisma ep = new Ent_PkkDasaWisma();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("dusun", dusun);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
                    JSONArray Jarray = myObject.getJSONArray("data");


                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jsonObject = Jarray.getJSONObject(a);
                        ep.setId_dasa_wisma(jsonObject.getString("id_dasa_wisma"));
                        ep.setId_cluster(jsonObject.getString("id_cluster"));
                        ep.setId_kepala(jsonObject.getString("id_kepala"));
                        ep.setNama_dasa_wisma(jsonObject.getString("nama_dasa_wisma"));

                        crudPkk.Insert_pkk_dasa_wisma(ep);

                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"response : ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }


    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    private void showDialogKeyAccess(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ambil_DataActivity.this);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }


    //Async Penduduk
    private class Get_Penduduk_Async extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun;

        private Get_Penduduk_Async(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            List<Ent_PkkKelompokDasawisma> data = new ArrayList<>();
            Ent_twebPenduduk etp = new Ent_twebPenduduk();



            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
//            this.progressDialog = new ProgressDialog(this.context);
//            if(this.progressDialog != null)
//            {
//                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                this.progressDialog.setCancelable(false);
//                this.progressDialog.show();
//            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
//            if (result == 1) {
//                this.progressDialog.dismiss();
//                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();
//
//            }
//            else if (result == 2) {
//                showDialogKeyAccess(keterangan);
////                this.progressDialog.dismiss();
//            } else {
//                Toast.makeText(Ambil_DataActivity.this,"response : ",Toast.LENGTH_LONG).show();
//                this.progressDialog.dismiss();
//            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }


    class GetPenduduk extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public GetPenduduk()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(Ambil_DataActivity.this);
            dialog.setMessage("We are Logging in. Please wait . . .");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getPenduduk");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", "3308012011");
                jsonObject.put("dusun", "JEBENGAN");
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

                String output;
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

//                    Log.v("Response","Kode : "+new JSONObject(response.toString()).getString("data"));
//                    Log.v("Response","Kode : "+new JSONObject(br.readLine()).getString("data"));
                json = new JSONObject(response.toString());
                JSONArray Jarray = json.getJSONArray("data");

                Ent_twebPenduduk etp = new Ent_twebPenduduk();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    etp.setRt(jOb.getString("rt"));
                    etp.setRw(jOb.getString("rw"));
                    etp.setId(jOb.getString("id"));
                    etp.setNama(jOb.getString("nama"));
                    etp.setNik(jOb.getString("nik"));
                    etp.setId_kk(jOb.getString("id_kk"));
                    etp.setKk_level(jOb.getString("kk_level"));
                    etp.setId_rtm(jOb.getString("id_rtm"));
                    etp.setRtm_level(jOb.getString("rtm_level"));
                    etp.setSex(jOb.getString("sex"));
                    etp.setTempatlahir(jOb.getString("tempatlahir"));
                    etp.setTanggallahir(jOb.getString("tanggallahir"));
                    etp.setAgama_id(jOb.getString("agama_id"));
                    etp.setPendidikan_kk_id(jOb.getString("pendidikan_kk_id"));
                    etp.setPekerjaan_id(jOb.getString("pekerjaan_id"));
                    etp.setStatus_kawin(jOb.getString("status_kawin"));
                    etp.setId_cluster(jOb.getString("id_cluster"));
                    etp.setAlamat_sekarang(jOb.getString("alamat_sekarang"));
                    etp.setCacat_id(jOb.getString("cacat_id"));

                    crudSqlite.InsertData_tweb_penduduk(etp);

                }


                Log.v("Response","Kode : "+json.getInt("response"));


                //String contentAsString = readIt(is,len);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return json;
        }

        protected void onPostExecute(JSONObject result)
        {
            dialog.dismiss();
            //this assumes that the response looks like this:
            //{"success" : true }
            String message = null;
            try {
                message = result.getString("pesan");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            int success = 0;
            try {
                success = result.getInt("response");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
                dialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }



    //Async Keluarga
    private class Get_Keluarga_Async extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun;

        private Get_Keluarga_Async(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            List<Ent_PkkKelompokDasawisma> data = new ArrayList<>();
            Ent_twebKeluarga etk = new Ent_twebKeluarga();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("dusun", dusun);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                JSONObject jsonobj = new JSONObject();

                jsonobj.put("kd_desa", kd_desa);
                jsonobj.put("dusun", dusun);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

                // POST
                httpPost.setEntity(new MyHttpEntity(new UrlEncodedFormEntity(nameValuePairs),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
                    JSONArray Jarray = myObject.getJSONArray("data");


                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jsonObject = Jarray.getJSONObject(a);
                        etk.setRt(jsonObject.getString("rt"));
                        etk.setRw(jsonObject.getString("rw"));
                        etk.setId(jsonObject.getString("id"));
                        etk.setNo_kk(jsonObject.getString("no_kk"));
                        etk.setNik_kepala(jsonObject.getString("nik_kepala"));
                        etk.setTgl_daftar(jsonObject.getString("tgl_daftar"));
                        etk.setKelas_sosial(jsonObject.getString("kelas_sosial"));
                        etk.setTgl_cetak_kk(jsonObject.getString("tgl_cetak_kk"));
                        etk.setAlamat(jsonObject.getString("alamat"));
                        etk.setId_cluster(jsonObject.getString("id_cluster"));

                        crudSqlite.InsertData_tweb_keluarga(etk);

                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"response : "+result,Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }


    //Async RTM
    private class Get_Rtm_Async extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun;

        private Get_Rtm_Async(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            List<Ent_PkkKelompokDasawisma> data = new ArrayList<>();
            Ent_twebRtm rtm = new Ent_twebRtm();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("dusun", dusun);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
                    JSONArray Jarray = myObject.getJSONArray("data");


                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jsonObject = Jarray.getJSONObject(a);
                        rtm.setId(jsonObject.getString("id"));
                        rtm.setNik_kepala(jsonObject.getString("nik_kepala"));
                        rtm.setNo_kk(jsonObject.getString("no_kk"));
                        rtm.setTgl_daftar(jsonObject.getString("tgl_daftar"));
                        rtm.setKelas_sosial(jsonObject.getString("kelas_sosial"));

                        crudSqlite.InsertData_tweb_rtm(rtm);

                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"response : ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }


    //Async PKK Catatan Keluarga
    private class Get_PkkCatatanKeluarga_Async extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun;

        private Get_PkkCatatanKeluarga_Async(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            List<Ent_PkkKelompokDasawisma> data = new ArrayList<>();
            Ent_PkkCatatanKeluarga ep = new Ent_PkkCatatanKeluarga();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("dusun", dusun);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
                    JSONArray Jarray = myObject.getJSONArray("data");



                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jsonObject = Jarray.getJSONObject(a);
                        ep.setId_cat(jsonObject.getString("id"));
                        ep.setId_kk(jsonObject.getString("id_kk"));
                        ep.setId_dk(jsonObject.getString("id_dk"));
                        ep.setTanggal_cat(jsonObject.getString("tanggal_cat"));

                        crudPkk.InsertData_pkk_catatan_keluarga(ep);

                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"response : ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }

    //Async PKK Catatan Keluarga Detail
    private class GV9Jm2u7rmsCe65wKzPTw5jtS38n2tVEGic extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun;

        private GV9Jm2u7rmsCe65wKzPTw5jtS38n2tVEGic(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            Ent_PkkCatatanKeluargaDetail ep = new Ent_PkkCatatanKeluargaDetail();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("dusun", dusun);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
                    JSONArray Jarray = myObject.getJSONArray("data");


                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jsonObject = Jarray.getJSONObject(a);
                        ep.setId_detail_cat(jsonObject.getString("id_detail_cat"));
                        ep.setNik(jsonObject.getString("nik"));
                        ep.setBerkebutuhan_khusus(jsonObject.getString("berkebutuhan_khusus"));
                        ep.setPenghayatan_dan_pengamalan_pancasila(jsonObject.getString("penghayatan_dan_pengamalan_pancasila"));
                        ep.setGotong_royong(jsonObject.getString("gotong_royong"));
                        ep.setPendidikan_ketrampilan(jsonObject.getString("pendidikan_ketrampilan"));
                        ep.setPengembangan_kehidupan_berkoperasi(jsonObject.getString("pengembangan_kehidupan_berkoperasi"));
                        ep.setPangan(jsonObject.getString("pangan"));
                        ep.setSandang(jsonObject.getString("sandang"));
                        ep.setKesehatan(jsonObject.getString("kesehatan"));
                        ep.setPerencanaan_sehat(jsonObject.getString("perencanaan_sehat"));
                        ep.setId_kelompok_umur(jsonObject.getString("id_kelompok_umur"));
                        ep.setUsia_subur(jsonObject.getString("usia_subur"));
                        ep.setIbu_hamil(jsonObject.getString("ibu_hamil"));
                        ep.setMenyusui(jsonObject.getString("menyusui"));
                        ep.setNifas(jsonObject.getString("nifas"));
                        ep.setButa_baca(jsonObject.getString("buta_baca"));
                        ep.setButa_tulis(jsonObject.getString("buta_tulis"));
                        ep.setButa_hitung(jsonObject.getString("buta_hitung"));

                        if(crudPkk.InsertData_pkk_catatan_keluarga_detail(ep) > 0 )
                        {
                            Log.i("sukses", "YES");
                        }
                        else
                        {
                            Log.i("sukses", "NO");
                        }


                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("menyusui")+", "+jsonObject.getString("nifas")+", "+jsonObject.getString("buta_baca")+", "+jsonObject.getString("buta_tulis")+", "+jsonObject.getString("buta_hitung"));

                    responseInt = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }

    //Async PKK Data Keluarga
    private class Get_PkkDataKeluarga extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,dusun;

        private Get_PkkDataKeluarga(Context context,String kd_desa,String dusun,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.dusun = dusun;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            List<Ent_PkkDataKeluarga> data = new ArrayList<>();
            Ent_PkkDataKeluarga ep = new Ent_PkkDataKeluarga();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("dusun", dusun);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));
                    JSONArray Jarray = myObject.getJSONArray("data");


                    for (int a=0;a<Jarray.length();a++)
                    {
                        JSONObject jsonObject = Jarray.getJSONObject(a);
                        ep.setId_dk(jsonObject.getString("id_kk"));
                        ep.setNo_kk(jsonObject.getString("no_kk"));
                        ep.setMakanan_pokok(jsonObject.getString("makanan_pokok"));
                        ep.setJml_makanan_pokok(jsonObject.getString("jml_makanan_pokok"));
                        ep.setJamban(jsonObject.getString("jamban"));
                        ep.setJml_jamban(jsonObject.getString("jml_jamban"));
                        ep.setSumber_air(jsonObject.getString("sumber_air"));
                        ep.setJml_sumber_air(jsonObject.getString("jml_sumber_air"));
                        ep.setTempat_sampah(jsonObject.getString("tempat_sampah"));
                        ep.setJml_tempat_sampah(jsonObject.getString("jml_tempat_sampah"));
                        ep.setSaluran_pembuangan_air(jsonObject.getString("saluran_pembuangan_air"));
                        ep.setJml_saluran_pembuangan_air(jsonObject.getString("jml_saluran_pembuangan_air"));
                        ep.setStiker_p4k(jsonObject.getString("stiker_p4k"));
                        ep.setJml_stiker_p4k(jsonObject.getString("jml_stiker_p4k"));
                        ep.setKriteria_rumah(jsonObject.getString("kriteria_rumah"));
                        ep.setJml_kriteria_rumah(jsonObject.getString("jml_kriteria_rumah"));
                        ep.setUp2k(jsonObject.getString("up2k"));
                        ep.setJml_up2k(jsonObject.getString("jml_up2k"));
                        ep.setKeg_sehat_lingkungan(jsonObject.getString("keg_sehat_lingkungan"));
                        ep.setJml_keg_sehat_lingkungan(jsonObject.getString("jml_keg_sehat_lingkungan"));
                        ep.setPtp(jsonObject.getString("ptp"));
                        ep.setIndustri_rt(jsonObject.getString("Industri_rt"));

                        crudPkk.Insert_pkk_data_keluarga(ep);


                    }
//
//                    Toast.makeText(Ambil_DataActivity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    keterangan = myObject.getString("pesan");
                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Ambil_DataActivity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Ambil_DataActivity.this,"response : ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}