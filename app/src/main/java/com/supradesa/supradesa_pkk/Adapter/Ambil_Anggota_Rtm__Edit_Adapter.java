package com.supradesa.supradesa_pkk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.List;

public class Ambil_Anggota_Rtm__Edit_Adapter extends RecyclerView.Adapter<Ambil_Anggota_Rtm__Edit_Adapter.Holder> {
private Context context;
private List<Ent_twebPenduduk> list_anggota_rtm;
List_Temporary list_temporary;
Crud crud;


    public Ambil_Anggota_Rtm__Edit_Adapter(Context context, List<Ent_twebPenduduk> list_anggota_rtm) {
        this.context = context;
        this.list_anggota_rtm = list_anggota_rtm;
        list_temporary = new List_Temporary();
        crud = new Crud(context);
    }

    @NonNull
    @Override
    public Ambil_Anggota_Rtm__Edit_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_pilih_kk,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Ambil_Anggota_Rtm__Edit_Adapter.Holder holder, int position) {
        holder.tvNik.setText(list_anggota_rtm.get(position).getTanggallahir());
        holder.tvNama.setText(list_anggota_rtm.get(position).getNama());

        holder.cb_list_pilih_kk.setChecked(true);
        holder.cb_list_pilih_kk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    list_temporary.listAnggotaRtm_Edit.add(list_anggota_rtm.get(position));
                }
                else
                {
                    list_temporary.listAnggotaRtm_Edit.remove(position);

//                    list_temporary.listAnggotaRtm.remove(list_anggota_rtm.get(position));
//                    list_temporary.listAnggotaRtm_Edit.remove(position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list_anggota_rtm.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvNik,tvNama;
        private CheckBox cb_list_pilih_kk;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvNik = itemView.findViewById(R.id.tvNik);
            tvNama = itemView.findViewById(R.id.tvNama);
            cb_list_pilih_kk = itemView.findViewById(R.id.cb_list_pilih_kk);
        }
    }
}
