package com.example.pcd.afinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by PCD on 9/15/2017.
 */

public class signup extends AppCompatActivity{

    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    Button b;
    String s1,s2,s3,s4,s5,s6,s7,url,res;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        e1=(EditText)findViewById(R.id.usrnm);
        e2=(EditText)findViewById(R.id.email_up);
        e3=(EditText)findViewById(R.id.pass);
        e4=(EditText)findViewById(R.id.con_pass);
        e5=(EditText)findViewById(R.id.phn);
        e6=(EditText)findViewById(R.id.adrs);
        e7=(EditText)findViewById(R.id.pic);
        e8=(EditText)findViewById(R.id.adhar1);
        b=(Button)findViewById(R.id.signup);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1=e1.getText().toString();
                s2=e2.getText().toString();
                s3=e3.getText().toString();
                s4=e4.getText().toString();
                s5=e5.getText().toString();
                s6=e6.getText().toString();
                s7=e8.getText().toString();

                url="http://192.168.0.26/final/insert2.php";
                new insert().execute();

            }
        });

    }

    private class insert extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            com.example.pcd.afinal.servicehandler sh = new com.example.pcd.afinal.servicehandler();
            HashMap<String,String>map=new HashMap<>();
            map.put("username",s1);
            map.put("email",s2);
            map.put("password",s3);
            map.put("confirm_password",s4);
            map.put("adhar1",s7);
            map.put("phone",s5);
            map.put("address",s6);

            try{
                res=sh.getData(url,map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(com.example.pcd.afinal.signup.this, "Data inserted", Toast.LENGTH_SHORT).show();
        }
    }
}
