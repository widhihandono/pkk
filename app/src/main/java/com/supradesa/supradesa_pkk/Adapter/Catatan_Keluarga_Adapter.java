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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Catatan_Keluarga_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Sub_Catatan_keluarga;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.List;

public class Catatan_Keluarga_Adapter extends RecyclerView.Adapter<Catatan_Keluarga_Adapter.Holder> {
private Context context;
private List<Ent_twebPenduduk> list_anggota_rtm;
List_Temporary list_temporary;
Sub_Cat_Keluarga_Adapter subCatKeluargaAdapter;
Sub_Catatan_keluarga[] data;
boolean status;


    public Catatan_Keluarga_Adapter(Context context, List<Ent_twebPenduduk> list_anggota_rtm) {
        this.context = context;
        this.list_anggota_rtm = list_anggota_rtm;
        list_temporary = new List_Temporary();
        status = false;
        data = new Sub_Catatan_keluarga[]
                {
                        new Sub_Catatan_keluarga("1","Berkebutuhan Khusus"),
                        new Sub_Catatan_keluarga("2","Usia Subur"),
                        new Sub_Catatan_keluarga("3","Ibu Hamil"),
                        new Sub_Catatan_keluarga("4","Menyusui"),
                        new Sub_Catatan_keluarga("5","Nifas"),
                        new Sub_Catatan_keluarga("6","Buta Baca"),
                        new Sub_Catatan_keluarga("7","Buta Tulis"),
                        new Sub_Catatan_keluarga("8","Buta Hitung"),
                        new Sub_Catatan_keluarga("9","Stunting"),
                        new Sub_Catatan_keluarga("10","KEGIATAN PKK YANG DIIKUTI"),
                        new Sub_Catatan_keluarga("11","Pengahayatan dan Pengamalan Pancasila"),
                        new Sub_Catatan_keluarga("12","Gotong Royong"),
                        new Sub_Catatan_keluarga("13","Pendidikan dan Keterampilan"),
                        new Sub_Catatan_keluarga("14","Pengembangan Kehidupan Berkoperasi"),
                        new Sub_Catatan_keluarga("15","Pangan"),
                        new Sub_Catatan_keluarga("16","Sandang"),
                        new Sub_Catatan_keluarga("17","Kesehatan"),
                        new Sub_Catatan_keluarga("18","Perencanaan Sehat"),
                };
    }

    @NonNull
    @Override
    public Catatan_Keluarga_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_catatan_keluarga,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Catatan_Keluarga_Adapter.Holder holder, int position) {
        holder.tvNik.setText(list_anggota_rtm.get(position).getNik());
        holder.tvNama.setText(list_anggota_rtm.get(position).getNama());

        holder.itemView.setOnClickListener(l->{
            if(status == false)
            {
                holder.rvList.setVisibility(View.VISIBLE);
                subCatKeluargaAdapter = new Sub_Cat_Keluarga_Adapter(context,data,position);
                holder.rvList.setAdapter(subCatKeluargaAdapter);
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
                subCatKeluargaAdapter = new Sub_Cat_Keluarga_Adapter(context,data,position);
                holder.rvList.setAdapter(subCatKeluargaAdapter);
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
            l.getContext().startActivity(new Intent(context, Catatan_Keluarga_Activity.class));
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
