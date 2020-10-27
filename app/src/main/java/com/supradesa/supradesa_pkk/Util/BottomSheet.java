package com.supradesa.supradesa_pkk.Util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.supradesa.supradesa_pkk.Api.Api_Client;
import com.supradesa.supradesa_pkk.Profile_Activity;
import com.supradesa.supradesa_pkk.R;
import com.supradesa.supradesa_pkk.databinding.LayoutEditAccountBinding;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BottomSheet extends BottomSheetDialogFragment {
    SharedPref sharedPref;
    BottomSheetBehavior bottomSheetBehavior;
    LayoutEditAccountBinding bi;
    private ProgressDialog dialog;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.layout_edit_account, null);

        //binding views to data binding.
        bi = DataBindingUtil.bind(view);
        sharedPref = new SharedPref(getContext());
        //setting layout with bottom sheet
        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);


        //setting max height of bottom sheet
        bi.extraSpace.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels) / 2);

        bi.etUsername.setText(sharedPref.sp.getString("username",""));
        bi.etDusun.setText(sharedPref.sp.getString("dusun",""));
        bi.etKodeDesa.setText(sharedPref.sp.getString("kode_desa",""));
        bi.etNik.setText(sharedPref.sp.getString("nik",""));
        bi.etEmail.setText(sharedPref.sp.getString("email",""));
        bi.etNoHp.setText(sharedPref.sp.getString("no_hp",""));

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    showView(bi.appBarLayout, getActionBarSize());

                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(bi.appBarLayout);
                }

                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        //aap bar cancel button clicked
        bi.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });


        //aap bar Save button clicked
        bi.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bi.etNoHp.getText().toString().isEmpty() || bi.etNoHp.getText().toString().isEmpty())
                {
                    showMessageDialog("Masukkan data dengan benar");
                }
                else if(validate_email(bi.etEmail.getText().toString()) == false)
                {
                    showMessageDialog("Masukkan email dengan benar");
                }
                else
                {
                    Edit_User_Async edit = new Edit_User_Async(sharedPref.sp.getString("id_user",""),
                            sharedPref.sp.getString("id_role",""),
                            bi.etNoHp.getText().toString(),bi.etUsername.getText().toString(),bi.etEmail.getText().toString(),
                            bi.etPassword.getText().toString(),
                            Api_Client.BASE_URL+"/Api_pkk/edit_User");
                    edit.execute();
                }
            }
        });

        bi.btnSimpanPerubahan.setOnClickListener(l->{
            if(bi.etNoHp.getText().toString().isEmpty() || bi.etNoHp.getText().toString().isEmpty())
            {
                showMessageDialog("Masukkan data dengan benar");
            }
            else if(validate_email(bi.etEmail.getText().toString()) == false)
            {
                showMessageDialog("Masukkan email dengan benar");
            }
            else
            {
                Edit_User_Async edit = new Edit_User_Async(sharedPref.sp.getString("id_user",""),
                        sharedPref.sp.getString("id_role",""),
                        bi.etNoHp.getText().toString(),bi.etUsername.getText().toString(),bi.etEmail.getText().toString(),
                        bi.etPassword.getText().toString(),
                        Api_Client.BASE_URL+"/Api_pkk/edit_User");
                edit.execute();
            }
        });


        //hiding app bar at the start
        hideAppBar(bi.appBarLayout);


        return bottomSheet;
    }

    @Override
    public void onStart() {
        super.onStart();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideAppBar(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);

    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray array = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) array.getDimension(0, 0);
        return size;
    }

    public static boolean validate_email(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    class Edit_User_Async extends AsyncTask<String, String, JSONObject>
    {
        private Exception exception;
        private ProgressDialog progressDialog = null;
        String keterangan = "";
        String SERVER_PATH;
        String no_hp,password;
        int response;
        String nama,email,id_user,id_role;


        private Edit_User_Async(String id_user,String id_role,String no_hp,String nama,String email,String password,String SERVER_PATH) {
            this.id_user = id_user;
            this.id_role = id_role;
            this.no_hp = no_hp;
            this.email = email;
            this.nama = nama;
            this.password = password;
            this.response = 0;

            this.SERVER_PATH = SERVER_PATH;
        }

        public void setResponse(int response) {
            this.response = response;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Please wait . . .");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
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
                jsonObject.put("id_role", id_role);
                jsonObject.put("id_user", id_user);
                jsonObject.put("nama", nama);
                jsonObject.put("no_hp", no_hp);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
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
//                conn.setRequestProperty("KEY","25f9e794323b453885f5181f1b624d0b");
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

//                setResponse(json.getInt("response"));

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
            int response = 0;
            try {
                response = result.getInt("response");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (response == 1) {
//                dialog.dismiss();
                setResponse(response);
                try {


                    sharedPref.saveSPBoolean(SharedPref.SP_SUDAH_LOGIN,true);
                    sharedPref.saveSPString("id_user", result.getString("id_user"));
                    sharedPref.saveSPString("id_role", result.getString("id_role"));
                    sharedPref.saveSPString("role", sharedPref.sp.getString("role",""));
                    sharedPref.saveSPString("nama", result.getString("nama"));
                    sharedPref.saveSPString("email",result.getString("email"));
                    sharedPref.saveSPString("no_hp",result.getString("no_hp"));


                    dialog.dismiss();
                    Intent i = new Intent(getContext(), Profile_Activity.class);
                    getContext().startActivity(i);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else if (response == 2) {
                setResponse(response);
                dialog.dismiss();
                showMessageDialog(message);
//                Toast.makeText(Registrasi_Activity.this,message,Toast.LENGTH_LONG).show();
//                this.progressDialog.dismiss();
            }
            else if (response == 3) {
                setResponse(response);
                dialog.dismiss();
                showMessageDialog(message);
//                Toast.makeText(Registrasi_Activity.this,message,Toast.LENGTH_LONG).show();
//                this.progressDialog.dismiss();
            }
            else if (response == 4) {
                setResponse(response);
                dialog.dismiss();
                showMessageDialog(message);
//                Toast.makeText(Registrasi_Activity.this,message,Toast.LENGTH_LONG).show();
//                this.progressDialog.dismiss();
            }
            else
            {
                setResponse(response);
                dialog.dismiss();
                showMessageDialog(message);
//                Toast.makeText(Registrasi_Activity.this,message,Toast.LENGTH_LONG).show();
            }



//            Toast.makeText(getBaseContext(), success ? "We are good to go." : "Something went wrong!",
//                    Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        }
    }


    private void showMessageDialog(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        // set title dialog
        alertDialogBuilder.setMessage(pesan);

        // set pesan dari dialog
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Close",new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
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
