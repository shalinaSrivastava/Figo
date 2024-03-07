package com.Ecomaxgo.maxpay.kiosk_status.Voucher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Ecomaxgo.maxpay.kiosk_status.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class loadreport extends Fragment {
    TextView t44,t45;
    RequestQueue rq;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    ArrayList enterydate,expAmt,expStatus,expDescription,message,expid;
    ArrayList<String> al;
    SwipeFlingAdapterView flingContainer;
    EditText tt;
    expensereportadapter arrayAdapter;
    int i;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_loadreport,container,false);
        rq=Volley.newRequestQueue(getContext());
        flingContainer = (SwipeFlingAdapterView) v.findViewById(R.id.frame);
        i=0;
        sp=getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        t44=v.findViewById(R.id.textView38);
        t45=v.findViewById(R.id.textView39);
//

        getdata();
        return v;
    }
    private void getdata()
    {
        enterydate=new ArrayList();
        expAmt=new ArrayList();
        expStatus=new ArrayList();
        expDescription=new ArrayList();
        message=new ArrayList();
        expid=new ArrayList();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        //RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/listAllAddedMoney",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
//                                    g.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            String status1 = obj.getString("status");
                            if(status1.equalsIgnoreCase("1")) {
                                JSONArray ary=obj.getJSONArray("data");
                                for (int i=0;i<ary.length();i++) {
                                    JSONObject obj1=ary.getJSONObject(i);
                                    enterydate.add(obj1.getString("loadEntryDate"));
                                    expAmt.add(obj1.getString("loadAmt"));
                                    expStatus.add(obj1.getString("loadStatus"));
                                    expDescription.add(obj1.getString("loadDescription"));
                                    message.add(obj1.getString("message"));
                                    expid.add(obj1.getString("loadId"));


                                }
                                t44.setText("Total money : "+obj.getString("withinDateTotal"));
                                t45.setText("All money : "+obj.getString("allOverTotal"));
                            }
                            else {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }



//                                    session.createLoginSession(id1, token1);

//                                    SM.sendData(id1);
//                                    SM.sendData(token1);

                            refine();

                        } catch (Exception e) {
                            Toast.makeText(getContext(), e + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(getContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //Adding parameters to request
                params.put("empId", loadmain.empid);
                params.put("from",loadmain.fromdate.getText().toString() );
                params.put("to", loadmain.todate.getText().toString());
                params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");


                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
    }
    private void refine()
    {
        arrayAdapter=new expensereportadapter(getActivity(),enterydate,expAmt,expStatus,expDescription,message);
        flingContainer.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                //Log.d("LIST", "removed object!");
                try {
                    enterydate.remove(0);
                    expAmt.remove(0);
                    expStatus.remove(0);
                    expDescription.remove(0);
                    message.remove(0);
                    expid.remove(0);

                    arrayAdapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Cards End", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                // Toast.makeText(getContext(), "Left!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                // Toast.makeText(getContext(), "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                al.add("XML ".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//               // Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float v) {

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(final int itemPosition, Object dataObject) {
//                ..//Toast.makeText(getContext(), "Clicked!");
//                AlertDialog.Builder ab=new AlertDialog.Builder(getContext());
//                tt= (EditText) new EditText(getContext());
//                ab.setView(tt);
//                ab.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        updatexpense(expid.get(itemPosition).toString(),"1","");
//                    }
//                });
//                ab.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        updatexpense(expid.get(itemPosition).toString(),"0",tt.getText().toString());
//                    }
//                });
//                ab.show();
            }
        });
    }
}