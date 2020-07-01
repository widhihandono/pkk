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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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
        tvTwebPenduduk.setText("Data Penduduk : "+crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().size());
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
        if(crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().size() == 0)
        {
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,Upload_Data_Activity.class));
            finish();
        }
        else
        {
            int jml = crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().size()-1;
            for(int a=0;a<crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().size();a++)
            {
                Upload_tweb_penduduk_Async upload = new Upload_tweb_penduduk_Async(this,sharedPref.sp.getString("kode_desa",""),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getNama(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getNik(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getId_kk(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getKk_level(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getId_rtm(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getRtm_level(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getSex(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getTempatlahir(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getTanggallahir(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getAgama_id(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getPendidikan_kk_id(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getPekerjaan_id(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getStatus_kawin(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getId_cluster(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getAlamat_sekarang(),
                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level().get(a).getCacat_id(),
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
                        crudPkk.getData_pkk_catatan_keluarga_detail().get(a).getId_detail_cat(),
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
    private class Upload_PkkDataKeluarga extends AsyncTask<Void, Integer, Integer> {

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
                multipartEntityBuilder.addTextBody("no_kk", no_kk);
                multipartEntityBuilder.addTextBody("makanan_pokok", makanan_pokok);
                multipartEntityBuilder.addTextBody("jml_makanan_pokok", jml_makanan_pokok);
                multipartEntityBuilder.addTextBody("jamban", jamban);
                multipartEntityBuilder.addTextBody("jml_jamban", jml_jamban);
                multipartEntityBuilder.addTextBody("sumber_air", sumber_air);
                multipartEntityBuilder.addTextBody("jml_sumber_air", jml_sumber_air);
                multipartEntityBuilder.addTextBody("tempat_sampah", tempat_sampah);
                multipartEntityBuilder.addTextBody("jml_tempat_sampah", jml_tempat_sampah);
                multipartEntityBuilder.addTextBody("saluran_pembuangan_air", saluran_pembuangan_air);
                multipartEntityBuilder.addTextBody("jml_saluran_pembuangan_air", jml_saluran_pembuangan_air);
                multipartEntityBuilder.addTextBody("stiker_p4k", stiker_p4k);
                multipartEntityBuilder.addTextBody("jml_stiker_p4k", jml_stiker_p4k);
                multipartEntityBuilder.addTextBody("kriteria_rumah", kriteria_rumah);
                multipartEntityBuilder.addTextBody("jml_kriteria_rumah", jml_kriteria_rumah);
                multipartEntityBuilder.addTextBody("up2k", up2k);
                multipartEntityBuilder.addTextBody("jml_up2k", jml_up2k);
                multipartEntityBuilder.addTextBody("keg_sehat_lingkungan", keg_sehat_lingkungan);
                multipartEntityBuilder.addTextBody("jml_keg_sehat_lingkungan", jml_keg_sehat_lingkungan);
                multipartEntityBuilder.addTextBody("ptp", ptp);
                multipartEntityBuilder.addTextBody("Industri_rt", Industri_rt);


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
//
//                    Toast.makeText(Profile_Activity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    if(responseInt == 1)
                    {
                        if(crudPkk.delete_pkk_data_keluarga_by_No_KK(no_kk))
                        {
                            responseInt = 1;
                        }
                        else
                        {
                            responseInt = 0;
                        }
                    }
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

            if(this.progressDialog != null && this.progressDialog.isShowing())
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
//                Toast.makeText(Upload_Data_Activity.this,"Sukses Upload Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,"Gagal Upload Data ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }


    }


    private class Upload_pkk_catatan_keluarga_detail extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,nik,berkebutuhan_khusus,penghayatan_dan_pengamalan_pancasila,gotong_royong,pendidikan_ketrampilan,pengembangan_kehidupan_berkoperasi,pangan,
                sandang,kesehatan,perencanaan_sehat,id_kelompok_umur,usia_subur,ibu_hamil,
                menyusui,nifas,buta_baca,buta_tulis,buta_hitung,id_detail_cat;

        private Upload_pkk_catatan_keluarga_detail(Context context,String kd_desa,String nik,String berkebutuhan_khusus,String penghayatan_dan_pengamalan_pancasila
                ,String gotong_royong,String pendidikan_ketrampilan,String pengembangan_kehidupan_berkoperasi,String pangan,String sandang,String kesehatan,String perencanaan_sehat
                ,String id_kelompok_umur,String usia_subur,String ibu_hamil,String menyusui,String nifas,String buta_baca
                ,String buta_tulis,String buta_hitung,String id_detail_cat,String SERVER_PATH) {
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

            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("id_penduduk", nik);
                multipartEntityBuilder.addTextBody("berkebutuhan_khusus", berkebutuhan_khusus);
                multipartEntityBuilder.addTextBody("penghayatan_dan_pengamalan_pancasila", penghayatan_dan_pengamalan_pancasila);
                multipartEntityBuilder.addTextBody("gotong_royong", gotong_royong);
                multipartEntityBuilder.addTextBody("pendidikan_ketrampilan", pendidikan_ketrampilan);
                multipartEntityBuilder.addTextBody("pengembangan_kehidupan_berkoperasi", pengembangan_kehidupan_berkoperasi);
                multipartEntityBuilder.addTextBody("pangan", pangan);
                multipartEntityBuilder.addTextBody("sandang", sandang);
                multipartEntityBuilder.addTextBody("kesehatan", kesehatan);
                multipartEntityBuilder.addTextBody("perencanaan_sehat", perencanaan_sehat);
                multipartEntityBuilder.addTextBody("id_kelompok_umur", id_kelompok_umur);
                multipartEntityBuilder.addTextBody("usia_subur", usia_subur);
                multipartEntityBuilder.addTextBody("ibu_hamil", ibu_hamil);
                multipartEntityBuilder.addTextBody("menyusui", menyusui);
                multipartEntityBuilder.addTextBody("nifas", nifas);
                multipartEntityBuilder.addTextBody("buta_baca", buta_baca);
                multipartEntityBuilder.addTextBody("buta_tulis", buta_tulis);
                multipartEntityBuilder.addTextBody("buta_hitung", buta_hitung);


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
//
//                    Toast.makeText(Profile_Activity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    if(responseInt == 1)
                    {
                        if(crudPkk.delete_pkk_catatan_keluarga_detail_by_id(id_detail_cat))
                        {
                            responseInt = 1;
                        }
                        else
                        {
                            responseInt = 0;
                        }
                    }
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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Upload Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,"Gagal Upload Data ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }

    private class Upload_PkkKelompokDasawisma_Async extends AsyncTask<Void, Integer, Integer> {

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

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("no_kk", no_kk);
                multipartEntityBuilder.addTextBody("id_dasa_wisma", id_dasa_wisma);


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
//
//                    Toast.makeText(Profile_Activity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));
                    responseInt = myObject.getInt("response");
                    if(responseInt == 1)
                    {
                        if(crudPkk.delete_pkk_kelompok_dasa_wisma_by_no_rtm(no_kk))
                        {
                            responseInt = 1;
                        }
                        else
                        {
                            responseInt = 0;
                        }
                    }

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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Upload Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,"Gagal Upload Data ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }


    private class Upload_tweb_penduduk_Async extends AsyncTask<Void, Integer, Integer> {

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

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("kd_desa", kd_desa);
                multipartEntityBuilder.addTextBody("nama", nama);
                multipartEntityBuilder.addTextBody("nik", nik);
                multipartEntityBuilder.addTextBody("id_kk", id_kk);
                multipartEntityBuilder.addTextBody("kk_level", kk_level);
                multipartEntityBuilder.addTextBody("id_rtm", id_rtm);
                multipartEntityBuilder.addTextBody("rtm_level", rtm_level);
                multipartEntityBuilder.addTextBody("sex", sex);
                multipartEntityBuilder.addTextBody("tempatlahir", tempatlahir);
                multipartEntityBuilder.addTextBody("tanggallahir", tanggallahir);
                multipartEntityBuilder.addTextBody("agama_id", agama_id);
                multipartEntityBuilder.addTextBody("pendidikan_kk_id", pendidikan_kk_id);
                multipartEntityBuilder.addTextBody("pekerjaan_id", pekerjaan_id);
                multipartEntityBuilder.addTextBody("status_kawin", status_kawin);
                multipartEntityBuilder.addTextBody("id_cluster", id_cluster);
                multipartEntityBuilder.addTextBody("alamat_sekarang", alamat_sekarang);
                multipartEntityBuilder.addTextBody("cacat_id", cacat_id);



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
//
//                    Toast.makeText(Profile_Activity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    if(responseInt == 1)
                    {
                        if(crud.delete_penduduk_by_nik(nik))
                        {
                            responseInt = 1;
                        }
                        else
                        {
                            responseInt = 0;
                        }
                    }
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
                Toast.makeText(Upload_Data_Activity.this,"Sukses Upload Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,"Gagal Upload Data ",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }


    private class Upload_Rtm_Async extends AsyncTask<Void, Integer, Integer> {

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
                multipartEntityBuilder.addTextBody("nik_kepala", nik_kepala);
                multipartEntityBuilder.addTextBody("no_kk", no_kk);
                multipartEntityBuilder.addTextBody("tgl_daftar", tgl_daftar);
                multipartEntityBuilder.addTextBody("kelas_sosial", kelas_sosial);


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
//
//                    Toast.makeText(Profile_Activity.this,"Sukses ambil data",Toast.LENGTH_LONG).show();


//                    Log.i("BERHASIL CUY", jsonObject.getString("no_kk"));

                    responseInt = myObject.getInt("response");
                    if(responseInt == 1)
                    {
                        if(crud.delete_rtm_by_id(id_rtm))
                        {
                            responseInt = 1;
                        }
                        else
                        {
                            responseInt = 0;
                        }
                    }
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

                Toast.makeText(Upload_Data_Activity.this,"Sukses Upload Data",Toast.LENGTH_LONG).show();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Upload_Data_Activity.this,"Gagal Upload Data ",Toast.LENGTH_LONG).show();
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
