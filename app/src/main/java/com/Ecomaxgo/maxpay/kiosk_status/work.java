package com.Ecomaxgo.maxpay.kiosk_status;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class work extends AppCompatActivity {
    TextView t46,t47;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work);
        t46=(TextView) findViewById(R.id.textView46);
        t47=(TextView) findViewById(R.id.textView47);
        t46.setBackgroundColor(Color.parseColor("#19b1bf"));
        t47.setBackgroundColor(Color.parseColor("#0f848f"));

        fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        WorkListFragment asf= new WorkListFragment();
        ft.replace(R.id.fl,asf);
        ft.commit();
        t46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t46.setBackgroundColor(Color.parseColor("#19b1bf"));
                t47.setBackgroundColor(Color.parseColor("#0f848f"));
                FragmentTransaction ft=fm.beginTransaction();
                WorkListFragment asf= new WorkListFragment();
                ft.replace(R.id.fl,asf);
                ft.commit();
            }
        });
t47.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        t47.setBackgroundColor(Color.parseColor("#19b1bf"));
        t46.setBackgroundColor(Color.parseColor("#0f848f"));
        FragmentTransaction ft=fm.beginTransaction();
        AssignWorkFragment asf=new AssignWorkFragment();
        ft.replace(R.id.fl,asf);
        ft.commit();
    }
});

    }
}
