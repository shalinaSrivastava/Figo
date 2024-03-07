package com.Ecomaxgo.maxpay.kiosk_status.Voucher;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.Ecomaxgo.maxpay.kiosk_status.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class loadmain extends AppCompatActivity {
    public static TextView fromdate,todate;
    ListView list;
    RequestQueue rq;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    SimpleDateFormat sdf;
    Calendar myCalendar;
    TextView t41,t42,t43;
    FragmentManager fm;
    public static String empid="",wallet="",name="";
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmain);
        fromdate=(TextView) findViewById(R.id.textView22);
        todate=(TextView) findViewById(R.id.textView34);
        empid=getIntent().getStringExtra("empid");
        wallet=getIntent().getStringExtra("wallet");
        name=getIntent().getStringExtra("name");


        rq=Volley.newRequestQueue(this);
        sp=getSharedPreferences("login",MODE_PRIVATE);
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        fm=getSupportFragmentManager();
        fromdate.setText(sdf.format(c.getTime()));
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();
                date = new
                        DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                // TODO Auto-generated method stub
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                fromdate.setText( new SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(myCalendar.getTime()));
                                //getData();
                            }

                        };

                new DatePickerDialog(loadmain.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        todate.setText(sdf.format(new Date()));
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();
                date = new
                        DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                // TODO Auto-generated method stub
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                todate.setText( new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(myCalendar.getTime()));
                                //getData();
                            }

                        };

                new DatePickerDialog(loadmain.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        t41=(TextView) findViewById(R.id.textView41);
        t42=(TextView) findViewById(R.id.textView42);
        t43=(TextView) findViewById(R.id.textView43);
        FragmentTransaction ft=fm.beginTransaction();
        expenseinfoadmin er= new expenseinfoadmin();
        ft.replace(R.id.fl,er);
        ft.commit();
        t41.setBackgroundColor(Color.parseColor("#117781"));
        t42.setBackgroundColor(Color.parseColor("#1a97a3"));
        t43.setBackgroundColor(Color.parseColor("#1a97a3"));
        t41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fm.beginTransaction();
                expenseinfoadmin er= new expenseinfoadmin();
                ft.replace(R.id.fl,er);
                ft.commit();
                t41.setBackgroundColor(Color.parseColor("#117781"));
                t42.setBackgroundColor(Color.parseColor("#1a97a3"));
                t43.setBackgroundColor(Color.parseColor("#1a97a3"));

            }
        });

        t42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fm.beginTransaction();
                loadentry er=new loadentry();
                ft.replace(R.id.fl,er);
                ft.commit();
                t42.setBackgroundColor(Color.parseColor("#117781"));
                t41.setBackgroundColor(Color.parseColor("#1a97a3"));
                t43.setBackgroundColor(Color.parseColor("#1a97a3"));

            }
        });
        t43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fm.beginTransaction();
                loadreport er=new loadreport();
                ft.replace(R.id.fl,er);
                ft.commit();
                t43.setBackgroundColor(Color.parseColor("#117781"));
                t42.setBackgroundColor(Color.parseColor("#1a97a3"));
                t41.setBackgroundColor(Color.parseColor("#1a97a3"));

            }
        });



    }
}
