package com.example.pcd.finald;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PCD on 9/23/2017.
 */

@SuppressLint("ValidFragment")
class buses extends Fragment {
    EditText e1,e2;
    Button b;
    ListView lv;
    ArrayList<single> ar;
    String s1,s2,url1,res;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buses,container,false);

        e1=(EditText)view.findViewById(R.id.start);
        e2=(EditText)view.findViewById(R.id.end);
        b=(Button)view.findViewById(R.id.check_buses);
        lv=(ListView)view.findViewById(R.id.bus_list);
        ar= new ArrayList<single>();

        getActivity().setTitle("Buses On the Route");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1=e1.getText().toString();
                s2=e2.getText().toString();

                if(s1.length()==0 || s2.length()==0){
                    Toast.makeText(getActivity(), "Please enter every values of destinatin", Toast.LENGTH_SHORT).show();
                }
                else{
                    new findbuses().execute();
                }
            }
        });

        return view;
    }

    private class findbuses extends AsyncTask<Void,Void,Void> {
        HashMap<String,String> map;
        ProgressDialog pd;
        String busid,type,time;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map=new HashMap<>();
            pd=new ProgressDialog(getActivity());
            pd.setMessage("please wait");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url1=getResources().getString(R.string.url)+"timetable.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ar.clear();
            map.put("start",s1);
            map.put("end",s2);
            servicehandler sh=new servicehandler();

            try {
                res = sh.getData(url1, map);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                res=sh.getData(url1,map);
                JSONObject jo = new JSONObject(res);
                JSONArray ja =jo.getJSONArray("data");
                for (int i = 0; i <ja.length() ; i++) {
                    JSONObject jo1 = ja.getJSONObject(i);
                    busid=jo1.getString("busid");
                    type=jo1.getString("type");
                    time=jo1.getString("time");

                    single s = new single();
                    s.setBusid(busid);
                    s.setTime(time);
                    s.setType(type);
                    ar.add(s);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
            Log.d("res",res);
            Context context =  getActivity().getApplicationContext();
            cust_adapter ca = new cust_adapter(getActivity(),ar);
            lv.setAdapter(ca);
        }
    }

    private class cust_adapter extends BaseAdapter{
        public cust_adapter(Activity activity, ArrayList<single> ar) {
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
            View view = getActivity().getLayoutInflater().inflate(R.layout.bus_list,parent,false);
            TextView tv1= (TextView) view.findViewById(R.id.getBus_Id);
            TextView tv2 = (TextView)view.findViewById(R.id.getBus_type);
            TextView tv3 = (TextView)view.findViewById(R.id.getBus_time);

            single s = (single)ar.get(position);
            tv1.setText(s.getBusid());
            tv2.setText(s.getType());
            tv3.setText(s.getTime());
            Log.d("ar",s.getBusid());
            return view;
        }
    }
}
