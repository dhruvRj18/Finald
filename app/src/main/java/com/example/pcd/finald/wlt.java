package com.example.pcd.finald;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pcd.finald.R;
import com.example.pcd.finald.servicehandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2/5/2018.
 */


public class wlt extends Fragment {
    TextView credit,debit,total,text,transection,amount;
    SharedPreferences sp;
    String s;
    EditText e;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.wlt,container,false);
        sp=getActivity().getSharedPreferences("pref",MODE_PRIVATE);
        getActivity().setTitle("Wallet");
        credit=(TextView)v.findViewById(R.id.totalv);
        debit=(TextView)v.findViewById(R.id.usag);
        total=(TextView)v.findViewById(R.id.mi);
        text=(TextView)v.findViewById(R.id.credit);
        amount=(TextView)v.findViewById(R.id.add_amount_wlt);
        e=(EditText)v.findViewById(R.id.add_amount);

       new wallet().execute();


        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=e.getText().toString();
                if(s.length()==0){
                    e.setError("Please enter some amount");
                    e.requestFocus();
                }
                else{
                    new add().execute();
                }
            }
        });

        return v;
    }

    private class wallet extends AsyncTask<Void,Void,Void> {
        String res,url,responce,cre,deb,tot;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url=getActivity().getResources().getString(R.string.url)+"getuserwallet.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            servicehandler sh=new servicehandler();
            HashMap<String,String> map=new HashMap<>();
            map.put("user_id",sp.getString("id",""));

            try{
                res=sh.getData(url,map);
                JSONObject jo=new JSONObject(res);
                responce=jo.getString("responce");
                if (responce.equals("Success")){
                    cre=jo.getString("credit");
                    deb=jo.getString("debit");
                    tot=jo.getString("total");
                }
                else {
                    res=responce;
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

            //
            if (responce.equals("Success")){
                if (!cre.equals("null")) {
                    credit.setText(cre + "\u20B9");
                }
                if (!deb.equals("null")) {
                    debit.setText(deb + "\u20B9");
                }
                total.setText(tot+"\u20B9");
                if ((!cre.equals("null")) && (!deb.equals("null")) ) {
                    text.setText("\u20B9 " + deb + " of " + cre);
                }
            }
            else {
                Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class add extends AsyncTask<Void,Void,Void>{

        String res1,url2;
        ProgressDialog pd;
        HashMap<String,String> map2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map2=new HashMap<>();
            pd=new ProgressDialog(getActivity());
            pd.setMessage("Please Wait");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url2=getResources().getString(R.string.url)+"add_credit.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {

            servicehandler sh= new servicehandler();
            map2.put("user_id",sp.getString("id",""));
            map2.put("amount",s);
            try{
                res1=sh.getData(url2,map2);
                //Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
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

            e.setText("");
            Toast.makeText(getActivity(), "Amount is Added to your wallet", Toast.LENGTH_SHORT).show();
             new wallet().execute();
        }
    }
}
