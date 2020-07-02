package com.supradesa.supradesa_pkk.Edit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Adapter.Tweb_keluarga_Edit_Adapter;
import com.supradesa.supradesa_pkk.Pemilihan_KK_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;

public class Edit_Cari_No_KK_Activity extends AppCompatActivity {
private Crud crud;
private RecyclerView rvCariNoKk;
private RecyclerView.LayoutManager layoutManager;
private Tweb_keluarga_Edit_Adapter keluarga_edit_adapter;
private EditText etCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cari__no__kk_);
        getSupportActionBar().hide();

        crud = new Crud(this);
        rvCariNoKk = findViewById(R.id.rvCariNoKk);
        layoutManager = new LinearLayoutManager(this);
        rvCariNoKk.setLayoutManager(layoutManager);
        etCari = findViewById(R.id.etCari);

        keluarga_edit_adapter = new Tweb_keluarga_Edit_Adapter(this,crud.getData_tweb_keluarga());
        rvCariNoKk.setAdapter(keluarga_edit_adapter);

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keluarga_edit_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        Intent intent = new Intent(Edit_Cari_No_KK_Activity.this,Pemilihan_KK_Activity.class);
        startActivity(intent);
        return;
    }
}
