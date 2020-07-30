package com.supradesa.supradesa_pkk.Edit;

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
import com.supradesa.supradesa_pkk.Adapter.Edit_Kepala_Rtm_Adapter;
import com.supradesa.supradesa_pkk.Adapter.Kepala_Rtm_Adapter;
import com.supradesa.supradesa_pkk.KelompokDasaWisma_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;
import com.supradesa.supradesa_pkk.Pemilihan_KK_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Edit_Kepala_Rtm_Activity extends AppCompatActivity {
    private RecyclerView rvEditKepalaRtm;
    private TextView tvNext,tvBack;
    private RecyclerView.LayoutManager layoutManager;
    Crud crud;
    Crud_pkk crudPkk;
    List_Temporary list_temporary;
    Edit_Kepala_Rtm_Adapter editKepalaRtmAdapter;
    StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__kepala__rtm_);
        getSupportActionBar().hide();

        crud = new Crud(this);
        crudPkk = new Crud_pkk(this);
        list_temporary = new List_Temporary();

        tvNext = findViewById(R.id.tvNext);
        tvBack = findViewById(R.id.tvBack);
        stepView = findViewById(R.id.step_view);

        rvEditKepalaRtm = findViewById(R.id.rvEditKepalaRtm);
        rvEditKepalaRtm.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvEditKepalaRtm.setLayoutManager(layoutManager);

        Toast.makeText(this,"size "+list_temporary.getListAnggotaRtm_Edit().size(),Toast.LENGTH_LONG).show();

        editKepalaRtmAdapter = new Edit_Kepala_Rtm_Adapter(this,list_temporary.getListAnggotaRtm_Edit());
        rvEditKepalaRtm.setAdapter(editKepalaRtmAdapter);


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
//            list_temporary.listAnggotaRtm_Edit.clear();
//            crud.delete_rtm_by_id(list_temporary.id_penduduk);
//            crud.updateData_tweb_penduduk_id_rtm("0",list_temporary.nik,list_temporary.id_penduduk);
            startActivity(new Intent(Edit_Kepala_Rtm_Activity.this,Edit_Rtm_Activity.class));
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
                    for (int i=0;i<list_temporary.listAnggotaRtm_Edit.size();i++)
                    {
                        if (crud.updateData_tweb_penduduk_id_rtm("08" + crud.getData_tweb_rtm_id_kk(list_temporary.listAnggotaRtm_Edit.get(i).getId_kk()),
                                list_temporary.listAnggotaRtm_Edit.get(i).getNik(),list_temporary.listAnggotaRtm_Edit.get(i).getId()) > 0)
                        {
                            Ent_PkkCatatanKeluargaDetail ep = new Ent_PkkCatatanKeluargaDetail();

                            ep.setId_detail_cat(list_temporary.listAnggotaRtm_Edit.get(i).getId());
                            ep.setNik(list_temporary.listAnggotaRtm_Edit.get(i).getNik());
                            if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])) >= 0 && 5 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("1");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])) >= 6 && 17 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("2");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])) >= 18 && 30 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("3");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])) >= 31 && 64 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("4");
                            }
                            else if(getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                    Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])) >= 65 && 400 >=
                                    getAge(Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[0]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[1]),
                                            Integer.parseInt(list_temporary.listAnggotaRtm_Edit.get(i).getTanggallahir().split("-")[2])))
                            {
                                ep.setId_kelompok_umur("27");
                            }

                            if (crudPkk.update_pkk_catatan_keluarga_detail(Helper.ID_KELOMPOK_UMUR,ep.getId_kelompok_umur(),ep.getId_detail_cat()) > 0) {
                                Log.i("simpan", "sukses");
                            } else {
//                                Toast.makeText(this, "Gagal Simpan Data Pkk Catatan Keluarga Detail", Toast.LENGTH_LONG).show();
                            }


                            Toast.makeText(this, "Sukses Simpan Data dan update data", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, Edit_Kelompok_Dasawisma_Activity.class);
                            intent.putExtra("no_rtm","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                            startActivity(intent);
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
        list_temporary.listAnggotaRtm_Edit.clear();
        startActivity(new Intent(Edit_Kepala_Rtm_Activity.this,Edit_Rtm_Activity.class));
        Animatoo.animateFade(this);
        finish();
    }
}
