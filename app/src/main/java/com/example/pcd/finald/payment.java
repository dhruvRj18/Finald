package com.example.pcd.finald;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by PCD on 3/20/2018.
 */

public class payment extends AppCompatActivity {
    int amount;
    TextView tv;
    Button b;
    SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        Intent i= getIntent();
       int i1= i.getIntExtra("month_count",0);
       String type= i.getStringExtra("type");
       String s_dis=i.getStringExtra("start_dis");
       String e_dis=i.getStringExtra("end_dis");
       tv=(TextView)findViewById(R.id.amount);
       b=(Button)findViewById(R.id.pay);
       sp=getSharedPreferences("pref",MODE_PRIVATE);


        if(i1==1 && type.equals("Express") && s_dis.equals("Sojitra") && e_dis.equals("Anand")){
            amount=300;
        }
        else if(i1==1 && type.equals("Local") && s_dis.equals("Sojitra") && e_dis.equals("Anand")){
            amount=200;
        }
        else if(i1==3 && type.equals("Express") && s_dis.equals("Sojitra") && e_dis.equals("Anand")){
            amount=600;
        }
        else if(i1==3 && type.equals("Local") && s_dis.equals("Sojitra") && e_dis.equals("Anand")){
            amount=400;
        }
        else if(i1==6 && type.equals("Express") && s_dis.equals("Sojitra") && e_dis.equals("Anand")){
            amount=1200;
        }
        else if(i1==6 && type.equals("Local") && s_dis.equals("Sojitra") && e_dis.equals("Anand")){
            amount=800;
        }
        tv.setText(Integer.toString(amount));
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new pay().execute();
            }
        });


    }

    private class pay extends AsyncTask<Void,Void,Void> {
        String res,url1;
        ProgressDialog pd;
        HashMap<String,String> map;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map=new HashMap<>();
            pd=new ProgressDialog(payment.this);
            pd.setMessage("Please Wait");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url1=getResources().getString(R.string.url)+"payment.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            servicehandler sh= new servicehandler();
            map.put("user_id",sp.getString("id",""));
            map.put("amount",Integer.toString(amount));
            try{
                res=sh.getData(url1,map);
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
            startActivity(new Intent(payment.this,dr_desh.class));
        }
    }
}
