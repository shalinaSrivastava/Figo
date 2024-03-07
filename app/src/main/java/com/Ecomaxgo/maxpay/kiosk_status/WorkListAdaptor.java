package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkListAdaptor extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    ArrayList a1, a2, a3, a4, a5, a6;
    ArrayList aa1, aa2, aa3, aa4, aa5, aa6, aa7, aa8, a9, a10, data;
    ArrayList aaa1, aaa2, aaa3, aaa4, aaa5, aaa6, aaa7, aaa8, aaa9, aaa10;
    Activity c;
    final String a = null;
    RequestQueue rq;
    String status, message, works;
    public static String value;
    Context context;
    FragmentManager fm;
    ListView listView;
    String id;
    CheckBox checkBox;
    RequestQueue queue;
    RequestQueue queue1;
    ProgressDialog progressDialog;
    private View l1;
    Fragment work1;

    public WorkListAdaptor() {
    }

    public WorkListAdaptor(Activity c, ArrayList a1, ArrayList a2, ArrayList a3, ArrayList a4, ArrayList a5, ArrayList a6, Fragment work) {
        this.c = c;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
        this.work1=work;

        // rq= Volley.newRequestQueue(c);

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
        LayoutInflater l = c.getLayoutInflater();
        View v = l.inflate(R.layout.worklistadapter, null, true);
        TextView t, t2, t3, t4, t6, t7;
        Button btndone;

        CardView btncardview;
        ImageView t5;
        t = v.findViewById(R.id.workDetails);
        t2 = v.findViewById(R.id.entrydate);
        t3 = v.findViewById(R.id.workstatus);
        t4 = v.findViewById(R.id.updatedate);
        btncardview = v.findViewById(R.id.btncardview);
        btndone=v.findViewById(R.id.btndone);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue1 = Volley.newRequestQueue(c);
                StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/updateWorkStatus", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {


                            JSONObject jsonObject = new JSONObject(s);
                            status = jsonObject.getString("status");
                            message = jsonObject.getString("message");
                            Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
                            ((WorkListFragment) work1).getemplist();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(c, "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                        params.put("workId", a1.get(i).toString());
                        params.put("status", "0");
                        return params;
                    }
                };
                sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue1.add(sr);
            }
        });


//        t5 = v.findViewById(R.id.updateimage);

        t6 = v.findViewById(R.id.addemploye);
        t7 = v.findViewById(R.id.removeempoye);
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                aa1 = new ArrayList();
                aa2 = new ArrayList();
                aa3 = new ArrayList();
                aa4 = new ArrayList();
                aa5 = new ArrayList();
                aa6 = new ArrayList();
                aa7 = new ArrayList();
                aa8 = new ArrayList();
                data = new ArrayList();
                RequestQueue queue = Volley.newRequestQueue(c);
                StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/listEmp", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        try {
                            JSONObject obj = new JSONObject(s);
                            JSONArray ary = obj.getJSONArray("data");
                            for (int i = 0; i < ary.length(); i++) {
                                JSONObject object = ary.getJSONObject(i);
                                aa1.add(object.getString("id"));
                                aa2.add(object.getString("title"));
                                aa3.add(object.getString("name"));
                                aa4.add(object.getString("entryDate"));
                                aa5.add(object.getString("desination"));
                                aa6.add(object.getString("image"));
                                aa7.add(object.getString("wallet"));
                                aa8.add(object.getString("status"));
                                data.add(object.getString("id") + " " + object.getString("title") + "   " + object.getString("name") + "\n" + object.getString("entryDate") + "\n" + object.getString("desination") + " " + object.getString("image") + " " + object.getString("wallet") + "" + object.getString("status"));


                            }


                            LayoutInflater li = LayoutInflater.from(c);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                            View dialog = li.inflate(R.layout.custom, null);
                            alertDialogBuilder.setView(dialog);
                            Button dialogButton = (Button) dialog.findViewById(R.id.btnsubmit);
                            listView = (ListView) dialog.findViewById(R.id.employelist);
                            listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
                            listView.setTextFilterEnabled(true);
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                            String string=adapter.getItem(position);
//
//                            value = (String)a1.get(position).toString();
//
//                            Toast.makeText(getContext().getApplicationContext(),value,Toast.LENGTH_LONG).show();
//
//                        }
//                    });
                            setListAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_list_item_checked, a1));
                            alertDialogBuilder.setCancelable(false).setPositiveButton("Assign", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int id) {
                                    queue1 = Volley.newRequestQueue(c);
                                    StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/addEmpToAssignedWork", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {

                                            try {


                                                JSONObject jsonObject = new JSONObject(s);
                                                status = jsonObject.getString("status");
                                                message = jsonObject.getString("message");
                                                works = jsonObject.getString("work");
                                                Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                            Toast.makeText(c, "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> params = new HashMap<String, String>();

                                            params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                                            params.put("workId", a1.get(i).toString());
                                            params.put("empIds", value);
                                            return params;
                                        }
                                    };
                                    sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    queue1.add(sr);
                                    dialog.dismiss();
                                }
                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            String status1 = obj.getString("status");
                            String message1 = obj.getString("message");
                            String work = obj.getString("work");


                        } catch (Exception e) {
                            Toast.makeText(c, "" + e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    private void setListAdapter(ArrayAdapter<String> stringArrayAdapter) {
                        Employedatalistadapter kk = new Employedatalistadapter((Activity) c, aa1, aa2, aa3, aa4, aa5, aa6, aa7, aa8);
                        listView.setAdapter(kk);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(c, "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> m = new HashMap<String, String>();
                        m.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                        return m;
                    }
                };
                sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(sr);

            }
        });
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaa1 = new ArrayList();
                aaa2 = new ArrayList();
                aaa3 = new ArrayList();
                aaa4 = new ArrayList();
                aaa5 = new ArrayList();
                aaa6 = new ArrayList();
                aaa7 = new ArrayList();
                aaa8 = new ArrayList();
                data = new ArrayList();
                queue = Volley.newRequestQueue(c);
                StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/listAllWork", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        try {
                            JSONObject obj = new JSONObject(s);

                            JSONArray ary = obj.getJSONArray("co-worker");

                            for (int i = 0; i < ary.length(); i++) {
                                JSONArray ary1 = ary.getJSONArray(i);
                                for (int j = 0; j < ary1.length(); j++) {
                                    JSONObject object = ary1.getJSONObject(j);
                                    aaa1.add(object.getString("empId"));
                                    aaa2.add(object.getString("title"));
                                    aaa3.add(object.getString("name"));
                                    aaa4.add(object.getString("mobile"));
                                    aaa5.add(object.getString("assignDate"));

                                    data.add(object.getString("empId") + " " + object.getString("title") + "   " + object.getString("name") + "\n" + object.getString("mobile") + "\n" + object.getString("assignDate"));
                                }
                            }

                            LayoutInflater li = LayoutInflater.from(c);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
                            View dialog = li.inflate(R.layout.custom, null);
                            alertDialogBuilder.setView(dialog);
                            Button dialogButton = (Button) dialog.findViewById(R.id.btnsubmit);
                            listView = (ListView) dialog.findViewById(R.id.employelist);
                            listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
                            listView.setTextFilterEnabled(true);
//
                            setListAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_list_item_checked, a1));
                            alertDialogBuilder.setCancelable(false).setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int id) {
                                    RequestQueue queue = Volley.newRequestQueue(c);
                                    StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/removeEmpFromAssignedWork", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                status = jsonObject.getString("status");
                                                message = jsonObject.getString("message");
                                                works = jsonObject.getString("work");
                                                Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                            Toast.makeText(c, "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> params = new HashMap<String, String>();

                                            params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                                            params.put("workId", a1.get(i).toString());
                                            params.put("empIds", value);


                                            return params;
                                        }
                                    };
                                    sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                    queue.add(sr);
                                    dialog.dismiss();
                                }
                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });


                            AlertDialog alertDialog = alertDialogBuilder.create();

                            alertDialog.show();

                            String status1 = obj.getString("status");
                            String message1 = obj.getString("message");
                            String work = obj.getString("work");
                        } catch (Exception e) {
                            Toast.makeText(c, "" + e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    private void setListAdapter(ArrayAdapter<String> stringArrayAdapter) {
                        Employedatalistadapter kk = new Employedatalistadapter((Activity) c, aaa1, aaa2, aaa3, aaa4, aaa5, aaa6, aaa7, aaa8);
                        listView.setAdapter(kk);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(c, "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> m = new HashMap<String, String>();
                        m.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                        return m;
                    }
                };
                sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(sr);


            }
        });


        btncardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm =work1.getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("id",a1.get(i).toString());
                FragmentTransaction fragmenntwallet = fm.beginTransaction();
                Chatfragment w = new Chatfragment();
                w.setArguments(bundle);
                fragmenntwallet.replace(R.id.fl, w);
                fragmenntwallet.addToBackStack(null);
                fragmenntwallet.commit();


            }
        });

        t.setText(a3.get(i).toString());
        t2.setText(a2.get(i).toString());
        t3.setText(a5.get(i).toString());
        t4.setText(a6.get(i).toString());
//        a.concat(a1.get(i).toString());
//        t5.setImageDrawable(Drawable.createFromPath(a4.get(i).toString()));


        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


