package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.Map;

public class Chatfragment extends Fragment {
    RequestQueue rq;
    ProgressDialog progressDialog;
    String id1;
    String message;
    String status;
    ArrayList a1,a2,a3,a4,a5,a6,a7,a8;
    ArrayList aa1,aa2,aa3,aa4,aa5,aa6,aa7,aa8,aa9;
    ListView listView;
    Button btnsend;

    EditText editText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.chatfragment,container,false);
        rq= Volley.newRequestQueue(getContext());

        listView=v.findViewById(R.id.listView);
        id1 = getArguments().getString("id");
        Toast.makeText(getContext().getApplicationContext(),id1,Toast.LENGTH_LONG).show();

        editText=v.findViewById(R.id.editText);

        btnsend=v.findViewById(R.id.btnsend);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(getContext(),"","Please wait...",false,false);
                StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/sendWorkProgressMsgForAdmin", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            status=obj.getString("status");
                            message=obj.getString("message");
                            Toast.makeText(getContext().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

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
                        m.put("skey","30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                        m.put("workId",id1);
                        m.put("message",editText.getText().toString());
                        m.put("image","");

                        return m;
                    }
                };
                sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                rq.add(sr);


            }
        });

        getdata();
        return v;
    }
    private void getdata()
    {
        a1=new ArrayList();
        a2=new ArrayList();
        a3=new ArrayList();
        a4=new ArrayList();
        a5=new ArrayList();
        a6=new ArrayList();
        a7=new ArrayList();
        a8=new ArrayList();
        progressDialog = ProgressDialog.show(getContext(),"","Please wait...",false,false);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/listWorkProgressMsgForAdmin", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray ary=obj.getJSONArray("message");
                    for (int i=0;i<ary.length();i++)
                    {
                        JSONObject object=ary.getJSONObject(i);
                        a1.add(object.getString("id"));
                        a2.add(object.getString("workId"));
                        a3.add(object.getString("Message"));
                        a4.add(object.getString("entryDate"));
                        a5.add(object.getString("image"));
                        a6.add(object.getString("empId"));
                        a7.add(object.getString("name"));
                        a8.add(object.getString("designation"));
                    }
                    progressDialog.dismiss();
                    refine();
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
                m.put("workId",id1);

                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);
    }
    private void refine()
    {
        Chatadapter kk=new Chatadapter(getActivity(),a1,a2,a3,a4,a5,a6,a7,a8);
        listView.setAdapter(kk);

    }

}
