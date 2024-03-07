package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class WastCollection extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {
    RequestQueue rq;
    ProgressDialog progressDialog;

    ArrayList a1, a2, a3, a4;
    EditText etenteryname, etkioskid, etrecyclecomapne, etcollectingperson, etremark;
    EditText spinnernumberofPlastics, spinnernumberofglasses, spinnernumberoftins;
    Button btnsumbmit;
    String status ,plastic="",glass="",tin="";
    String message ;
    ImageView im;
    AutoCompleteTextView editText;
    SharedPreferences sp;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wast_collection, container, false);
sp=getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        etenteryname = v.findViewById(R.id.etenteryname);
//        etenteryname.setText(toString());
        etenteryname.setText(sp.getString("name",""));

        etkioskid = v.findViewById(R.id.etkioskid);
//        etkioskid.setText(toString());

        etrecyclecomapne = v.findViewById(R.id.etrecyclecomapne);
//        etrecyclecomapne.setText(toString());

        etcollectingperson = v.findViewById(R.id.etcollectingperson);
//        etcollectingperson.setText(toString());

        spinnernumberofPlastics = v.findViewById(R.id.spinnernumberofPlastics);


        spinnernumberofglasses = v.findViewById(R.id.spinnernumberofglasses);


        spinnernumberoftins = v.findViewById(R.id.spinnernumberoftins);


        etremark = v.findViewById(R.id.etremark);






        btnsumbmit = v.findViewById(R.id.btnsumbmit);
        btnsumbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                etcollectingperson.getText().clear();
//                etenteryname.getText().clear();
//                etkioskid.getText().clear();
//                etremark.getText().clear();
//                etrecyclecomapne.getText().clear();

                 String name = etenteryname.getText().toString().trim();
                 String kioskid = etkioskid.getText().toString().trim();
                 String comapanyname = etrecyclecomapne.getText().toString().trim();
                 String Collectingperson = etcollectingperson.getText().toString().trim();
                 String Remark = etremark.getText().toString().trim();


                if (TextUtils.isEmpty(name)) {
                    etenteryname.setError("Please enter name");
                    return;
                }

               else if (TextUtils.isEmpty(kioskid)) {
                    etkioskid.setError("please enter kiosk_id");
                    return;
                }
               else if (TextUtils.isEmpty(comapanyname)) {
                    etrecyclecomapne.setError("please enter your company name");
                    return;
                }
               else if (TextUtils.isEmpty(Collectingperson)) {
                    etcollectingperson.setError("enter collection person name");

                    return;
                }
                else if(spinnernumberofPlastics.getText().toString().equalsIgnoreCase(""))
                    {
                        plastic="0";
                    }
                else if(spinnernumberofglasses.getText().toString().equalsIgnoreCase(""))
                {
                    glass="0";
                }
                else if(spinnernumberoftins.getText().toString().equalsIgnoreCase(""))
                {
                    tin="0";
                }
                else {


                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Uploading, please wait...");
                    progressDialog.show();
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/wasteCollection", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            progressDialog.dismiss();

                            try {
                                JSONObject obj = new JSONObject(s);
                                status = obj.getString("status");
                                message = obj.getString("message");
                                Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            progressDialog.dismiss();
                            VolleyLog.d(TAG, "Error: " + volleyError.getMessage());


                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                            params.put("entryName", etenteryname.getText().toString());
                            params.put("kioskId", etkioskid.getText().toString());
                            params.put("noOfPlastics", plastic);
                            params.put("noOfGlasses", glass);
                            params.put("noOfTins", tin);
                            params.put("recycleCompany", etrecyclecomapne.getText().toString());
                            params.put("collectingPerson", etcollectingperson.getText().toString());
                            params.put("remark", etremark.getText().toString());
                            return params;
                        }


                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(stringRequest);
                }

            }

        });

//        rq= Volley.newRequestQueue(getContext());


//        im=v.findViewById(R.id.imageView);
//        im.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getdata();
//            }
//        });
//        editText=v.findViewById(R.id.editText);
//        editText.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,a2));
//        editText.setThreshold(1);
//        getdata();
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void getdata()
//    {
//        a1=new ArrayList();
//        a2=new ArrayList();
//        a3=new ArrayList();
//        a4=new ArrayList();
//        progressDialog = ProgressDialog.show(getContext(),"","Please wait...",false,false);
//        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/kioskSimInfo", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                try {
//                    JSONObject obj = new JSONObject(s);
//                    JSONArray ary=obj.getJSONArray("data");
//                    for (int i=0;i<ary.length();i++)
//                    {
//                        JSONObject object=ary.getJSONObject(i);
//                        a1.add(object.getString("kioskId"));
//                        a2.add(object.getString("simNumber"));
//                        a3.add(object.getString("nearBy"));
//                        a4.add(object.getString("lastRecharge"));
//                    }
//                    progressDialog.dismiss();
////                    refine();
//                } catch (Exception e) {
//                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getContext(), "" + volleyError, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> m = new HashMap<String, String>();
//                m.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
//
//                return m;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        rq.add(sr);
//    }
//    private void refine()
//    {
//        simadapter kk=new simadapter(getActivity(),a1,a2,a3,a4);
//        listView.setAdapter(kk);

//    }

}
