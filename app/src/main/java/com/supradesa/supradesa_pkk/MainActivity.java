package com.supradesa.supradesa_pkk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Edit.Edit_Cari_No_Rtm_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_jumlah_data;
import com.supradesa.supradesa_pkk.Model.Ent_versioning;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.Util.SharedPref;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.SQLite.Crud;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private TextView tvDusun,tvHome,tvProfile,tvJumlahBalita,tvJmlHamil,
        tvJmlMenyusui,tvJmlWus,tvJmlPus,tvJmlLansia,tvJmlButa,upload,pendataan,tvDesaKecamatan,edit;
SharedPref sharedPref;
Crud crudSqlite;
    Crud_pkk crudPkk;
Api_Interface apiInterface;
FloatingActionButton fabPendataan,fabSync,fabDoc,myFab;

    //Animation
    private Boolean isFabOpen = false,isFabOpenClear = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward,
            fab_openClear,fab_closeClear,rotate_forwardClear,rotate_backwardClear;

    Snackbar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sharedPref = new SharedPref(this);
        crudSqlite = new Crud(this);
        crudPkk = new Crud_pkk(this);
        apiInterface = Api_Client.getClient().create(Api_Interface.class);


        if(!sharedPref.sp.getBoolean(SharedPref.SP_SUDAH_LOGIN,false))
        {
            startActivity(new Intent(this,Login_Activity.class));
            finish();
        }
//        Toast.makeText(MainActivity.this,sharedPref.sp.getString("no_hp",""),Toast.LENGTH_LONG).show();

        tvHome = findViewById(R.id.tvHome);
        tvProfile = findViewById(R.id.tvProfile);
        tvJumlahBalita = findViewById(R.id.tvJmlBalita);
        tvJmlHamil = findViewById(R.id.tvJmlHamil);
        tvJmlMenyusui = findViewById(R.id.tvJmlMenuyusi);
        tvJmlWus = findViewById(R.id.tvJmlWUS);
        tvJmlPus = findViewById(R.id.tvJmlPUS);
        tvJmlLansia = findViewById(R.id.tvJmlLansia);
        tvJmlButa = findViewById(R.id.tvJmlButa);
        fabPendataan = findViewById(R.id.fabPendataan);
        fabSync = findViewById(R.id.fabSync);
        fabDoc = findViewById(R.id.fabDoc);
        tvDusun = findViewById(R.id.tvDusun);
        myFab = (FloatingActionButton) findViewById(R.id.fab);


        myFab.setColorFilter(Color.WHITE);

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

//
//        tvJumlahBalita.setText("Jumlah : "+String.valueOf(crudPkk.getData_pkk_catatan_keluarga_detail_by_id_kelompok_umur("1").size()));
//        tvJmlMenyusui.setText("Jumlah : "+String.valueOf(crudPkk.getData_pkk_catatan_keluarga_detail_by_param(Helper.MENYUSUI,"ya").size()));
//        tvJmlHamil.setText("Jumlah : "+String.valueOf(crudPkk.getData_pkk_catatan_keluarga_detail_by_param(Helper.IBU_HAMIL,"ya").size()));
//        tvJmlWus.setText("Jumlah : "+String.valueOf(crudPkk.getData_pkk_catatan_keluarga_detail_WUS().size()));
//        tvJmlPus.setText("Jumlah : "+String.valueOf(crudPkk.getData_pkk_catatan_keluarga_detail_PUS().size()));
//        tvJmlButa.setText("Jumlah : "+String.valueOf(crudPkk.getData_pkk_catatan_keluarga_detail_tiga_buta().size()));
//        tvJmlLansia.setText("Jumlah : "+getLansia_Penduduk());

        //=============================== Cek Versi ==================================================
        cek_versi();
        //============================================================================================

        for (Drawable drawable : tvHome.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(tvHome.getContext(), R.color.blue), PorterDuff.Mode.SRC_IN));
            }
        }
        tvHome.setTextColor(ContextCompat.getColor(this,R.color.blue));

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

            startActivity(new Intent(this,Profile_Activity.class));
            Animatoo.animateFade(MainActivity.this);
            finish();
        });

        myFab.setOnClickListener(l->{
            animateFAB();
        });

        fabPendataan.setOnClickListener(l->{
            Intent intent = new Intent(MainActivity.this,Pemilihan_KK_Activity.class);
            intent.putExtra("id_kk","0");
            startActivity(intent);
            Animatoo.animateFade(MainActivity.this);
            finish();

        });

        fabSync.setOnClickListener(l->{
            Intent intent = new Intent(MainActivity.this,Ambil_DataActivity.class);
            startActivity(intent);
            Animatoo.animateFade(MainActivity.this);
//            finish();
        });

        fabDoc.setOnClickListener(l->{
            Intent intent = new Intent(MainActivity.this,Data_Belum_Upload_Activity.class);
            intent.putExtra("pager",0);
            startActivity(intent);
            Animatoo.animateFade(MainActivity.this);
        });

//        Crud crud = new Crud(this);
//        String no_rtm = crud.getData_config_code().get(0).getKode_kabupaten()+
//                crud.getData_config_code().get(0).getKode_kecamatan()+
//                crud.getData_config_code().get(0).getKode_desa()+crud.getData_tweb_rtm().size()+1;

//        Toast.makeText(this,no_rtm,Toast.LENGTH_LONG).show();
//        getPenduduk();

        tvDusun.setText(sharedPref.sp.getString("dusun",""));
        get_jumlah_tot_data();
    }

    private int getLansia_Penduduk()
    {
        int jml=0;
        for(int a=0;a<crudSqlite.getData_tweb_penduduk().size();a++)
        {
            if(getAge(Integer.parseInt(crudSqlite.getData_tweb_penduduk().get(a).getTanggallahir().split("-")[0]),
                    Integer.parseInt(crudSqlite.getData_tweb_penduduk().get(a).getTanggallahir().split("-")[1]),
                    Integer.parseInt(crudSqlite.getData_tweb_penduduk().get(a).getTanggallahir().split("-")[2])) >= 60)
            {
                jml = jml+1;
            }

        }
        return jml;
    }

    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

//        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
//            age--;
//        }

        Integer ageInt = new Integer(age);

        return ageInt;
    }

    private void get_jumlah_tot_data()
    {
        Call<Ent_jumlah_data> callDataPenduduk = apiInterface.jumlah_tot_data(sharedPref.sp.getString("kode_desa",""));
        callDataPenduduk.enqueue(new Callback<Ent_jumlah_data>() {
            @Override
            public void onResponse(Call<Ent_jumlah_data> call, Response<Ent_jumlah_data> response) {
                if(response.isSuccessful())
                {
                    tvJmlMenyusui.setText("Jumlah : "+response.body().getData_menyusui());
                    tvJmlButa.setText("Jumlah : "+response.body().getData_buta());
                    tvJmlHamil.setText("Jumlah : "+response.body().getData_hamil());
                    tvJumlahBalita.setText("Jumlah : "+response.body().getData_balita());
                    tvJmlPus.setText("Jumlah : "+response.body().getData_pus());
                    tvJmlWus.setText("Jumlah : "+response.body().getData_wus());
                    tvJmlLansia.setText("Jumlah : "+response.body().getData_lansia());
                }
            }

            @Override
            public void onFailure(Call<Ent_jumlah_data> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
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


    private void showDialogKeluar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin ingin Keluar ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya untuk Keluar")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {

                        finishAffinity();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.RED);
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }

    private void cek_versi()
    {
        Call<Ent_versioning> callCekVersi = apiInterface.versioning();

        callCekVersi.enqueue(new Callback<Ent_versioning>() {
            @Override
            public void onResponse(Call<Ent_versioning> call, Response<Ent_versioning> response) {
                if(response.isSuccessful())
                {

                        if(Float.parseFloat(response.body().getVersi()) > 1.0)
                        {
                            showDialogCekVersion(response.body().getPesan());
                        }
                        else
                        {
                            Log.i("version","Memenuhi");
                        }


                }
                else
                {
                    showSnackbar("Mohon maaf,ada gangguan pada server","Refresh");
                    Toast.makeText(MainActivity.this,"Mohon maaf,ada gangguan pada server",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Ent_versioning> call, Throwable t) {
                showSnackbar("Mohon maaf,ada gangguan pada server","Refresh");
                Toast.makeText(MainActivity.this,"Network failed",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showDialogCekVersion(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Lanjutkan Download",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.supradesa.supradesa_pkk")));
                        finish();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }



    private void showSnackbar(String text, String action)
    {

        bar = Snackbar.make(findViewById(R.id.sb_menu_utama),text, Snackbar.LENGTH_INDEFINITE);
        View view = bar.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
        mainTextView.setTextColor(Color.WHITE);

        bar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        }).setActionTextColor(Color.YELLOW);
        bar.show();
    }

    @Override
    public void onBackPressed() {
        showDialogKeluar();
    }
}
