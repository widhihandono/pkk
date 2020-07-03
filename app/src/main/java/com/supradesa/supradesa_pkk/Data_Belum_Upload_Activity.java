package com.supradesa.supradesa_pkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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
private EditText etCari;

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
        etCari = findViewById(R.id.etCari);

        dataBelumUploadAdapter = new Data_Belum_Upload_Adapter(this,crud.getData_tweb_rtm_join_penduduk());
        rvDataBelumUpload.setAdapter(dataBelumUploadAdapter);

//        Toast.makeText(this,crud.getData_tweb_rtm().get(0).getNo_kk(),Toast.LENGTH_LONG).show();
        tvBack.setOnClickListener(l->{
            startActivity(new Intent(Data_Belum_Upload_Activity.this,MainActivity.class));
            Animatoo.animateFade(this);
            finish();
        });

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataBelumUploadAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Data_Belum_Upload_Activity.this,MainActivity.class));
        Animatoo.animateFade(this);
        finish();
    }

}
