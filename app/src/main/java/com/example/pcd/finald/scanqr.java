package com.example.pcd.finald;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.content.Context.WINDOW_SERVICE;


@SuppressLint("ValidFragment")
class scanqr extends Fragment {

    private static final String TAG = "Loggg";
    private String LOG_TAG = "GenerateQRCode";
    ImageView myImage,user_image;
    TextView first,last,addr,gen,typ,start_date,end_date,start_dis,end_dis;
    String url1,res,qrInputText,first_name,last_name,address,gender,type,img,end,start,s_dis,e_dis;
    SharedPreferences sp;
    String current_date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.scanqr,container,false);


        getActivity().setTitle("My Pass");
        myImage = (ImageView)view.findViewById(R.id.qr_image);
        user_image=(ImageView)view.findViewById(R.id.usr_img);
        Button button1 = (Button)view.findViewById(R.id.button1);

        first=(TextView)view.findViewById(R.id.usr_first_name);
        last=(TextView)view.findViewById(R.id.usr_last_name);
        addr=(TextView)view.findViewById(R.id.address);
        gen=(TextView)view.findViewById(R.id.usr_gender);
        typ=(TextView)view.findViewById(R.id.usr_type);
        start_date=(TextView)view.findViewById(R.id.issue_date);
        end_date=(TextView)view.findViewById(R.id.end_date);
        start_dis=(TextView)view.findViewById(R.id.s_dis);
        end_dis=(TextView)view.findViewById(R.id.e_dis);

        sp=getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        new fetch().execute();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                current_date =  new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Log.d("current",current_date + end);
              //  Toast.makeText(getActivity(), "current"+" "+current_date, Toast.LENGTH_SHORT).show();

                if (!end.equals(current_date)) {
                    qrInputText = "TP-" + sp.getString("id", "");
                //    Toast.makeText(getActivity(), qrInputText, Toast.LENGTH_SHORT).show();
                    Log.v(LOG_TAG, qrInputText);
                    WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;
                    //Encode with a QR Code image
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            smallerDimension);
                    try {
                        Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                        myImage.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    // More buttons go here (if any) ...
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Your pass id Expired Please Renew your pass")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            }
        });


        return view;
    }

    private class fetch extends AsyncTask<Void,Void,Void> {
        HashMap<String,String> map;

        @Override
        protected void onPreExecute() {
            map=new HashMap<>();
            url1=getActivity().getResources().getString(R.string.url)+"fetch_qr.php";

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            map.put("usr_id",sp.getString("id",""));
            servicehandler sh = new servicehandler();
            try{
                res=sh.getData(url1,map);
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

            first.setText(first_name);
            last.setText(last_name);
            addr.setText(address);
            gen.setText(gender);
            typ.setText(type);
            start_date.setText(start);
            end_date.setText(end);
            start_dis.setText(s_dis);
            end_dis.setText(e_dis);

                Context context = getActivity().getApplicationContext();
            Picasso.with(context).load(getResources().getString(R.string.url)+"image/"+img+".jpg").resize(150,150).centerCrop().into(user_image);
            Log.d("jj",getResources().getString(R.string.url)+"image/"+img);


        }
    }




    /*ListView lv;
    ArrayList<String>ar;
    String url1,res;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scanqr,container,false);
        lv=(ListView)view.findViewById(R.id.lv);
        ar= new ArrayList<>();
       // Log.d("ja",url1);

        registerForContextMenu(lv);

        new fetch().execute();
        return view;
    }


    private class fetch extends AsyncTask<Void,Void,Void>{
        String id,email,password,adhar;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url1=getResources().getString(R.string.url)+"sfetch.php";
        }
        @Override
        protected Void doInBackground(Void... params) {
            servicehandler sh = new servicehandler();
            HashMap<String,String>map= new HashMap<>();
            try{
                res=sh.getData(url1,map);
                JSONObject jo = new JSONObject(res);
                JSONArray ja =jo.getJSONArray("data");
                for (int i = 0; i <ja.length() ; i++) {
                    JSONObject jo1 = ja.getJSONObject(i);
                    id=jo1.getString("id");
                    email=jo1.getString("email");
                    password=jo1.getString("password");
                    adhar=jo1.getString("adhar");
                    ar.add(email);
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
           *//* Toast.makeText(getActivity(),res, Toast.LENGTH_SHORT).show();
            Log.d("ja1",res);*//*
            ArrayAdapter ad;
            ad = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,ar);
            lv.setAdapter(ad);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("select");
        menu.add(0,v.getId(),0,"update");
        menu.add(0,v.getId(),0,"delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle()=="update"){
            update f =new update();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f);
            ft.addToBackStack(null);
            ft.commit();
        }
        else{
            delete f =new delete();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f);
            ft.addToBackStack(null);
            ft.commit();
        }

        return true;
    }*/

    /*private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }
    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }*/
}
