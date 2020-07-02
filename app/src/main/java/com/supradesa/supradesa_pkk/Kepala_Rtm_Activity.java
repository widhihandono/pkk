package com.supradesa.supradesa_pkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.shuhart.stepview.StepView;
import com.supradesa.supradesa_pkk.Adapter.Kepala_Rtm_Adapter;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Kepala_Rtm_Activity extends AppCompatActivity {
    private RecyclerView rvKepalaRtm;
    private TextView tvNext,tvBack;
    private RecyclerView.LayoutManager layoutManager;
    Crud crud;
    Crud_pkk crudPkk;
    List_Temporary list_temporary;
    Kepala_Rtm_Adapter kepalaRtmAdapter;
    StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kepala__rtm_);
        getSupportActionBar().hide();

        crud = new Crud(this);
        crudPkk = new Crud_pkk(this);
        list_temporary = new List_Temporary();

        tvNext = findViewById(R.id.tvNext);
        tvBack = findViewById(R.id.tvBack);
        stepView = findViewById(R.id.step_view);

        rvKepalaRtm = findViewById(R.id.rvKepalaRtm);
        rvKepalaRtm.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvKepalaRtm.setLayoutManager(layoutManager);

        kepalaRtmAdapter = new Kepala_Rtm_Adapter(this,list_temporary.getListAnggotaRtm());
        rvKepalaRtm.setAdapter(kepalaRtmAdapter);


        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(this, R.color.white))
                .animationType(StepView.ANIMATION_ALL)
                .selectedCircleColor(ContextCompat.getColor(this, R.color.blue))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.white))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
//                .steps(new ArrayList<String>() {{
//                    add("First step");
//                    add("Second step");
//                    add("Third step");
//                    add("Fourth step");
//                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(5)
                .animationDuration(200)
                // other state methods are equal to the corresponding xml attributes
                .commit();
        stepView.go(1,false);



        tvBack.setOnClickListener(l->{
            startActivity(new Intent(this,Pemilihan_KK_Activity.class));
            Animatoo.animateFade(this);
            finish();
        });



        tvNext.setOnClickListener(l->{
            Ent_twebRtm rtm = new Ent_twebRtm();
            rtm.setId(list_temporary.id_penduduk);
            rtm.setNik_kepala(list_temporary.id_penduduk);
            rtm.setNo_kk("08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk)); //no_kk atau no_rtm
            rtm.setTgl_daftar(tanggal());
            rtm.setKelas_sosial("0");

            if(crud.InsertData_tweb_rtm(rtm) > 0)
            {
                Toast.makeText(this,"Sukses Simpan Data",Toast.LENGTH_LONG).show();
                for (int i=0;i<list_temporary.listAnggotaRtm.size();i++)
                {
                    if (crud.updateData_tweb_penduduk_id_rtm("08" + crud.getData_tweb_rtm_id_kk(list_temporary.listAnggotaRtm.get(i).getId_kk()),
                            list_temporary.listAnggotaRtm.get(i).getNik(),list_temporary.listAnggotaRtm.get(i).getId()) > 0)
                    {
                            Ent_PkkCatatanKeluargaDetail ep = new Ent_PkkCatatanKeluargaDetail();

                            ep.setId_detail_cat(list_temporary.listAnggotaRtm.get(i).getId());
                            ep.setNik(list_temporary.listAnggotaRtm.get(i).getNik());
                            if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])) >= 0 && 5 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("1");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])) >= 6 && 17 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("2");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])) >= 18 && 30 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("3");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])) >= 31 && 64 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("4");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])) >= 65 && 400 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("27");
                            }

                            if (crudPkk.InsertData_pkk_catatan_keluarga_detail(ep) > 0) {
                                Log.i("simpan", "sukses");
                            } else {
//                                Toast.makeText(this, "Gagal Simpan Data Pkk Catatan Keluarga Detail", Toast.LENGTH_LONG).show();
                            }


//                        Toast.makeText(this, "Sukses Simpan Data dan update data", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, KelompokDasaWisma_Activity.class);
                        startActivity(intent);
                        Animatoo.animateFade(this);
                        finish();
                    } else {
                        Toast.makeText(this, "Gagal Simpan Data dan update data", Toast.LENGTH_LONG).show();
                    }
                }

            }
            else
            {
                Toast.makeText(this,crud.cek_data_rtm_by_id(rtm.getNo_kk()).get(0).getId(),Toast.LENGTH_LONG).show();
            }
//            Toast.makeText(this,crud.getData_tweb_rtm_no_kk(list_temporary.id_kk)+" , "+list_temporary.id_penduduk,Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this,Catatan_Keluarga_Activity.class);
//            startActivity(intent);
//            finish();

        });
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

    private String tanggal()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Pemilihan_KK_Activity.class);
        startActivity(intent);
        finish();
    }
}
