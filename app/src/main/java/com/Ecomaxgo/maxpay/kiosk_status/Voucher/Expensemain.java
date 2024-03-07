package com.Ecomaxgo.maxpay.kiosk_status.Voucher;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.Ecomaxgo.maxpay.kiosk_status.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Expensemain extends AppCompatActivity {
FragmentManager fm;
public static TextView fromdate,todate;
TextView entry,report;
SimpleDateFormat sdf;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensemain);
        fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        expenseentry e=new expenseentry();
        ft.replace(R.id.fl,e);
        ft.commit();
        fromdate=(TextView) findViewById(R.id.textView22);
        todate=(TextView) findViewById(R.id.textView34);
        entry=(TextView) findViewById(R.id.textView35);
        report=(TextView) findViewById(R.id.textView36);
        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);

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

                new DatePickerDialog(Expensemain.this, date, myCalendar
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

                new DatePickerDialog(Expensemain.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fm.beginTransaction();
                expenseentry e=new expenseentry();
                ft.replace(R.id.fl,e);
                ft.commit();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fm.beginTransaction();
                expensereport e=new expensereport();
                ft.replace(R.id.fl,e);
                ft.commit();
            }
        });
    }
}
