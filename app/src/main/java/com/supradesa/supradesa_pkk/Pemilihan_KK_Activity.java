package com.supradesa.supradesa_pkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.shuhart.stepview.StepView;
import com.supradesa.supradesa_pkk.Adapter.Ambil_Anggota_Rtm_Adapter;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.ArrayList;

public class Pemilihan_KK_Activity extends AppCompatActivity {
private TextView tvTambahKK,tvNext,tvBack;
private RecyclerView rvPemilihanAnggota;
private RecyclerView.LayoutManager layoutManager;
private Crud crud;
Ambil_Anggota_Rtm_Adapter anggota_rtm_adapter;
List_Temporary list_temporary;
StepView stepView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilihan__kk_);
        getSupportActionBar().hide();

        crud = new Crud(this);
        list_temporary = new List_Temporary();

        tvTambahKK = findViewById(R.id.tvTambahKK);
        tvNext = findViewById(R.id.tvNext);
        tvBack = findViewById(R.id.tvBack);
        rvPemilihanAnggota = findViewById(R.id.rvPemilihanAnggota);
        layoutManager = new LinearLayoutManager(this);
        rvPemilihanAnggota.setLayoutManager(layoutManager);
        stepView = findViewById(R.id.step_view);

        list_temporary.listAllAnggota.addAll(list_temporary.listAllAnggota_sementara);



        for(int a=0;a<list_temporary.listAllAnggota.size();a++)
        {
            for(int i=0;i<list_temporary.listAllAnggota_sementara.size();i++)
            {
                if(!list_temporary.listAllAnggota.get(a).getId_kk().equals(list_temporary.listAllAnggota_sementara.get(i)))
                {

                }
            }

        }
        anggota_rtm_adapter = new Ambil_Anggota_Rtm_Adapter(this,list_temporary.listAllAnggota);
        rvPemilihanAnggota.setAdapter(anggota_rtm_adapter);

        list_temporary.listAllAnggota_sementara.clear();

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



        tvTambahKK.setOnClickListener(l->{
            startActivity(new Intent(Pemilihan_KK_Activity.this,Cari_No_KK_Activity.class));
            Animatoo.animateFade(this);
            finish();
        });

        tvNext.setOnClickListener(l->{
            startActivity(new Intent(Pemilihan_KK_Activity.this,Kepala_Rtm_Activity.class));
            Animatoo.animateFade(this);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
//        list_temporary.listAnggotaRtm.clear();
//        list_temporary.listAllAnggota.clear();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
