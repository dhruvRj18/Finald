package com.example.pcd.finald;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by PCD on 9/15/2017.
 */

public class signup extends AppCompatActivity{

    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b,b1;
    String s1,s2,s3,s4,s5,s6,s7,url1,res;
    SharedPreferences sp,token;
    AwesomeValidation aValid;
    int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    Uri filePath;
    private static final int CAMERA_REQUEST = 1888;
    boolean flag=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        e1=(EditText)findViewById(R.id.usrnm);
        e2=(EditText)findViewById(R.id.email_up);
        e3=(EditText)findViewById(R.id.s_pass);
        e4=(EditText)findViewById(R.id.con_pass);
        e5=(EditText)findViewById(R.id.phn);
        e6=(EditText)findViewById(R.id.adrs);
        b=(Button)findViewById(R.id.signup);
        b1=(Button)findViewById(R.id.pic);
        token=getSharedPreferences("DeviceToken",MODE_PRIVATE);

        aValid= new AwesomeValidation(ValidationStyle.BASIC);

        aValid.addValidation(this, R.id.email_up, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        aValid.addValidation(this,R.id.phn, Patterns.PHONE,R.string.mobileerror);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(signup.this)
                        .setMessage("Choose one")
                        .setNegativeButton("take picture", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                            }
                        })
                        .setPositiveButton("go to gallery", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            public void onClick(DialogInterface dialog, int id) {
                                showFileChooser();

                            }
                        }).create().show();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitform();
                s1=e1.getText().toString();
                s2=e2.getText().toString();
                s3=e3.getText().toString();
                s4=e4.getText().toString();
                s5=e5.getText().toString();
                s6=e6.getText().toString();


                if(s1.length()==0){
                    e1.setError("First name not entered");
                    e1.requestFocus();
                    flag=false;
                }
                if(s2.length()==0){
                    e2.setError("email name not entered");
                    e2.requestFocus();
                    flag=false;
                }


                if(s3.length()==0){
                    e3.setError("Password not entered");
                    e3.requestFocus();
                    flag=false;
                }
                if(e4.getText().toString().length()==0){
                    e4.setError("Please confirm password");
                    e4.requestFocus();
                    flag=false;
                }
                if(!e3.getText().toString().equals(e4.getText().toString())){
                    e4.setError("Password Not matched");
                    e4.requestFocus();
                    flag=false;
                }
                if(s5.length()==0){
                    e5.setError("phone number not entered");
                    e5.requestFocus();
                    flag=false;
                }
                if (s6.length() == 0) {

                    e6.setError("Please enter Address");
                    e6.requestFocus();
                    flag=false;

                }

                if (flag) {
                    new insert().execute();
                }

            }
        });

    }

    private void showFileChooser() {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),PICK_IMAGE_REQUEST);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(signup.this.getContentResolver(),filePath);
               // iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");

        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void submitform() {
        if (!aValid.validate()) {
            Toast.makeText(this, " Please enter valid credentials", Toast.LENGTH_LONG).show();

        }
    }

    private class insert extends AsyncTask<Void,Void,Void> {
        String saveid;
        boolean result1;
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(signup.this);
            pd.setMessage("Signing UP");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            url1=getResources().getString(R.string.url)+"insert2.php";
        }

        @Override
        protected Void doInBackground(Void... params) {
            servicehandler sh = new servicehandler();
            HashMap<String,String>map=new HashMap<>();
            map.put("username",s1);
            map.put("email",s2);
            map.put("password",s3);
            map.put("phone",s5);
            map.put("address",s6);
            map.put("image",getStringImage(bitmap));
            map.put("token",token.getString("token",""));

            try{
                res=sh.getData(url1,map);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pd.isShowing()){
                pd.dismiss();}

               // Toast.makeText(signup.this, res, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), usr_login.class);
                startActivity(i);
                finish();
        }
    }
}
