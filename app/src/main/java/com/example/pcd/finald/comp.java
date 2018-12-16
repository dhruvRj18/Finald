package com.example.pcd.finald;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by PCD on 9/23/2017.
 */

@SuppressLint("ValidFragment")
class comp extends Fragment {


    EditText e;
    Button b;
    String s, res, url1;
    SharedPreferences sp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.comp, container, false);

        e = (EditText) v.findViewById(R.id.complaint);
        b = (Button) v.findViewById(R.id.sub_comp);
        sp=getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);


        getActivity().setTitle("Complaits");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = e.getText().toString();

                if(s.length()==0){
                    Toast.makeText(getActivity(), "Please enter complaint", Toast.LENGTH_SHORT).show();
                }

                else{
                    new complaint().execute();
                }

            }
        });
        return v;
    }
    private class complaint extends AsyncTask<Void,Void,Void>{
        HashMap<String,String> map;
        ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(getActivity());
            pd.setMessage("Submitting Your Complaint");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url1=getResources().getString(R.string.url)+"complaint.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            map =new HashMap<>();
            servicehandler sh = new servicehandler();
            map.put("userid",sp.getString("id",""));
            map.put("complaint",s);

            try{
                res=sh.getData(url1,map);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pd.isShowing()){
                pd.dismiss();
            }
            Toast.makeText(getActivity(), "Your complaint has successfully submitted", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity(), dr_desh.class);
            startActivity(i);
        }
    }
}