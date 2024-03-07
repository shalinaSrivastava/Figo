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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class userdetail extends Fragment {
    AutoCompleteTextView auto;
    ImageView im;
    TextView t1, t2, t3;
    RequestQueue rq;
    ProgressDialog progressDialog;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf;
    String s;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_userdetail, null, true);
        auto = v.findViewById(R.id.editText);
        im = v.findViewById(R.id.imageView);
        t1 = v.findViewById(R.id.textView8);
        t2 = v.findViewById(R.id.textView9);
        t3 = v.findViewById(R.id.textView11);
        rq = Volley.newRequestQueue(getContext());
        sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        t1.setText(sdf.format(new Date()));
        t1.setOnClickListener(new View.OnClickListener() {
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
                                t1.setText(new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(myCalendar.getTime()));
                                getData();
                            }

                        };

                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        getData();
        auto.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,kioskstatus.a1));
        auto.setThreshold(1);
        auto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        return v;
    }

    private void getData() {
        if (auto.getText().toString().equals(""))
        {
            s="";

        }
        else {
            s = auto.getText().toString();
        }
        progressDialog = ProgressDialog.show(getContext(), "", "Please wait...", false, false);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/getKioskUserDetail", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);


                    progressDialog.dismiss();
                    t2.setText(obj.getString("noOfUser"));
                    t3.setText(obj.getString("totalNoOfUser"));

                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
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
                m.put("date", t1.getText().toString());
                m.put("regByKiosk", s);



                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);
    }


}
