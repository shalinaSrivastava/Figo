package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
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

public class SimDetail extends Fragment {
    RequestQueue rq;
    ProgressDialog progressDialog;

    ArrayList a1,a2,a3,a4;
    ArrayList aa1,aa2,aa3,aa4,aa5,aa6,aa7,aa8,aa9;
    ListView listView;
    ImageView im;
    AutoCompleteTextView editText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_sim_detail,container,false);
        rq= Volley.newRequestQueue(getContext());

        listView=v.findViewById(R.id.listView);
        im=v.findViewById(R.id.imageView);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });
        editText=v.findViewById(R.id.editText);
        editText.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,a2));
        editText.setThreshold(1);
        getdata();
        return v;
    }
    private void getdata()
    {
        a1=new ArrayList();
        a2=new ArrayList();
        a3=new ArrayList();
        a4=new ArrayList();
        progressDialog = ProgressDialog.show(getContext(),"","Please wait...",false,false);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/kioskSimInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray ary=obj.getJSONArray("data");
                    for (int i=0;i<ary.length();i++)
                    {
                        JSONObject object=ary.getJSONObject(i);
                        a1.add(object.getString("kioskId"));
                        a2.add(object.getString("simNumber"));
                        a3.add(object.getString("nearBy"));
                        a4.add(object.getString("lastRecharge"));
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

                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);
    }
    private void refine()
    {
        simadapter kk=new simadapter(getActivity(),a1,a2,a3,a4);
        listView.setAdapter(kk);

    }

}
