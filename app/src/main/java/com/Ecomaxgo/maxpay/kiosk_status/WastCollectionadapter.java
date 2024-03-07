package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class WastCollectionadapter extends BaseAdapter {

    ArrayList a1,a2,a3,a4;
    Activity c;
    RequestQueue rq;
    ProgressDialog progressDialog;
    public WastCollectionadapter(){}
    public WastCollectionadapter(Activity c, ArrayList a1, ArrayList a2, ArrayList a3, ArrayList a4)
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
        View v=l.inflate(R.layout.wastcollection_row,null,true);
        EditText etenteryname,etkioskid,etrecyclecomapne,etcollectingperson,etremark;
        Spinner spinnernumberofPlastics,spinnernumberofglasses,spinnernumberoftins;
        Button btnsumbmit;

        etenteryname=v.findViewById(R.id.etenteryname);
        etkioskid=v.findViewById(R.id.etkioskid);
        etrecyclecomapne=v.findViewById(R.id.etrecyclecomapne);
        etcollectingperson=v.findViewById(R.id.etcollectingperson);
        etcollectingperson=v.findViewById(R.id.etcollectingperson);
        spinnernumberofPlastics=v.findViewById(R.id.spinnernumberofPlastics);
        spinnernumberofglasses=v.findViewById(R.id.spinnernumberofglasses);
        spinnernumberoftins=v.findViewById(R.id.spinnernumberoftins);
        etremark=v.findViewById(R.id.etremark);


        btnsumbmit=v.findViewById(R.id.btnsumbmit);
        btnsumbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        etenteryname.setText(a1.get(i).toString());
        etkioskid.setText(a2.get(i).toString());
        etrecyclecomapne.setText(a3.get(i).toString());
        etcollectingperson.setText(a4.get(i).toString());


        return v;
    }
}