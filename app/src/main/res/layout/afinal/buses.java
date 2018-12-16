package com.example.pcd.afinal;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PCD on 9/18/2017.
 */

public class buses extends Fragment {
    String res,url;
    ArrayList<single>ar;
    ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.buses,container,false);



        lv=(ListView)view.findViewById(R.id.list);
        ar=new ArrayList<>();

        url="http://192.168.0.31/final/sfetch.php";
        new ftch().execute();
        return view;
    }

    private class ftch extends AsyncTask<Void,Void,Void>{
        String id,username,email,password;
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(getActivity());
            pd.setMessage("Please Wait...");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            com.example.pcd.afinal.servicehandler sh=new com.example.pcd.afinal.servicehandler();
            HashMap<String,String>map=new HashMap<>();
            try
            {
                res=sh.getData(url,map);
                JSONObject jo=new JSONObject(res);
                JSONArray ja=jo.getJSONArray("data");
                for (int i = 0; i <ja.length() ; i++) {
                    JSONObject jo1=ja.getJSONObject(i);
                    id=jo1.getString("id");
                    username=jo1.getString("username");
                    email=jo1.getString("email");
                    password=jo1.getString("password");

                    single s=new single();
                    s.setId(id);
                    s.setEmail(email);
                    s.setUsername(username);
                    s.setPassword(password);
                    ar.add(s);


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
            if (pd.isShowing()){
                pd.dismiss();
            }
           /* Toast.makeText(getActivity(),res, Toast.LENGTH_SHORT).show();
            Log.d("ja",res);*/

            Cust_adpter ca=new Cust_adpter(getActivity(),ar);
            lv.setAdapter(ca);
        }
    }

    private class Cust_adpter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<single>ar;
        public Cust_adpter(Context context, ArrayList<single> ar) {
            this.ar=ar;
            this.context=context;
        }

        @Override
        public int getCount() {
            return ar.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=inflater.inflate(R.layout.view,parent,false);
            TextView tv1=(TextView)v.findViewById(R.id.tex1);
            TextView tv2=(TextView)v.findViewById(R.id.tex2);
            TextView tv3=(TextView)v.findViewById(R.id.tex3);
            TextView tv4=(TextView)v.findViewById(R.id.tex4);

            single s=ar.get(position);
            tv1.setText(s.getId());
            tv2.setText(s.getUsername());
            tv3.setText(s.getEmail());
            tv4.setText(s.getPassword());
            return v;
        }
    }
}
