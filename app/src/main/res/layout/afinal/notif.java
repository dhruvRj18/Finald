package com.example.pcd.afinal;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PCD on 9/18/2017.
 */

public class notif extends Fragment {
    Spinner sp;
    ArrayList<String>ar;
    String url,res;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notif,container,false);


        sp=(Spinner)view.findViewById(R.id.sp);
        ar=new ArrayList<>();

        ArrayAdapter ad=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,ar);
        sp.setAdapter(ad);


        url="htt[://192.168.0.31/final/fetch2.php";
        new fetch().execute();




        return view;




    }

    private class fetch extends AsyncTask<Void,Void,Void> {
        String id,email,password,adhar;

        @Override
        protected Void doInBackground(Void... params) {

            com.example.pcd.afinal.servicehandler sh = new com.example.pcd.afinal.servicehandler();
            HashMap<String,String>map=new HashMap<>();
            try {
                res=sh.getData(url,map);
                JSONObject jo = new JSONObject(res);
                JSONArray ja = new JSONArray("data");
                for (int i = 0; i <ja.length() ; i++) {
                    JSONObject jo1= ja.getJSONObject(i);
                    id=jo1.getString("id");
                    email=jo1.getString("email");
                    password=jo1.getString("password");
                    adhar=jo1.getString("password");

                    single2 s= new single2();
                    s.setId(id);
                    s.setEmail(email);
                    s.setPassword(password);
                    s.setAdhar(adhar);
                    ar.add(s);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
