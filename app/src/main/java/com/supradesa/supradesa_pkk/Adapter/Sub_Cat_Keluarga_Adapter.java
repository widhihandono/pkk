package com.supradesa.supradesa_pkk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Sub_Catatan_keluarga;
import com.supradesa.supradesa_pkk.Pemilihan_KK_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

import java.util.Collections;
import java.util.List;

public class Sub_Cat_Keluarga_Adapter extends RecyclerView.Adapter<Sub_Cat_Keluarga_Adapter.Holder> {
private Context context;
private Sub_Catatan_keluarga[] data;
List_Temporary list_temporary;
int posisi;
Crud_pkk crudPkk;
Helper helper;

    public Sub_Cat_Keluarga_Adapter(Context context, Sub_Catatan_keluarga[] data,int posisi) {
        this.context = context;
        this.data = data;
        list_temporary = new List_Temporary();
        this.posisi = posisi;
        crudPkk = new Crud_pkk(context);
        helper = new Helper(context);
    }

    @NonNull
    @Override
    public Sub_Cat_Keluarga_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sub_list_cat_keluarga,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Sub_Cat_Keluarga_Adapter.Holder holder, int position) {
        holder.tvListCatatan.setText(data[position].getNama());
        if(data[position].getId().equals("10"))
        {
            holder.cbListCatatan.setVisibility(View.INVISIBLE);
            holder.tvListCatatan.setTypeface(null, Typeface.BOLD);
            holder.tvListCatatan.setTextSize(16);
        }
        if(holder.cbListCatatan.isChecked())
        {
            if(data[position].getNama().equals("Gotong Royong"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Berkebutuhan Khusus"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pangan"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Sandang"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Kesehatan"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Perencanaan Sehat"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Usia Subur"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Ibu Hamil"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Menyusui"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Nifas"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Buta Baca"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Buta Tulis"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Buta Hitung"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Stunting"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.STUNTING,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
        }
        else
        {
            if(data[position].getNama().equals("Gotong Royong"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Berkebutuhan Khusus"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Pangan"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Sandang"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Kesehatan"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Perencanaan Sehat"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Usia Subur"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Ibu Hamil"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Menyusui"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Nifas"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Buta Baca"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Buta Tulis"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Buta Hitung"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
            else if(data[position].getNama().equals("Stunting"))
            {
                crudPkk.update_pkk_catatan_keluarga_detail(helper.STUNTING,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
            }
        }

        holder.cbListCatatan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
//                    list_temporary.getListSub().add(data[position]);
                    if(data[position].getNama().equals("Gotong Royong"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Berkebutuhan Khusus"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pangan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Sandang"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Kesehatan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Perencanaan Sehat"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"1",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Usia Subur"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Ibu Hamil"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Menyusui"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Nifas"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Baca"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Tulis"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Hitung"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Stunting"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.STUNTING,"ya",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }


//                    Toast.makeText(context,list_temporary.getListPenduduk().get(posisi).getNama(),Toast.LENGTH_LONG).show();
                }
                else
                {

                    if(data[position].getNama().equals("Gotong Royong"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Berkebutuhan Khusus"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pangan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Sandang"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Kesehatan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Perencanaan Sehat"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"0",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Usia Subur"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Ibu Hamil"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Menyusui"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Nifas"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Baca"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Tulis"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Hitung"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Stunting"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.STUNTING,"tidak",list_temporary.getListAnggotaRtm().get(posisi).getId());
                    }
//                    list_temporary.getListSub().remove(data[position]);
//                    Toast.makeText(context,data[position].getNama(),Toast.LENGTH_LONG).show();
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
