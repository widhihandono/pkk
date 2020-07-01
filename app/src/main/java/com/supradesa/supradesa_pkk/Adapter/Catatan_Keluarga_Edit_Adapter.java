package com.supradesa.supradesa_pkk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Catatan_Keluarga_Activity;
import com.supradesa.supradesa_pkk.Edit.Catatan_Keluarga_Edit_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Sub_Catatan_keluarga;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.List;

public class Catatan_Keluarga_Edit_Adapter extends RecyclerView.Adapter<Catatan_Keluarga_Edit_Adapter.Holder> {
private Context context;
private List<Ent_twebPenduduk> list_anggota_rtm;
List_Temporary list_temporary;
Sub_Cat_Keluarga_Edit_Adapter subCatKeluargaEditAdapter;
Sub_Catatan_keluarga[] data;
boolean status;


    public Catatan_Keluarga_Edit_Adapter(Context context, List<Ent_twebPenduduk> list_anggota_rtm) {
        this.context = context;
        this.list_anggota_rtm = list_anggota_rtm;
        list_temporary = new List_Temporary();
        status = false;
        data = new Sub_Catatan_keluarga[]
                {
                        new Sub_Catatan_keluarga("1","Berkebutuhan Khusus"),
                        new Sub_Catatan_keluarga("2","Pengahayatan dan Pengamalan Pancasila"),
                        new Sub_Catatan_keluarga("3","Gotong Royong"),
                        new Sub_Catatan_keluarga("4","Pendidikan dan Keterampilan"),
                        new Sub_Catatan_keluarga("5","Pengembangan Kehidupan Berkoperasi"),
                        new Sub_Catatan_keluarga("6","Pangan"),
                        new Sub_Catatan_keluarga("7","Sandang"),
                        new Sub_Catatan_keluarga("8","Kesehatan"),
                        new Sub_Catatan_keluarga("9","Perencanaan Sehat"),
                        new Sub_Catatan_keluarga("10","Usia Subur"),
                        new Sub_Catatan_keluarga("11","Ibu Hamil"),
                        new Sub_Catatan_keluarga("12","Menyusui"),
                        new Sub_Catatan_keluarga("13","Nifas"),
                        new Sub_Catatan_keluarga("14","Buta Baca"),
                        new Sub_Catatan_keluarga("15","Buta Tulis"),
                        new Sub_Catatan_keluarga("16","Buta Hitung")
                };
    }

    @NonNull
    @Override
    public Catatan_Keluarga_Edit_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_catatan_keluarga,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Catatan_Keluarga_Edit_Adapter.Holder holder, int position) {
        holder.tvNik.setText(list_anggota_rtm.get(position).getNik());
        holder.tvNama.setText(list_anggota_rtm.get(position).getNama());


        holder.itemView.setOnClickListener(l->{
            if(status == false)
            {
                holder.rvList.setVisibility(View.VISIBLE);
                subCatKeluargaEditAdapter = new Sub_Cat_Keluarga_Edit_Adapter(context,data,position,list_anggota_rtm.get(position).getNik());
                holder.rvList.setAdapter(subCatKeluargaEditAdapter);
                status = true;
            }
            else
            {
                holder.rvList.setVisibility(View.GONE);
                status = false;
            }
        });

        holder.tvDown.setOnClickListener(l->{
            if(status == false)
            {
                holder.rvList.setVisibility(View.VISIBLE);
                subCatKeluargaEditAdapter = new Sub_Cat_Keluarga_Edit_Adapter(context,data,position,list_anggota_rtm.get(position).getNik());
                holder.rvList.setAdapter(subCatKeluargaEditAdapter);
                status = true;
            }
            else
            {
                holder.rvList.setVisibility(View.GONE);
                status = false;
            }
        });

        holder.tvRemove.setOnClickListener(l->{
            list_temporary.listAnggotaRtm.remove(position);
            l.getContext().startActivity(new Intent(context, Catatan_Keluarga_Edit_Activity.class));
            ((Activity)context).finish();
        });


    }

    @Override
    public int getItemCount() {
        return list_anggota_rtm.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvNik,tvNama,tvDown,tvRemove;
        private RecyclerView rvList;
        private RecyclerView.LayoutManager layoutManager;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvNik = itemView.findViewById(R.id.tvNik);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvDown = itemView.findViewById(R.id.tvDown);
            rvList = itemView.findViewById(R.id.rvList);
            tvRemove = itemView.findViewById(R.id.tvRemove);
            layoutManager = new LinearLayoutManager(context);
            rvList.setLayoutManager(layoutManager);

        }
    }
}
