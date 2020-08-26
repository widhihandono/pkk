package com.supradesa.supradesa_pkk.Edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.shuhart.stepview.StepView;
import com.supradesa.supradesa_pkk.Adapter.Ambil_Anggota_Rtm_Adapter;
import com.supradesa.supradesa_pkk.Adapter.Ambil_Anggota_Rtm__Edit_Adapter;
import com.supradesa.supradesa_pkk.Adapter.Ambil_Anggota_Rtm__Edit_Adapter_1;
import com.supradesa.supradesa_pkk.Cari_No_KK_Activity;
import com.supradesa.supradesa_pkk.Data_Belum_Upload_Activity;
import com.supradesa.supradesa_pkk.Kepala_Rtm_Activity;
import com.supradesa.supradesa_pkk.MainActivity;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Pemilihan_KK_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.ArrayList;
import java.util.List;

public class Edit_Rtm_Activity extends AppCompatActivity {
    private TextView tvTambahKK,tvNext,tvBack;
    private RecyclerView rvPemilihanAnggota,rvPemilihanAnggota_baru;
    private RecyclerView.LayoutManager layoutManager,layoutManager_1;
    private Crud crud;
    Ambil_Anggota_Rtm__Edit_Adapter anggota_rtm_edit_adapter;
    Ambil_Anggota_Rtm__Edit_Adapter_1 anggota_rtm_edit_adapter_1;
    List_Temporary list_temporary;
    List<Ent_twebPenduduk> listAnggotaRtm_sementara = new ArrayList<>();
    StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__rtm_);

        getSupportActionBar().hide();

        crud = new Crud(this);
        list_temporary = new List_Temporary();

        stepView = findViewById(R.id.step_view);
        tvTambahKK = findViewById(R.id.tvTambahKK);
        tvNext = findViewById(R.id.tvNext);
        tvBack = findViewById(R.id.tvBack);
        rvPemilihanAnggota = findViewById(R.id.rvPemilihanAnggota);
        rvPemilihanAnggota_baru = findViewById(R.id.rvPemilihanAnggota_baru);
        layoutManager = new LinearLayoutManager(this);
        layoutManager_1 = new LinearLayoutManager(this);

        rvPemilihanAnggota.setLayoutManager(layoutManager);
        rvPemilihanAnggota_baru.setLayoutManager(layoutManager_1);

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

        stepView.go(0,false);


        anggota_rtm_edit_adapter = new Ambil_Anggota_Rtm__Edit_Adapter(this,list_temporary.listAnggotaRtm_Edit);
        rvPemilihanAnggota.setAdapter(anggota_rtm_edit_adapter);

            anggota_rtm_edit_adapter_1 = new Ambil_Anggota_Rtm__Edit_Adapter_1(this,list_temporary.list_AmbilAnggotaRtm_Edit);
            rvPemilihanAnggota_baru.setAdapter(anggota_rtm_edit_adapter_1);



        //nanti bisa menggunakan size dari anggotaRtm_edit. jika size lebih dari anggota_Rtm_Edit, maka lebihnya tidak di ceklis


        tvTambahKK.setOnClickListener(l->{
            if(list_temporary.list_AmbilAnggotaRtm_Edit.size() > 0)
            {
                list_temporary.list_AmbilAnggotaRtm_Edit.clear();
                startActivity(new Intent(Edit_Rtm_Activity.this, Edit_Cari_No_KK_Activity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(Edit_Rtm_Activity.this, Edit_Cari_No_KK_Activity.class));
                finish();
            }

        });

        tvNext.setOnClickListener(l->{
            list_temporary.listAnggotaRtm_Edit.clear();
//            Toast.makeText(Edit_Rtm_Activity.this,""+list_temporary.listAnggotaRtm_Edit_tampung.size(),Toast.LENGTH_LONG).show();
            list_temporary.listAnggotaRtm_Edit.addAll(list_temporary.listAnggotaRtm_Edit_tampung);
            list_temporary.listAnggotaRtm_Edit_tampung.clear();
            Toast.makeText(this,"size "+list_temporary.getListAnggotaRtm_Edit().size(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(Edit_Rtm_Activity.this, Edit_Kepala_Rtm_Activity.class));
        });

        tvBack.setOnClickListener(l->{
//            list_temporary.listAnggotaRtm.clear();
            list_temporary.listAnggotaRtm_Edit.clear();
            list_temporary.list_AmbilAnggotaRtm_Edit.clear();
            list_temporary.listAllAnggota.clear();
            startActivity(new Intent(this, Data_Belum_Upload_Activity.class));
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        list_temporary.listAnggotaRtm_Edit.clear();
        list_temporary.list_AmbilAnggotaRtm_Edit.clear();
        list_temporary.listAllAnggota.clear();
        startActivity(new Intent(this, Data_Belum_Upload_Activity.class));
        finish();
    }
}
