package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class LeaveRequest  extends Fragment{
    TextView from,to,t27,t28,t29;
    RequestQueue rq;
    ProgressDialog progressDialog;
    ListView list;
    Calendar myCalendar;
    String status;
    String id,token;
//    SessionManager session;
    String message,id1,token1,aa1,aa2,aa3,aa4,aa5,aa6,aa7,aa8;
    EditText etleavedetails;
    DatePickerDialog.OnDateSetListener date;
    ArrayList a1,a2,a3,a4,a5,a6,a7;
    SimpleDateFormat sdf;

    public static Fragment newInstance(String s) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_leave_request,container,false);
        from=v.findViewById(R.id.from);
        etleavedetails = v.findViewById(R.id.etleavedetails);
        etleavedetails.clearFocus();

        id1 = getArguments().getString("id1");
        token1 = getArguments().getString("token1");
        to=v.findViewById(R.id.to);
        rq= Volley.newRequestQueue(getContext());
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
//                                getData();
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
//                                getData();
                            }

                        };

                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Button btnsubmit1 = (Button) v.findViewById(R.id.btnsubmit1);
        btnsubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vId=v.getId();

                String fromdate=from.getText().toString().trim();
                String todate=to.getText().toString().trim();
                String leavedetails=etleavedetails.getText().toString().trim();

                if (vId==R.id.btnsubmit1) {

                    getData();
                }

            }
        });
//        getData();

        return v;

//        etleavedetails.getText();
//
//        btnsubmit1 = (Button) v.findViewById(R.id.btnsubmit1);
//        btnsubmit1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                getData();
//
//            }
//        });
    }




    private void getData() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.show();

//        HashMap<String, String> user = session.getUserDetails();

        // name
//        id = user.get(SessionManager.KEY_ID);
//
//         email
//        token = user.get(SessionManager.KEY_TOKEN);
//        progressDialog = ProgressDialog.show(getContext(), "", "Please wait...", false, false);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/empLeave", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                progressDialog.dismiss();
                try {


                    JSONObject jsonObject=new JSONObject(s);
                    status = jsonObject.getString("status");
                    message = jsonObject.getString("message");
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();

                params.put("id",id1);
                params.put("token", token1);
                params.put("from",from.getText().toString());
                params.put("to", to.getText().toString());
                params.put("brief", etleavedetails.getText().toString());

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
//        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


//        progressDialog.dismiss();


    }



//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    TextView from, to;
//    EditText etleavedetails;
//    SimpleDateFormat sdf;
//    RequestQueue rq;
//    ProgressDialog progressDialog;
//    Calendar myCalendar;
//    Button btnsubmit1;
//    DatePickerDialog.OnDateSetListener date;
//    private String mParam1;
//    private String mParam2;
//
//
////    private OnFragmentInteractionListener mListener;
//
////    public LeaveRequest() {
////    }
//
//
////    public static LeaveRequest newInstance(String param1, String param2) {
////        LeaveRequest fragment = new LeaveRequest();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
////        return fragment;
////    }
////
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
////        }
////    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_leave_request, container, false);
//
//        etleavedetails = v.findViewById(R.id.etleavedetails);
//        etleavedetails.getText();
//
//        btnsubmit1 = (Button) v.findViewById(R.id.btnsubmit1);
//        btnsubmit1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                getData();
//
//            }
//        });
//
//
//        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
//        from.setText(sdf.format(new Date()));
//        to.setText(sdf.format(new Date()));
//
//        from.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myCalendar = Calendar.getInstance();
//                date = new
//                        DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                                  int dayOfMonth) {
//                                // TODO Auto-generated method stub
//                                myCalendar.set(Calendar.YEAR, year);
//                                myCalendar.set(Calendar.MONTH, monthOfYear);
//                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                                from.setText(new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(myCalendar.getTime()));
////                                getData();
//                            }
//
//                        };
//
//                new DatePickerDialog(getContext(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        to.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myCalendar = Calendar.getInstance();
//                date = new
//                        DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                                  int dayOfMonth) {
//                                // TODO Auto-generated method stub
//                                myCalendar.set(Calendar.YEAR, year);
//                                myCalendar.set(Calendar.MONTH, monthOfYear);
//                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                                to.setText(new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(myCalendar.getTime()));
////                                getData();
//                            }
//
//                        };
//
//                new DatePickerDialog(getContext(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
////        getData();
//        return v;
//
//
//    }
//
//
//    private void getData() {
//
//        String leavedetails = etleavedetails.toString().trim();
//
//
//        progressDialog = ProgressDialog.show(getContext(), "", "Please wait...", false, false);
//        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/walletUser", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//
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
//                m.put("id ", "");
//                m.put("token ", "");
//                m.put("from", from.getText().toString());
//                m.put("to", to.getText().toString());
//                m.put("brief", etleavedetails.getText().toString());
//
//
//                return m;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        rq.add(sr);
//
//
//    }
//
////    public void onButtonPressed(Uri uri) {
////        if (mListener != null) {
////            mListener.onFragmentInteraction(uri);
////        }
////    }
//
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        if (context instanceof OnFragmentInteractionListener) {
////            mListener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
////    }
//
////    @Override
////    public void onDetach() {
////        super.onDetach();
////        mListener = null;
////    }
//
//
////    public interface OnFragmentInteractionListener {
////        void onFragmentInteraction(Uri uri);
////    }
}
