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

public class simadapter extends BaseAdapter {

    ArrayList a1,a2,a3,a4;
    Activity c;
    RequestQueue rq;
    ProgressDialog progressDialog;
    public simadapter(){}
    public simadapter(Activity c,ArrayList a1,ArrayList a2,ArrayList a3,ArrayList a4)
    {
        this.c=c;
        this.a1=a1;
        this.a2=a2;
        this.a3=a3;
        this.a4=a4;
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
        View v=l.inflate(R.layout.activity_simadapter,null,true);
        TextView t,t2,t3,t4;
        t=v.findViewById(R.id.txtkid);
        t2=v.findViewById(R.id.txtsno);
        t3=v.findViewById(R.id.txtnearby);
        t4=v.findViewById(R.id.txtlrec);
        t.setText(a1.get(i).toString());
        t2.setText(a2.get(i).toString());
        t3.setText(a3.get(i).toString());
        t4.setText(a4.get(i).toString());


        return v;
    }
}