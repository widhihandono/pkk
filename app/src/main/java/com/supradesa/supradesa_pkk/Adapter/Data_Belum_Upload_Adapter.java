package com.supradesa.supradesa_pkk.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.supradesa.supradesa_pkk.Data_Belum_Upload_Activity;
import com.supradesa.supradesa_pkk.Edit.Edit_Rtm_Activity;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.SQLite.Crud;
import com.supradesa.supradesa_pkk.SQLite.Crud_pkk;
import com.supradesa.supradesa_pkk.SQLite.Helper;
import com.supradesa.supradesa_pkk.Upload_Data_Activity;
import com.supradesa.supradesa_pkk.Util.Get_Data_From_Server;
import com.supradesa.supradesa_pkk.Util.List_Temporary;
import com.supradesa.supradesa_pkk.Util.SharedPref;

import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Data_Belum_Upload_Adapter extends RecyclerView.Adapter<Data_Belum_Upload_Adapter.Holder> implements Filterable {
private Context context;
private List<Ent_twebRtm> listRtm;
private Crud crud;
private Crud_pkk crudPkk;
private List_Temporary list_temporary;
private List<Ent_twebRtm> filterList;
    public AlertDialog dialog = null; //Ganti
    SharedPref sharedPref;
    Get_Data_From_Server get_data_from_server;

    public Data_Belum_Upload_Adapter(Context context, List<Ent_twebRtm> listRtm) {
        this.context = context;
        this.listRtm = listRtm;
        this.filterList = listRtm;
        this.crud = new Crud(context);
        this.crudPkk = new Crud_pkk(context);
        list_temporary = new List_Temporary();
        sharedPref = new SharedPref(context);
        get_data_from_server = new Get_Data_From_Server(context);

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
        int size_data_rtm_by_rtm_level_1 = crud.getData_tweb_rtm_join_penduduk_rtm_level(listRtm.get(position).getNo_kk()).size();
        if(size_data_rtm_by_rtm_level_1 > 0)
        {
            holder.tvNamaKepalaKeluarga.setText(crud.getData_tweb_rtm_join_penduduk_rtm_level(listRtm.get(position).getNo_kk()).get(0).getNama());
        }
        else
        {
            holder.tvNamaKepalaKeluarga.setText("");
        }

        holder.tvJumlah.setText("Jumlah : "+crud.getData_tweb_penduduk_no_rtm(listRtm.get(position).getNo_kk()).size());

        holder.itemView.setOnClickListener(l->{
            list_temporary.listAnggotaRtm_Edit.addAll(crud.getData_tweb_penduduk(listRtm.get(position).getNo_kk()));
            list_temporary.no_rtm = listRtm.get(position).getNo_kk();
            list_temporary.id_rtm = listRtm.get(position).getId();
            list_temporary.no_rtm_edit = listRtm.get(position).getNo_kk();
            list_temporary.kepalaRtm_edit = listRtm.get(position).getNik_kepala();
            Intent intent = new Intent(context, Edit_Rtm_Activity.class);
            intent.putExtra("no_kk",listRtm.get(position).getNo_kk());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            l.getContext().startActivity(intent);
        });


        holder.imgUpload.setOnClickListener(l->{
            setProgressDialog();
//            Toast.makeText(context,""+crudPkk.getPkk_kelompok_dasa_wisma_no_rtm(listRtm.get(position).getNo_kk()).get(0).getNo_kk(),Toast.LENGTH_LONG).show();
            Upload_Rtm_Async upload = new Upload_Rtm_Async(context,sharedPref.sp.getString("kode_desa",""),
                    crud.getData_config_code().get(0).getKode_kecamatan(),crud.getData_config_code().get(0).getKode_kabupaten(),
                    listRtm.get(position).getNik_kepala(),
                    listRtm.get(position).getNo_kk(),listRtm.get(position).getTgl_daftar(),
                    listRtm.get(position).getKelas_sosial(),listRtm.get(position).getId(),
                    "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_rtm");


            try {
                upload.execute().get().getInt("response");
//                Toast.makeText(context,upload.get().getString("no_rtm"),Toast.LENGTH_LONG).show();
                if(upload.getResponse() == 1)
                {
                    crud.updateData_rtm(listRtm.get(position).getNo_kk(),"yes");
//                    Toast.makeText(context,listRtm.get(position).getNo_kk(),Toast.LENGTH_LONG).show();
                    crud.updateData_rtm_by_value(listRtm.get(position).getNo_kk(), Helper.NO_KK,upload.get().getString("no_rtm"));
                    for(int a=0;a<crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).size(); a++)
                    {
//                        if(crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getHapus_id_rtm().equals("ya"))
//                        {
////                            Toast.makeText(context,crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getNik(),Toast.LENGTH_LONG).show();
//                        }
//                        Toast.makeText(context,crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getNama(),Toast.LENGTH_LONG).show();
                        Upload_tweb_penduduk_Async upload_pdd = new Upload_tweb_penduduk_Async(context,sharedPref.sp.getString("kode_desa",""),
                                crud.getData_config_code().get(0).getKode_kecamatan(),crud.getData_config_code().get(0).getKode_kabupaten(),
                                crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getNama(),
                                crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getNik(),
                                crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getId_kk(),
                                crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getKk_level(),
                                upload.get().getString("no_rtm"),
                                crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getRtm_level(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getSex(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getTempatlahir(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getTanggallahir(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getAgama_id(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getPendidikan_kk_id(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getPekerjaan_id(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getStatus_kawin(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getId_cluster(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getAlamat_sekarang(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getCacat_id(),
                                        crud.getData_tweb_penduduk_by_id_rtm_and_rtm_level(listRtm.get(position).getNo_kk()).get(a).getHapus_id_rtm(),
                                        "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_tweb_penduduk");
                        upload_pdd.execute();


                    }

                    Upload_PkkKelompokDasawisma_Async upload_pkd = new Upload_PkkKelompokDasawisma_Async(context,sharedPref.sp.getString("kode_desa",""),
                            crud.getData_config_code().get(0).getKode_kecamatan(),crud.getData_config_code().get(0).getKode_kabupaten(),
                            upload.get().getString("no_rtm"),
                            crudPkk.getPkk_kelompok_dasa_wisma_no_rtm(listRtm.get(position).getNo_kk()).get(0).getId_dasa_wisma(),
                            "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_pkk_kelompok_dasawisma");
                    upload_pkd.execute();
                    crudPkk.update_pkk_kelompok_dasawisma(Helper.NO_KK,upload.get().getString("no_rtm"),listRtm.get(position).getNo_kk());

                    Upload_PkkDataKeluarga upload_dk = new Upload_PkkDataKeluarga(context, sharedPref.sp.getString("kode_desa", ""),crud.getData_config_code().get(0).getKode_kecamatan(),crud.getData_config_code().get(0).getKode_kabupaten(),
                            upload.get().getString("no_rtm"), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getMakanan_pokok(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_makanan_pokok(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJamban(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_jamban(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getSumber_air(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_sumber_air(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getTempat_sampah(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_tempat_sampah(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getSaluran_pembuangan_air(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_saluran_pembuangan_air(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getStiker_p4k(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_stiker_p4k(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getKriteria_rumah(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_kriteria_rumah(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getUp2k(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_up2k(), crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getKeg_sehat_lingkungan(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getJml_keg_sehat_lingkungan(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getPtp(),
                            crudPkk.getPkk_DataKeluarga_by_id(listRtm.get(position).getNo_kk()).get(0).getIndustri_rt(), "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_pkk_data_keluarga");
                    upload_dk.execute();
                    crudPkk.update_pkk_data_keluarga(Helper.NO_KK,upload.get().getString("no_rtm"),listRtm.get(position).getNo_kk());

                    for(int a=0 ; a < crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).size() ; a++)
                    {
                        Upload_pkk_catatan_keluarga_detail upload_ckd = new Upload_pkk_catatan_keluarga_detail(context,sharedPref.sp.getString("kode_desa",""),crud.getData_config_code().get(0).getKode_kecamatan(),crud.getData_config_code().get(0).getKode_kabupaten(),
                                crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getNik(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getBerkebutuhan_khusus(),
                                crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getPenghayatan_dan_pengamalan_pancasila(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getGotong_royong(),
                                crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getPendidikan_ketrampilan(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getPengembangan_kehidupan_berkoperasi()
                                ,crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getPangan(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getSandang()
                                ,crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getKesehatan(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getPerencanaan_sehat()
                                ,crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getId_kelompok_umur(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getUsia_subur()
                                ,crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getIbu_hamil(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getMenyusui()
                                ,crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getNifas(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getButa_baca()
                                ,crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getButa_tulis(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getButa_hitung(),
                                crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getId_detail_cat(),crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getStunting(),
                                crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getHapus(),
                                "https://pkk.magelangkab.go.id/Api_pkk_upload/upload_pkk_catatan_keluarga_detail");
                        upload_ckd.execute();

//                        if(crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getHapus().equals("ya"))
//                        {
//                            Toast.makeText(context,crudPkk.getData_pkk_catatan_keluarga_detail_by_no_rtm(listRtm.get(position).getNo_kk()).get(a).getNik(),Toast.LENGTH_LONG).show();
//                        }
                    }


//                    get_data_from_server.getAll();
                    notifyItemChanged(position);
                    notifyItemChanged(position,listRtm.size());
                    notifyDataSetChanged();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            context.startActivity(new Intent(context,Data_Belum_Upload_Activity.class));
            ((Activity) context).finish();

        });


        if(listRtm.get(position).getUpload().equals("sync"))
        {
            holder.imgUpload.setVisibility(View.INVISIBLE);
        }

        if(listRtm.get(position).getUpload().equals("yes"))
        {
            holder.imgUpload.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.young_blue));
        }

    }


    class Upload_Rtm_Async extends AsyncTask<String, String, JSONObject>
    {
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,kd_kecamatan,kd_kabupaten,nik_kepala,no_kk,tgl_daftar,kelas_sosial,id_rtm;
        int response;
        String no_rtm_fix;

        private Upload_Rtm_Async(Context context,String kd_desa,String kd_kecamatan,String kd_kabupaten,String nik_kepala,String no_kk,String tgl_daftar,String kelas_sosial,String id_rtm,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.kd_kecamatan = kd_kecamatan;
            this.kd_kabupaten = kd_kabupaten;
            this.nik_kepala = nik_kepala;
            this.no_kk = no_kk;
            this.tgl_daftar = tgl_daftar;
            this.kelas_sosial = kelas_sosial;
            this.id_rtm = id_rtm;
            this.response = 0;

            this.SERVER_PATH = SERVER_PATH;
        }

        public void setResponse(int response) {
            this.response = response;
        }
        public void setNo_rtm_fix(String no_rtm_fix) {
            this.no_rtm_fix = no_rtm_fix;
        }
        protected void onPreExecute()
        {
            super.onPreExecute();
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("Upload Data. Please wait . . .");
//            dialog.setIndeterminate(false);
//            dialog.setCancelable(false);
//            dialog.show();
        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("kd_kecamatan", kd_kecamatan);
                jsonObject.put("kd_kabupaten", kd_kabupaten);
                jsonObject.put("nik_kepala", nik_kepala);
                jsonObject.put("no_kk", no_kk);
                jsonObject.put("tgl_daftar", tgl_daftar);
                jsonObject.put("kelas_sosial", kelas_sosial);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

                String output;
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

//                    Log.v("Response","Kode : "+new JSONObject(response.toString()).getString("data"));
//                    Log.v("Response","Kode : "+new JSONObject(br.readLine()).getString("data"));
                json = new JSONObject(response.toString());

                setResponse(json.getInt("response"));
                Log.v("Response","Kode : "+json.getInt("response"));


                //String contentAsString = readIt(is,len);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.disconnect();
            }


            return json;
        }

        public String getNo_rtm_fix() {
            return no_rtm_fix;
        }

        public int getResponse() {
            return response;
        }

        protected void onPostExecute(JSONObject result)
        {
//            dialog.dismiss();
            //this assumes that the response looks like this:
            //{"success" : true }
            String message = null;
            try {
                message = result.getString("pesan");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            int success = 0;
            String no_rtm = "";
            try {
                success = result.getInt("response");
                no_rtm = result.getString("no_rtm");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
//                dialog.dismiss();
                setResponse(success);
                setNo_rtm_fix(no_rtm);


            }
            else if (success == 2) {
                setResponse(success);
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            }
            else
            {
                setResponse(success);
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }



//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    class Upload_tweb_penduduk_Async extends AsyncTask<String, String, JSONObject>
    {
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,kd_kecamatan,kd_kabupaten,nama,nik,id_kk,kk_level,id_rtm,rtm_level,sex,tempatlahir,tanggallahir,agama_id,
                pendidikan_kk_id,pekerjaan_id,status_kawin,id_cluster,alamat_sekarang,cacat_id,hapus_id_rtm;

        private Upload_tweb_penduduk_Async(Context context,String kd_desa,String kd_kecamatan,String kd_kabupaten,String nama,String nik,String id_kk,String kk_level,String id_rtm,
                                           String rtm_level,String sex,String tempatlahir,String tanggallahir,String agama_id,
                                           String pendidikan_kk_id,String pekerjaan_id,String status_kawin,String id_cluster,
                                           String alamat_sekarang,String cacat_id,String hapus_id_rtm,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.kd_kecamatan = kd_kecamatan;
            this.kd_kabupaten = kd_kabupaten;
            this.nama = nama;
            this.nik = nik;
            this.id_kk = id_kk;
            this.kk_level = kk_level;
            this.id_rtm = id_rtm;
            this.rtm_level = rtm_level;
            this.sex = sex;
            this.tempatlahir = tempatlahir;
            this.tanggallahir = tanggallahir;
            this.agama_id = agama_id;
            this.pendidikan_kk_id = pendidikan_kk_id;
            this.pekerjaan_id = pekerjaan_id;
            this.status_kawin = status_kawin;
            this.id_cluster = id_cluster;
            this.alamat_sekarang = alamat_sekarang;
            this.cacat_id = cacat_id;
            this.hapus_id_rtm = hapus_id_rtm;

            this.SERVER_PATH = SERVER_PATH;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("We are Logging in. Please wait . . .");
//            dialog.setIndeterminate(false);
//            dialog.setCancelable(false);
//            dialog.show();
        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("kd_kecamatan", kd_kecamatan);
                jsonObject.put("kd_kabupaten", kd_kabupaten);
                jsonObject.put("nama", nama);
                jsonObject.put("nik", nik);
                jsonObject.put("id_kk", id_kk);
                jsonObject.put("kk_level", kk_level);
                jsonObject.put("id_rtm", id_rtm);
                jsonObject.put("rtm_level", rtm_level);
                jsonObject.put("sex", sex);
                jsonObject.put("tempatlahir", tempatlahir);
                jsonObject.put("tanggallahir", tanggallahir);
                jsonObject.put("agama_id", agama_id);
                jsonObject.put("pendidikan_kk_id", pendidikan_kk_id);
                jsonObject.put("pekerjaan_id", pekerjaan_id);
                jsonObject.put("status_kawin", status_kawin);
                jsonObject.put("id_cluster", id_cluster);
                jsonObject.put("alamat_sekarang", alamat_sekarang);
                jsonObject.put("cacat_id", cacat_id);
                jsonObject.put("hapus_id_rtm", hapus_id_rtm);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

                String output;
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

//                    Log.v("Response","Kode : "+new JSONObject(response.toString()).getString("data"));
//                    Log.v("Response","Kode : "+new JSONObject(br.readLine()).getString("data"));
                json = new JSONObject(response.toString());


                Log.v("Response","Kode : "+json.getInt("response"));


                //String contentAsString = readIt(is,len);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return json;
        }

        protected void onPostExecute(JSONObject result)
        {
//            dialog.dismiss();
            //this assumes that the response looks like this:
            //{"success" : true }
            String message = null,no_rtm = "";
            try {
                message = result.getString("pesan");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            int success = 0;
            try {
                success = result.getInt("response");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
                crud.updateData_tweb_penduduk(nik,id_rtm,Helper.ID_RTM);
//                dialog.dismiss();
                Toast.makeText(context,"Sukses Kirim Data Penduduk",Toast.LENGTH_LONG).show();
                crud.updateData_tweb_penduduk_upload(nik,"yes");

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    class Upload_PkkKelompokDasawisma_Async extends AsyncTask<String, String, JSONObject>
    {
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,kd_kecamatan,kd_kabupaten,no_kk,id_dasa_wisma;

        private Upload_PkkKelompokDasawisma_Async(Context context,String kd_desa,String kd_kecamatan,String kd_kabupaten,String no_kk,String id_dasa_wisma,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.kd_kecamatan = kd_kecamatan;
            this.kd_kabupaten = kd_kabupaten;
            this.no_kk = no_kk;
            this.id_dasa_wisma = id_dasa_wisma;

            this.SERVER_PATH = SERVER_PATH;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("We are Logging in. Please wait . . .");
//            dialog.setIndeterminate(false);
//            dialog.setCancelable(false);
//            dialog.show();
        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("kd_kecamatan", kd_kecamatan);
                jsonObject.put("kd_kabupaten", kd_kabupaten);
                jsonObject.put("no_kk", no_kk);
                jsonObject.put("id_dasa_wisma", id_dasa_wisma);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

                String output;
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

//                    Log.v("Response","Kode : "+new JSONObject(response.toString()).getString("data"));
//                    Log.v("Response","Kode : "+new JSONObject(br.readLine()).getString("data"));
                json = new JSONObject(response.toString());


                Log.v("Response","Kode : "+json.getInt("response"));


                //String contentAsString = readIt(is,len);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return json;
        }

        protected void onPostExecute(JSONObject result)
        {
//            dialog.dismiss();
            //this assumes that the response looks like this:
            //{"success" : true }
            String message = null;
            try {
                message = result.getString("pesan");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            int success = 0;
            try {
                success = result.getInt("response");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
//                dialog.dismiss();
//                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                crudPkk.update_pkk_kelompok_dasawisma("upload","yes",no_kk);
            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    class Upload_PkkDataKeluarga extends AsyncTask<String, String, JSONObject>
    {
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,kd_kecamatan,kd_kabupaten,no_kk,makanan_pokok,jml_makanan_pokok,jamban,jml_jamban,sumber_air,jml_sumber_air,tempat_sampah,jml_tempat_sampah,
                saluran_pembuangan_air,jml_saluran_pembuangan_air,stiker_p4k,jml_stiker_p4k
                ,kriteria_rumah,jml_kriteria_rumah,up2k,jml_up2k,keg_sehat_lingkungan,jml_keg_sehat_lingkungan,ptp,Industri_rt;

        private Upload_PkkDataKeluarga(Context context,String kd_desa,String kd_kecamatan,String kd_kabupaten,String no_kk,String makanan_pokok,String jml_makanan_pokok
                ,String jamban,String jml_jamban,String sumber_air,String jml_sumber_air,String tempat_sampah,String jml_tempat_sampah,String saluran_pembuangan_air
                ,String jml_saluran_pembuangan_air,String stiker_p4k,String jml_stiker_p4k,String kriteria_rumah,String jml_kriteria_rumah,String up2k
                ,String jml_up2k,String keg_sehat_lingkungan,String jml_keg_sehat_lingkungan,String ptp,String Industri_rt,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.kd_kecamatan = kd_kecamatan;
            this.kd_kabupaten = kd_kabupaten;
            this.no_kk = no_kk;
            this.makanan_pokok = makanan_pokok;
            this.jml_makanan_pokok = jml_makanan_pokok;
            this.jamban = jamban;
            this.jml_jamban = jml_jamban;
            this.sumber_air = sumber_air;
            this.jml_sumber_air = jml_sumber_air;
            this.tempat_sampah = tempat_sampah;
            this.jml_tempat_sampah = jml_tempat_sampah;
            this.saluran_pembuangan_air = saluran_pembuangan_air;
            this.jml_saluran_pembuangan_air = jml_saluran_pembuangan_air;
            this.stiker_p4k = stiker_p4k;
            this.jml_stiker_p4k = jml_stiker_p4k;
            this.kriteria_rumah = kriteria_rumah;
            this.jml_kriteria_rumah = jml_kriteria_rumah;
            this.up2k = up2k;
            this.jml_up2k = jml_up2k;
            this.keg_sehat_lingkungan = keg_sehat_lingkungan;
            this.jml_keg_sehat_lingkungan = jml_keg_sehat_lingkungan;
            this.ptp = ptp;
            this.Industri_rt = Industri_rt;

            this.SERVER_PATH = SERVER_PATH;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("We are Logging in. Please wait . . .");
//            dialog.setIndeterminate(false);
//            dialog.setCancelable(false);
//            dialog.show();
        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("kd_kecamatan", kd_kecamatan);
                jsonObject.put("kd_kabupaten", kd_kabupaten);
                jsonObject.put("no_kk", no_kk);
                jsonObject.put("makanan_pokok", makanan_pokok);
                jsonObject.put("jml_makanan_pokok", jml_makanan_pokok);
                jsonObject.put("jamban", jamban);
                jsonObject.put("jml_jamban", jml_jamban);
                jsonObject.put("sumber_air", sumber_air);
                jsonObject.put("jml_sumber_air", jml_sumber_air);
                jsonObject.put("tempat_sampah", tempat_sampah);
                jsonObject.put("jml_tempat_sampah", jml_tempat_sampah);
                jsonObject.put("saluran_pembuangan_air", saluran_pembuangan_air);
                jsonObject.put("jml_saluran_pembuangan_air", jml_saluran_pembuangan_air);
                jsonObject.put("stiker_p4k", stiker_p4k);
                jsonObject.put("jml_stiker_p4k", jml_stiker_p4k);
                jsonObject.put("kriteria_rumah", kriteria_rumah);
                jsonObject.put("jml_kriteria_rumah", jml_kriteria_rumah);
                jsonObject.put("up2k", up2k);
                jsonObject.put("jml_up2k", jml_up2k);
                jsonObject.put("keg_sehat_lingkungan", keg_sehat_lingkungan);
                jsonObject.put("jml_keg_sehat_lingkungan", jml_keg_sehat_lingkungan);
                jsonObject.put("ptp", ptp);
                jsonObject.put("Industri_rt", Industri_rt);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

                String output;
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

//                    Log.v("Response","Kode : "+new JSONObject(response.toString()).getString("data"));
//                    Log.v("Response","Kode : "+new JSONObject(br.readLine()).getString("data"));
                json = new JSONObject(response.toString());


                Log.v("Response","Kode : "+json.getInt("response"));


                //String contentAsString = readIt(is,len);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return json;
        }

        protected void onPostExecute(JSONObject result)
        {
//            dialog.dismiss();
            //this assumes that the response looks like this:
            //{"success" : true }
            String message = null;
            try {
                message = result.getString("pesan");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            int success = 0;
            try {
                success = result.getInt("response");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
//                dialog.dismiss();
//                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                crudPkk.update_pkk_data_keluarga("upload","yes",no_kk);

            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    class Upload_pkk_catatan_keluarga_detail extends AsyncTask<String, String, JSONObject>
    {
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String kd_desa,kd_kecamatan,kd_kabupaten,nik,berkebutuhan_khusus,penghayatan_dan_pengamalan_pancasila,gotong_royong,pendidikan_ketrampilan,pengembangan_kehidupan_berkoperasi,pangan,
                sandang,kesehatan,perencanaan_sehat,id_kelompok_umur,usia_subur,ibu_hamil,
                menyusui,nifas,buta_baca,buta_tulis,buta_hitung,id_detail_cat,stunting,hapus;

        private Upload_pkk_catatan_keluarga_detail(Context context,String kd_desa,String kd_kecamatan,String kd_kabupaten,String nik,String berkebutuhan_khusus,String penghayatan_dan_pengamalan_pancasila
                ,String gotong_royong,String pendidikan_ketrampilan,String pengembangan_kehidupan_berkoperasi,String pangan,String sandang,String kesehatan,String perencanaan_sehat
                ,String id_kelompok_umur,String usia_subur,String ibu_hamil,String menyusui,String nifas,String buta_baca
                ,String buta_tulis,String buta_hitung,String id_detail_cat,String stunting,String hapus,String SERVER_PATH) {
            this.context = context;
            this.kd_desa = kd_desa;
            this.kd_kecamatan = kd_kecamatan;
            this.kd_kabupaten = kd_kabupaten;
            this.nik = nik;
            this.berkebutuhan_khusus = berkebutuhan_khusus;
            this.penghayatan_dan_pengamalan_pancasila = penghayatan_dan_pengamalan_pancasila;
            this.gotong_royong = gotong_royong;
            this.pendidikan_ketrampilan = pendidikan_ketrampilan;
            this.pengembangan_kehidupan_berkoperasi = pengembangan_kehidupan_berkoperasi;
            this.pangan = pangan;
            this.sandang = sandang;
            this.kesehatan = kesehatan;
            this.perencanaan_sehat = perencanaan_sehat;
            this.id_kelompok_umur = id_kelompok_umur;
            this.usia_subur = usia_subur;
            this.ibu_hamil = ibu_hamil;
            this.menyusui = menyusui;
            this.nifas = nifas;
            this.buta_baca = buta_baca;
            this.buta_tulis = buta_tulis;
            this.buta_hitung = buta_hitung;
            this.id_detail_cat = id_detail_cat;
            this.stunting = stunting;
            this.hapus = hapus;

            this.SERVER_PATH = SERVER_PATH;
        }


        protected void onPreExecute()
        {
            super.onPreExecute();
//            dialog = new ProgressDialog(context);
//            dialog.setMessage("We are Logging in. Please wait . . .");
//            dialog.setIndeterminate(false);
//            dialog.setCancelable(false);
//            dialog.show();
        }

        protected JSONObject doInBackground(String... args)
        {
            JSONObject json = null;
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL(SERVER_PATH);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kd_desa", kd_desa);
                jsonObject.put("kd_kecamatan", kd_kecamatan);
                jsonObject.put("kd_kabupaten", kd_kabupaten);
                jsonObject.put("nik", nik);
                jsonObject.put("berkebutuhan_khusus", berkebutuhan_khusus);
                jsonObject.put("penghayatan_dan_pengamalan_pancasila", penghayatan_dan_pengamalan_pancasila);
                jsonObject.put("gotong_royong", gotong_royong);
                jsonObject.put("pendidikan_ketrampilan", pendidikan_ketrampilan);
                jsonObject.put("pengembangan_kehidupan_berkoperasi", pengembangan_kehidupan_berkoperasi);
                jsonObject.put("pangan", pangan);
                jsonObject.put("sandang", sandang);
                jsonObject.put("kesehatan", kesehatan);
                jsonObject.put("perencanaan_sehat", perencanaan_sehat);
                jsonObject.put("id_kelompok_umur", id_kelompok_umur);
                jsonObject.put("usia_subur", usia_subur);
                jsonObject.put("ibu_hamil", ibu_hamil);
                jsonObject.put("menyusui", menyusui);
                jsonObject.put("nifas", nifas);
                jsonObject.put("buta_baca", buta_baca);
                jsonObject.put("buta_tulis", buta_tulis);
                jsonObject.put("buta_hitung", buta_hitung);
                jsonObject.put("stunting",stunting);
                jsonObject.put("hapus",hapus);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader( (conn.getInputStream())));

                String output;
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                {
                    response.append(line);
                }

//                    Log.v("Response","Kode : "+new JSONObject(response.toString()).getString("data"));
//                    Log.v("Response","Kode : "+new JSONObject(br.readLine()).getString("data"));
                json = new JSONObject(response.toString());


                Log.v("Response","Kode : "+json.getInt("response"));


                //String contentAsString = readIt(is,len);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return json;
        }

        protected void onPostExecute(JSONObject result)
        {
//            dialog.dismiss();
            //this assumes that the response looks like this:
            //{"success" : true }
            String message = null;
            try {
                message = result.getString("pesan");
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            int success = 0;
            try {
                success = result.getInt("response");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (success == 1) {
                dialog.dismiss();
//                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                crudPkk.update_pkk_catatan_keluarga_detail("upload","yes",id_detail_cat);
            }
            else if (success == 2) {
                showDialogKeyAccess(message);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
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

    private void showDialogKeyAccess(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity)context).finishAffinity();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }

    public void setProgressDialog() {

        int llPadding = 30;
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(context);
        tvText.setText("Upload Data to Server. Please wait . . .");
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(18);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(ll);


        dialog = builder.create();
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
        else
        {
            dialog.show();
        }
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }
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
