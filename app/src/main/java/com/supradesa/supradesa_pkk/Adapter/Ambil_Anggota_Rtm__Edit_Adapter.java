package com.supradesa.supradesa_pkk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.supradesa.supradesa_pkk.Edit.Edit_Rtm_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Pemilihan_KK_Activity;
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

        if(holder.cb_list_pilih_kk.isChecked())
        {
            list_temporary.listAnggotaRtm_Edit_tampung.add(list_anggota_rtm.get(position));
        }

        holder.cb_list_pilih_kk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    list_temporary.listAnggotaRtm_Edit_tampung.add(list_anggota_rtm.get(position));
                }
                else
                {
                    list_temporary.list_nik.add(list_anggota_rtm.get(position).getNik());

                    list_temporary.listAnggotaRtm_Edit_tampung.remove(list_anggota_rtm.get(position));
//                    list_temporary.listAnggotaRtm.remove(list_anggota_rtm.get(position));
//                    list_temporary.listAnggotaRtm_Edit.remove(position);
                }
//                Toast.makeText(context,""+list_temporary.listAnggotaRtm_Edit.size(),Toast.LENGTH_LONG).show();

            }
        });

        holder.tvRemove.setOnClickListener(l->{
            list_temporary.list_nik.add(list_anggota_rtm.get(position).getNik());
            list_temporary.listAnggotaRtm_Edit_tampung.clear();
            list_temporary.listAnggotaRtm_Edit.remove(position);
            notifyDataSetChanged();
//            Intent intent = new Intent(context, Edit_Rtm_Activity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//            Animatoo.animateFade(context);
//            ((Activity)context).finish();

        });

    }

    @Override
    public int getItemCount() {
        return list_anggota_rtm.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvNik,tvNama,tvRemove;
        private CheckBox cb_list_pilih_kk;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvRemove = itemView.findViewById(R.id.tvRemove);
            tvNik = itemView.findViewById(R.id.tvNik);
            tvNama = itemView.findViewById(R.id.tvNama);
            cb_list_pilih_kk = itemView.findViewById(R.id.cb_list_pilih_kk);
        }
    }
}
