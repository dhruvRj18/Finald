package com.example.pcd.finald;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class dr_desh extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String s,s2;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_desh);
        sp=getSharedPreferences("pref",MODE_PRIVATE);



        new fetch().execute();

        Deshboard_frag f1 = new Deshboard_frag();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content,f1);
        ft.addToBackStack(null);
        ft.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


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
        getMenuInflater().inflate(R.menu.dr_desh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


         if (id==R.id.logout){
            SharedPreferences ss = getSharedPreferences("pref",MODE_PRIVATE);
            SharedPreferences.Editor ed = ss.edit();
            ed.clear();
            ed.commit();
            System.exit(0); // exits back press return to deshboard without this

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_pass_ser) {
            pass_ser f2 = new pass_ser();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f2);
            ft.addToBackStack(null);
            ft.commit();



        }
        else if (id == R.id.nav_wlt) {
            wlt f2 = new wlt();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f2);
            ft.addToBackStack(null);
            ft.commit();

        }

        else if (id == R.id.nav_trac) {
            trac f6 = new trac();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f6);
            ft.addToBackStack(null);
            ft.commit();

        }
        else if (id == R.id.nav_busesontheroute) {
            buses f7 = new buses();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f7);
            ft.addToBackStack(null);
            ft.commit();

        }

        else if (id == R.id.nav_cmp) {
            comp f9 = new comp();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f9);
            ft.addToBackStack(null);
            ft.commit();

        }
        else if(id==R.id.nav_review){
            startActivity(new Intent(dr_desh.this,review_rating.class));
        }
        else if(id==R.id.nav_logout){
            SharedPreferences ss = getSharedPreferences("pref",MODE_PRIVATE);
            SharedPreferences.Editor ed = ss.edit();
            ed.clear();
            ed.commit();
            System.exit(0); // exits back press return to deshboard without this


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private class fetch extends AsyncTask<Void,Void,Void>{
        String url,res;
        HashMap<String,String> map;
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map=new HashMap<>();
            pd= new ProgressDialog(dr_desh.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
            url=getResources().getString(R.string.url)+"fetch_image.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            servicehandler sh= new servicehandler();
             map.put("id",sp.getString("id",""));
             try{
                 res=sh.getData(url,map);
             } catch (IOException e) {
                 e.printStackTrace();
             }
            try {
                JSONObject jo = new JSONObject(res);

                    JSONArray JA = jo.getJSONArray("data");
                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject jo1 = JA.getJSONObject(i);
                        s = jo1.getString("image");
                        s2 = jo1.getString("username");

                }
                } catch(JSONException e){
                    e.printStackTrace();
                }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pd.isShowing()){
                pd.dismiss();
            }
           // Toast.makeText(dr_desh.this, s, Toast.LENGTH_SHORT).show();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(dr_desh.this);
            View hView =  navigationView.getHeaderView(0);
            ImageView iv= (ImageView) hView.findViewById(R.id.propic);
            TextView tv=(TextView) hView.findViewById(R.id.getname);
            Context context = getApplicationContext();
           // Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            Log.d("imgname",getResources().getString(R.string.url)+"image/"+s);
            Picasso.with(context).load(getResources().getString(R.string.url)+"image/"+s+".jpg").resize(150,150).error(R.drawable.buslogo2).centerCrop().into(iv);
            tv.setText(s2);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(dr_desh.this).setMessage("sjbhf").create().show();
                }
            });

        }
    }
}
