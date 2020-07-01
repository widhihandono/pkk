package com.supradesa.supradesa_pkk.Edit;

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
import com.supradesa.supradesa_pkk.Adapter.Catatan_Keluarga_Edit_Adapter;
import com.supradesa.supradesa_pkk.Adapter.KelompokDasawisma_Adapter;
import com.supradesa.supradesa_pkk.Adapter.KelompokDasawisma_Edit_Adapter;
import com.supradesa.supradesa_pkk.Catatan_Keluarga_Activity;
import com.supradesa.supradesa_pkk.Kepala_Rtm_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

public class Edit_Kelompok_Dasawisma_Activity extends AppCompatActivity {
    private RecyclerView rvKelompokDasawisma;
    private KelompokDasawisma_Edit_Adapter kelompokDasawisma_edit_adapter;
    private List_Temporary list_temporary;
    private Crud_pkk crudPkk;
    private Crud crud;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tvNext,tvBack,tvBackEditKepalaRtm;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__kelompok__dasawisma_);
        getSupportActionBar().hide();

        list_temporary = new List_Temporary();
        helper = new Helper(this);
        crud = new Crud(this);
        rvKelompokDasawisma = findViewById(R.id.rvKelompokDasawisma);
        tvNext = findViewById(R.id.tvNext);
        tvBack = findViewById(R.id.tvBack);
        tvBackEditKepalaRtm = findViewById(R.id.tvBackEditKepalaRtm);
        crudPkk = new Crud_pkk(this);
        layoutManager = new LinearLayoutManager(this);

        rvKelompokDasawisma.setLayoutManager(layoutManager);
        kelompokDasawisma_edit_adapter = new KelompokDasawisma_Edit_Adapter(this,crudPkk.getPkk_dasa_wisma(),getIntent().getExtras().getString("no_rtm",""));
        rvKelompokDasawisma.setAdapter(kelompokDasawisma_edit_adapter);



        tvNext.setOnClickListener(l->{
            if(crudPkk.Input_kelompok_dasawisma(helper.ID_DASAWISMA,list_temporary.id_dasawisma,"08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk)) > 0)
            {
                Toast.makeText(getApplicationContext(),"Sukses Input",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Catatan_Keluarga_Edit_Activity.class);
                startActivity(intent);
                Animatoo.animateFade(this);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Gagal Input",Toast.LENGTH_LONG).show();
            }
        });

        tvBackEditKepalaRtm.setOnClickListener(l->{
            Intent intent = new Intent(this, Edit_Kepala_Rtm_Activity.class);
            startActivity(intent);
            Animatoo.animateFade(this);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Edit_Kepala_Rtm_Activity.class);
        startActivity(intent);
        Animatoo.animateFade(this);
        finish();
    }
}
