package com.example.pcd.finald;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by PCD on 9/15/2017.
 */

public class bs_login extends AppCompatActivity {
    Button b;
    EditText e1,e2;
    String s1,s2,url1,res;
    SharedPreferences sp;
    boolean flag=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bs_login);

        b=(Button)findViewById(R.id.blogin);
        e1=(EditText)findViewById(R.id.id);
        e2=(EditText)findViewById(R.id.b_pass);

        sp=getSharedPreferences("pref1",MODE_PRIVATE);
        if(sp.contains("id")){
            Intent i=new Intent(bs_login.this,bs_ser.class);
            startActivity(i);
        }




        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1=e1.getText().toString();
                s2=e2.getText().toString();

                if(s1.length()==0){
                    e1.setError("Bus ID not entered");
                    e1.requestFocus();
                    flag=false;
                }
                if(s2.length()==0){
                    e2.setError("Password not entered");
                    e2.requestFocus();
                    flag=false;
                }

                if (flag) {
                    new buslogin().execute();
                }

            }
        });
    }


    private class buslogin extends AsyncTask<Void,Void,Void>{

        HashMap<String,String> map;
        String saveid,savepass;
        boolean result1;

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map=new HashMap<>();
            pd=new ProgressDialog(bs_login.this);
            pd.setMessage("Authenticating");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url1=getResources().getString(R.string.url)+"bs_login.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            map.put("bus_id",s1);
            map.put("password",s2);
            servicehandler sh=new servicehandler();
            try {
                res = sh.getData(url1, map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jo = new JSONObject(res);
                if (jo.getString("response").equals("Success")) {
                    result1 = true;
                    JSONArray ja = jo.getJSONArray("data");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo1 = ja.getJSONObject(i);
                        savepass = jo1.getString("password");
                        saveid = jo1.getString("bus_id");
                    }
                }
                else
                {
                    result1=false;
                    res=jo.getString("response");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pd.isShowing()){
                pd.dismiss();}
            if(result1){


                sp = getSharedPreferences("pref1", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putString("id", saveid);
                e.commit();

                Intent i=new Intent(bs_login.this,bs_ser.class);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(bs_login.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
