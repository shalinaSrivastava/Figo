package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class kioskadapter extends BaseAdapter{

    ArrayList a1,a2,a3,a4,a5,a6,a7,a8,a9;
    Activity c;
    RequestQueue rq;
    ProgressDialog progressDialog;
    public kioskadapter(){}
    public kioskadapter(Activity c,ArrayList a1,ArrayList a2,ArrayList a3,ArrayList a4,ArrayList a5,ArrayList a6,ArrayList a7,ArrayList a8,ArrayList a9)
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
        this.a9=a9;
        rq= Volley.newRequestQueue(c);

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
        View v=l.inflate(R.layout.activity_kioskadapter,null,true);
        TextView t,t2,t3,t4,t5,t6,t7;
        Switch s;
        t=v.findViewById(R.id.textView);
        t2=v.findViewById(R.id.textView2);
        t3=v.findViewById(R.id.textView3);
        t4=v.findViewById(R.id.textView4);
        t5=v.findViewById(R.id.textView5);
        t6=v.findViewById(R.id.textView6);
        t7=v.findViewById(R.id.textView7);
        s=v.findViewById(R.id.switchbtn);
        t.setText(a1.get(i).toString());
        t2.setText(a2.get(i).toString());
        t3.setText(a3.get(i).toString());
        t4.setText(a4.get(i).toString());
        t5.setText(a5.get(i).toString());
        t6.setText(a6.get(i).toString());
        t7.setText(a7.get(i).toString()+" : "+a8.get(i).toString());
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:"+a8.get(i).toString()));
                c.startActivity(in);
            }
        });
        if (a9.get(i).toString().equals("on"))
        {
            s.setChecked(true);
        }
        else
        {
            s.setChecked(false);
        }
//        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                progressDialog = ProgressDialog.show(c,"","Please wait...",false,false);
//                StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/kioskSwitch", new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//                        try {
//                            JSONObject obj = new JSONObject(s);
//                            JSONArray ary=obj.getJSONArray("message");
//                            for (int i=0;i<ary.length();i++)
//                            {
//                                JSONObject object=ary.getJSONObject(i);
//                                a1.add(object.getString("code"));
//                                a2.add(object.getString("Status"));
//                                a3.add(object.getString("minute"));
//                                a4.add(object.getString("location"));
//                                a5.add(object.getString("lastHit"));
//                                a6.add(object.getString("serialNumber"));
//                                a7.add(object.getString("GuardName"));
//                                a8.add(object.getString("GuardMobileNo"));
//                                a9.add(object.getString("switched"));
//
//
//                            }
//                            progressDialog.dismiss();
//                            refine();
//                        } catch (Exception e) {
//                            Toast.makeText(c, "" + e, Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(c, "" + volleyError, Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        HashMap<String, String> m = new HashMap<String, String>();
//                        m.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
//
//                        return m;
//                    }
//                };
//                sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                rq.add(sr);
//            }
//        });
        return v;
    }
}