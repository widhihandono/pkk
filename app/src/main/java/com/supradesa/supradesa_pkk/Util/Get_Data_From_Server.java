package com.supradesa.supradesa_pkk.Util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Model.Ent_ConfigCode;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDasaWisma;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDataKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkKelompokDasawisma;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_master_tweb;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.List;

public class Get_Data_From_Server {
Context context;
    SharedPref sharedPref;
    Crud crudSqlite;
    Crud_master_tweb crudMasterTweb;
    Crud_pkk crudPkk;
    Api_Interface apiInterface;
    List_Temporary list_temporary;
    int respon_config = -1,respon_penduduk = -1,respon_rtm = -1,respon_keluarga = -1,respon_cat_keluarga = -1,
    respon_cat_keluarga_det = -1,respon_pkk_keluarga = -1,respon_kelompok_dasawisma = -1,respon_dasawisma = -1;

    public AlertDialog dialog = null;


    public Get_Data_From_Server(Context context) {
        this.context = context;
        sharedPref = new SharedPref(context);
        crudSqlite = new Crud(context);
        crudMasterTweb = new Crud_master_tweb(context);
        crudPkk = new Crud_pkk(context);
        list_temporary = new List_Temporary();
        apiInterface = Api_Client.getClient().create(Api_Interface.class);

//        dialog = new Dialog(context);
//        dialog.setTitle("Getting Data From Server. Please wait . . .");
//        dialog.setCancelable(false);

        dialog = setProgressDialog().create();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }

    }

    public AlertDialog.Builder setProgressDialog() {

        int llPadding = 30;
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(context);
        tvText.setText("Getting Data From Server. Please wait . . .");
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(18);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(ll);

        return builder;

    }

    public void getAll()
    {
//        setProgressDialog();
        getConfig_Code();
        getPenduduk();
        getRtm();
        getKeluarga();
        getPkkCatatanKeluarga();
        getPkkDataKeluarga();
        getPkkKelompokDasaWisma();
        getPkkDasaWisma();
//
//        if(respon_config >= 0)
//        {
//
//            getPenduduk();
//            if(respon_penduduk >= 0)
//            {
//                Toast.makeText(context,"Response : "+respon_penduduk,Toast.LENGTH_LONG).show();
//                getRtm();
//                if(respon_rtm >= 0)
//                {
//                    getKeluarga();
//                    if(respon_keluarga >= 0)
//                    {
//                        getPkkCatatanKeluarga();
//                        if(respon_cat_keluarga >= 0)
//                        {
//                            getPkkCatatanKeluargaDetail();
//                            if(respon_cat_keluarga_det >= 0)
//                            {
//                                getPkkDataKeluarga();
//                                if(respon_pkk_keluarga >= 0)
//                                {
//                                    getPkkKelompokDasaWisma();
//                                    if(respon_kelompok_dasawisma >= 0)
//                                    {
//                                        getPkkDasaWisma();
//                                        if(respon_dasawisma >= 0)
//                                        {
//                                            dialog.dismiss();
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }

//        dialog.dismiss();
    }
    public void penduduk()
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

    public void getConfig_Code()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            GetConfigCode productTask = new GetConfigCode();
            productTask.execute();
        }

    }


    public void getPenduduk()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudSqlite.getData_tweb_penduduk().size() > 0)
            {
                if(crudSqlite.delete_all_penduduk())
                {
                    GetPenduduk productTask = new GetPenduduk();
                    productTask.execute();

                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                GetPenduduk productTask = new GetPenduduk();
                productTask.execute();
            }
        }


    }

    public void getKeluarga()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudSqlite.getData_tweb_keluarga().size() > 0)
            {
                if(crudSqlite.delete_all_keluarga())
                {
                    Get_Keluarga_Async getAsync = new Get_Keluarga_Async();
                    getAsync.execute();
                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Get_Keluarga_Async getAsync = new Get_Keluarga_Async();
                getAsync.execute();
            }
        }


    }

    public void getRtm()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudSqlite.getData_tweb_rtm().size() > 0)
            {
                if(crudSqlite.delete_all_rtm())
                {
                    Get_Rtm_Async getUseAsyncTask = new Get_Rtm_Async();
                    getUseAsyncTask.execute();

                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Get_Rtm_Async getUseAsyncTask = new Get_Rtm_Async();
                getUseAsyncTask.execute();
            }
        }

    }


    public void getPkkCatatanKeluarga()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudPkk.getData_pkkCatatanKeluarga().size() > 0)
            {
                if(crudPkk.delete_all_pkk_catatan_keluarga())
                {
                    Get_PkkCatatanKeluarga_Async getUseAsyncTask = new Get_PkkCatatanKeluarga_Async();
                    getUseAsyncTask.execute();
                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Get_PkkCatatanKeluarga_Async getUseAsyncTask = new Get_PkkCatatanKeluarga_Async();
                getUseAsyncTask.execute();
            }
        }


    }



    public void getPkkCatatanKeluargaDetail()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudPkk.getData_pkk_catatan_keluarga_detail().size() > 0)
            {
                if(crudPkk.delete_all_pkk_catatan_keluarga_detail())
                {
                    get_PkkCatatanKeluargaDetail getUseAsyncTask = new get_PkkCatatanKeluargaDetail();
                    getUseAsyncTask.execute();
                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                get_PkkCatatanKeluargaDetail getUseAsyncTask = new get_PkkCatatanKeluargaDetail();
                getUseAsyncTask.execute();
            }
        }

    }


    public void getPkkDataKeluarga()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudPkk.getPkk_data_keluarga().size() > 0)
            {
                if(crudPkk.delete_all_pkk_data_keluarga())
                {
                    Get_PkkDataKeluarga getUseAsyncTask = new Get_PkkDataKeluarga();
                    getUseAsyncTask.execute();
                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Get_PkkDataKeluarga getUseAsyncTask = new Get_PkkDataKeluarga();
                getUseAsyncTask.execute();
            }
        }


    }


    public void getPkkDasaWisma()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudPkk.getPkk_dasa_wisma().size() > 0)
            {
                if(crudPkk.delete_all_pkk_dasa_wisma())
                {
                    Pkk_Dasawisma_Async getUseAsyncTask = new Pkk_Dasawisma_Async();
                    getUseAsyncTask.execute();

                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Pkk_Dasawisma_Async getUseAsyncTask = new Pkk_Dasawisma_Async();
                getUseAsyncTask.execute();


            }
        }
    }


    public void getPkkKelompokDasaWisma()
    {
        if(dialog.isShowing())
        {
            Toast.makeText(context,"Tunggu Sebentar, Proses Sync belum selesai",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(crudPkk.getPkk_kelompok_dasa_wisma().size() > 0)
            {
                if(crudPkk.delete_all_pkk_kelompok_dasa_wisma())
                {
                    Pkk_kelomDasawisma_Async getUseAsyncTask = new Pkk_kelomDasawisma_Async();
                    getUseAsyncTask.execute();
                }
                else
                {
                    Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Pkk_kelomDasawisma_Async getUseAsyncTask = new Pkk_kelomDasawisma_Async();
                getUseAsyncTask.execute();
            }
        }



    }


    //Async

    class Pkk_kelomDasawisma_Async extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public Pkk_kelomDasawisma_Async()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            setProgressDialog();

        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getPkkKelompokDasaWisma");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_PkkKelompokDasawisma ekd = new Ent_PkkKelompokDasawisma();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    ekd.setId_kelompok(jOb.getString("id_kelompok"));
                    ekd.setNo_kk(jOb.getString("no_kk"));
                    ekd.setId_dasa_wisma(jOb.getString("id_dasa_wisma"));

                    crudPkk.Insert_pkk_kelompok_dasa_wisma(ekd);

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
//                Toast.makeText(context,"Sukses Ambil Data Kelompok Dasawisma",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    class Pkk_Dasawisma_Async extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public Pkk_Dasawisma_Async()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            setProgressDialog();

        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getPkkDasaWisma");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_PkkDasaWisma pd = new Ent_PkkDasaWisma();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    pd.setId_dasa_wisma(jOb.getString("id_dasa_wisma"));
                    pd.setId_cluster(jOb.getString("id_cluster"));
                    pd.setId_kepala(jOb.getString("id_kepala"));
                    pd.setNama_dasa_wisma(jOb.getString("nama_dasa_wisma"));

                    crudPkk.Insert_pkk_dasa_wisma(pd);
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
                respon_dasawisma = success;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {

                Toast.makeText(context,"Sukses Ambil Data",Toast.LENGTH_LONG).show();

                    dialog.dismiss();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            dialog.cancel();
            dialog.dismiss();
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

    public void showDialogKeyAccess(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity)context).finishAffinity();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }

    //Async Config Code
    public class GetConfigCode extends AsyncTask<String, String, JSONObject>
    {

        public GetConfigCode()
        {}

        protected void onPreExecute()
        {
            super.onPreExecute();
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
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getConfigCode");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_ConfigCode ecc = new Ent_ConfigCode();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    ecc.setKode_desa(jOb.getString("kode_desa"));
                    ecc.setKode_kecamatan(jOb.getString("kode_kecamatan"));
                    ecc.setKode_kabupaten(jOb.getString("kode_kabupaten"));

                    if(crudSqlite.InsertData_config_code(ecc) > 0)
                    {
                        Log.i("config","Sukses Ambil Data Config");
                    }


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
                respon_config = success;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
                dialog.dismiss();
//                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    //Async Penduduk
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
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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
//            dialog.dismiss();
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
//                Toast.makeText(context,"Sukses Ambil Data "+respon_penduduk,Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                dialog.dismiss();
//                respon_penduduk = success;
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
//                respon_penduduk = success;
                Toast.makeText(context,"Gagal Ambil Data Penduduk",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
//            dialog.dismiss();
        }


    }


    //Async Keluarga
    class Get_Keluarga_Async extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public Get_Keluarga_Async()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
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
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getKeluarga");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_twebKeluarga etk = new Ent_twebKeluarga();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    etk.setRt(jOb.getString("rt"));
                    etk.setRw(jOb.getString("rw"));
                    etk.setId(jOb.getString("id"));
                    etk.setNo_kk(jOb.getString("no_kk"));
                    etk.setNik_kepala(jOb.getString("nik_kepala"));
                    etk.setTgl_daftar(jOb.getString("tgl_daftar"));
                    etk.setKelas_sosial(jOb.getString("kelas_sosial"));
                    etk.setTgl_cetak_kk(jOb.getString("tgl_cetak_kk"));
                    etk.setAlamat(jOb.getString("alamat"));
                    etk.setId_cluster(jOb.getString("id_cluster"));

                    crudSqlite.InsertData_tweb_keluarga(etk);

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
//                Toast.makeText(context,"Sukses Ambil Data Keluarga",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            dialog.dismiss();
            dialog.cancel();
        }
    }


    //Async RTM
    class Get_Rtm_Async extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public Get_Rtm_Async()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
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
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getRtm");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_twebRtm rtm = new Ent_twebRtm();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    rtm.setId(jOb.getString("id"));
                    rtm.setNik_kepala(jOb.getString("nik_kepala"));
                    rtm.setNo_kk(jOb.getString("no_kk"));
                    rtm.setTgl_daftar(jOb.getString("tgl_daftar"));
                    rtm.setKelas_sosial(jOb.getString("kelas_sosial"));

                    crudSqlite.InsertData_tweb_rtm_sync(rtm);
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
//                Toast.makeText(context,"Sukses Ambil Data Rumah Tangga",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                dialog.dismiss();
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    //Async PKK Catatan Keluarga
    class Get_PkkCatatanKeluarga_Async extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public Get_PkkCatatanKeluarga_Async()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
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
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getPkkCatatanKeluarga");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_PkkCatatanKeluarga ek = new Ent_PkkCatatanKeluarga();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    ek.setId_cat(jOb.getString("id"));
                    ek.setId_kk(jOb.getString("id_kk"));
                    ek.setId_dk(jOb.getString("id_dk"));
                    ek.setTanggal_cat(jOb.getString("tanggal_cat"));

                    crudPkk.InsertData_pkk_catatan_keluarga(ek);
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
                respon_cat_keluarga = success;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
                dialog.dismiss();
//                Toast.makeText(context,"Sukses Ambil Data Catatan Keluarga",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                dialog.dismiss();
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    //Async PKK Catatan Keluarga Detail
    class get_PkkCatatanKeluargaDetail extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public get_PkkCatatanKeluargaDetail()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
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
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getPkkCatatanKeluargaDetail");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_PkkCatatanKeluargaDetail ekd = new Ent_PkkCatatanKeluargaDetail();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    ekd.setId_detail_cat(jOb.getString("id_detail_cat"));
                    ekd.setNik(jOb.getString("nik"));
                    ekd.setBerkebutuhan_khusus(jOb.getString("berkebutuhan_khusus"));
                    ekd.setPenghayatan_dan_pengamalan_pancasila(jOb.getString("penghayatan_dan_pengamalan_pancasila"));
                    ekd.setGotong_royong(jOb.getString("gotong_royong"));
                    ekd.setPendidikan_ketrampilan(jOb.getString("pendidikan_ketrampilan"));
                    ekd.setPengembangan_kehidupan_berkoperasi(jOb.getString("pengembangan_kehidupan_berkoperasi"));
                    ekd.setPangan(jOb.getString("pangan"));
                    ekd.setSandang(jOb.getString("sandang"));
                    ekd.setKesehatan(jOb.getString("kesehatan"));
                    ekd.setPerencanaan_sehat(jOb.getString("perencanaan_sehat"));
                    ekd.setId_kelompok_umur(jOb.getString("id_kelompok_umur"));
                    ekd.setUsia_subur(jOb.getString("usia_subur"));
                    ekd.setIbu_hamil(jOb.getString("ibu_hamil"));
                    ekd.setMenyusui(jOb.getString("menyusui"));
                    ekd.setNifas(jOb.getString("nifas"));
                    ekd.setButa_baca(jOb.getString("buta_baca"));
                    ekd.setButa_tulis(jOb.getString("buta_tulis"));
                    ekd.setButa_hitung(jOb.getString("buta_hitung"));
                    ekd.setStunting(jOb.getString("stunting"));
                    ekd.setHapus("tidak");

                    if(crudPkk.InsertData_pkk_catatan_keluarga_detail(ekd) > 0 )
                    {
                        Log.i("sukses", "YES");
                    }
                    else
                    {
                        Log.i("sukses", "NO");
                    }
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
                respon_cat_keluarga_det = success;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
                dialog.dismiss();
//                Toast.makeText(context,"Sukses Ambil Data Catatan Keluarga Detail",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                dialog.dismiss();
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }


    //Async PKK Data Keluarga
    class Get_PkkDataKeluarga extends AsyncTask<String, String, JSONObject>
    {
        List<NameValuePair> params;

        public Get_PkkDataKeluarga()
        {
            this.params = params;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
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
                URL url = new URL("https://pkk.magelangkab.go.id/Api_pkk/getPkkDataKeluarga");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", sharedPref.sp.getString("kode_desa",""));
                jsonObject.put("dusun", sharedPref.sp.getString("dusun",""));
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

                Ent_PkkDataKeluarga dk = new Ent_PkkDataKeluarga();
                for (int a=0;a<Jarray.length();a++)
                {
                    JSONObject jOb = Jarray.getJSONObject(a);
                    dk.setId_dk(jOb.getString("id_dk"));
                    dk.setNo_kk(jOb.getString("no_kk"));
                    dk.setMakanan_pokok(jOb.getString("makanan_pokok"));
                    dk.setJml_makanan_pokok(jOb.getString("jml_makanan_pokok"));
                    dk.setJamban(jOb.getString("jamban"));
                    dk.setJml_jamban(jOb.getString("jml_jamban"));
                    dk.setSumber_air(jOb.getString("sumber_air"));
                    dk.setJml_sumber_air(jOb.getString("jml_sumber_air"));
                    dk.setTempat_sampah(jOb.getString("tempat_sampah"));
                    dk.setJml_tempat_sampah(jOb.getString("jml_tempat_sampah"));
                    dk.setSaluran_pembuangan_air(jOb.getString("saluran_pembuangan_air"));
                    dk.setJml_saluran_pembuangan_air(jOb.getString("jml_saluran_pembuangan_air"));
                    dk.setStiker_p4k(jOb.getString("stiker_p4k"));
                    dk.setJml_stiker_p4k(jOb.getString("jml_stiker_p4k"));
                    dk.setKriteria_rumah(jOb.getString("kriteria_rumah"));
                    dk.setJml_kriteria_rumah(jOb.getString("jml_kriteria_rumah"));
                    dk.setUp2k(jOb.getString("up2k"));
                    dk.setJml_up2k(jOb.getString("jml_up2k"));
                    dk.setKeg_sehat_lingkungan(jOb.getString("keg_sehat_lingkungan"));
                    dk.setJml_keg_sehat_lingkungan(jOb.getString("jml_keg_sehat_lingkungan"));
                    dk.setPtp(jOb.getString("ptp"));
                    dk.setIndustri_rt(jOb.getString("Industri_rt"));

                    crudPkk.Insert_pkk_data_keluarga(dk);
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
                respon_pkk_keluarga = success;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
                dialog.dismiss();
//                Toast.makeText(context,"Sukses Ambil Data Keluarga",Toast.LENGTH_LONG).show();

            }
            else if (success == 2) {
                dialog.dismiss();
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,"Gagal Ambil Data ",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

}
