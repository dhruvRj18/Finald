package com.example.pcd.finald;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class renew_payment extends AppCompatActivity {

    int amount;
    TextView tv;
    Button b;
    SharedPreferences sp;

    String start,end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_payment);
        tv=(TextView)findViewById(R.id.renew_amount);
        b=(Button)findViewById(R.id.renew_pay);

        sp=getSharedPreferences("pref",MODE_PRIVATE);
        Intent i = getIntent();
        int i1= i.getIntExtra("ren_month_count",0);
        String type= i.getStringExtra("ren_type");
        start=i.getStringExtra("start");
        end=i.getStringExtra("end");

      //  Toast.makeText(this, Integer.toString(i1)+" "+ start+" "+end, Toast.LENGTH_SHORT).show();



        if(i1==1 && type.equals("Express") ){
            amount=300;
        }
        else if(i1==1 && type.equals("Local")){
            amount=200;
        }
        else if(i1==3 && type.equals("Express") ){
            amount=600;
        }
        else if(i1==3 && type.equals("Local") ){
            amount=400;
        }
        else if(i1==6 && type.equals("Express")){
            amount=1200;
        }
        else if(i1==6 && type.equals("Local") ){
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
            pd=new ProgressDialog(renew_payment.this);
            pd.setMessage("Please Wait");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url1=getResources().getString(R.string.url)+"renew_payment.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            servicehandler sh= new servicehandler();
            map.put("user_id",sp.getString("id",""));
            map.put("amount",Integer.toString(amount));
            map.put("start",start);
            map.put("end",end);
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
            Toast.makeText(renew_payment.this, "Your Pass has been renewed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(renew_payment.this,dr_desh.class));
        }
    }
}
