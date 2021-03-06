package com.supradesa.supradesa_pkk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDataKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkKelompokDasawisma;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_master_tweb;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.MyHttpEntity;
import com.supradesa.supradesa_pkk.Util.MySSLSocketFactory;
import com.supradesa.supradesa_pkk.Util.SharedPref;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upload_Data_Activity extends AppCompatActivity {
private LinearLayout lnRtm,lnDataPenduduk,lnPkkKelompokDasawisma,lnDataKeluarga,lnPkkCatatanKeluargaDetail;
private TextView tvRtm,tvTwebPenduduk,tvKelompokDasawisma,tvDataKeluarga,tvPkkCatatanKeluargaDetail;
private Api_Interface apiInterface;
Crud crud;
Crud_pkk crudPkk;
SharedPref sharedPref;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__data_);
        getSupportActionBar().hide();

        apiInterface = Api_Client.getClient().create(Api_Interface.class);
        crud = new Crud(this);
        crudPkk = new Crud_pkk(this);
        sharedPref = new SharedPref(this);

        lnRtm = findViewById(R.id.lnRtm);
        tvRtm = findViewById(R.id.tvRtm);
        lnDataPenduduk = findViewById(R.id.lnDataPenduduk);
        tvTwebPenduduk = findViewById(R.id.tvTwebPenduduk);
        tvKelompokDasawisma = findViewById(R.id.tvKelompokDasawisma);
        lnPkkKelompokDasawisma = findViewById(R.id.lnPkkKelompokDasawisma);
        lnDataKeluarga = findViewById(R.id.lnDataKeluarga);
        tvDataKeluarga = findViewById(R.id.tvDataKeluarga);
        tvPkkCatatanKeluargaDetail = findViewById(R.id.tvPkkCatatanKeluargaDetail);
        lnPkkCatatanKeluargaDetail = findViewById(R.id.lnPkkCatatanKeluargaDetail);

        tvRtm.setText("Data Rumah Tangga : "+crud.getData_tweb_rtm().size());
        tvTwebPenduduk.setText("Data Penduduk : "+crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("93838").size());
        tvKelompokDasawisma.setText("PKK Kelompok Dasawisma : "+crudPkk.getPkk_kelompok_dasa_wisma().size());
        tvDataKeluarga.setText("PKK Data Keluarga : "+crudPkk.getPkk_data_keluarga().size());
        tvPkkCatatanKeluargaDetail.setText("PKK Catatan Keluarga Detail: "+crudPkk.getData_pkk_catatan_keluarga_detail().size());

        lnRtm.setOnClickListener(l->{
            upload_rtm();
        });

        lnDataPenduduk.setOnClickListener(l->{
            upload_tweb_penduduk();
        });

        lnPkkKelompokDasawisma.setOnClickListener(l->{
            upload_pkk_kelompok_dasawisma();
        });

        lnDataKeluarga.setOnClickListener(l->{
            upload_pkk_data_keluarga();
        });

        lnPkkCatatanKeluargaDetail.setOnClickListener(l->{
            upload_pkk_catatan_keluarga_detail();
        });
    }

    private void upload_rtm()
    {
        if(crud.getData_tweb_rtm().size() == 0)
        {
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Upload_Data_Activity.class));
            finish();
        }
        else
        {
            int jml = crud.getData_tweb_rtm().size()-1;
            for(int a=0;a<crud.getData_tweb_rtm().size();a++)
            {
                Upload_Rtm_Async upload = new Upload_Rtm_Async(this,sharedPref.sp.getString("kode_desa",""),crud.getData_tweb_rtm().get(a).getNik_kepala(),
                        crud.getData_tweb_rtm().get(a).getNo_kk(),crud.getData_tweb_rtm().get(a).getTgl_daftar(),
                        crud.getData_tweb_rtm().get(a).getKelas_sosial(),crud.getData_tweb_rtm().get(a).getId(),
                        "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_rtm");
                upload.execute();

                if(a == jml)
                {
                    startActivity(new Intent(this,Upload_Data_Activity.class));
                    finish();
                }
            }
        }


    }

    private void upload_tweb_penduduk()
    {
        if(crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("098887").size() == 0)
        {
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Upload_Data_Activity.class));
            finish();
        }
        else
        {
            int jml = crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").size()-1;
            for(int a=0;a<crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("98984").size();a++)
            {
                Upload_tweb_penduduk_Async upload = new Upload_tweb_penduduk_Async(this,sharedPref.sp.getString("kode_desa",""),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getNama(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getNik(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getId_kk(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getKk_level(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getId_rtm(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getRtm_level(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getSex(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getTempatlahir(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getTanggallahir(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getAgama_id(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getPendidikan_kk_id(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getPekerjaan_id(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getStatus_kawin(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getId_cluster(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getAlamat_sekarang(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level("09889989").get(a).getCacat_id(),
                        "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_tweb_penduduk");
                upload.execute();

                if(a == jml)
                {
                    startActivity(new Intent(this,Upload_Data_Activity.class));
                    finish();
                }
            }
        }


    }

    private void upload_pkk_kelompok_dasawisma()
    {
//        if(crudPkk.delete_pkk_kelompok_dasa_wisma_by_no_rtm(crudPkk.getPkk_kelompok_dasa_wisma().get(0).getNo_kk()))
//        {
//            Toast.makeText(this,"Sukses Hapus",Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            Toast.makeText(this,"Gagal Hapus",Toast.LENGTH_LONG).show();
//        }
//        Toast.makeText(this,crudPkk.getPkk_kelompok_dasa_wisma().get(0).getNo_kk(),Toast.LENGTH_LONG).show();
        if(crudPkk.getPkk_kelompok_dasa_wisma().size() == 0)
        {
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Upload_Data_Activity.class));
            finish();
        }
        else
        {
            int jml = crudPkk.getPkk_kelompok_dasa_wisma().size()-1;
            for(int a=0;a<crudPkk.getPkk_kelompok_dasa_wisma().size();a++)
            {
                Upload_PkkKelompokDasawisma_Async upload = new Upload_PkkKelompokDasawisma_Async(this,sharedPref.sp.getString("kode_desa",""),
                        crudPkk.getPkk_kelompok_dasa_wisma().get(a).getNo_kk(),
                        crudPkk.getPkk_kelompok_dasa_wisma().get(a).getId_dasa_wisma(),
                        "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_pkk_kelompok_dasawisma");
                upload.execute();

                if(a == jml)
                {
                    startActivity(new Intent(this,Upload_Data_Activity.class));
                    finish();
                }
            }
        }


    }

    private void upload_pkk_data_keluarga()
    {
        if(crudPkk.getPkk_data_keluarga().size() == 0)
        {
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Upload_Data_Activity.class));
            finish();
        }
        else
        {
            int jml = crudPkk.getPkk_data_keluarga().size()-1;
            for(int a=0;a<crudPkk.getPkk_data_keluarga().size();a++)
            {
                Upload_PkkDataKeluarga upload = new Upload_PkkDataKeluarga(this, sharedPref.sp.getString("kode_desa", ""),
                        crudPkk.getPkk_data_keluarga().get(a).getNo_kk(), crudPkk.getPkk_data_keluarga().get(a).getMakanan_pokok(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_makanan_pokok(), crudPkk.getPkk_data_keluarga().get(a).getJamban(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_jamban(), crudPkk.getPkk_data_keluarga().get(a).getSumber_air(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_sumber_air(), crudPkk.getPkk_data_keluarga().get(a).getTempat_sampah(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_tempat_sampah(), crudPkk.getPkk_data_keluarga().get(a).getSaluran_pembuangan_air(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_saluran_pembuangan_air(), crudPkk.getPkk_data_keluarga().get(a).getStiker_p4k(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_stiker_p4k(), crudPkk.getPkk_data_keluarga().get(a).getKriteria_rumah(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_kriteria_rumah(), crudPkk.getPkk_data_keluarga().get(a).getUp2k(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_up2k(), crudPkk.getPkk_data_keluarga().get(a).getKeg_sehat_lingkungan(),
                        crudPkk.getPkk_data_keluarga().get(a).getJml_keg_sehat_lingkungan(),
                        crudPkk.getPkk_data_keluarga().get(a).getPtp(),
                        crudPkk.getPkk_data_keluarga().get(a).getIndustri_rt(), "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_pkk_data_keluarga");
                upload.execute();

                if(a == jml)
                {
                    startActivity(new Intent(this,Upload_Data_Activity.class));
                    finish();
                }
            }
        }


    }

    private void upload_pkk_catatan_keluarga_detail()
    {
        if(crudPkk.getData_pkk_catatan_keluarga_detail().size() == 0)
        {
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Upload_Data_Activity.class));
            finish();
        }
        else
        {
            int jml = crudPkk.getData_pkk_catatan_keluarga_detail().size()-1;
            for(int a=0;a<crudPkk.getData_pkk_catatan_keluarga_detail().size();a++)
            {
                Upload_pkk_catatan_keluarga_detail upload = new Upload_pkk_catatan_keluarga_detail(this,sharedPref.sp.getString("kode_desa",""),
                        crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getNik(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getBerkebutuhan_khusus(),
                        crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getPenghayatan_dan_pengamalan_pancasila(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getGotong_royong(),
                        crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getPendidikan_ketrampilan(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getPengembangan_kehidupan_berkoperasi()
                        ,crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getPangan(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getSandang()
                        ,crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getKesehatan(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getPerencanaan_sehat()
                        ,crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getId_kelompok_umur(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getUsia_subur()
                        ,crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getIbu_hamil(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getMenyusui()
                        ,crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getNifas(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getButa_baca()
                        ,crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getButa_tulis(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getButa_hitung(),
                        crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getId_detail_cat(),crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getStunting(),
                        "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_pkk_catatan_keluarga_detail");
                upload.execute();

                if(a == jml)
                {
                    startActivity(new Intent(this,Upload_Data_Activity.class));
                    finish();
                }
            }
        }


    }


    //==================================Async=======================================================
    //Async PKK Data Keluarga

    class Upload_PkkDataKeluarga extends AsyncTask<String, String, JSONObject>
    {
        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,no_kk,makanan_pokok,jml_makanan_pokok,jamban,jml_jamban,sumber_air,jml_sumber_air,tempat_sampah,jml_tempat_sampah,
                saluran_pembuangan_air,jml_saluran_pembuangan_air,stiker_p4k,jml_stiker_p4k
                ,kriteria_rumah,jml_kriteria_rumah,up2k,jml_up2k,keg_sehat_lingkungan,jml_keg_sehat_lingkungan,ptp,Industri_rt;

        private Upload_PkkDataKeluarga(Context context,String kd_desa,String no_kk,String makanan_pokok,String jml_makanan_pokok
                ,String jamban,String jml_jamban,String sumber_air,String jml_sumber_air,String tempat_sampah,String jml_tempat_sampah,String saluran_pembuangan_air
                ,String jml_saluran_pembuangan_air,String stiker_p4k,String jml_stiker_p4k,String kriteria_rumah,String jml_kriteria_rumah,String up2k
                ,String jml_up2k,String keg_sehat_lingkungan,String jml_keg_sehat_lingkungan,String ptp,String Industri_rt,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.no_kk = no_kk;
            this.makanan_pokok = makanan_pokok;
            this.jml_makanan_pokok = jml_makanan_pokok;
            this.jamban = jamban;
            this.jml_jamban = jml_jamban;
            this.sumber_air = sumber_air;
            this.jml_sumber_air = jml_sumber_air;
            this.tempat_sampah = tempat_sampah;
            this.jml_tempat_sampah = jml_tempat_sampah;
            this.saluran_pembuangan_air = saluran_pembuangan_air;
            this.jml_saluran_pembuangan_air = jml_saluran_pembuangan_air;
            this.stiker_p4k = stiker_p4k;
            this.jml_stiker_p4k = jml_stiker_p4k;
            this.kriteria_rumah = kriteria_rumah;
            this.jml_kriteria_rumah = jml_kriteria_rumah;
            this.up2k = up2k;
            this.jml_up2k = jml_up2k;
            this.keg_sehat_lingkungan = keg_sehat_lingkungan;
            this.jml_keg_sehat_lingkungan = jml_keg_sehat_lingkungan;
            this.ptp = ptp;
            this.Industri_rt = Industri_rt;

            this.SERVER_PATH = SERVER_PATH;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(Upload_Data_Activity.this);
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
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("no_kk", no_kk);
                jsonObject.put("makanan_pokok", makanan_pokok);
                jsonObject.put("jml_makanan_pokok", jml_makanan_pokok);
                jsonObject.put("jamban", jamban);
                jsonObject.put("jml_jamban", jml_jamban);
                jsonObject.put("sumber_air", sumber_air);
                jsonObject.put("jml_sumber_air", jml_sumber_air);
                jsonObject.put("tempat_sampah", tempat_sampah);
                jsonObject.put("jml_tempat_sampah", jml_tempat_sampah);
                jsonObject.put("saluran_pembuangan_air", saluran_pembuangan_air);
                jsonObject.put("jml_saluran_pembuangan_air", jml_saluran_pembuangan_air);
                jsonObject.put("stiker_p4k", stiker_p4k);
                jsonObject.put("jml_stiker_p4k", jml_stiker_p4k);
                jsonObject.put("kriteria_rumah", kriteria_rumah);
                jsonObject.put("jml_kriteria_rumah", jml_kriteria_rumah);
                jsonObject.put("up2k", up2k);
                jsonObject.put("jml_up2k", jml_up2k);
                jsonObject.put("keg_sehat_lingkungan", keg_sehat_lingkungan);
                jsonObject.put("jml_keg_sehat_lingkungan", jml_keg_sehat_lingkungan);
                jsonObject.put("ptp", ptp);
                jsonObject.put("Industri_rt", Industri_rt);
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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }


    class Upload_pkk_catatan_keluarga_detail extends AsyncTask<String, String, JSONObject>
    {
        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,nik,berkebutuhan_khusus,penghayatan_dan_pengamalan_pancasila,gotong_royong,pendidikan_ketrampilan,pengembangan_kehidupan_berkoperasi,pangan,
                sandang,kesehatan,perencanaan_sehat,id_kelompok_umur,usia_subur,ibu_hamil,
                menyusui,nifas,buta_baca,buta_tulis,buta_hitung,id_detail_cat,stunting;

        private Upload_pkk_catatan_keluarga_detail(Context context,String kd_desa,String nik,String berkebutuhan_khusus,String penghayatan_dan_pengamalan_pancasila
                ,String gotong_royong,String pendidikan_ketrampilan,String pengembangan_kehidupan_berkoperasi,String pangan,String sandang,String kesehatan,String perencanaan_sehat
                ,String id_kelompok_umur,String usia_subur,String ibu_hamil,String menyusui,String nifas,String buta_baca
                ,String buta_tulis,String buta_hitung,String id_detail_cat,String stunting,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.nik = nik;
            this.berkebutuhan_khusus = berkebutuhan_khusus;
            this.penghayatan_dan_pengamalan_pancasila = penghayatan_dan_pengamalan_pancasila;
            this.gotong_royong = gotong_royong;
            this.pendidikan_ketrampilan = pendidikan_ketrampilan;
            this.pengembangan_kehidupan_berkoperasi = pengembangan_kehidupan_berkoperasi;
            this.pangan = pangan;
            this.sandang = sandang;
            this.kesehatan = kesehatan;
            this.perencanaan_sehat = perencanaan_sehat;
            this.id_kelompok_umur = id_kelompok_umur;
            this.usia_subur = usia_subur;
            this.ibu_hamil = ibu_hamil;
            this.menyusui = menyusui;
            this.nifas = nifas;
            this.buta_baca = buta_baca;
            this.buta_tulis = buta_tulis;
            this.buta_hitung = buta_hitung;
            this.id_detail_cat = id_detail_cat;
            this.stunting = stunting;

            this.SERVER_PATH = SERVER_PATH;
        }


        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(Upload_Data_Activity.this);
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
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("id_penduduk", nik);
                jsonObject.put("berkebutuhan_khusus", berkebutuhan_khusus);
                jsonObject.put("penghayatan_dan_pengamalan_pancasila", penghayatan_dan_pengamalan_pancasila);
                jsonObject.put("gotong_royong", gotong_royong);
                jsonObject.put("pendidikan_ketrampilan", pendidikan_ketrampilan);
                jsonObject.put("pengembangan_kehidupan_berkoperasi", pengembangan_kehidupan_berkoperasi);
                jsonObject.put("pangan", pangan);
                jsonObject.put("sandang", sandang);
                jsonObject.put("kesehatan", kesehatan);
                jsonObject.put("perencanaan_sehat", perencanaan_sehat);
                jsonObject.put("id_kelompok_umur", id_kelompok_umur);
                jsonObject.put("usia_subur", usia_subur);
                jsonObject.put("ibu_hamil", ibu_hamil);
                jsonObject.put("menyusui", menyusui);
                jsonObject.put("nifas", nifas);
                jsonObject.put("buta_baca", buta_baca);
                jsonObject.put("buta_tulis", buta_tulis);
                jsonObject.put("buta_hitung", buta_hitung);
                jsonObject.put("stunting",stunting);
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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }


    class Upload_PkkKelompokDasawisma_Async extends AsyncTask<String, String, JSONObject>
    {
        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,no_kk,id_dasa_wisma;

        private Upload_PkkKelompokDasawisma_Async(Context context,String kd_desa,String no_kk,String id_dasa_wisma,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.no_kk = no_kk;
            this.id_dasa_wisma = id_dasa_wisma;

            this.SERVER_PATH = SERVER_PATH;
        }
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(Upload_Data_Activity.this);
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
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("no_kk", no_kk);
                jsonObject.put("id_dasa_wisma", id_dasa_wisma);
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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }



    class Upload_tweb_penduduk_Async extends AsyncTask<String, String, JSONObject>
    {
        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,nama,nik,id_kk,kk_level,id_rtm,rtm_level,sex,tempatlahir,tanggallahir,agama_id,
                pendidikan_kk_id,pekerjaan_id,status_kawin,id_cluster,alamat_sekarang,cacat_id;

        private Upload_tweb_penduduk_Async(Context context,String kd_desa,String nama,String nik,String id_kk,String kk_level,String id_rtm,
                                           String rtm_level,String sex,String tempatlahir,String tanggallahir,String agama_id,
                                           String pendidikan_kk_id,String pekerjaan_id,String status_kawin,String id_cluster,
                                           String alamat_sekarang,String cacat_id,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.nama = nama;
            this.nik = nik;
            this.id_kk = id_kk;
            this.kk_level = kk_level;
            this.id_rtm = id_rtm;
            this.rtm_level = rtm_level;
            this.sex = sex;
            this.tempatlahir = tempatlahir;
            this.tanggallahir = tanggallahir;
            this.agama_id = agama_id;
            this.pendidikan_kk_id = pendidikan_kk_id;
            this.pekerjaan_id = pekerjaan_id;
            this.status_kawin = status_kawin;
            this.id_cluster = id_cluster;
            this.alamat_sekarang = alamat_sekarang;
            this.cacat_id = cacat_id;

            this.SERVER_PATH = SERVER_PATH;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(Upload_Data_Activity.this);
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
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("nama", nama);
                jsonObject.put("nik", nik);
                jsonObject.put("id_kk", id_kk);
                jsonObject.put("kk_level", kk_level);
                jsonObject.put("id_rtm", id_rtm);
                jsonObject.put("rtm_level", rtm_level);
                jsonObject.put("sex", sex);
                jsonObject.put("tempatlahir", tempatlahir);
                jsonObject.put("tanggallahir", tanggallahir);
                jsonObject.put("agama_id", agama_id);
                jsonObject.put("pendidikan_kk_id", pendidikan_kk_id);
                jsonObject.put("pekerjaan_id", pekerjaan_id);
                jsonObject.put("status_kawin", status_kawin);
                jsonObject.put("id_cluster", id_cluster);
                jsonObject.put("alamat_sekarang", alamat_sekarang);
                jsonObject.put("cacat_id", cacat_id);
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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }



    class Upload_Rtm_Async extends AsyncTask<String, String, JSONObject>
    {
        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,nik_kepala,no_kk,tgl_daftar,kelas_sosial,id_rtm;

        private Upload_Rtm_Async(Context context,String kd_desa,String nik_kepala,String no_kk,String tgl_daftar,String kelas_sosial,String id_rtm,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.nik_kepala = nik_kepala;
            this.no_kk = no_kk;
            this.tgl_daftar = tgl_daftar;
            this.kelas_sosial = kelas_sosial;
            this.id_rtm = id_rtm;

            this.SERVER_PATH = SERVER_PATH;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(Upload_Data_Activity.this);
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
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("nik_kepala", nik_kepala);
                jsonObject.put("no_kk", no_kk);
                jsonObject.put("tgl_daftar", tgl_daftar);
                jsonObject.put("kelas_sosial", kelas_sosial);
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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Upload_Data_Activity.this);

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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Upload_Data_Activity.this,MainActivity.class));
        finish();
    }

}
