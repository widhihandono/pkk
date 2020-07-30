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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Edit.Edit_Cari_No_Rtm_Activity;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_master_tweb;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
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
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Activity extends AppCompatActivity {
    private TextView tvHome,tvProfile,tvNik,tvNama,tvJenisKelamin,tvAlamat;
    SharedPref sharedPref;
    Crud crudSqlite;
    Crud_master_tweb crudMasterTweb;
    Crud_pkk crudPkk;
    Api_Interface apiInterface;
    List_Temporary list_temporary;
    LinearLayout lnLogOut_profile;

    FloatingActionButton fabPendataan,fabSync,fabDoc,myFab;

    //Animation
    private Boolean isFabOpen = false,isFabOpenClear = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward,
            fab_openClear,fab_closeClear,rotate_forwardClear,rotate_backwardClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        getSupportActionBar().hide();

        sharedPref = new SharedPref(this);
        crudSqlite = new Crud(this);
        crudMasterTweb = new Crud_master_tweb(this);
        crudPkk = new Crud_pkk(this);
        list_temporary = new List_Temporary();
        apiInterface = Api_Client.getClient().create(Api_Interface.class);

        fabPendataan = findViewById(R.id.fabPendataan);
        fabSync = findViewById(R.id.fabSync);
        fabDoc = findViewById(R.id.fabDoc);
        tvNik = findViewById(R.id.tvNik);
        tvNama = findViewById(R.id.tvNama);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvJenisKelamin = findViewById(R.id.tvJenisKelamin);
        lnLogOut_profile = findViewById(R.id.lnLogOut_profile);
        myFab = (FloatingActionButton) findViewById(R.id.fab);

        myFab.setColorFilter(Color.WHITE);

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);


        if(!sharedPref.getSPSudahLogin())
        {
            startActivity(new Intent(this,Login_Activity.class));
            finish();
        }

        tvHome = findViewById(R.id.tvHome);
        tvProfile = findViewById(R.id.tvProfile);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);

        myFab.setColorFilter(Color.WHITE);


        tvNik.setText(sharedPref.sp.getString("nik",""));
        tvNama.setText(sharedPref.sp.getString("email",""));
        tvAlamat.setText(sharedPref.sp.getString("dusun",""));

        tvHome.setOnClickListener(l->{
            for (Drawable drawable : tvHome.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(tvHome.getContext(), R.color.blue), PorterDuff.Mode.SRC_IN));
                }
            }
            tvHome.setTextColor(ContextCompat.getColor(this,R.color.blue));

            for (Drawable drawable : tvProfile.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(tvProfile.getContext(), R.color.black), PorterDuff.Mode.SRC_IN));
                }
            }
            tvProfile.setTextColor(ContextCompat.getColor(this,R.color.black));

            startActivity(new Intent(this,MainActivity.class));
            finish();
        });

        tvProfile.setOnClickListener(l->{
            for (Drawable drawable : tvProfile.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(tvProfile.getContext(), R.color.blue), PorterDuff.Mode.SRC_IN));
                }
            }
            tvProfile.setTextColor(ContextCompat.getColor(this,R.color.blue));

            for (Drawable drawable : tvHome.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(tvHome.getContext(), R.color.black), PorterDuff.Mode.SRC_IN));
                }
            }
            tvHome.setTextColor(ContextCompat.getColor(this,R.color.black));
        });


        myFab.setOnClickListener(l->{
            animateFAB();
        });

        fabPendataan.setOnClickListener(l->{
            Intent intent = new Intent(Profile_Activity.this,Pemilihan_KK_Activity.class);
            intent.putExtra("id_kk","0");
            startActivity(intent);
            finish();

        });

        fabSync.setOnClickListener(l->{
            Intent intent = new Intent(Profile_Activity.this,Upload_Data_Activity.class);
            startActivity(intent);
            finish();
        });

        fabDoc.setOnClickListener(l->{
            Intent intent = new Intent(Profile_Activity.this, Edit_Cari_No_Rtm_Activity.class);
            startActivity(intent);
            finish();
        });


        lnLogOut_profile.setOnClickListener(l->{
//            crudSqlite.delete_all_penduduk();
//            crudSqlite.delete_all_keluarga();
//            crudSqlite.delete_all_rtm();
//
//            crudPkk.delete_all_pkk_catatan_keluarga();
//            crudPkk.delete_all_pkk_catatan_keluarga_detail();
//            crudPkk.delete_all_pkk_dasa_wisma();
//            crudPkk.delete_all_pkk_data_keluarga();
//            crudPkk.delete_all_pkk_kelompok_dasa_wisma();
//
//            crudMasterTweb.delete_all_penduduk_agama();
//            crudMasterTweb.delete_all_penduduk_hubungan();
//            crudMasterTweb.delete_all_penduduk_pekerjaan();
//            crudMasterTweb.delete_all_penduduk_pendidikan_kk();
//            crudMasterTweb.delete_all_penduduk_umur();

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


    public void animateFAB(){

        if(isFabOpen){

            myFab.startAnimation(rotate_backward);
            fabSync.startAnimation(fab_close);
            fabPendataan.startAnimation(fab_close);
            fabSync.startAnimation(fab_close);
            fabDoc.startAnimation(fab_close);
//            upload.startAnimation(fab_close);
//            pendataan.startAnimation(fab_close);
            fabSync.setClickable(false);
            fabPendataan.setClickable(false);
            fabDoc.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {
            myFab.startAnimation(rotate_forward);
            fabSync.startAnimation(fab_open);
            fabDoc.startAnimation(fab_open);
//            upload.startAnimation(fab_open);
            fabPendataan.startAnimation(fab_open);
//            pendataan.startAnimation(fab_open);
            fabPendataan.setClickable(true);
            fabSync.setClickable(true);
            fabDoc.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
}
