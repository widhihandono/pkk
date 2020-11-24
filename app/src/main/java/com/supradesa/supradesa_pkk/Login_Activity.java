package com.supradesa.supradesa_pkk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Api.Api_Interface;
import com.supradesa.supradesa_pkk.Util.MyHttpEntity;
import com.supradesa.supradesa_pkk.Util.MySSLSocketFactory;
import com.supradesa.supradesa_pkk.Util.SharedPref;
import com.supradesa.supradesa_pkk.Model.Ent_user_pkk_dusun;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
private EditText etUsername,etPassword;
private TextView tvForgotPass;
private ImageView imgLogin;
private Api_Interface apiInterface;
private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        getSupportActionBar().hide();

        apiInterface = Api_Client.getClient().create(Api_Interface.class);

        sharedPref = new SharedPref(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        imgLogin = findViewById(R.id.imgLogin);


        imgLogin.setOnClickListener(l->{
//            login();
            Login_user login = new Login_user(this,etUsername.getText().toString(),etPassword.getText().toString(),"https://pkk.magelangkab.go.id/Api_pkk/authUser");
            login.execute();
        });
    }

    private void login()
    {
        Call<Ent_user_pkk_dusun> callLogin = apiInterface.authUser(etUsername.getText().toString(),etPassword.getText().toString());
        callLogin.enqueue(new Callback<Ent_user_pkk_dusun>() {
            @Override
            public void onResponse(Call<Ent_user_pkk_dusun> call, Response<Ent_user_pkk_dusun> response) {
                if(response.isSuccessful())
                {
                    if(response.body().isResponse())
                    {
                        Toast.makeText(Login_Activity.this,"Sukses Login",Toast.LENGTH_LONG).show();
                        sharedPref.saveSPBoolean(sharedPref.SP_SUDAH_LOGIN,true);
                        sharedPref.saveSPString("nik",response.body().getNik());
                        sharedPref.saveSPString("kode_desa",response.body().getKode_desa());
                        sharedPref.saveSPString("dusun",response.body().getDusun());
                        sharedPref.saveSPString("nama_desa",response.body().getNama_desa());
                        sharedPref.saveSPString("nama_kecamatan",response.body().getNama_kecamatan());
                        sharedPref.saveSPString("no_hp",response.body().getNo_hp());
                        sharedPref.saveSPString("email",response.body().getEmail());

                        startActivity(new Intent(Login_Activity.this,MainActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Login_Activity.this,"Username atau password salah",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(Login_Activity.this,"Network Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Ent_user_pkk_dusun> call, Throwable t) {
                Toast.makeText(Login_Activity.this,"Network Failed, Check your connection",Toast.LENGTH_LONG).show();
            }
        });
    }

    //Async Login
    private class Login_user extends AsyncTask<Void, Integer, Integer> {

        HttpClient httpClient = getNewHttpClient();
        private Context context;
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String nik = "",kode_desa = "",dusun = "",no_hp="",email="",keterangan="";
        String SERVER_PATH;
        String username,password;

        private Login_user(Context context,String username,String password,String SERVER_PATH) {
            this.context = context;
            this.username = username;
            this.password = password;
            this.SERVER_PATH = SERVER_PATH;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            HttpResponse httpResponse = null;
            HttpEntity httpEntity = null;
            Integer responseInt = 10;
            Ent_user_pkk_dusun ep = new Ent_user_pkk_dusun();

            try {
                URI url = new URI(SERVER_PATH);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("KEY","25f9e794323b453885f5181f1b624d0b");
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

                // Add the file to be uploaded
                multipartEntityBuilder.addTextBody("user", username);
                multipartEntityBuilder.addTextBody("pass", password);

                // Progress listener - updates task's progress
                MyHttpEntity.ProgressListener progressListener =
                        new MyHttpEntity.ProgressListener() {
                            @Override
                            public void transferred(float progress) {
                                publishProgress((int) progress);
                            }
                        };

                // POST
                httpPost.setEntity(new MyHttpEntity(multipartEntityBuilder.build(),
                        progressListener));


                httpResponse = httpClient.execute(httpPost);
                httpClient.getConnectionManager().closeExpiredConnections();
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // Server response
                    //cek respone

                    JSONObject myObject = new JSONObject(EntityUtils.toString(httpEntity));

                    responseInt = myObject.getInt("response");
                    if(responseInt == 1)
                    {
                        responseInt = 1;
                        nik = myObject.getString("nik");
                        kode_desa = myObject.getString("kode_desa");
                        dusun = myObject.getString("dusun");
                        no_hp = myObject.getString("no_hp");
                        email = myObject.getString("email");
                        username = myObject.getString("username");
                    }
                    else if(responseInt == 2)
                    {
                        keterangan = myObject.getString("pesan");
                    }
                    else
                    {
                        responseInt = 0;
                    }

                } else {
                    responseInt = statusCode;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
//                    e.printStackTrace();
//                    Log.e("SAVE", e.getMessage());
//                    this.exception = e;
            } catch (IOException e) {
//                    e.printStackTrace();
            } catch (JSONException e) {
//                    e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return responseInt;
        }

        @Override
        protected void onPreExecute() {

            // Init and show dialog
            this.progressDialog = new ProgressDialog(this.context);
            if(this.progressDialog != null)
            {
                this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }

        @Override
        protected void onPostExecute(Integer result) {

            // Close dialog
            if (result == 1) {
                this.progressDialog.dismiss();
                Toast.makeText(Login_Activity.this,"Sukses Login",Toast.LENGTH_LONG).show();
                sharedPref.saveSPBoolean(sharedPref.SP_SUDAH_LOGIN,true);
                sharedPref.saveSPString("nik",nik);
                sharedPref.saveSPString("kode_desa",kode_desa);
                sharedPref.saveSPString("dusun",dusun);
                sharedPref.saveSPString("no_hp",no_hp);
                sharedPref.saveSPString("username",username);
                sharedPref.saveSPString("email",email);

                startActivity(new Intent(Login_Activity.this,MainActivity.class));
                finish();

            }
            else if (result == 2) {
                showDialogKeyAccess(keterangan);
//                this.progressDialog.dismiss();
            } else {
                Toast.makeText(Login_Activity.this,"Gagal Login",Toast.LENGTH_LONG).show();
                this.progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Update process
            this.progressDialog.setProgress((int) progress[0]-2);

        }

    }

    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    private void showDialogKeyAccess(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.RED);
    }

}
