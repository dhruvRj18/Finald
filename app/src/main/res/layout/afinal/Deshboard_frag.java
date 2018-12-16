package com.example.pcd.afinal;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Deshboard_frag extends Fragment {

    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.deshboard_frag,container,false);

        i1=(ImageView)view.findViewById(R.id.qr);
        i2=(ImageView)view.findViewById(R.id.wlt);
        i3=(ImageView)view.findViewById(R.id.notif);
        i4=(ImageView)view.findViewById(R.id.reg);
        i5=(ImageView)view.findViewById(R.id.ren);
        i6=(ImageView)view.findViewById(R.id.trac);
        i7=(ImageView)view.findViewById(R.id.buses);
        i8=(ImageView)view.findViewById(R.id.seat);
        i9=(ImageView)view.findViewById(R.id.comp);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            com.example.pcd.afinal.scanqr f1 = new com.example.pcd.afinal.scanqr();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.blank,f1);
            ft.addToBackStack(null);
            ft.commit();
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.pcd.afinal.wlt f2 = new com.example.pcd.afinal.wlt();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f2);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.pcd.afinal.notif f3 = new com.example.pcd.afinal.notif();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f3);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.pcd.afinal.reg f4 = new com.example.pcd.afinal.reg();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f4);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ren f5 = new ren();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f5);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trac f6 = new trac();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f6);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buses f7 = new buses();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f7);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.pcd.afinal.seat f8 = new com.example.pcd.afinal.seat();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f8);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comp f9 = new comp();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.blank,f9);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;

    }

}
