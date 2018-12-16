package com.example.pcd.finald;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by PCD on 9/23/2017.
 */

@SuppressLint("ValidFragment")
class ren extends Fragment  {
    Spinner s,s2;
    TextView tv;
    Button b;
    String a,end,st1;
    ArrayList<Integer> a3;
    ArrayList<String> a2;
    int st3,dday,dmonth,dyear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ren,container,false);
        s=(Spinner)v.findViewById(R.id.renew_month_count);
        s2=(Spinner)v.findViewById(R.id.renew_type);
        tv=(TextView)v.findViewById(R.id.renew_start_date);
        b=(Button)v.findViewById(R.id.renew);


        getActivity().setTitle("Renew");
        a3=new ArrayList<>();
        a3.add(1);
        a3.add(3);
        a3.add(6);

        ArrayAdapter ad3 =  new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,a3);
        s.setAdapter(ad3);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st3 = a3.get(position);

               // Toast.makeText(getActivity(), Integer.toString(st3), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please Select", Toast.LENGTH_SHORT).show();

            }
        });

        a2=new ArrayList<>();
        a2.add("Local");
        a2.add("Express");

        ArrayAdapter ad1 = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,a2);
        s2.setAdapter(ad1);

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st1 = a2.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getActivity(), "Please Select", Toast.LENGTH_SHORT).show();

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c1 = Calendar.getInstance();
                dday = c1.get(Calendar.DAY_OF_MONTH);
                dmonth = c1.get(Calendar.MONTH);
                dyear = c1.get(Calendar.YEAR);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void  onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                        tv.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                    }
                },dyear,dmonth,dday);

                dpd.show();


                c1.add(Calendar.MONTH, st3);
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                end = sdf1.format(c1.getTime());
                Toast.makeText(getActivity(), "yeee"+end, Toast.LENGTH_SHORT).show();

            }
        });



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=tv.getText().toString();
                Toast.makeText(getActivity(), st3+" "+ tv.getText().toString()+" "+end, Toast.LENGTH_SHORT).show();
                if(a.length()==0 ){
                    tv.setError("Please select date");
                    tv.requestFocus();
                }
                else{
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Your pass has been renewed, Please Complete Payment to use your pass And your ending date is "+end)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i =  new Intent(getActivity(),renew_payment.class);
                                    i.putExtra("ren_month_count",st3);
                                    i.putExtra("start",tv.getText().toString());
                                    i.putExtra("end",end);
                                    i.putExtra("ren_type",st1);
                                    startActivity(i);
                                }
                            }).create().show();
                }
            }
        });


        return v;
    }
}
