package com.supradesa.supradesa_pkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.supradesa.supradesa_pkk.Adapter.Data_Belum_Upload_Adapter;
import com.supradesa.supradesa_pkk.SQLite.Crud;

public class Data_Belum_Upload_Activity extends AppCompatActivity {
private Data_Belum_Upload_Adapter data_belum_upload_adapter;
private RecyclerView.LayoutManager layoutManager;
private RecyclerView rvDataBelumUpload;
private Data_Belum_Upload_Adapter dataBelumUploadAdapter;
private Crud crud;
private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__belum__upload_);
        getSupportActionBar().hide();

        rvDataBelumUpload = findViewById(R.id.rvDataBelumUpload);
        layoutManager = new LinearLayoutManager(this);
        rvDataBelumUpload.setLayoutManager(layoutManager);
        crud = new Crud(this);
        tvBack = findViewById(R.id.tvBack);

        dataBelumUploadAdapter = new Data_Belum_Upload_Adapter(this,crud.getData_tweb_rtm());
        rvDataBelumUpload.setAdapter(dataBelumUploadAdapter);

//        Toast.makeText(this,crud.getData_tweb_rtm().get(0).getNo_kk(),Toast.LENGTH_LONG).show();
        tvBack.setOnClickListener(l->{
            startActivity(new Intent(Data_Belum_Upload_Activity.this,MainActivity.class));
            Animatoo.animateFade(this);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Data_Belum_Upload_Activity.this,MainActivity.class));
        Animatoo.animateFade(this);
        finish();
    }
}
