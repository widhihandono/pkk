package com.supradesa.supradesa_pkk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Model.Ent_ConfigCode;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_master_tweb;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.Util.Get_All_Data_From_Server;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private TextView tvSyncAll,tvTglSync_konf,tvTglSync_penduduk,tvTglSync_rmhTgga,tvTglSync_keluarga,
            tvTglSync_catKeluarga,tvTglSync_catKeluargaDet,tvTglSync_pkkKeluarga,tvTglSync_kelompok_dasawisma,
            tvTglSync_pkkDasawisma,tvBack;

    private TextView tvJumlah_penduduk,tvJumlah_rmhTgga,tvJumlah_keluarga,
            tvJumlah_catKeluarga,tvJumlah_catKeluargaDet,tvJumlah_pkkKeluarga,tvJumlah_kelompok_dasawisma,
            tvJumlah_pkkDasawisma;

    private Dialog dialog;

    Get_Data_From_Server get_data_from_server;
    Get_All_Data_From_Server get_all_data_from_server;
    int clickCount_config = 0,clickCount_rtm = 0,clickCount_penduduk = 0,clickCount_keluarga = 0,clickCount_cat_keluarga = 0,
            getClickCount_cat_keluarga_det = 0,clickCount_pkk_keluarga = 0,clickCount_kelompok_dasawisma,clickCount_dasawisma = 0,clickCount_all = 0;


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
        get_all_data_from_server = new Get_All_Data_From_Server(Ambil_DataActivity.this);

        dialog = new Dialog(this);
        dialog.setTitle("Getting Data From Server. Please wait . . .");
        dialog.setCancelable(false);

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

        tvTglSync_konf = findViewById(R.id.tvTglSync_konf);
        tvTglSync_penduduk = findViewById(R.id.tvTglSync_penduduk);
        tvTglSync_rmhTgga = findViewById(R.id.tvTglSync_rmhTgga);
        tvTglSync_keluarga = findViewById(R.id.tvTglSync_keluarga);
        tvTglSync_catKeluarga = findViewById(R.id.tvTglSync_catKeluarga);
        tvTglSync_catKeluargaDet = findViewById(R.id.tvTglSync_catKeluargaDet);
        tvTglSync_pkkKeluarga = findViewById(R.id.tvTglSync_pkkKeluarga);
        tvTglSync_kelompok_dasawisma = findViewById(R.id.tvTglSync_kelompok_dasawisma);
        tvTglSync_pkkDasawisma = findViewById(R.id.tvTglSync_pkkDasawisma);
        tvJumlah_penduduk = findViewById(R.id.tvJumlah_penduduk);
        tvJumlah_rmhTgga = findViewById(R.id.tvJumlah_rmhTgga);
        tvJumlah_keluarga = findViewById(R.id.tvJumlah_keluarga);
        tvJumlah_catKeluarga = findViewById(R.id.tvJumlah_catKeluarga);
        tvJumlah_catKeluargaDet = findViewById(R.id.tvJumlah_catKeluargaDet);
        tvJumlah_pkkKeluarga = findViewById(R.id.tvJumlah_pkkKeluarga);
        tvJumlah_kelompok_dasawisma = findViewById(R.id.tvJumlah_kelompok_dasawisma);
        tvJumlah_pkkDasawisma = findViewById(R.id.tvJumlah_pkkDasawisma);
        tvBack = findViewById(R.id.tvBack);

        lnConfigCode.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    //                if(clickCount_config < 1)
//                {
//                    clickCount_config = clickCount_config+1;
                    get_data_from_server.getConfig_Code();

                    hapus_static_list();
                    sharedPref.saveSPString("tgl_konf",tgl());
                    tvTglSync_konf.setText("Last Sync : "+sharedPref.sp.getString("tgl_konf",""));
//                }
//                else
//                {
//                    Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                    clickCount_config = 0;
//                }
                }


            }
            else
            {
                showDialogOnNetwork();
            }

        });

        lnPenduduk.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(clickCount_penduduk < 1)
//                {
//                    clickCount_penduduk = clickCount_penduduk+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_data_from_server.getPenduduk();

                    hapus_static_list();
                    sharedPref.saveSPString("tglSync_penduduk",tgl());
                    tvTglSync_penduduk.setText("Last Sync : "+sharedPref.sp.getString("tglSync_penduduk",""));
                    tvJumlah_penduduk.setText("Total : "+sharedPref.sp.getInt("count_penduduk",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_penduduk = 0;
//                    }
                }

            }
            else
            {
                showDialogOnNetwork();
            }

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
            if(isNetworkAvailable())
            {
//                if(clickCount_rtm < 1)
//                {
//                    clickCount_rtm = clickCount_rtm+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                 {
                    get_data_from_server.getRtm();

                    hapus_static_list();
                    sharedPref.saveSPString("tgl_rmhTgga", tgl());
                    tvTglSync_rmhTgga.setText("Last Sync : " + sharedPref.sp.getString("tgl_rmhTgga", ""));
                     tvJumlah_rmhTgga.setText("Total : "+sharedPref.sp.getInt("count_rtm",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_rtm = 0;
//                    }
                }
            }
            else
            {
                showDialogOnNetwork();
            }

        });

        lnKeluarga.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(clickCount_keluarga < 1)
//                {
//                    clickCount_keluarga = clickCount_keluarga+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_data_from_server.getKeluarga();

                    hapus_static_list();
                    sharedPref.saveSPString("tglSync_keluarga", tgl());
                    tvTglSync_keluarga.setText("Last Sync : " + sharedPref.sp.getString("tglSync_keluarga", ""));
                    tvJumlah_keluarga.setText("Total : "+sharedPref.sp.getInt("count_keluarga",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_keluarga = 0;
//                    }
                }
            }
            else
            {
                showDialogOnNetwork();
            }
        });


        lnPkkCatatanKeluarga.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(clickCount_cat_keluarga < 1)
//                {
//                    clickCount_cat_keluarga = clickCount_cat_keluarga+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_data_from_server.getPkkCatatanKeluarga();

                    hapus_static_list();
                    sharedPref.saveSPString("tglSync_catKeluarga",tgl());
                    tvTglSync_catKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluarga",""));
                    tvJumlah_catKeluarga.setText("Total : "+sharedPref.sp.getInt("count_pkkCatatanKeluarga",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_cat_keluarga = 0;
//                    }
                }

            }
            else
            {
                showDialogOnNetwork();
            }

        });

        lnPkkCatatanKeluargaDetail.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(getClickCount_cat_keluarga_det < 1)
//                {
//                    getClickCount_cat_keluarga_det = getClickCount_cat_keluarga_det+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_data_from_server.getPkkCatatanKeluargaDetail();

                    hapus_static_list();
                    sharedPref.saveSPString("tglSync_catKeluargaDet",tgl());
                    tvTglSync_catKeluargaDet.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluargaDet",""));
                    tvJumlah_catKeluargaDet.setText("Total : "+sharedPref.sp.getInt("count_pkkCatatanKeluargaDetail",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        getClickCount_cat_keluarga_det = 0;
//                    }
                }

            }
            else
            {
                showDialogOnNetwork();
            }

        });

        lnPkkDataKeluarga.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(clickCount_pkk_keluarga < 1)
//                {
//                    clickCount_pkk_keluarga = clickCount_pkk_keluarga+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_data_from_server.getPkkDataKeluarga();

                    hapus_static_list();
                    sharedPref.saveSPString("tglSync_pkkKeluarga",tgl());
                    tvTglSync_pkkKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkKeluarga",""));
                    tvJumlah_pkkKeluarga.setText("Total: "+sharedPref.sp.getInt("count_pkkData_keluarga",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_pkk_keluarga = 0;
//                    }
                }

            }
            else
            {
                showDialogOnNetwork();
            }

        });

        lnPkkKelompokDasawisma.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(clickCount_kelompok_dasawisma < 1)
//                {
//                    clickCount_kelompok_dasawisma = clickCount_kelompok_dasawisma+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_data_from_server.getPkkKelompokDasaWisma();

                    hapus_static_list();
                    sharedPref.saveSPString("tglSync_kelompok_dasawiswa",tgl());
                    tvTglSync_kelompok_dasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_kelompok_dasawiswa",""));
                    tvJumlah_kelompok_dasawisma.setText("Total : "+sharedPref.sp.getInt("count_kelompokDasawisma",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_kelompok_dasawisma = 0;
//                    }
                }

            }
            else
            {
                showDialogOnNetwork();
            }

        });

        lnPkkDasawisma.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(clickCount_dasawisma < 1)
//                {
//                    clickCount_dasawisma = clickCount_dasawisma+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_data_from_server.getPkkDasaWisma();

                    hapus_static_list();
                    sharedPref.saveSPString("tglSync_pkkDasawisma",tgl());
                    tvTglSync_pkkDasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkDasawisma",""));
                    tvJumlah_pkkDasawisma.setText("Total : "+sharedPref.sp.getInt("count_dasawisma",0));
//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_dasawisma = 0;
//                    }
                }

            }
            else
            {
                showDialogOnNetwork();
            }

        });

        tvSyncAll.setOnClickListener(l->{
            if(isNetworkAvailable())
            {
//                if(clickCount_all < 1)
//                {
//                    clickCount_all = clickCount_all+1;
                if(crudSqlite.getData_tweb_rtm_join_penduduk_belum_upload().size() > 0)
                {
                    showDialogCek_rtm_hapus();
                }
                else
                {
                    get_all_data_from_server.getAll_Data();

                    hapus_static_list();


                    tvTglSync_konf.setText("Last Sync : "+sharedPref.sp.getString("tgl_konf",""));
                    tvTglSync_penduduk.setText("Last Sync : "+sharedPref.sp.getString("tglSync_penduduk",""));
                    tvTglSync_rmhTgga.setText("Last Sync : "+sharedPref.sp.getString("tgl_rmhTgga",""));
                    tvTglSync_keluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_keluarga",""));
                    tvTglSync_catKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluarga",""));
                    tvTglSync_catKeluargaDet.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluargaDet",""));
                    tvTglSync_pkkKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkKeluarga",""));
                    tvTglSync_kelompok_dasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_kelompok_dasawiswa",""));
                    tvTglSync_pkkDasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkDasawisma",""));

                    tvJumlah_penduduk.setText("Total : "+sharedPref.sp.getInt("count_penduduk",0));
                    tvJumlah_rmhTgga.setText("Total : "+sharedPref.sp.getInt("count_rtm",0));
                    tvJumlah_keluarga.setText("Total : "+sharedPref.sp.getInt("count_keluarga",0));
                    tvJumlah_catKeluarga.setText("Total : "+sharedPref.sp.getInt("count_pkkCatatanKeluarga",0));
                    tvJumlah_catKeluargaDet.setText("Total : "+sharedPref.sp.getInt("count_pkkCatatanKeluargaDetail",0));
                    tvJumlah_pkkKeluarga.setText("Total: "+sharedPref.sp.getInt("count_pkkData_keluarga",0));
                    tvJumlah_kelompok_dasawisma.setText("Total : "+sharedPref.sp.getInt("count_kelompokDasawisma",0));
                    tvJumlah_pkkDasawisma.setText("Total : "+sharedPref.sp.getInt("count_dasawisma",0));
                }

//                    }
//                    else
//                    {
//                        Toast.makeText(this,"Anda tadi sudah menekan tombol ini. Mohon Bersabar.",Toast.LENGTH_LONG).show();
//                        clickCount_all = 0;
//                    }
            }
            else
            {
                showDialogOnNetwork();
            }

        });

        tvBack.setOnClickListener(l->{
            startActivity(new Intent(Ambil_DataActivity.this,MainActivity.class));
            Animatoo.animateFade(this);
            finish();
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

            hapus_static_list();

            Toast.makeText(this,"Sukses Logout",Toast.LENGTH_LONG).show();
            sharedPref.saveSPBoolean(sharedPref.SP_SUDAH_LOGIN,false);
            sharedPref.saveSPString("nik","");
            sharedPref.saveSPString("kode_desa","");
            sharedPref.saveSPString("dusun","");
            sharedPref.saveSPString("nama_desa","");
            sharedPref.saveSPString("nama_kecamatan","");
            sharedPref.saveSPString("no_hp","");
            sharedPref.saveSPString("email","");

            sharedPref.saveSPString("tgl_konf","");
            sharedPref.saveSPString("tglSync_penduduk","");
            sharedPref.saveSPString("tgl_rmhTgga","");
            sharedPref.saveSPString("tglSync_keluarga","");
            sharedPref.saveSPString("tglSync_catKeluarga","");
            sharedPref.saveSPString("tglSync_catKeluargaDet","");
            sharedPref.saveSPString("tglSync_pkkKeluarga","");
            sharedPref.saveSPString("tglSync_kelompok_dasawiswa","");
            sharedPref.saveSPString("tglSync_pkkDasawisma","");

            startActivity(new Intent(this,Login_Activity.class));
            finish();
        });

        tvTglSync_konf.setText("Last Sync : "+sharedPref.sp.getString("tgl_konf",""));
        tvTglSync_penduduk.setText("Last Sync : "+sharedPref.sp.getString("tglSync_penduduk",""));
        tvTglSync_rmhTgga.setText("Last Sync : "+sharedPref.sp.getString("tgl_rmhTgga",""));
        tvTglSync_keluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_keluarga",""));
        tvTglSync_catKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluarga",""));
        tvTglSync_catKeluargaDet.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluargaDet",""));
        tvTglSync_pkkKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkKeluarga",""));
        tvTglSync_kelompok_dasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_kelompok_dasawiswa",""));
        tvTglSync_pkkDasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkDasawisma",""));

        tvJumlah_penduduk.setText("Total : "+sharedPref.sp.getInt("count_penduduk",0));
        tvJumlah_rmhTgga.setText("Total : "+sharedPref.sp.getInt("count_rtm",0));
        tvJumlah_keluarga.setText("Total : "+sharedPref.sp.getInt("count_keluarga",0));
        tvJumlah_catKeluarga.setText("Total : "+sharedPref.sp.getInt("count_pkkCatatanKeluarga",0));
        tvJumlah_catKeluargaDet.setText("Total : "+sharedPref.sp.getInt("count_pkkCatatanKeluargaDetail",0));
        tvJumlah_pkkKeluarga.setText("Total: "+sharedPref.sp.getInt("count_pkkData_keluarga",0));
        tvJumlah_kelompok_dasawisma.setText("Total : "+sharedPref.sp.getInt("count_kelompokDasawisma",0));
        tvJumlah_pkkDasawisma.setText("Total : "+sharedPref.sp.getInt("count_dasawisma",0));

    }

    private void hapus_static_list()
    {
        list_temporary.listAllAnggota_sementara.clear();
        list_temporary.listAllAnggota_edit_sementara.clear();
        list_temporary.listAnggotaRtm.clear();
        list_temporary.listAnggotaRtm_Edit_tampung.clear();
        list_temporary.listAnggotaRtm_Edit.clear();
        list_temporary.list_AmbilAnggotaRtm_Edit.clear();
        list_temporary.list_no_kk.clear();
        list_temporary.listAllAnggota.clear();
        list_temporary.dasawismaPosition = -1;
        list_temporary.kepalaRtm = -1;
        list_temporary.id_dasawisma = "";
        list_temporary.id_penduduk = "";
        list_temporary.id_kk = "";
        list_temporary.nik = "";
        list_temporary.listPenduduk_Detail.clear();
        list_temporary.listSub.clear();
    }

    //=========Check Internet Connection==========================
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showDialogOnNetwork(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Mohon hidupkan koneksi internet anda. Jika ingin Ambil Settingan")
                .setIcon(R.drawable.buta)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button btn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btn.setTextColor(Color.RED);
    }

    private void showDialogCek_rtm_hapus(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set pesan dari dialog
        alertDialogBuilder.setTitle("Yakin mau Sinkron Data sekarang ?");
        alertDialogBuilder
                .setMessage("Masih ada data yang belum di upload. Yakin mau melanjutkan Sinkron Data ? Data yang belum di upload, akan otomatis terhapus ketika Sinkron Data (Klik Tombol 'YA')")
                .setIcon(R.drawable.ic_delete)
                .setCancelable(false)
                .setPositiveButton("YA",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        if(isNetworkAvailable())
                        {
                                get_all_data_from_server.getAll_Data();

                                hapus_static_list();


                                tvTglSync_konf.setText("Last Sync : "+sharedPref.sp.getString("tgl_konf",""));
                                tvTglSync_penduduk.setText("Last Sync : "+sharedPref.sp.getString("tglSync_penduduk",""));
                                tvTglSync_rmhTgga.setText("Last Sync : "+sharedPref.sp.getString("tgl_rmhTgga",""));
                                tvTglSync_keluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_keluarga",""));
                                tvTglSync_catKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluarga",""));
                                tvTglSync_catKeluargaDet.setText("Last Sync : "+sharedPref.sp.getString("tglSync_catKeluargaDet",""));
                                tvTglSync_pkkKeluarga.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkKeluarga",""));
                                tvTglSync_kelompok_dasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_kelompok_dasawiswa",""));
                                tvTglSync_pkkDasawisma.setText("Last Sync : "+sharedPref.sp.getString("tglSync_pkkDasawisma",""));

                        }
                        else
                        {
                            showDialogOnNetwork();
                        }


                    }
                }).setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(Ambil_DataActivity.this,Data_Belum_Upload_Activity.class));
                Animatoo.animateFade(Ambil_DataActivity.this);
                finish();
            }
        });


        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbtn.setTextColor(Color.BLUE);
        Button nbtn = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbtn.setTextColor(Color.RED);

    }
    //===============================================

    private String tgl()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Ambil_DataActivity.this,MainActivity.class));
        Animatoo.animateFade(Ambil_DataActivity.this);
        finish();
    }
}