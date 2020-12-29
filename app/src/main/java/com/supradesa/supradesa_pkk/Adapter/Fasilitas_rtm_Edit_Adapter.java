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
    String nik="";

    public Fasilitas_rtm_Edit_Adapter(Context context, Fasilitas_Rtm[] data,String nik) {
        this.context = context;
        this.nik = nik;
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

//        Toast.makeText(context,crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getStiker_p4k(),Toast.LENGTH_LONG).show();
        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getTempat_sampah() == null && data[position].getNama().equals("Tempat Sampah"))
        {
            crudPkk.update_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Tidak",list_temporary.nik);
        }
        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getSaluran_pembuangan_air() == null && data[position].getNama().equals("Saluran Pembuangan Air"))
        {
            crudPkk.update_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Tidak",list_temporary.nik);
        }
        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getStiker_p4k() == null && data[position].getNama().equals("Stiker P4K"))
        {
            crudPkk.update_pkk_data_keluarga(Helper.STIKER_P4K,"Tidak",list_temporary.nik);
        }
        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getUp2k() == null && data[position].getNama().equals("Aktifitas UP2K"))
        {
            crudPkk.update_pkk_data_keluarga(Helper.UP2K,"Tidak",list_temporary.nik);
        }
        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getKeg_sehat_lingkungan() == null && data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
        {
            crudPkk.update_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Tidak",list_temporary.nik);
        }
        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getPtp() == null && data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
        {
            crudPkk.update_pkk_data_keluarga(Helper.PTP,"Tidak",list_temporary.nik);
        }
        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getIndustri_rt() == null && data[position].getNama().equals("Industri Rumah Tangga"))
        {
            crudPkk.update_pkk_data_keluarga(Helper.INDUSTRI_RT,"Tidak",list_temporary.nik);
        }


        if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getTempat_sampah().equalsIgnoreCase("Ya") && data[position].getNama().equals("Tempat Sampah"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getSaluran_pembuangan_air().equalsIgnoreCase("Ya") && data[position].getNama().equals("Saluran Pembuangan Air"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getStiker_p4k().equalsIgnoreCase("Ya") && data[position].getNama().equals("Stiker P4K"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getUp2k().equalsIgnoreCase("Ya") && data[position].getNama().equals("Aktifitas UP2K"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getKeg_sehat_lingkungan().equalsIgnoreCase("Ya") && data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getPtp().equalsIgnoreCase("Ya") && data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getPkk_DataKeluarga_by_id(nik).get(0).getIndustri_rt().equalsIgnoreCase("Ya") && data[position].getNama().equals("Industri Rumah Tangga"))
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

                        crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya",list_temporary.nik);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Tempat Sampah"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Ya",list_temporary.nik);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_TEMPAT_SAMPAH,holder.etJml.getText().toString(),list_temporary.nik);
                    }
                    else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Ya",list_temporary.nik);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_SALURAN_PEMBUANGAN_AIR,holder.etJml.getText().toString(),list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Stiker P4K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Ya",list_temporary.nik);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_STIKER_P4K,holder.etJml.getText().toString(),list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Aktifitas UP2K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Ya",list_temporary.nik);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_UP2K,holder.etJml.getText().toString(),list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Ya",list_temporary.nik);
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_KEG_SEHAT_LINGKUNGAN,holder.etJml.getText().toString(),list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Ya",list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Industri Rumah Tangga"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Ya",list_temporary.nik);
                    }
                }
                else
                {
//                    holder.etJml.setEnabled(false);
                    if(data[position].getNama().equals("Jamban"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak",list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Tempat Sampah"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Tidak",list_temporary.nik);
                    }
                    else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Tidak",list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Stiker P4K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Tidak",list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Aktifitas UP2K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Tidak",list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Tidak",list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Tidak",list_temporary.nik);
                    }
                    else if(data[position].getNama().equals("Industri Rumah Tangga"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Tidak",list_temporary.nik);
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
                    crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak",list_temporary.nik);
                }
                else if(data[position].getNama().equals("Tempat Sampah"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Tidak",list_temporary.nik);
                }
                else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Tidak",list_temporary.nik);
                }
                else if(data[position].getNama().equals("Stiker P4K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Tidak",list_temporary.nik);
                }
                else if(data[position].getNama().equals("Aktifitas UP2K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Tidak",list_temporary.nik);
                }
                else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Tidak",list_temporary.nik);
                }
                else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Tidak",list_temporary.nik);
                }
                else if(data[position].getNama().equals("Industri Rumah Tangga"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Tidak",list_temporary.nik);
                }
            }
            else
            {
                holder.cbListCatatan.setChecked(true);
                status = true;

//                holder.etJml.setEnabled(true);
                if(data[position].getNama().equals("Jamban"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya",list_temporary.nik);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,holder.etJml.getText().toString(),list_temporary.nik);
                }
                else if(data[position].getNama().equals("Tempat Sampah"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Ya",list_temporary.nik);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_TEMPAT_SAMPAH,holder.etJml.getText().toString(),list_temporary.nik);
                }
                else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Ya",list_temporary.nik);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_SALURAN_PEMBUANGAN_AIR,holder.etJml.getText().toString(),list_temporary.nik);

                }
                else if(data[position].getNama().equals("Stiker P4K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Ya",list_temporary.nik);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_STIKER_P4K,holder.etJml.getText().toString(),list_temporary.nik);

                }
                else if(data[position].getNama().equals("Aktifitas UP2K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Ya",list_temporary.nik);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_UP2K,holder.etJml.getText().toString(),list_temporary.nik);

                }
                else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Ya",list_temporary.nik);
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_KEG_SEHAT_LINGKUNGAN,holder.etJml.getText().toString(),list_temporary.nik);

                }
                else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Ya",list_temporary.nik);
                }
                else if(data[position].getNama().equals("Industri Rumah Tangga"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Ya",list_temporary.nik);
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
