package com.supradesa.supradesa_pkk.Edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.supradesa.supradesa_pkk.Adapter.Cari_No_Rtm_Adapter;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;

public class Edit_Cari_No_Rtm_Activity extends AppCompatActivity {
    private Crud crud;
    private RecyclerView rvCariNamaKK;
    private RecyclerView.LayoutManager layoutManager;
    private Cari_No_Rtm_Adapter rtm_adapter;
    private EditText etCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__cari__no__rtm_);
        getSupportActionBar().hide();

        crud = new Crud(this);
        rvCariNamaKK = findViewById(R.id.rvCariNamaKK);
        layoutManager = new LinearLayoutManager(this);
        rvCariNamaKK.setLayoutManager(layoutManager);
        etCari = findViewById(R.id.etCari);

        rtm_adapter = new Cari_No_Rtm_Adapter(this,crud.getData_tweb_rtm_keluarga());
        rvCariNamaKK.setAdapter(rtm_adapter);

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rtm_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
