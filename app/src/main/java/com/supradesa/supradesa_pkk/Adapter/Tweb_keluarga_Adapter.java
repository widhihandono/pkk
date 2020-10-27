package com.supradesa.supradesa_pkk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Pemilihan_KK_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.ArrayList;
import java.util.List;

public class Tweb_keluarga_Adapter extends RecyclerView.Adapter<Tweb_keluarga_Adapter.Holder> implements Filterable {
private Context context;
private List<Ent_twebKeluarga> list_twebKeluarga;
List_Temporary list_temporary;
Crud crud;
private List<Ent_twebKeluarga> filterList;
boolean status;

    public Tweb_keluarga_Adapter(Context context,List<Ent_twebKeluarga> list_twebKeluarga) {
        this.context = context;
        this.list_twebKeluarga = list_twebKeluarga;
        this.filterList = list_twebKeluarga;
        list_temporary = new List_Temporary();
        crud = new Crud(context);
        status = false;
    }

    @NonNull
    @Override
    public Tweb_keluarga_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_cari_kk,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Tweb_keluarga_Adapter.Holder holder, int position) {
        holder.tvNo_kk.setText(list_twebKeluarga.get(position).getNama()); //no_kk / nama Kepala Keluarga
        holder.tvRtRw.setText("RT "+list_twebKeluarga.get(position).getRt()+"/ RW "+list_twebKeluarga.get(position).getRw());
        holder.itemView.setOnClickListener(l->{

            list_temporary.listAllAnggota_sementara.addAll(crud.getData_tweb_penduduk_id_kk(list_twebKeluarga.get(position).getId()));
            list_temporary.list_no_kk.add(list_twebKeluarga.get(position).getId());

            Intent intent = new Intent(context,Pemilihan_KK_Activity.class);
            intent.putExtra("id_kk",list_twebKeluarga.get(position).getId());
            intent.putExtra("no_kk",list_twebKeluarga.get(position).getNo_kk());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            l.getContext().startActivity(intent);

        });

        if(list_temporary.list_no_kk.size() != 0 )
        {
            for(int a=0;a<list_temporary.list_no_kk.size();a++)
            {
                if(list_temporary.list_no_kk.get(a).equals(list_twebKeluarga.get(position).getId()))
                {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGray));
//                    holder.itemView.setEnabled(false);
                }
            }
        }
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
        private TextView tvNo_kk,tvRtRw;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvRtRw = itemView.findViewById(R.id.tvRtRw);
            tvNo_kk = itemView.findViewById(R.id.tvNo_kk);
        }
    }
}
