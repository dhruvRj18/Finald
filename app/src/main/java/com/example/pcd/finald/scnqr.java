package com.example.pcd.finald;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by PCD on 1/16/2018.
 */

public class scnqr extends AppCompatActivity {

    private ImageView btn;
    String s;
    String first_name,last_name,address,gender,type,img,end,start,s_dis,e_dis;

    ImageView user_image;
    TextView first,last,addr,gen,typ,start_date,end_date,start_dis,end_dis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scnqr);

        Intent i= getIntent();
        if(i.hasExtra("raw")) {
            s = i.getStringExtra("raw").substring(3);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

            new fetch().execute();

        }

        user_image=(ImageView)findViewById(R.id.bus_usr_img);
        first=(TextView)findViewById(R.id.bus_usr_first_name);
        last=(TextView)findViewById(R.id.bus_usr_last_name);
        addr=(TextView)findViewById(R.id.bus_address);
        gen=(TextView)findViewById(R.id.bus_usr_gender);
        typ=(TextView)findViewById(R.id.bus_usr_type);
        start_date=(TextView)findViewById(R.id.bus_issue_date);
        end_date=(TextView)findViewById(R.id.bus_end_date);
        start_dis=(TextView)findViewById(R.id.bus_s_dis);
        end_dis=(TextView)findViewById(R.id.bus_e_dis);

        //tvresult = (TextView) findViewById(R.id.tvresult);
        btn = (ImageView) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scnqr.this, ScanActivity.class);
                startActivity(intent);
            }
        });


    }

    private class fetch extends AsyncTask<Void,Void,Void>{
        String url,res;
        ProgressDialog pd;
        HashMap<String,String> map;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map= new HashMap<>();
            pd= new ProgressDialog(scnqr.this);
            pd.setMessage("please wait");
            pd.show();
            url=getResources().getString(R.string.url)+"bus_fetch_qr.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            servicehandler sh = new servicehandler();
            map.put("user_id",s);

            try{
                res=sh.getData(url,map);
                JSONObject jo = new JSONObject(res);
                JSONArray ja =jo.getJSONArray("data");
                for (int i = 0; i <ja.length() ; i++) {
                    JSONObject jo1 = ja.getJSONObject(i);
                    first_name=jo1.getString("first");
                    last_name=jo1.getString("last");
                    address=jo1.getString("address");
                    gender=jo1.getString("gender");
                    type=jo1.getString("type");
                    img=jo1.getString("img");
                    start=jo1.getString("start");
                    end=jo1.getString("end");
                    s_dis=jo1.getString("s_dis");
                    e_dis=jo1.getString("e_dis");
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
          //  Toast.makeText(scnqr.this, res, Toast.LENGTH_SHORT).show();
            first.setText(first_name);
            last.setText(last_name);
            addr.setText(address);
            gen.setText(gender);
            typ.setText(type);
            start_date.setText(start);
            end_date.setText(end);
            start_dis.setText(s_dis);
            end_dis.setText(e_dis);

            Context context = getApplicationContext();
            Picasso.with(context).load(getResources().getString(R.string.url)+"image/"+img+".jpg").resize(150,150).centerCrop().into(user_image);
            Log.d("jj",getResources().getString(R.string.url)+"image/"+img);
        }
    }
}
