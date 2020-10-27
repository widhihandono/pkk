package com.supradesa.supradesa_pkk.Edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.shuhart.stepview.StepView;
import com.supradesa.supradesa_pkk.Adapter.Catatan_Keluarga_Adapter;
import com.supradesa.supradesa_pkk.Adapter.Catatan_Keluarga_Edit_Adapter;
import com.supradesa.supradesa_pkk.Fasilitas_Rtm_Activity;
import com.supradesa.supradesa_pkk.KelompokDasaWisma_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

public class Catatan_Keluarga_Edit_Activity extends AppCompatActivity {
    private RecyclerView rvCatatanKeluarga;
    private TextView tvBack,tvNext;
    private RecyclerView.LayoutManager layoutManager;
    Crud crud;
    List_Temporary list_temporary;
    Catatan_Keluarga_Edit_Adapter catatanKeluargaEditAdapter;
    StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan__keluarga__edit_);
        getSupportActionBar().hide();

        crud = new Crud(this);
        list_temporary = new List_Temporary();

        tvBack = findViewById(R.id.tvBack);
        tvNext = findViewById(R.id.tvNext);
        stepView = findViewById(R.id.step_view);

        rvCatatanKeluarga = findViewById(R.id.rvCatatanKeluarga);
        layoutManager = new LinearLayoutManager(this);
        rvCatatanKeluarga.setLayoutManager(layoutManager);

        catatanKeluargaEditAdapter = new Catatan_Keluarga_Edit_Adapter(this,list_temporary.getListAnggotaRtm_Edit());
        rvCatatanKeluarga.setAdapter(catatanKeluargaEditAdapter);


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

        stepView.go(3,false);


        tvNext.setOnClickListener(l->{
            crud.updateData_rtm(list_temporary.getNo_rtm_edit(),"no");
            Intent intent = new Intent(this, Fasilitas_Rtm_Edit_Activity.class);
            startActivity(intent);
            Animatoo.animateFade(this);

        });

        tvBack.setOnClickListener(l->{
            super.onBackPressed();
            Animatoo.animateFade(this);

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateFade(this);
    }

}
