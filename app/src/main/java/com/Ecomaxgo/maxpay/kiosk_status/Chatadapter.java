package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;


import java.util.ArrayList;

public class Chatadapter extends BaseAdapter {

    ArrayList a1,a2,a3,a4,a5,a6,a7,a8;
    Activity c;
    RequestQueue rq;
    ProgressDialog progressDialog;
    public Chatadapter(){}
    public Chatadapter(Activity c,ArrayList a1,ArrayList a2,ArrayList a3,ArrayList a4,ArrayList a5,ArrayList a6,ArrayList a7,ArrayList a8)
    {
        this.c=c;
        this.a1=a1;
        this.a2=a2;
        this.a3=a3;
        this.a4=a4;
        this.a5=a5;
        this.a6=a6;
        this.a7=a7;
        this.a8=a8;
        // rq= Volley.newRequestQueue(c);

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater l=c.getLayoutInflater();
        View v=l.inflate(R.layout.chatdetails,null,true);
        TextView t,t2,t3,t4;


        t=v.findViewById(R.id.textrecived);
        t2=v.findViewById(R.id.textsend);

        t.setText(a7.get(i).toString());
        t2.setText(a3.get(i).toString());



        return v;
    }
}