package com.Ecomaxgo.maxpay.kiosk_status.Voucher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loadentry extends Fragment{
    RequestQueue rq;
    ProgressDialog progressDialog;
    TextView name,wallet,desc;
    EditText amt;
    Button submit;
    SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_loadentry,container,false);
        sp=getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        rq=Volley.newRequestQueue(getContext());
        name=v.findViewById(R.id.name);
        wallet=v.findViewById(R.id.wallet);
        desc=v.findViewById(R.id.desc);
        amt=v.findViewById(R.id.amt);
        submit=v.findViewById(R.id.submit);
        name.setText(loadmain.name);
        wallet.setText(loadmain.wallet);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amt.getText().toString().equals("")||amt.getText().toString().equals(" "))
                {
                    Toast.makeText(getContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
                    amt.setText("");
                    amt.requestFocus();
                }
                else {
                    loadmoney();
                }
            }
        });
        
        return v;
    }
    private  void loadmoney()
    {
//        Toast.makeText(getContext(), loadmain.empid, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), amt.getText().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), desc.getText().toString(), Toast.LENGTH_SHORT).show();
name.setText(loadmain.empid+" "+amt.getText().toString()+" "+desc.getText().toString());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        //RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/addMoneyToEmp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
//                                    g.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            if (obj.getString("status").equalsIgnoreCase("1"))
                            {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }
//getActivity().finish();



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
                params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                params.put("empId", loadmain.empid);
                params.put("amount", amt.getText().toString());
                params.put("description",desc.getText().toString() );



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
