package com.supradesa.supradesa_pkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Edit.Edit_Cari_No_Rtm_Activity;
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
private TextView tvNamaDusun,tvHome,tvProfile,tvJumlahBalita,tvJmlHamil,
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
            finish();
        });

        myFab.setOnClickListener(l->{
            animateFAB();
        });

        fabPendataan.setOnClickListener(l->{
            Intent intent = new Intent(MainActivity.this,Pemilihan_KK_Activity.class);
            intent.putExtra("id_kk","0");
            startActivity(intent);
            finish();

        });

        fabSync.setOnClickListener(l->{
            Intent intent = new Intent(MainActivity.this,Ambil_DataActivity.class);
            startActivity(intent);
//            finish();
        });

        fabDoc.setOnClickListener(l->{
            Intent intent = new Intent(MainActivity.this, Data_Belum_Upload_Activity.class);
            startActivity(intent);
        });

//        Crud crud = new Crud(this);
//        String no_rtm = crud.getData_config_code().get(0).getKode_kabupaten()+
//                crud.getData_config_code().get(0).getKode_kecamatan()+
//                crud.getData_config_code().get(0).getKode_desa()+crud.getData_tweb_rtm().size()+1;

//        Toast.makeText(this,no_rtm,Toast.LENGTH_LONG).show();
//        getPenduduk();
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

    private void getPenduduk()
    {
        Call<Ent_twebPenduduk> callDataPenduduk = apiInterface.getTwebPenduduk("3308012011","JEBENGAN");
        callDataPenduduk.enqueue(new Callback<Ent_twebPenduduk>() {
            @Override
            public void onResponse(Call<Ent_twebPenduduk> call, Response<Ent_twebPenduduk> response) {
                if(response.body().isResponse())
                {
                    List<Ent_twebPenduduk> dataPenduduk = response.body().getData();
                    Ent_twebPenduduk etp = new Ent_twebPenduduk();

                    for (int a=0;a<dataPenduduk.size();a++)
                    {
                        etp.setId(dataPenduduk.get(a).getId());
                        etp.setNama(dataPenduduk.get(a).getNama());
                        etp.setNik(dataPenduduk.get(a).getNik());
                        etp.setId_kk(dataPenduduk.get(a).getId_kk());
                        etp.setKk_level(dataPenduduk.get(a).getKk_level());
                        etp.setId_rtm(dataPenduduk.get(a).getId_rtm());
                        etp.setRtm_level(dataPenduduk.get(a).getRtm_level());
                        etp.setSex(dataPenduduk.get(a).getSex());
                        etp.setTempatlahir(dataPenduduk.get(a).getTempatlahir());
                        etp.setTanggallahir(dataPenduduk.get(a).getTanggallahir());
                        etp.setAgama_id(dataPenduduk.get(a).getAgama_id());
                        etp.setPendidikan_kk_id(dataPenduduk.get(a).getPendidikan_kk_id());
                        etp.setPekerjaan_id(dataPenduduk.get(a).getPekerjaan_id());
                        etp.setStatus_kawin(dataPenduduk.get(a).getStatus_kawin());
                        etp.setId_cluster(dataPenduduk.get(a).getId_cluster());
                        etp.setAlamat_sekarang(dataPenduduk.get(a).getAlamat_sekarang());
                        etp.setCacat_id(dataPenduduk.get(a).getCacat_id());

                        crudSqlite.InsertData_tweb_penduduk(etp);
                    }
                }
            }

            @Override
            public void onFailure(Call<Ent_twebPenduduk> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Network Failed",Toast.LENGTH_LONG).show();
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


}
