package com.example.pcd.finald;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class reg extends Fragment {
    /*ImageView i;
    String s;*/

    private static final String UPLOAD_KEY ="image" ;
    private   String UPLOAD_URL;
    int PICK_IMAGE_REQUEST = 1;
    Uri filePath;
    Bitmap bitmap;
    EditText e1,e2,e3,e4,e5;
    TextView tv,tv1;
    Spinner s1,s2,s3,s4,s5;
    Button b1,b3;
    int dday,dmonth,dyear;
    String r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,url1,res;
    SharedPreferences sp;
    AwesomeValidation aValid;
    ArrayList<String>a1,a2,a4,a5;
    ArrayList<Integer> a3;
    String end,st1;
    String s_dis,e_dis;
    int st3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reg,container,false);
        /* i =  (ImageView) view.findViewById(R.id.iv);
         s= getResources().getString(R.string.url)+"image/13606542_1566403986749297_2989361901447782542_n.jpg";
        Log.d(s,"dsf");
        Context context = getActivity().getApplicationContext();
        Picasso.with(context).load(s).into(i);*/

        sp=getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
        getActivity().setTitle("Register");

        e1=(EditText)view.findViewById(R.id.r_fname);
        e2=(EditText)view.findViewById(R.id.r_lname);
        e3=(EditText)view.findViewById(R.id.r_addr);
        e4=(EditText)view.findViewById(R.id.r_clg);
        e5=(EditText)view.findViewById(R.id.r_enrl);


        tv=(TextView) view.findViewById(R.id.r_date);
        tv1=(TextView) view.findViewById(R.id.start_date);


        s1=(Spinner)view.findViewById(R.id.gender);
        s2=(Spinner)view.findViewById(R.id.r_ptype);
        s3 =(Spinner)view.findViewById(R.id.month_count);
        s4=(Spinner)view.findViewById(R.id.start_distance);
        s5=(Spinner)view.findViewById(R.id.end_destination);


        b1=(Button)view.findViewById(R.id.r_pic);
        b3=(Button)view.findViewById(R.id.r_reg);

        aValid= new AwesomeValidation(ValidationStyle.BASIC);


       // b2=(Button)view.findViewById(R.id.r_upldpic);
        UPLOAD_URL = getResources().getString(R.string.url)+"reg_insert.php";

        //Spinner123 for gender
        a1=new ArrayList<>();
        a1.add("Male");
        a1.add("Female");

        ArrayAdapter ad = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,a1);
        s1.setAdapter(ad);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String st = a1.get(position);
                //r7=setText(st);
                if(st=="Male"){
                    //code
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please Select", Toast.LENGTH_SHORT).show();

            }
        });

        //Spinner123 for pass type

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

        //spinner for start distance
        a4=new ArrayList<>();
        a4.add("Sojitra");
        ArrayAdapter ad4 = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,a4);
        s4.setAdapter(ad4);

        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               s_dis=a4.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please select start distance", Toast.LENGTH_SHORT).show();

            }
        });

        //spinner for end distance
        a5=new ArrayList<>();
        a5.add("Anand");
        ArrayAdapter ad5 = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,a5);
        s5.setAdapter(ad5);

        s5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                e_dis=a5.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getActivity(), "Please select end distance", Toast.LENGTH_SHORT).show();
            }
        });



        //month count
        a3=new ArrayList<>();
        a3.add(1);
        a3.add(3);
        a3.add(6);

        ArrayAdapter ad3 =  new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,android.R.id.text1,a3);
        s3.setAdapter(ad3);

        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st3 = a3.get(position);

                   /// Toast.makeText(getActivity(), Integer.toString(st3), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please Select", Toast.LENGTH_SHORT).show();

            }
        });


        //date picker
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                dday = c.get(Calendar.DAY_OF_MONTH);
                dmonth = c.get(Calendar.MONTH);
                dyear = c.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void  onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                        tv.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },dyear,dmonth+1,dday);

                dpd.show();

            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c1 = Calendar.getInstance();
                dday = c1.get(Calendar.DAY_OF_MONTH);
                dmonth = c1.get(Calendar.MONTH);
                dyear = c1.get(Calendar.YEAR);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void  onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                        tv1.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },dyear,dmonth,dday);

                dpd.show();


                c1.add(Calendar.MONTH, st3);
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                end = sdf1.format(c1.getTime());
                Toast.makeText(getActivity(), "yeee"+end, Toast.LENGTH_SHORT).show();

            }
        });

        //Image upload

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registration button
                r1=e1.getText().toString(); //fname
                r2=e2.getText().toString(); //lname
                r3=e3.getText().toString(); //addr
                r4=e4.getText().toString(); //clg
                r5=e5.getText().toString(); //enrl
                r6=tv.getText().toString(); //date
                r7=s1.getSelectedItem().toString(); //gender
                r8=s2.getSelectedItem().toString(); //bustype
                r9=tv1.getText().toString();


                Toast.makeText(getActivity(), r9, Toast.LENGTH_SHORT).show();
                if(r1.length()==0){
                    e1.setError("First name not entered");
                    e1.requestFocus();
                }
                else if(r2.length()==0){
                    e2.setError("Last name not entered");
                    e2.requestFocus();
                }
                else if(r3.length()==0){
                    e3.setError("Address not entered");
                    e3.requestFocus();
                }
                else if(r4.length()==0){
                    e4.setError("College name not entered");
                    e4.requestFocus();
                }
                else if(r5.length()==0){
                    e5.setError("Enrollment Number not entered");
                    e5.requestFocus();
                }
                else if(r6.length()==0){
                    tv.setError("Date not Selected");
                    tv.requestFocus();
                }
                else if(r9.length()==0){
                    tv1.setError("Select Starting Date");
                    tv1.requestFocus();
                }
               else if(s_dis.length()==0){
                    Toast.makeText(getActivity(), "please select starting distance", Toast.LENGTH_SHORT).show();
                }
                else if(e_dis.length()==0){
                    Toast.makeText(getActivity(), "please select Ending distance", Toast.LENGTH_SHORT).show();
                }

                else if (r1.length()!=0 && r2.length()!=0 && r3.length()!=0 && r4.length()!=0 && r5.length()!=0 &&
                        r6.length()!=0 && r9.length()!=0 && s_dis.length()!=0 && e_dis.length()!=0) {
                    new insert().execute();
                }
                else{
                    Toast.makeText(getActivity(), "proper", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void showFileChooser() {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private class insert extends AsyncTask<Void,Void,Void> {
        ProgressDialog pd;
        String uploadImage;
        HashMap<String,String> map;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            map=new HashMap<>();
            pd=new ProgressDialog(getActivity());
            pd.setMessage("Authenticating");
            pd.setIndeterminate(true);
            pd.setCancelable(true);
            pd.show();
            url1 = getResources().getString(R.string.url) + "reg_insert.php";
       }

        @Override
        protected Void doInBackground(Void... voids) {

            map.put("usr_id",sp.getString("id",""));
            servicehandler sh = new servicehandler();
            uploadImage = getStringImage(bitmap);
            map.put("first",r1);
            map.put("last",r2);
            map.put("address",r3);
            map.put("college",r4);
            map.put("enroll",r5);
            map.put("dob",r6);
            map.put("gender",r7);
            map.put("type",r8);
            map.put("image",uploadImage);
            map.put("start",r9);
            map.put("end",end);
            map.put("s_dis",s_dis);
            map.put("e_dis",e_dis);
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
            if(pd.isShowing())
            {
                pd.dismiss();
            }
            new AlertDialog.Builder(getActivity())
                    .setMessage("Your pass has been registered, Please Complete Payment to use your pass And your ending date is "+end)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i =  new Intent(getActivity(),payment.class);
                            i.putExtra("month_count",st3);
                            i.putExtra("type",st1);
                            i.putExtra("start_dis",s_dis);
                            i.putExtra("end_dis",e_dis);
                            startActivity(i);
                        }
            }).create().show();
        }
    }
}

