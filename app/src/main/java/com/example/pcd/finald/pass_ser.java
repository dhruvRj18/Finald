package com.example.pcd.finald;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by PCD on 1/30/2018.
 */

public class pass_ser extends Fragment {

    ImageView i1, i2, i3;
    String url1, res;
    String status,s;
    SharedPreferences sp,ss;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pass_ser, container, false);

        i1 = (ImageView) view.findViewById(R.id.reg);
        i2 = (ImageView) view.findViewById(R.id.qr);
        i3 = (ImageView) view.findViewById(R.id.ren);






        sp = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        if (sp.contains("id")) {
            new fetchstatus().execute();
        }
        return view;

    }

    private class fetchstatus extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;
        HashMap<String,String> map;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map=new HashMap<>();
            pd=new ProgressDialog(getActivity());
            pd.setMessage("Authenticating");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            url1 = getResources().getString(R.string.url) + "fetch_status.php";


        }

        @Override
            protected Void doInBackground(Void... voids) {
            map.put("id",sp.getString("id",""));

            servicehandler sh = new servicehandler();
           // HashMap<String, String> map = new HashMap<>();
            try {
                res = sh.getData(url1, map);
                JSONObject jo = new JSONObject(res);
                JSONArray ja = jo.getJSONArray("data");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo1 = ja.getJSONObject(i);
                    status = jo1.getString("status");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(status,"jj");


            if(pd.isShowing()){
                pd.dismiss();}

                ss=getActivity().getSharedPreferences("Pass",MODE_PRIVATE);
            SharedPreferences.Editor ed=ss.edit();
            ed.putString("status", status);
            ed.commit();
            i1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ss.getString("status","").equals("pending")) {
                        reg f1 = new reg();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content, f1);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    else{
                        Toast.makeText(getActivity(), "Already  registered", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            i2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(ss.getString("status","").equals("Activated")) {
                        scanqr f2 = new scanqr();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content, f2);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    else{
                        Toast.makeText(getActivity(), "Please register", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            i3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(ss.getString("status","").equals("Activated")) {
                        ren f3 = new ren();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content, f3);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    else{
                        Toast.makeText(getActivity(), "Please register", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }
    }
}
