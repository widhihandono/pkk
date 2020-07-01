package com.supradesa.supradesa_pkk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Edit.Edit_Rtm_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Pemilihan_KK_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.ArrayList;
import java.util.List;

public class Cari_No_Rtm_Adapter extends RecyclerView.Adapter<Cari_No_Rtm_Adapter.Holder> implements Filterable {
private Context context;
private List<Ent_twebKeluarga> list_twebKeluarga;
List_Temporary list_temporary;
Crud crud;
private List<Ent_twebKeluarga> filterList;

    public Cari_No_Rtm_Adapter(Context context, List<Ent_twebKeluarga> list_twebKeluarga) {
        this.context = context;
        this.list_twebKeluarga = list_twebKeluarga;
        this.filterList = list_twebKeluarga;
        list_temporary = new List_Temporary();
        crud = new Crud(context);
    }

    @NonNull
    @Override
    public Cari_No_Rtm_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_rtm,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Cari_No_Rtm_Adapter.Holder holder, int position) {
        holder.tvNikKepala.setText(list_twebKeluarga.get(position).getNo_kk()); //NO KK / NO RTM
        holder.tvNama.setText(list_twebKeluarga.get(position).getNama());
        holder.itemView.setOnClickListener(l->{
            list_temporary.listAnggotaRtm.addAll(crud.getData_tweb_penduduk_edit(list_twebKeluarga.get(position).getNo_kk()));
            list_temporary.listAnggotaRtm_Edit.addAll(crud.getData_tweb_penduduk_edit(list_twebKeluarga.get(position).getNo_kk()));
            Intent intent = new Intent(context, Edit_Rtm_Activity.class);
            intent.putExtra("id_kk",list_twebKeluarga.get(position).getId());
            intent.putExtra("no_kk",list_twebKeluarga.get(position).getNo_kk());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            l.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return list_twebKeluarga.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String cari = constraint.toString();
                if(cari.isEmpty())
                {
                    list_twebKeluarga = filterList;
                }
                else
                {
                    List<Ent_twebKeluarga> mListTwebKeluarga = new ArrayList<>();
                    for (Ent_twebKeluarga data : filterList)
                    {
                        if(data.getNama().toLowerCase().contains(cari))
                        {
                            mListTwebKeluarga.add(data);
                        }
                    }
                    list_twebKeluarga = mListTwebKeluarga;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list_twebKeluarga;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list_twebKeluarga = (List<Ent_twebKeluarga>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvNikKepala,tvNama;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvNikKepala = itemView.findViewById(R.id.tvNikKepala);
            tvNama = itemView.findViewById(R.id.tvNama);
        }
    }
}
