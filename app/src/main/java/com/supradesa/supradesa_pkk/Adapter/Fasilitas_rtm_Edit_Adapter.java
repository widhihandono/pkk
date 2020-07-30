package com.supradesa.supradesa_pkk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Model.Ent_PkkDataKeluarga;
import com.supradesa.supradesa_pkk.Model.Fasilitas_Rtm;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.List;

public class Fasilitas_rtm_Edit_Adapter extends RecyclerView.Adapter<Fasilitas_rtm_Edit_Adapter.Holder>  {
    private Context context;
    private List<Ent_PkkDataKeluarga> list_fasilitas_rtm;
    private List_Temporary list_temporary;
    private Fasilitas_Rtm[] data;
    boolean status = false;
    Crud_pkk crudPkk;
    Crud crud;
    String no_rtm="";

    public Fasilitas_rtm_Edit_Adapter(Context context, Fasilitas_Rtm[] data,String no_rtm) {
        this.context = context;
        this.no_rtm = no_rtm;
        this.data = data;
        this.crudPkk = new Crud_pkk(context);
        this.crud = new Crud(context);
        this.list_temporary = new List_Temporary();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_fasilitas_keluarga,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvListCatatan.setText(data[position].getNama());

        if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getTempat_sampah().equals("Ya") && data[position].getNama().equals("Tempat Sampah"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getSaluran_pembuangan_air().equals("Ya") && data[position].getNama().equals("Saluran Pembuangan Air"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getStiker_p4k().equals("Ya") && data[position].getNama().equals("Stiker P4K"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getUp2k().equals("Ya") && data[position].getNama().equals("Aktifitas UP2K"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getKeg_sehat_lingkungan().equals("Ya") && data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getPtp().equals("Ya") && data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(no_rtm).get(0).getIndustri_rt().equals("Ya") && data[position].getNama().equals("Industri Rumah Tangga"))
        {
            holder.cbListCatatan.setChecked(true);
        }

        holder.cbListCatatan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if(data[position].getNama().equals("Jamban"))
                    {

                        crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya",list_temporary.no_rtm);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Tempat Sampah"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Ya",list_temporary.no_rtm);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_TEMPAT_SAMPAH,holder.etJml.getText().toString(),list_temporary.no_rtm);
                    }
                    else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Ya",list_temporary.no_rtm);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_SALURAN_PEMBUANGAN_AIR,holder.etJml.getText().toString(),list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Stiker P4K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Ya",list_temporary.no_rtm);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_STIKER_P4K,holder.etJml.getText().toString(),list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Aktifitas UP2K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Ya",list_temporary.no_rtm);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_UP2K,holder.etJml.getText().toString(),list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Ya",list_temporary.no_rtm);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_KEG_SEHAT_LINGKUNGAN,holder.etJml.getText().toString(),list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Ya",list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Industri Rumah Tangga"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Ya",list_temporary.no_rtm);
                    }
                }
                else
                {
//                    holder.etJml.setEnabled(false);
                    if(data[position].getNama().equals("Jamban"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak",list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Tempat Sampah"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Tidak",list_temporary.no_rtm);
                    }
                    else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Tidak",list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Stiker P4K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Tidak",list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Aktifitas UP2K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Tidak",list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Tidak",list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Tidak",list_temporary.no_rtm);
                    }
                    else if(data[position].getNama().equals("Industri Rumah Tangga"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Tidak",list_temporary.no_rtm);
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(l->{
            if(status)
            {
                holder.cbListCatatan.setChecked(false);
                status = false;

                if(data[position].getNama().equals("Jamban"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak",list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("Tempat Sampah"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Tidak",list_temporary.no_rtm);
                }
                else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Tidak",list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("Stiker P4K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Tidak",list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("Aktifitas UP2K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Tidak",list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Tidak",list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Tidak",list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("Industri Rumah Tangga"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Tidak",list_temporary.no_rtm);
                }
            }
            else
            {
                holder.cbListCatatan.setChecked(true);
                status = true;

//                holder.etJml.setEnabled(true);
                if(data[position].getNama().equals("Jamban"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya",list_temporary.no_rtm);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,holder.etJml.getText().toString(),list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("Tempat Sampah"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Ya",list_temporary.no_rtm);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_TEMPAT_SAMPAH,holder.etJml.getText().toString(),list_temporary.no_rtm);
                }
                else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Ya",list_temporary.no_rtm);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_SALURAN_PEMBUANGAN_AIR,holder.etJml.getText().toString(),list_temporary.no_rtm);

                }
                else if(data[position].getNama().equals("Stiker P4K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Ya",list_temporary.no_rtm);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_STIKER_P4K,holder.etJml.getText().toString(),list_temporary.no_rtm);

                }
                else if(data[position].getNama().equals("Aktifitas UP2K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Ya",list_temporary.no_rtm);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_UP2K,holder.etJml.getText().toString(),list_temporary.no_rtm);

                }
                else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Ya",list_temporary.no_rtm);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_KEG_SEHAT_LINGKUNGAN,holder.etJml.getText().toString(),list_temporary.no_rtm);

                }
                else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Ya",list_temporary.no_rtm);
                }
                else if(data[position].getNama().equals("Industri Rumah Tangga"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Ya",list_temporary.no_rtm);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvListCatatan;
        private CheckBox cbListCatatan;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tvListCatatan = itemView.findViewById(R.id.tvListCatatan);
            cbListCatatan = itemView.findViewById(R.id.cbListCatatan);

        }
    }
}
