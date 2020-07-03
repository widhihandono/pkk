package com.supradesa.supradesa_pkk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Edit.Edit_Rtm_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.ArrayList;
import java.util.List;

public class Data_Belum_Upload_Adapter extends RecyclerView.Adapter<Data_Belum_Upload_Adapter.Holder> implements Filterable {
private Context context;
private List<Ent_twebRtm> listRtm;
private Crud crud;
private List_Temporary list_temporary;
private List<Ent_twebRtm> filterList;

    public Data_Belum_Upload_Adapter(Context context, List<Ent_twebRtm> listRtm) {
        this.context = context;
        this.listRtm = listRtm;
        this.filterList = listRtm;
        this.crud = new Crud(context);
        list_temporary = new List_Temporary();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_data_belum_upload,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvNoRtm.setText(listRtm.get(position).getNo_kk());
        if(!listRtm.get(position).getNama().equals("") || listRtm.get(position).getNama() != null)
        {
            holder.tvNamaKepalaKeluarga.setText(listRtm.get(position).getNama());
        }
        else
        {
            holder.tvNamaKepalaKeluarga.setText("");
        }

        holder.tvJumlah.setText("Jumlah : "+crud.getData_tweb_penduduk_no_rtm(listRtm.get(position).getNo_kk()).size());

        holder.itemView.setOnClickListener(l->{
            list_temporary.listAnggotaRtm_Edit.addAll(crud.getData_tweb_penduduk(listRtm.get(position).getNo_kk()));
            Intent intent = new Intent(context, Edit_Rtm_Activity.class);
            intent.putExtra("no_kk",listRtm.get(position).getNo_kk());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            l.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listRtm.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String cari = constraint.toString();
                if(cari.isEmpty())
                {
                    listRtm = filterList;
                }
                else
                {
                    List<Ent_twebRtm> mListTwebRtm = new ArrayList<>();

                    for(Ent_twebRtm data:filterList)
                    {
                        if(data.getNama().toLowerCase().contains(cari))
                        {
                            mListTwebRtm.add(data);
                        }
                    }
                    listRtm = mListTwebRtm;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listRtm;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listRtm = (List<Ent_twebRtm>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvNoRtm,tvNamaKepalaKeluarga,tvJumlah;
        private ImageButton imgUpload,imgShow;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvNamaKepalaKeluarga = itemView.findViewById(R.id.tvNamaKepalaKeluarga);
            tvNoRtm = itemView.findViewById(R.id.tvNoRtm);
            imgUpload = itemView.findViewById(R.id.imgUpload);
            imgShow = itemView.findViewById(R.id.imgShow);
        }
    }
}
