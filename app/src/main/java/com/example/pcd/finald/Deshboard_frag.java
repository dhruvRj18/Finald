package com.example.pcd.finald;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Deshboard_frag extends Fragment {

    ImageView i1,i2,i3,i6,i7,i9;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.deshboard_frag ,container,false);

        i1=(ImageView)view.findViewById(R.id.pass_ser);
        i2=(ImageView)view.findViewById(R.id.wlt);
        i6=(ImageView)view.findViewById(R.id.trac);
        i7=(ImageView)view.findViewById(R.id.buses);
        i9=(ImageView)view.findViewById(R.id.comp);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            pass_ser f1 = new pass_ser();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content,f1);
            ft.addToBackStack(null);
            ft.commit();
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wlt f2 = new wlt();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content,f2);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trac f6 = new trac();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content,f6);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buses f7 = new buses();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content,f7);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comp f9 = new comp();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content,f9);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }

}
