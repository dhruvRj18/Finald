package com.example.pcd.afinal;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Blank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        com.example.pcd.afinal.Deshboard_frag f = new com.example.pcd.afinal.Deshboard_frag();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.blank,f);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count <= 1) {
            finish();

        }
        else{
            getFragmentManager().popBackStack();
        }


        return true;
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logot){
            SharedPreferences ss =getSharedPreferences("share",MODE_PRIVATE);
            SharedPreferences.Editor ed=ss.edit();
            ed.clear();
            ed.commit();
        }
        return true;
    }
}
