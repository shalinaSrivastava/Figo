package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Walletuser extends Fragment{
    TextView from,to,t27,t28,t29;
    RequestQueue rq;
    ProgressDialog progressDialog,progressDialog2;
    ListView list;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    ArrayList a1,a2,a3,a4,a5,a6,a7;
    SimpleDateFormat sdf;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_walletuser,container,false);
        from=v.findViewById(R.id.from);
        to=v.findViewById(R.id.to);
        t27=v.findViewById(R.id.textView27);
        t28=v.findViewById(R.id.textView28);
        t29=v.findViewById(R.id.textView29);
        rq= Volley.newRequestQueue(getContext());
        list=v.findViewById(R.id.list);
        sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        from.setText(sdf.format(new Date()));
        to.setText(sdf.format(new Date()));

        from.setOnClickListener(new View.OnClickListener() {
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
                                from.setText(new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(myCalendar.getTime()));
                                getData();
                            }

                        };

                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        to.setOnClickListener(new View.OnClickListener() {
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
                                to.setText(new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(myCalendar.getTime()));
                                getData();
                            }

                        };

                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        getData();
        return v;
    }
    private void getData()
    {
        a1=new ArrayList();
        a2=new ArrayList();
        a3=new ArrayList();
        a4=new ArrayList();
        a5=new ArrayList();
        a6=new ArrayList();
        a7=new ArrayList();

        progressDialog = ProgressDialog.show(getContext(), "", "Please wait...", false, false);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/walletUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);


                    progressDialog.dismiss();
                    t27.setText("Total User\n"+obj.getString("TotalUser"));
                    t28.setText("Android\n"+obj.getString("TotalAndriodUser"));
                    t29.setText("IOS\n"+obj.getString("TotaliOSUser"));

                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                    t27.setText("Total User\n0 ");
                    t28.setText("Android\n0 ");
                    t29.setText("IOS\n0 ");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "" + volleyError, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> m = new HashMap<String, String>();
                m.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                m.put("from", from.getText().toString());
                m.put("to", to.getText().toString());



                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);

        progressDialog2 = ProgressDialog.show(getContext(), "", "Please wait...", false, false);
        StringRequest sr2 = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/walletUserList", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray ary=obj.getJSONArray("data");
                    for (int i=0;i<ary.length();i++)
                    {
                        JSONObject objj=ary.getJSONObject(i);
                        a1.add(objj.getString("id"));
                        a2.add(objj.getString("username"));
                        a3.add(objj.getString("phonenumber"));
                        a4.add(objj.getString("address"));
                        a5.add(objj.getString("gender"));
                        a6.add(objj.getString("device"));
                        a7.add(objj.getString("entry_date"));

                    }

                    progressDialog2.dismiss();
                    walletuserdetailsadapter wl=new walletuserdetailsadapter(getActivity(),a1,a2,a3,a4,a5,a6,a7);
                    list.setAdapter(wl);


                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();

                    progressDialog2.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "" + volleyError, Toast.LENGTH_SHORT).show();
                progressDialog2.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> m = new HashMap<String, String>();
                m.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
//                m.put("from", from.getText().toString());
//                m.put("to", to.getText().toString());



                return m;
            }
        };
        sr2.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr2);
    }

}