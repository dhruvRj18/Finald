package com.example.pcd.finald;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by PCD on 1/9/2018.
 */

public class usr_login extends AppCompatActivity {

    EditText email, password;
    Button login,signup;
    String url1,res;
    SharedPreferences sp,ss;
    boolean flag=true;
    AwesomeValidation aValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.usr_login);
        getSupportActionBar().hide();

        aValid = new AwesomeValidation(ValidationStyle.BASIC);

        sp = getSharedPreferences("pref", MODE_PRIVATE);
        ss=getSharedPreferences("DeviceToken",MODE_PRIVATE);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.sgnup);

        aValid.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);


        if (sp.contains("email")) {
            Intent i = new Intent(getApplicationContext(), dr_desh.class);
            startActivity(i);
            finish();
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), signup.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitform();
                if(email.getText().toString().length()==0){
                    email.setError("required");
                    email.requestFocus();
                    flag=false;
                }
                if(password.getText().toString().length()==0){
                    password.setError("required");
                    password.requestFocus();
                    flag=false;
                }

                if(flag){
                    new login(email.getText().toString(), password.getText().toString()).execute();
                }
            }
        });
    }
    private void submitform(){
        if (!aValid.validate()) {
            Toast.makeText(this, " Please enter valid credentials", Toast.LENGTH_LONG).show();

        }

    }

    private class login extends AsyncTask<Void,Void,Void> {
        HashMap<String,String> map;

        String saveemail,saveid;
        boolean result1;
        ProgressDialog pd;
        String s,s1;


        public login(String s, String s1) {
            this.s=s;
            this.s1=s1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map=new HashMap<>();
            pd=new ProgressDialog(usr_login.this);
            pd.setMessage("Authenticating");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            url1=getResources().getString(R.string.url)+"login.php";
        }

        @Override
        protected Void doInBackground(Void... params) {
            map.put("email",s);
            map.put("password",s1);
            map.put("token",ss.getString("token",""));

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
                        saveemail = jo1.getString("email");
                        saveid = jo1.getString("id");
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
           // Toast.makeText(usr_login.this, result, Toast.LENGTH_SHORT).show();
            if(pd.isShowing()){
                pd.dismiss();}
            if(result1){

                sp = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putString("email", saveemail);
                e.putString("id", saveid);
                e.commit();

                Intent i=new Intent(getApplicationContext(),dr_desh.class);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(usr_login.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
