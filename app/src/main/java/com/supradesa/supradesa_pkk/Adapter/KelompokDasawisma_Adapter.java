package com.supradesa.supradesa_pkk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Kepala_Rtm_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDasaWisma;
import com.supradesa.supradesa_pkk.Model.Ent_PkkKelompokDasawisma;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.List;

public class KelompokDasawisma_Adapter extends RecyclerView.Adapter<KelompokDasawisma_Adapter.Holder> {
private Context context;
private List<Ent_PkkDasaWisma> list_kelompok_dasawisma;
List_Temporary list_temporary;
    private RadioButton mSelectedRB;
    public int selectedPosition = -1;


    public KelompokDasawisma_Adapter(Context context, List<Ent_PkkDasaWisma> list_kelompok_dasawisma) {
        this.context = context;
        this.list_kelompok_dasawisma = list_kelompok_dasawisma;
        list_temporary = new List_Temporary();
    }

    @NonNull
    @Override
    public KelompokDasawisma_Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pilih_dasawisma,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final KelompokDasawisma_Adapter.Holder holder, final int position) {
        holder.tvNama.setText(list_kelompok_dasawisma.get(position).getNama_dasa_wisma());

        holder.rb_kepala_rtm.setOnClickListener(l->{
            list_temporary.dasawismaPosition = position;
            selectedPosition = position;
            list_temporary.id_dasawisma = list_kelompok_dasawisma.get(position).getId_dasa_wisma();
//            Toast.makeText(context,"Selected Item : "+ selectedPosition,Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
        });

        if(list_temporary.dasawismaPosition != -1 && list_temporary.dasawismaPosition == position)
        {
            holder.rb_kepala_rtm.setChecked(true);
        }
        else
        {
            holder.rb_kepala_rtm.setChecked(selectedPosition == position);
        }


//        holder.rb_kepala_rtm.setTag(position);
//        holder.rb_kepala_rtm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemCheckChanged(v);
//
//            }
//        });



    }

    private void itemCheckChanged(View v)
    {
        selectedPosition = (Integer) v.getTag();
        Toast.makeText(context,"Selected Item : "+ selectedPosition,Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != list_kelompok_dasawisma ? list_kelompok_dasawisma.size() : 0);
    }

    public String getSelectedItem()
    {
        if(selectedPosition != -1)
        {
            Toast.makeText(context,"Selected Item : "+ list_kelompok_dasawisma.get(selectedPosition).getNama_dasa_wisma(),Toast.LENGTH_LONG).show();
            return list_kelompok_dasawisma.get(selectedPosition).getNama_dasa_wisma();
        }
        return "";
    }

    public void deleteSelectedPosition()
    {
        if(selectedPosition != -1)
        {
            list_kelompok_dasawisma.remove(selectedPosition);
            selectedPosition = -1;
            notifyDataSetChanged();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvNama;
        private RadioButton rb_kepala_rtm;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            rb_kepala_rtm = itemView.findViewById(R.id.rb_kepala_rtm);
        }
    }
}
