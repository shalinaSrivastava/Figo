package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class trackuser extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {
    WebView web;
    private GoogleMap mMap;
    AutoCompleteTextView servyReprt;
    ProgressDialog progressDialog2;
    RequestQueue rq;
    ArrayList id,name,phonenumber,address,gender,device,entry_date,gpsLoc,gpsLocAddress,gpsTime;
    FragmentManager fm;
    ArrayList lat,lang;
    String key="30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackuser);
        id=new ArrayList();
        rq= Volley.newRequestQueue(this);
        name=new ArrayList();
        phonenumber=new ArrayList();
        address=new ArrayList();
        gender=new ArrayList();
        device=new ArrayList();
        entry_date=new ArrayList();
        gpsLoc=new ArrayList();
        gpsLocAddress=new ArrayList();
        gpsTime=new ArrayList();
        lat=new ArrayList();
        lang=new ArrayList();
//        servyReprt=findViewById(R.id.autoCompleteTextView2);
//        servyReprt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        progressDialog2 = ProgressDialog.show(trackuser.this,"","Please wait...",false,false);
        StringRequest sr=new StringRequest(Request.Method.POST,"https://newapi.maxpaywallet.com/index.php/Testing/Panel_Rest/walletUserListGPS", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject obj = new JSONObject(response);
                    JSONArray ary=obj.getJSONArray("data");
                    for (int i=0;i<ary.length();i++)
                    {
                        JSONObject obj1=ary.getJSONObject(i);
                        id.add(obj1.getString("id"));
                        name.add(obj1.getString("username"));
                        phonenumber.add(obj1.getString("phonenumber"));
                        address.add(obj1.getString("address"));
                        device.add(obj1.getString("device"));
                        entry_date.add(obj1.getString("entry_date"));
                        lat.add(obj1.getString("latitude"));
                        lang.add(obj1.getString("longitude"));
                        gpsLocAddress.add(obj1.getString("gpsLocAddress"));
                        gpsTime.add(obj1.getString("gpsTime"));
//try {
//
//
//    lat.add(obj1.getString("gpsLoc").substring(0, obj1.getString("gpsLoc").indexOf(",")));
//    lang.add(obj1.getString("gpsLoc").substring(obj1.getString("gpsLoc").indexOf(","), obj1.getString("gpsLoc").length() - 1));
//}
//catch (Exception e)
//{
//
//}
                    }

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.fragment2);
                    mapFragment.getMapAsync(trackuser.this);
                    progressDialog2.dismiss();
                }
                catch (Exception e)
                {
                    Toast.makeText(trackuser.this, "Data Connection Lost"+e, Toast.LENGTH_SHORT).show();
                    progressDialog2.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(trackuser.this, ""+error, Toast.LENGTH_SHORT).show();
                progressDialog2.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("skey","30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                return map;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(70 * 2000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);
    }




    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat.get(0).toString()),Double.parseDouble(lang.get(0).toString())),14) );

        for (int i=0;i<id.size();i++)
        {

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(lat.get(i).toString()),Double.parseDouble(lang.get(i).toString())))
                    .title(name.get(i).toString())




            ).setTag(i);
        }
        // Add some markers to the map, and add a data object to each marker.
//        mCP = mMap.addMarker(new MarkerOptions()
//                .position(CP)
//                .title("Canught Place")
//                );
//        mCP.setTag(0);
//
//
//        mREDFORT = mMap.addMarker(new MarkerOptions()
//                .position(REDFORT)
//                .title("Red Fort"));
//        mREDFORT.setTag(0);
//
//        mINDIAGATE = mMap.addMarker(new MarkerOptions()
//                .position(INDIAGATE)
//                .title("India Gate"));
//        mINDIAGATE.setTag(0);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);

//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
//        Integer clickCount = (Integer) marker.getTag();
//
//        // Check if a click count was set, then display the click count.
//        if (clickCount != null) {
//            clickCount = clickCount + 1;
//            marker.setTag(clickCount);
//            Toast.makeText(this,
//                    marker.getTitle() +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show();
//        position = marker.getPosition().latitude + "," + marker.getPosition().longitude;
        return false;
    }

}