package com.example.pcd.afinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class usr_login extends AppCompatActivity{
    EditText ed1,ed2,ed3;
    TextView tv,tv1;
    Button b1,b2;
    String s1,s2,s3,url,res,d1,d2,d3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usr_login);
        getSupportActionBar().hide();


        ed1=(EditText)findViewById(R.id.email);
        ed2=(EditText)findViewById(R.id.pass);
        ed3=(EditText)findViewById(R.id.adhar);
        tv=(TextView)findViewById(R.id.inopt);
        tv1=(TextView)findViewById(R.id.frgt);
        b1=(Button)findViewById(R.id.login);
        b2=(Button)findViewById(R.id.sgnup);

        registerForContextMenu(tv);

        s1=ed1.getText().toString();
        s2=ed2.getText().toString();
        s3= ed3.getText().toString();


        /*if(s1.length()>0){
            Intent i = new Intent(this,Blank.class);
            startActivity(i);
        }*/

        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences ss  =getSharedPreferences("share",MODE_PRIVATE);
                SharedPreferences.Editor ed=ss.edit();
                ed.putString("email",s1);
                ed.putString("password",s2);
                ed.putString("adhar",s3);
                ed.commit();


                url= "http://192.168.0.31/final/insert.php";
                new insert().execute();
                Intent i1 = new Intent(com.example.pcd.afinal.usr_login.this,Blank.class);
                startActivity(i1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(com.example.pcd.afinal.usr_login.this,signup.class);
                startActivity(i2);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Signin with");
        menu.add(0,v.getId(),0,"Google");
        menu.add(0,v.getId(),0,"Facebook");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Google"){
            Toast.makeText(this, "google", Toast.LENGTH_SHORT).show();
        }
        else if(item.getTitle()=="Facebook"){
            Toast.makeText(this, "facebook", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private class insert extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            com.example.pcd.afinal.servicehandler sh = new com.example.pcd.afinal.servicehandler();
            HashMap<String,String>map= new HashMap<>();
            map.put("email",s1);
            map.put("password",s2);
            map.put("adhar",s3);
            try {
                res=sh.getData(url,map);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}