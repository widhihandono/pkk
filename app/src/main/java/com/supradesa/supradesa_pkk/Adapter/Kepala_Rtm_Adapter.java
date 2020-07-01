package com.supradesa.supradesa_pkk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Catatan_Keluarga_Activity;
import com.supradesa.supradesa_pkk.Kepala_Rtm_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.List;

public class Kepala_Rtm_Adapter extends RecyclerView.Adapter<Kepala_Rtm_Adapter.Holder> {
private Context context;
private List<Ent_twebPenduduk> list_anggota_rtm;
List_Temporary list_temporary;
    private RadioButton mSelectedRB;
    public int selectedPosition = -1;


    public Kepala_Rtm_Adapter(Context context, List<Ent_twebPenduduk> list_anggota_rtm) {
        this.context = context;
        this.list_anggota_rtm = list_anggota_rtm;
        list_temporary = new List_Temporary();
    }

    @NonNull
    @Override
    public Kepala_Rtm_Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pilih_kepala_rtm,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Kepala_Rtm_Adapter.Holder holder,final int position) {
        holder.tvNik.setText(list_anggota_rtm.get(position).getNik());
        holder.tvNama.setText(list_anggota_rtm.get(position).getNama());

        holder.rb_kepala_rtm.setOnClickListener(l->{
            list_temporary.kepalaRtm = position;
            selectedPosition = position;
            list_temporary.id_penduduk = list_anggota_rtm.get(position).getId();
            list_temporary.id_kk = list_anggota_rtm.get(position).getId_kk();
            list_temporary.nik = list_anggota_rtm.get(position).getNik();
//            Toast.makeText(context,"Selected Item : "+ list_temporary.kepalaRtm,Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
        });

        if(list_temporary.kepalaRtm != -1 && list_temporary.kepalaRtm == position)
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

        holder.tvRemoveKepalaRtm.setOnClickListener(l->{
            list_temporary.listAnggotaRtm.remove(position);
            l.getContext().startActivity(new Intent(context, Kepala_Rtm_Activity.class));
            ((Activity)context).finish();
        });


    }

    private void itemCheckChanged(View v)
    {
        selectedPosition = (Integer) v.getTag();
        Toast.makeText(context,"Selected Item : "+ selectedPosition,Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != list_anggota_rtm ? list_anggota_rtm.size() : 0);
    }

    public String getSelectedItem()
    {
        if(selectedPosition != -1)
        {
            Toast.makeText(context,"Selected Item : "+ list_anggota_rtm.get(selectedPosition).getNama(),Toast.LENGTH_LONG).show();
            return list_anggota_rtm.get(selectedPosition).getNama();
        }
        return "";
    }

    public void deleteSelectedPosition()
    {
        if(selectedPosition != -1)
        {
            list_anggota_rtm.remove(selectedPosition);
            selectedPosition = -1;
            notifyDataSetChanged();
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvNik,tvNama,tvRemoveKepalaRtm;
        private RadioButton rb_kepala_rtm;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvNik = itemView.findViewById(R.id.tvNik);
            tvNama = itemView.findViewById(R.id.tvNama);
            rb_kepala_rtm = itemView.findViewById(R.id.rb_kepala_rtm);
            tvRemoveKepalaRtm = itemView.findViewById(R.id.tvRemoveKepalaRtm);

        }
    }
}
