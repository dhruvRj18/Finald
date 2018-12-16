package com.example.pcd.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private final int delay = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(com.example.pcd.afinal.Splash.this, com.example.pcd.afinal.Startup.class);
                startActivity(i);
                com.example.pcd.afinal.Splash.this.finish();
            }
        },delay*1000);
    }
}
