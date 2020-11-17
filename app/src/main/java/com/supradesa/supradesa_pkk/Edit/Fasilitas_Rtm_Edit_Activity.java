package com.supradesa.supradesa_pkk.Edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.shuhart.stepview.StepView;
import com.supradesa.supradesa_pkk.Adapter.Fasilitas_rtm_Adapter;
import com.supradesa.supradesa_pkk.Adapter.Fasilitas_rtm_Edit_Adapter;
import com.supradesa.supradesa_pkk.Fasilitas_Rtm_Activity;
import com.supradesa.supradesa_pkk.MainActivity;
import com.supradesa.supradesa_pkk.Model.Fasilitas_Rtm;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;
import com.travijuu.numberpicker.library.NumberPicker;

public class Fasilitas_Rtm_Edit_Activity extends AppCompatActivity {
    Fasilitas_Rtm[] data;
    Fasilitas_rtm_Edit_Adapter fasilitasRtmEditAdapter;
    private RecyclerView rvFasilitasRtm;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tvSave,tvBack;
    private RadioGroup rgKriteriaRumah,rgSumberAir,rgMakananPokok;
    private RadioButton rbBeras,rbNonBeras,rbPdam,
            rbSumur,rbSungai,rbLainnya,rbLayakHuni,rbTidakLayakHuni;
    List_Temporary list_temporary;
    Crud_pkk crudPkk;
    Crud crud;
    StepView stepView;
    private NumberPicker number_picker_jamban;
    String no_rtm="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas__rtm__edit_);
        getSupportActionBar().hide();

        crudPkk = new Crud_pkk(this);
        crud = new Crud(this);
        list_temporary = new List_Temporary();
        stepView = findViewById(R.id.step_view);
        tvBack = findViewById(R.id.tvBack);

        tvSave = findViewById(R.id.tvSave);
        rvFasilitasRtm = findViewById(R.id.rvFasilitasRtm);
        rgKriteriaRumah = findViewById(R.id.rgKriteriaRumah);
        rgMakananPokok = findViewById(R.id.rgMakananPokok);
        rgSumberAir = findViewById(R.id.rgSumberAir);
        rbBeras = findViewById(R.id.rbBeras);
        rbNonBeras = findViewById(R.id.rbNonBeras);
        rbPdam = findViewById(R.id.rbPdam);
        rbSumur = findViewById(R.id.rbSumur);
        rbSungai = findViewById(R.id.rbSungai);
        rbLainnya = findViewById(R.id.rbLainnya);
        rbLayakHuni = findViewById(R.id.rbLayakHuni);
        rbTidakLayakHuni = findViewById(R.id.rbTidakLayakHuni);
        number_picker_jamban = findViewById(R.id.number_picker_jamban);
//        etJmlMakananPokok = findViewById(R.id.etJmlMakananPokok);
//        etJmlSumberAir = findViewById(R.id.etJmlSumberAir);
        layoutManager = new LinearLayoutManager(this);
        rvFasilitasRtm.setLayoutManager(layoutManager);

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

        stepView.go(4,false);

        no_rtm = list_temporary.getNo_rtm();

        Toast.makeText(this,no_rtm,Toast.LENGTH_LONG).show();

        tvBack.setOnClickListener(l->{
            super.onBackPressed();
            Animatoo.animateFade(this);
        });

//        Toast.makeText(this,crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getMakanan_pokok(),Toast.LENGTH_LONG).show();
        if(!crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getMakanan_pokok().equals("") || !crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getMakanan_pokok().equals(null))
        {
            if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getMakanan_pokok().equals("Beras"))
            {
                rbBeras.setChecked(true);
            }
            else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getMakanan_pokok().equals("Non Beras"))
            {
                rbNonBeras.setChecked(true);
            }
        }


        if(!crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getSumber_air().equals("") || !crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getSumber_air().equals(null))
        {
            if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getSumber_air().equals("PDAM"))
            {
                rbPdam.setChecked(true);
            }
            else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getSumber_air().equals("Sumur"))
            {
                rbSumur.setChecked(true);
            }
            else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getSumber_air().equals("Sungai"))
            {
                rbSungai.setChecked(true);
            }
            else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getSumber_air().equals("Lainnya"))
            {
                rbLainnya.setChecked(true);
            }

        }

        if(!crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getKriteria_rumah().equals("") || !crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getKriteria_rumah().equals(null))
        {
            if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getKriteria_rumah().equals("Layak Huni"))
            {
                rbLayakHuni.setChecked(true);
            }
            else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getKriteria_rumah().equals("Tidak Layak Huni"))
            {
                rbTidakLayakHuni.setChecked(true);
            }
        }

        if((!crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getJamban().equals("") || !crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getJamban().equals(null)) && Integer.parseInt(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getJml_jamban()) > 0)
        {
            number_picker_jamban.setValue(Integer.parseInt(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getJml_jamban()));
//            rbBeras.setChecked(true);
        }
        else
        {
//            rbNonBeras.setChecked(true);
            number_picker_jamban.setValue(0);
        }


        tvSave.setOnClickListener(l->{
            if(rbBeras.isChecked() || rbNonBeras.isChecked())
            {
                if(rbPdam.isChecked() || rbSumur.isChecked() || rbSungai.isChecked() || rbLainnya.isChecked())
                {
                    if(rbLayakHuni.isChecked() || rbTidakLayakHuni.isChecked())
                    {
                        if(number_picker_jamban.getValue() > 0)
                        {
                            crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya",no_rtm);
                            crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,String.valueOf(number_picker_jamban.getValue()),no_rtm);
                        }
                        else
                        {
                            crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak",no_rtm);
                            crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,String.valueOf(0),no_rtm);
                        }

                        //Menghapus data berdasarkan nik yang sudah didapatkan dari Edit_Rtm_Activity yang di remove (list_temporary.list_nik)

                        if(list_temporary.list_nik.size() > 0)
                        {
                            for(int a=0;a<list_temporary.list_nik.size();a++)
                            {
                                crud.updateData_tweb_penduduk(list_temporary.list_nik.get(a),"ya",Helper.HAPUS_ID_RTM);
                                crudPkk.update_pkk_catatan_keluarga_detail(Helper.HAPUS,"ya","",list_temporary.list_nik.get(a));
                            }
                        }

                        list_temporary.listAllAnggota_sementara.clear();
                        list_temporary.list_no_kk.clear();
                        list_temporary.no_rtm = "";
                        list_temporary.listAnggotaRtm.clear();
                        list_temporary.listAllAnggota.clear();
                        list_temporary.dasawismaPosition = -1;
                        list_temporary.kepalaRtm = -1;
                        list_temporary.id_dasawisma = "";
                        list_temporary.id_penduduk = "";
                        list_temporary.id_kk = "";
                        list_temporary.nik = "";
                        list_temporary.listPenduduk_Detail.clear();
                        list_temporary.listSub.clear();
                        list_temporary.listAnggotaRtm_Edit_tampung.clear();
                        list_temporary.listCekAnggotaRtm.clear();
                        list_temporary.listAnggotaRtm_Edit.clear();
                        list_temporary.listAnggotaRtm_Edit_sementara.clear();
                        list_temporary.listAnggotaRtm_Edit_tampung.clear();
                        list_temporary.list_AmbilAnggotaRtm_Edit.clear();
                        list_temporary.listAllAnggota_edit_sementara.clear();
                        list_temporary.list_no_kk_edit.clear();
                        list_temporary.list_nik.clear();

                        list_temporary.id_rtm = "";
                        list_temporary.no_rtm_edit = "no";
                        list_temporary.kepalaRtm_edit = "";

                        crud.updateData_rtm(list_temporary.getNo_rtm_edit(),"no");
                        startActivity(new Intent(Fasilitas_Rtm_Edit_Activity.this, MainActivity.class));
                        Animatoo.animateFade(this);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Harus Memilih Salah Satu Kriteria Rumah !",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Harus Memilih Salah Satu Sumber Air !",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Harus Memilih Salah Satu Makanan Pokok !",Toast.LENGTH_LONG).show();
            }

        });


        data = new Fasilitas_Rtm[]
                {
                        new Fasilitas_Rtm("1","Tempat Sampah"),
                        new Fasilitas_Rtm("2","Saluran Pembuangan Air"),
                        new Fasilitas_Rtm("3","Stiker P4K"),
                        new Fasilitas_Rtm("4","Aktifitas UP2K"),
                        new Fasilitas_Rtm("5","Aktifitas Kegiatan Sehat Lingkungan"),
                        new Fasilitas_Rtm("6","PTP (Pemanfaatan Tanah Pekarangan)"),
                        new Fasilitas_Rtm("7","Industri Rumah Tangga")
                };

        fasilitasRtmEditAdapter = new Fasilitas_rtm_Edit_Adapter(this,data,no_rtm);
        rvFasilitasRtm.setAdapter(fasilitasRtmEditAdapter);

//        etJmlKriteriaRumah.setEnabled(false);
//        etJmlSumberAir.setEnabled(false);
//        etJmlMakananPokok.setEnabled(false);

        rbBeras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
//                    Toast.makeText(getApplicationContext(),String.valueOf(crudPkk.update_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Beras","08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk))),Toast.LENGTH_LONG).show();
                    crudPkk.Input_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Beras",no_rtm);

                }
            }
        });

        rbNonBeras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
//                    Toast.makeText(getApplicationContext(),String.valueOf(crudPkk.update_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Non Beras","08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk))),Toast.LENGTH_LONG).show();
                    crudPkk.Input_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Non Beras",no_rtm);

                }
            }
        });

        rbPdam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"PDAM",no_rtm);

                }
            }
        });

        rbSumur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"Sumur",no_rtm);

                }
            }
        });

        rbSungai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"Sungai",no_rtm);

                }
            }
        });

        rbLainnya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"Lainnya",no_rtm);

                }
            }
        });

        rbLayakHuni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KRITERIA_RUMAH,"Layak Huni",no_rtm);

                }
            }
        });

        rbTidakLayakHuni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KRITERIA_RUMAH,"Tidak Layak Huni",no_rtm);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }
}
