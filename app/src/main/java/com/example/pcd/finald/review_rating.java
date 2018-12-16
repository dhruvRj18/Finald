package com.example.pcd.finald;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class review_rating extends AppCompatActivity {

    RatingBar rating_tv;
    Button com;
    EditText e;
    String url1, res;
    String rating,feedback;
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);
        rating_tv = (RatingBar)findViewById(R.id.rating);
        com = (Button)findViewById(R.id.rating_sub);
        e=(EditText)findViewById(R.id.com);
        sp= getSharedPreferences("pref", Context.MODE_PRIVATE);
        addListenerOnButtonClick();
    }



    public void addListenerOnButtonClick() {
        com.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                rating = String.valueOf(rating_tv.getRating());
                feedback = e.getText().toString();
                new review().execute();
            }


        });
    }

    private class review extends AsyncTask<Void, Void, Void> {

        HashMap<String, String> map;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map = new HashMap<>();
            pd = new ProgressDialog(review_rating.this);
            pd.setMessage("Submitting");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url1 = getResources().getString(R.string.url) + "review.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            servicehandler sh = new servicehandler();
            map.put("rating", rating);
            map.put("feedback", feedback);
            map.put("user_id",sp.getString("id",""));

            try {
                res = sh.getData(url1, map);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            startActivity(new Intent(review_rating.this,dr_desh.class));
        }
    }
}

