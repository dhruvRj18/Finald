package com.example.pcd.finald;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class bs_ser extends AppCompatActivity {

    ImageView i1,i2;
    Button b1;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bs_ser);
        i1=(ImageView)findViewById(R.id.scanqr);

        i2=(ImageView)findViewById(R.id.share_loc);
        sp=getSharedPreferences("pref1",MODE_PRIVATE);


        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 =  new Intent(bs_ser.this,share_location.class);
                startActivity(i1);
            }
        });
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(bs_ser.this,scnqr.class);
                startActivity(i2);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dr_desh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id==R.id.logout){
            SharedPreferences ss = getSharedPreferences("pref1",MODE_PRIVATE);
            SharedPreferences.Editor ed = ss.edit();
            ed.clear();
            ed.commit();
            System.exit(0); // exits back press return to deshboard without this

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count <= 1) {


            //super.onBackPressed();
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                            System.exit(0);
                        }
                    }).create().show();
        }
        else{
            getFragmentManager().popBackStack();
        }
    }

}
