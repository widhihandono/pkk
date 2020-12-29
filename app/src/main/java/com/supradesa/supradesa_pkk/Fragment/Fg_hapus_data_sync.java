package com.supradesa.supradesa_pkk.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.supradesa.supradesa_pkk.Adapter.Data_Belum_Upload_Adapter;
import com.supradesa.supradesa_pkk.MainActivity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fg_hapus_data_sync#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg_hapus_data_sync extends Fragment {
    private Data_Belum_Upload_Adapter data_belum_upload_adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvDataBelumUpload;
    private Data_Belum_Upload_Adapter dataBelumUploadAdapter;
    private Crud crud;
    private TextView tvBack;
    private EditText etCari;

    public Fg_hapus_data_sync() {
        // Required empty public constructor
    }

    public static Fg_hapus_data_sync newInstance(String param1, String param2) {
        Fg_hapus_data_sync fragment = new Fg_hapus_data_sync();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fg_hapus_data_sync, container, false);
        rvDataBelumUpload = view.findViewById(R.id.rvDataBelumUpload);
        layoutManager = new LinearLayoutManager(getContext());
        rvDataBelumUpload.setLayoutManager(layoutManager);
        crud = new Crud(getContext());
        tvBack = view.findViewById(R.id.tvBack);
        etCari = view.findViewById(R.id.etCari);

        dataBelumUploadAdapter = new Data_Belum_Upload_Adapter(getContext(),crud.getData_tweb_rtm_join_penduduk_hapus());
        rvDataBelumUpload.setAdapter(dataBelumUploadAdapter);

        //        Toast.makeText(this,crud.getData_tweb_rtm().get(0).getNo_kk(),Toast.LENGTH_LONG).show();
        tvBack.setOnClickListener(l->{
            startActivity(new Intent(getContext(), MainActivity.class));
            Animatoo.animateFade(getContext());
            getActivity().finish();
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
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}