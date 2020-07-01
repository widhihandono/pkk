package com.supradesa.supradesa_pkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.shuhart.stepview.StepView;
import com.supradesa.supradesa_pkk.Adapter.Fasilitas_rtm_Adapter;
import com.supradesa.supradesa_pkk.Model.Fasilitas_Rtm;
import com.supradesa.supradesa_pkk.Model.Sub_Catatan_keluarga;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;
import com.travijuu.numberpicker.library.NumberPicker;

public class Fasilitas_Rtm_Activity extends AppCompatActivity {
Fasilitas_Rtm[] data;
Fasilitas_rtm_Adapter fasilitasRtmAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas_keluarga_);
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


        tvBack.setOnClickListener(l->{
            super.onBackPressed();
            Animatoo.animateFade(this);
        });

        tvSave.setOnClickListener(l->{

            if(number_picker_jamban.getValue() > 0)
            {
                crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,String.valueOf(number_picker_jamban.getValue()),"08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
            }
            else
            {
                crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,String.valueOf(0),"08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
            }

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
            startActivity(new Intent(Fasilitas_Rtm_Activity.this,MainActivity.class));
            Animatoo.animateFade(this);
            finish();
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

        fasilitasRtmAdapter = new Fasilitas_rtm_Adapter(this,data);
        rvFasilitasRtm.setAdapter(fasilitasRtmAdapter);

//        etJmlKriteriaRumah.setEnabled(false);
//        etJmlSumberAir.setEnabled(false);
//        etJmlMakananPokok.setEnabled(false);

        rbBeras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
//                    Toast.makeText(getApplicationContext(),String.valueOf(crudPkk.update_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Beras","08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk))),Toast.LENGTH_LONG).show();
                    crudPkk.Input_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Beras","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });

        rbNonBeras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
//                    Toast.makeText(getApplicationContext(),String.valueOf(crudPkk.update_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Non Beras","08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk))),Toast.LENGTH_LONG).show();
                    crudPkk.Input_pkk_data_keluarga(Helper.MAKANAN_POKOK,"Non Beras","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });

        rbPdam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"PDAM","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });

        rbSumur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"Sumur","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });

        rbSungai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"Sungai","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });

        rbLainnya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SUMBER_AIR,"Lainnya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });

        rbLayakHuni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KRITERIA_RUMAH,"Layak Huni","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });

        rbTidakLayakHuni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KRITERIA_RUMAH,"Tidak Layak Huni","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        onBackPressed();
    }
}
