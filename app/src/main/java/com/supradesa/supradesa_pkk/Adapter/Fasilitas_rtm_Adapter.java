package com.supradesa.supradesa_pkk.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Model.Ent_PkkDataKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Fasilitas_Rtm;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.List;

public class Fasilitas_rtm_Adapter extends RecyclerView.Adapter<Fasilitas_rtm_Adapter.Holder>  {
    private Context context;
    private List<Ent_PkkDataKeluarga> list_fasilitas_rtm;
    private List_Temporary list_temporary;
    private Fasilitas_Rtm[] data;
    boolean status = false;
    Crud_pkk crudPkk;
    Crud crud;

    public Fasilitas_rtm_Adapter(Context context, Fasilitas_Rtm[] data) {
        this.context = context;
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


        holder.cbListCatatan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if(data[position].getNama().equals("Jamban"))
                    {

                        crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Tempat Sampah"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_TEMPAT_SAMPAH,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                    }
                    else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_SALURAN_PEMBUANGAN_AIR,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Stiker P4K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_STIKER_P4K,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Aktifitas UP2K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_UP2K,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                        crudPkk.Input_pkk_data_keluarga(Helper.JML_KEG_SEHAT_LINGKUNGAN,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Industri Rumah Tangga"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                }
                else
                {
//                    holder.etJml.setEnabled(false);
                    if(data[position].getNama().equals("Jamban"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Tempat Sampah"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Stiker P4K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Aktifitas UP2K"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                    }
                    else if(data[position].getNama().equals("Industri Rumah Tangga"))
                    {
                        crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
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
                    crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("Tempat Sampah"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("Stiker P4K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("Aktifitas UP2K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("Industri Rumah Tangga"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Tidak","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
            }
            else
            {
                holder.cbListCatatan.setChecked(true);
                status = true;

//                holder.etJml.setEnabled(true);
                if(data[position].getNama().equals("Jamban"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.JAMBAN,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_JAMBAN,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("Tempat Sampah"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.TEMPAT_SAMPAH,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_TEMPAT_SAMPAH,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));
                }
                else if (data[position].getNama().equals("Saluran Pembuangan Air"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.SALURAN_PEMBUANGAN_AIR,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_SALURAN_PEMBUANGAN_AIR,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));

                }
                else if(data[position].getNama().equals("Stiker P4K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.STIKER_P4K,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_STIKER_P4K,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));

                }
                else if(data[position].getNama().equals("Aktifitas UP2K"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.UP2K,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_UP2K,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));

                }
                else if(data[position].getNama().equals("Aktifitas Kegiatan Sehat Lingkungan"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.KEG_SEHAT_LINGKUNGAN,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
//                    crudPkk.Input_pkk_data_keluarga(Helper.JML_KEG_SEHAT_LINGKUNGAN,holder.etJml.getText().toString(),"08"+crud.getData_tweb_rtm_no_kk(list_temporary.id_kk));

                }
                else if(data[position].getNama().equals("PTP (Pemanfaatan Tanah Pekarangan)"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.PTP,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
                }
                else if(data[position].getNama().equals("Industri Rumah Tangga"))
                {
                    crudPkk.Input_pkk_data_keluarga(Helper.INDUSTRI_RT,"Ya","08"+crud.getData_tweb_rtm_id_kk(list_temporary.id_kk));
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
