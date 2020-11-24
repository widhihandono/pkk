package com.supradesa.supradesa_pkk.Api;

import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkCatatanKeluargaDetail;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDasaWisma;
import com.supradesa.supradesa_pkk.Model.Ent_PkkDataKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_PkkKelompokDasawisma;
import com.supradesa.supradesa_pkk.Model.Ent_jumlah_data;
import com.supradesa.supradesa_pkk.Model.Ent_twebKeluarga;
import com.supradesa.supradesa_pkk.Model.Ent_twebPenduduk;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukAgama;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukHubungan;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukPekerjaan;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukPendidikanKK;
import com.supradesa.supradesa_pkk.Model.Ent_twebPendudukUmur;
import com.supradesa.supradesa_pkk.Model.Ent_twebRtm;
import com.supradesa.supradesa_pkk.Model.Ent_user_pkk_dusun;
import com.supradesa.supradesa_pkk.Model.Ent_versioning;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api_Interface {

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/authUser")
    Call<Ent_user_pkk_dusun> authUser(@Field("user") String user,
                                      @Field("pass") String pass
                                    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/jumlah_tot_data")
    Call<Ent_jumlah_data> jumlah_tot_data(@Field("kd_desa") String kd_desa
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/jumlah_lansia")
    Call<Ent_jumlah_data> jumlah_lansia(@Field("kd_desa") String kd_desa
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/jumlah_menyusui_buta_hamil")
    Call<Ent_jumlah_data> jumlah_menyusui_buta_hamil(@Field("kd_desa") String kd_desa
    );


    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPenduduk")
    Call<Ent_twebPenduduk> getTwebPenduduk(@Field("kd_desa") String kd_desa,
                                                @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getKeluarga")
    Call<Ent_twebKeluarga> getTwebKeluarga(@Field("kd_desa") String kd_desa,
                                           @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getRtm")
    Call<Ent_twebRtm> getTwebRtm(@Field("kd_desa") String kd_desa,
                                 @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPkkCatatanKeluarga")
    Call<Ent_PkkCatatanKeluarga> getPkkCatatanKeluarga(@Field("kd_desa") String kd_desa,
                                                       @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPkkCatatanKeluargaDetail")
    Call<Ent_PkkCatatanKeluargaDetail> getPkkCatatanKeluargaDetail(@Field("kd_desa") String kd_desa,
                                                                   @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPkkDataKeluarga")
    Call<Ent_PkkDataKeluarga> getPkkDataKeluarga(@Field("kd_desa") String kd_desa,
                                                 @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPkkDasaWisma")
    Call<Ent_PkkDasaWisma> getPkkDasaWisma(@Field("kd_desa") String kd_desa,
                                           @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPkkKelompokDasaWisma")
    Call<Ent_PkkKelompokDasawisma> getPkkKelompokDasaWisma(@Field("kd_desa") String kd_desa,
                                                           @Field("dusun") String dusun
    );

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPendudukPendidikanKk")
    Call<Ent_twebPendudukPendidikanKK> getPendudukPendidikanKk(@Field("kd_desa") String kd_desa);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPendudukPekerjaan")
    Call<Ent_twebPendudukPekerjaan> getPendudukPekerjaan(@Field("kd_desa") String kd_desa);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPendudukUmur")
    Call<Ent_twebPendudukUmur> getPendudukUmur(@Field("kd_desa") String kd_desa);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPendudukAgama")
    Call<Ent_twebPendudukAgama> getPendudukAgama(@Field("kd_desa") String kd_desa);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk/getPendudukHubungan")
    Call<Ent_twebPendudukHubungan> getPendudukHubungan(@Field("kd_desa") String kd_desa);

    //UPLOAD
    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk_upload/upload_rtm")
    Call<Ent_twebRtm> upload_rtm(@Field("kd_desa") String kd_desa,
                                 @Field("nik_kepala") String nik_kepala,
                                 @Field("no_kk") String no_kk,
                                 @Field("tgl_daftar") String tgl_daftar,
                                 @Field("kelas_sosial") String kelas_sosial);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk_upload/upload_tweb_penduduk")
    Call<Ent_twebPenduduk> upload_tweb_penduduk(@Field("kd_desa") String kd_desa,
                                                @Field("nama") String nama,
                                                 @Field("nik") String nik,
                                                 @Field("id_kk") String id_kk,
                                                 @Field("kk_level") String kk_level,
                                                 @Field("id_rtm") String id_rtm,
                                                @Field("rtm_level") String rtm_level,
                                                @Field("sex") String sex,
                                                @Field("tempatlahir") String tempatlahir,
                                                @Field("tanggallahir") String tanggallahir,
                                                @Field("agama_id") String agama_id,
                                                @Field("pendidikan_kk_id") String pendidikan_kk_id,
                                                @Field("pekerjaan_id") String pekerjaan_id,
                                                @Field("status_kawin") String status_kawin,
                                                @Field("id_cluster") String id_cluster,
                                                @Field("alamat_sekarang") String alamat_sekarang,
                                                @Field("cacat_id") String cacat_id);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk_upload/upload_pkk_kelompok_dasawisma")
    Call<Ent_PkkKelompokDasawisma> upload_pkk_kelompok_dasawisma(@Field("kd_desa") String kd_desa,
                                                                @Field("no_kk") String no_kk,
                                                                @Field("id_dasa_wisma") String id_dasa_wisma);


    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk_upload/upload_pkk_data_keluarga")
    Call<Ent_PkkDataKeluarga> upload_pkk_data_keluarga(@Field("kd_desa") String kd_desa,
                                                       @Field("no_kk") String no_kk,
                                                       @Field("makanan_pokok") String makanan_pokok,
                                                       @Field("jml_makanan_pokok") String jml_makanan_pokok,
                                                       @Field("jamban") String jamban,
                                                       @Field("jml_jamban") String jml_jamban,
                                                       @Field("sumber_air") String sumber_air,
                                                       @Field("jml_sumber_air") String jml_sumber_air,
                                                       @Field("tempat_sampah") String tempat_sampah,
                                                       @Field("jml_tempat_sampah") String jml_tempat_sampah,
                                                       @Field("saluran_pembuangan_air") String saluran_pembuangan_air,
                                                       @Field("jml_saluran_pembuangan_air") String jml_saluran_pembuangan_air,
                                                       @Field("stiker_p4k") String stiker_p4k,
                                                       @Field("jml_stiker_p4k") String jml_stiker_p4k,
                                                       @Field("kriteria_rumah") String kriteria_rumah,
                                                       @Field("jml_kriteria_rumah") String jml_kriteria_rumah,
                                                       @Field("up2k") String up2k,
                                                       @Field("jml_up2k") String jml_up2k,
                                                       @Field("keg_sehat_lingkungan") String keg_sehat_lingkungan,
                                                       @Field("jml_keg_sehat_lingkungan") String jml_keg_sehat_lingkungan,
                                                       @Field("ptp") String ptp,
                                                       @Field("Industri_rt") String Industri_rt);


    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @FormUrlEncoded
    @POST("Api_pkk_upload/upload_pkk_catatan_keluarga_detail")
    Call<Ent_PkkCatatanKeluargaDetail> upload_pkk_catatan_keluarga_detail(@Field("kd_desa") String kd_desa,
                                                                            @Field("id_penduduk") String id_penduduk,
                                                                           @Field("berkebutuhan_khusus") String berkebutuhan_khusus,
                                                                           @Field("penghayatan_dan_pengamalan_pancasila") String penghayatan_dan_pengamalan_pancasila,
                                                                           @Field("gotong_royong") String gotong_royong,
                                                                           @Field("pendidikan_ketrampilan") String pendidikan_ketrampilan,
                                                                           @Field("pengembangan_kehidupan_berkoperasi") String pengembangan_kehidupan_berkoperasi,
                                                                           @Field("pangan") String pangan,
                                                                           @Field("sandang") String sandang,
                                                                           @Field("kesehatan") String kesehatan,
                                                                           @Field("perencanaan_sehat") String perencanaan_sehat,
                                                                           @Field("id_kelompok_umur") String id_kelompok_umur,
                                                                           @Field("usia_subur") String usia_subur,
                                                                           @Field("ibu_hamil") String ibu_hamil,
                                                                           @Field("menyusui") String menyusui,
                                                                           @Field("nifas") String nifas,
                                                                           @Field("buta_baca") String buta_baca,
                                                                           @Field("buta_tulis") String buta_tulis,
                                                                           @Field("buta_hitung") String buta_hitung);

    @Headers("KEY:25f9e794323b453885f5181f1b624d0b")
    @GET("Api_pkk/versioning")
    Call<Ent_versioning> versioning();

}
