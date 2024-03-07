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

public class kioskstatus extends Fragment{
    RequestQueue rq;
    ProgressDialog progressDialog;
    public static ArrayList a1;
    ArrayList a2,a3,a4,a5,a6,a7,a8,a9;
    ArrayList aa1,aa2,aa3,aa4,aa5,aa6,aa7,aa8,aa9;

    ListView listView;
    ImageView im;
    AutoCompleteTextView editText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_kioskstatus,container,false);
        rq= Volley.newRequestQueue(getContext());
        getdata();
        listView=v.findViewById(R.id.list);
        im=v.findViewById(R.id.imageView);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
            }
        });
        editText=v.findViewById(R.id.editText);
        editText.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,a1));
        editText.setThreshold(1);
//        editText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                aa1=new ArrayList();
//                aa2=new ArrayList();
//                aa3=new ArrayList();
//                aa4=new ArrayList();
//                aa5=new ArrayList();
//                aa6=new ArrayList();
//                aa7=new ArrayList();
//                aa8=new ArrayList();
//                aa9=new ArrayList();
//                int an=a1.indexOf(editText.getText().toString());
//                aa1.add(a1);
//                aa2.add(a2);
//                aa3.add(a3);
//                aa4.add(a4);
//                aa5.add(a5);
//                aa6.add(a6);
//                aa7.add(a7);
//                aa8.add(a8);
//                aa9.add(a9);
//                kioskadapter kk=new kioskadapter(getActivity(),a1,a2,a3,a4,a5,a6,a7,a8,a9);
//                listView.setAdapter(kk);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
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
        a9=new ArrayList();


        progressDialog = ProgressDialog.show(getContext(),"","Please wait...",false,false);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/getKioskStatus", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray ary=obj.getJSONArray("message");
                    for (int i=0;i<ary.length();i++)
                    {
                        JSONObject object=ary.getJSONObject(i);
                    a1.add(object.getString("code"));
                        a2.add(object.getString("Status"));
                        a3.add(object.getString("minute"));
                        a4.add(object.getString("location"));
                        a5.add(object.getString("lastHit"));
                        a6.add(object.getString("serialNumber"));
                        a7.add(object.getString("GuardName"));
                        a8.add(object.getString("GuardMobileNo"));
                        a9.add(object.getString("switched"));


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
    kioskadapter kk=new kioskadapter(getActivity(),a1,a2,a3,a4,a5,a6,a7,a8,a9);
    listView.setAdapter(kk);
//    FragmentManager fm=getFragmentManager();
//    FragmentTransaction ft2=fm.beginTransaction();
//    userdetail u= new userdetail();
//    ft2.replace(R.id.fl,u);
//    ft2.commit();
}

}
