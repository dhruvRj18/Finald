package com.example.pcd.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Startup extends AppCompatActivity {

    ImageView i1,i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        getSupportActionBar().hide();

        i1=(ImageView)findViewById(R.id.usr);
        i2=(ImageView)findViewById(R.id.bs);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.pcd.afinal.Startup.this,usr_login.class);
                startActivity(i);
            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(com.example.pcd.afinal.Startup.this,bs_login.class);
                startActivity(i1);
            }
        });
    }
}
