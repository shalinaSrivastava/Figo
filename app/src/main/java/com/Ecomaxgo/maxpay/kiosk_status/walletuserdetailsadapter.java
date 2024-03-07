package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class walletuserdetailsadapter extends BaseAdapter{

    Activity c;

    ArrayList a1,a2,a3,a4,a5,a6,a7;
    public walletuserdetailsadapter(Activity c,ArrayList a1,ArrayList a2,ArrayList a3,ArrayList a4,ArrayList a5,ArrayList a6,ArrayList a7)
    {
        this.c=c;
        this.a1=a1;
        this.a2=a2;
        this.a3=a3;
        this.a4=a4;
        this.a5=a5;
        this.a6=a6;
        this.a7=a7;


    }
    @Override
    public int getCount() {
        return a1.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater l=c.getLayoutInflater();
        View v=l.inflate(R.layout.walletuserdetailsadapter,null,true);
        TextView t26,t27,t28,t29,t30,t31,t32;
        t26=v.findViewById(R.id.textView26);
        t27=v.findViewById(R.id.textView27);
        t28=v.findViewById(R.id.textView28);
        t29=v.findViewById(R.id.textView29);
        t30=v.findViewById(R.id.textView30);
        t31=v.findViewById(R.id.textView31);
        t32=v.findViewById(R.id.textView32);

        t26.setText(a1.get(i).toString());
        t27.setText(a2.get(i).toString());
        t28.setText(a3.get(i).toString());
        t29.setText(a4.get(i).toString());
        t30.setText(a5.get(i).toString());
        t31.setText(a6.get(i).toString());
        t32.setText(a7.get(i).toString());

        return v;
    }
}