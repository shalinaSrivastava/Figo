package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class WorkListFragment extends Fragment {

    ListView list1,l2;
    RequestQueue rq;
    ProgressDialog progressDialog;
    ArrayList a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,data;

    String fromdate,todate;
    DatePickerDialog.OnDateSetListener date;
    View v;

    SwipeRefreshLayout mSwipeRefreshLayout;
    TextToSpeech t1;
    String ed1="Please wait";
    Calendar myCalendar;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_work_list,container,false);
        list1=v.findViewById(R.id.list1);

//        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeToRefresh);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
        rq = Volley.newRequestQueue(getContext());


//        t1=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if(status != TextToSpeech.ERROR) {
//                    t1.setLanguage(Locale.UK);
//                }
//            }
//        });
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        String toSpeak = ed1 ;
//                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//                        refine();
//
//
//                    }
//                }, 2500);
//            }
//        });
//        mSwipeRefreshLayout.onFinishTemporaryDetach();
        getemplist();

        return v;
    }
    public void getemplist()
    {
        a1=new ArrayList();
        a2=new ArrayList();
        a3=new ArrayList();
        a4=new ArrayList();
        a5=new ArrayList();
        a6=new ArrayList();

        data=new ArrayList();

        progressDialog = ProgressDialog.show(getContext(),"","Please wait...",false,false);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/listAllWork", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray ary=obj.getJSONArray("work");
                    for (int i=0;i<ary.length();i++)
                    {

                        JSONObject object=ary.getJSONObject(i);
                        a1.add(object.getString("id"));
                        a2.add(object.getString("entryDate"));
                        a3.add(object.getString("work"));
                        a4.add(object.getString("image"));
                        a5.add(object.getString("status"));
                        a6.add(object.getString("updateDate"));
                        data.add(object.getString("id")+" "+object.getString("entryDate")+"   "+object.getString("work")+"\n"+object.getString("image")+"\n"+object.getString("status")+" "
                                +object.getString("updateDate"));



                    }
                    progressDialog.dismiss();
                    refine();
//                    l1.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,data));
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
        WorkListAdaptor kk=new WorkListAdaptor(getActivity(),a1,a2,a3,a4,a5,a6,WorkListFragment.this);
        list1.setAdapter(kk);

    }
}
