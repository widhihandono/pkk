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

import com.supradesa.supradesa_pkk.Model.Sub_Catatan_keluarga;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Util.List_Temporary;

public class Sub_Cat_Keluarga_Edit_Adapter extends RecyclerView.Adapter<Sub_Cat_Keluarga_Edit_Adapter.Holder> {
private Context context;
private Sub_Catatan_keluarga[] data;
List_Temporary list_temporary;
int posisi;
Crud_pkk crudPkk;
Helper helper;
String nik;

    public Sub_Cat_Keluarga_Edit_Adapter(Context context, Sub_Catatan_keluarga[] data, int posisi,String nik) {
        this.context = context;
        this.nik = nik;
        this.data = data;
        list_temporary = new List_Temporary();
        this.posisi = posisi;
        crudPkk = new Crud_pkk(context);
        helper = new Helper(context);
    }

    @NonNull
    @Override
    public Sub_Cat_Keluarga_Edit_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sub_list_cat_keluarga_edit,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Sub_Cat_Keluarga_Edit_Adapter.Holder holder, int position) {
        holder.tvListCatatan.setText(data[position].getNama());

//        Toast.makeText(context,"Size "+crudPkk.getData_pkk_catatan_keluarga_detail("1801214609890001").get(0).getBerkebutuhan_khusus()+","+
//                        crudPkk.getData_pkk_catatan_keluarga_detail("1801214609890001").get(0).getPenghayatan_dan_pengamalan_pancasila()+","+
//                        crudPkk.getData_pkk_catatan_keluarga_detail("1801214609890001").get(0).getGotong_royong()+","+
//                        crudPkk.getData_pkk_catatan_keluarga_detail("1801214609890001").get(0).getPendidikan_ketrampilan()+","+
//                        crudPkk.getData_pkk_catatan_keluarga_detail("1801214609890001").get(0).getPengembangan_kehidupan_berkoperasi()+","+
//                        crudPkk.getData_pkk_catatan_keluarga_detail("1801214609890001").get(0).getPangan()+","+
//                        crudPkk.getData_pkk_catatan_keluarga_detail("1801214609890001").get(0).getSandang(),Toast.LENGTH_LONG).show();
        if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getBerkebutuhan_khusus().equals("1") && data[position].getNama().equals("Berkebutuhan Khusus"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getPenghayatan_dan_pengamalan_pancasila().equals("1") && data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getGotong_royong().equals("1") && data[position].getNama().equals("Gotong Royong"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getPendidikan_ketrampilan().equals("1") && data[position].getNama().equals("Pendidikan dan Keterampilan"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getPengembangan_kehidupan_berkoperasi().equals("1") && data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getPangan().equals("1") && data[position].getNama().equals("Pangan"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getSandang().equals("1") && data[position].getNama().equals("Sandang"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getKesehatan().equals("1") && data[position].getNama().equals("Kesehatan"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getPerencanaan_sehat().equals("1") && data[position].getNama().equals("Perencanaan Sehat"))
        {
            holder.cbListCatatan.setChecked(true);//
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getUsia_subur().equals("ya") && data[position].getNama().equals("Usia Subur"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getIbu_hamil().equals("ya") && data[position].getNama().equals("Ibu Hamil"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getMenyusui().equals("ya") && data[position].getNama().equals("Menyusui"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getNifas().equals("ya") && data[position].getNama().equals("Nifas"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getButa_baca().equals("ya") && data[position].getNama().equals("Buta Baca"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getButa_tulis().equals("ya") && data[position].getNama().equals("Buta Tulis"))
        {
            holder.cbListCatatan.setChecked(true);
        }
        else if(crudPkk.getData_pkk_catatan_keluarga_detail(nik).get(0).getButa_hitung().equals("ya") && data[position].getNama().equals("Buta Hitung"))
        {
            holder.cbListCatatan.setChecked(true);
        }


//        if(holder.cbListCatatan.isChecked())
//        {
//            if(data[position].getNama().equals("Gotong Royong"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Berkebutuhan Khusus"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pangan"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Sandang"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Kesehatan"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Perencanaan Sehat"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Usia Subur"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Ibu Hamil"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Menyusui"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Nifas"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Buta Baca"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Buta Tulis"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Buta Hitung"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//        }
//        else
//        {
//            if(data[position].getNama().equals("Gotong Royong"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Berkebutuhan Khusus"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Pangan"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Sandang"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Kesehatan"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Perencanaan Sehat"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Usia Subur"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Ibu Hamil"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Menyusui"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Nifas"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Buta Baca"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Buta Tulis"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//            else if(data[position].getNama().equals("Buta Hitung"))
//            {
//                crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
//            }
//        }

        holder.cbListCatatan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
//                    list_temporary.getListSub().add(data[position]);
                    if(data[position].getNama().equals("Gotong Royong"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Berkebutuhan Khusus"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pangan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Sandang"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Kesehatan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Perencanaan Sehat"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"1",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Usia Subur"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Ibu Hamil"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Menyusui"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Nifas"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Baca"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Tulis"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Hitung"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"ya",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }


//                    Toast.makeText(context,list_temporary.getListPenduduk().get(posisi).getNama(),Toast.LENGTH_LONG).show();
                }
                else
                {

                    if(data[position].getNama().equals("Gotong Royong"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.GOTONG_ROYONG,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengahayatan dan Pengamalan Pancasila"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGHAYATAN_DAN_PENGAMALAN_PANCASILA,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Berkebutuhan Khusus"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BERKEBUTUHAN_KHUSUS,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pendidikan dan Keterampilan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENDIDIKAN_KETRAMPILAN,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pengembangan Kehidupan Berkoperasi"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PENGEMBANGAN_KEHIDUPAN_BERKOPERASI,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Pangan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PANGAN,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Sandang"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.SANDANG,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Kesehatan"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.KESEHATAN,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Perencanaan Sehat"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.PERENCANAAN_SEHAT,"0",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Usia Subur"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.USIA_SUBUR,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Ibu Hamil"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.IBU_HAMIL,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Menyusui"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.MENYUSUI,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Nifas"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.NIFAS,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Baca"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_BACA,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Tulis"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_TULIS,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
                    }
                    else if(data[position].getNama().equals("Buta Hitung"))
                    {
                        crudPkk.update_pkk_catatan_keluarga_detail(helper.BUTA_HITUNG,"tidak",list_temporary.getListAnggotaRtm_Edit().get(posisi).getId());
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
