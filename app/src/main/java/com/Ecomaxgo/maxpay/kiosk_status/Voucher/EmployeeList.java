package com.Ecomaxgo.maxpay.kiosk_status.Voucher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.Ecomaxgo.maxpay.kiosk_status.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeList extends AppCompatActivity {
    ListView list;
    RequestQueue rq;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    ArrayList name,id,wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        rq=Volley.newRequestQueue(this);
        list=findViewById(R.id.list);
        sp=getSharedPreferences("login",MODE_PRIVATE);
        getdata();
    }
    private void getdata()
    {
        name=new ArrayList();
        id=new ArrayList();
        wallet=new ArrayList();


        progressDialog = new ProgressDialog(EmployeeList.this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        //RequestQueue requestQueue = Volley.newRequestQueue(EmployeeList.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/listEmp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
//                                    g.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("status").equalsIgnoreCase("1"))
                            {
                                JSONArray ary=obj.getJSONArray("data");
                                for (int i=0;i<ary.length();i++)
                                {
                                    JSONObject obj1=ary.getJSONObject(i);
                                    name.add(obj1.getString("name")+"\n"+obj1.getString("wallet")+"\n");
                                    id.add(obj1.getString("id"));
                                    wallet.add(obj1.getString("wallet"));


                                }
                                list.setAdapter(new ArrayAdapter<String>(EmployeeList.this,android.R.layout.simple_list_item_1,name));
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long ida) {
                                        Intent in=new Intent(EmployeeList.this,loadmain.class);
                                        in.putExtra("empid",id.get(position).toString());
                                        in.putExtra("wallet",wallet.get(position).toString());
                                        in.putExtra("name",name.get(position).toString());


                                        startActivity(in);
                                    }
                                });
                            }
                            else {
                                Toast.makeText(EmployeeList.this, "Failed", Toast.LENGTH_SHORT).show();
                            }




                        } catch (Exception e) {
                            Toast.makeText(EmployeeList.this, e + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(EmployeeList.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //Adding parameters to request
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
}
