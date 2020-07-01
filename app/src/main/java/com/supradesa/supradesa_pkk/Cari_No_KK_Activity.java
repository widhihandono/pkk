package com.supradesa.supradesa_pkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.supradesa.supradesa_pkk.Adapter.Tweb_keluarga_Adapter;
import com.supradesa.supradesa_pkk.SQLite.Crud;

public class Cari_No_KK_Activity extends AppCompatActivity {
private Crud crud;
private RecyclerView rvCariNoKk;
private RecyclerView.LayoutManager layoutManager;
private Tweb_keluarga_Adapter keluarga_adapter;
private EditText etCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari__no__kk_);
        getSupportActionBar().hide();

        crud = new Crud(this);
        rvCariNoKk = findViewById(R.id.rvCariNoKk);
        layoutManager = new LinearLayoutManager(this);
        rvCariNoKk.setLayoutManager(layoutManager);
        etCari = findViewById(R.id.etCari);

        keluarga_adapter = new Tweb_keluarga_Adapter(this,crud.getData_tweb_keluarga());
        rvCariNoKk.setAdapter(keluarga_adapter);

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keluarga_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Pemilihan_KK_Activity.class);
        intent.putExtra("id_kk","0");
        startActivity(intent);
        finish();
    }
}
