package com.Ecomaxgo.maxpay.kiosk_status;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Loginactivity extends AppCompatActivity {
    EditText number, password2;
    Button b;
    RequestQueue rq;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    LocationManager locationManager;
    Location location;
    TextToSpeech tts;
    AdView mAdView;

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Loginactivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        // int result2 = ContextCompat.checkSelfPermission(Loginactivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Loginactivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(Loginactivity.this, "Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            //ActivityCompat.requestPermissions(Loginactivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(Loginactivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Log.e("value", "Permission Granted, Now you can use local drive .");
//                    new FetchAppVersionFromGooglePlayStore().execute();
                    work();
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkPermission()) {
            work();

        } else {
            requestPermission();
        }


    }

    private void work() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location!=null)
        {
            setContentView(R.layout.activity_loginactivity);
            MobileAds.initialize(this, "ca-app-pub-5258083952445606~8114830990");
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            rq=Volley.newRequestQueue(this);
            number=findViewById(R.id.etemailid);
            password2=findViewById(R.id.etpassword);
            b=findViewById(R.id.btnCapturePicture);
            sp=getSharedPreferences("login",MODE_PRIVATE);
            try{
                if (sp.getString("id","").equalsIgnoreCase(""))
                {
                    login();
                }
                else {
                    startActivity(new Intent(Loginactivity.this,MainActivity.class));
                    finish();
                }
            }
            catch (Exception e)
            {

            }
        }
        else {
            Toast.makeText(this, "Open Your GPS", Toast.LENGTH_SHORT).show();
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(1000);
            }
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    // TODO Auto-generated method stub
                    if (status == TextToSpeech.SUCCESS) {
                        tts.speak("Please On GPS", TextToSpeech.QUEUE_FLUSH, null);

                    } else
                        Log.e("error", "Initilization Failed!");
                }

            });
            finish();
        }


    }
    private void login()
    {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = number.getText().toString().trim();
                final String password = password2.getText().toString().trim();
                boolean b1 = false, b2 = false, b3 = false, b4 = false;

                if (number.getText().toString().equals("") || number.getText().toString().length() < 10) {
                    // Toast.makeText(Login.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                    b1 = true;

                    Toast.makeText(Loginactivity.this, "Please Enter 10 Digit Phone Number", Toast.LENGTH_SHORT).show();
                    b2 = true;
//                    g.setVisibility(View.GONE);
                }
                else if (password2.getText().toString().equals("")) {
                    Toast.makeText(Loginactivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    b3 = true;
//                    g.setVisibility(View.GONE);
                }
//                }if (small  && num)
//                {
//                    b4=true;
////                    g.setVisibility(View.GONE);
//                }
//                else
//                {
//                    Toast.makeText(getActivity(), "Please Enter 8 Digit AlphaNumeric Password", Toast.LENGTH_SHORT).show();
////                    g.setVisibility(View.GONE);
//                }

//                Toast.makeText(getContext(), etemailid.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), etpassword.getText().toString(), Toast.LENGTH_SHORT).show();
                progressDialog = new ProgressDialog(Loginactivity.this);
                progressDialog.setMessage("please wait...");
                progressDialog.show();
                //RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/employeeLogin",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressDialog.dismiss();
                                try {
//                                    g.setVisibility(View.GONE);
                                    JSONObject obj = new JSONObject(response);
                                    String status1 = obj.getString("status");
                                    String message1 = obj.getString("message");
                                    String title1 = obj.getString("title");
                                    String name1 = obj.getString("name");
                                    String mobile1 = obj.getString("mobile");
                                    String desination1 = obj.getString("desination");
                                    String image1 = obj.getString("image");
                                    String id1 = obj.getString("id");
                                    String token1 = obj.getString("token");
                                    Toast.makeText(Loginactivity.this, "successfully login", Toast.LENGTH_LONG).show();

//                                    session.createLoginSession(id1, token1);

//                                    SM.sendData(id1);
//                                    SM.sendData(token1);


                                    edt=sp.edit();
                                    edt.putString("no",number.getText().toString());
                                    edt.putString("id",id1);
                                    edt.putString("name",name1 );
                                    edt.putString("desination",desination1 );

                                    edt.putString("token",token1);
                                    edt.commit();
                                    Intent intent = new Intent(Loginactivity.this, MainActivity.class);
//                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    Toast.makeText(Loginactivity.this, e + "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {


                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                Toast.makeText(Loginactivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        //Adding parameters to request
                        params.put("phonenumber", number.getText().toString());
                        params.put("password", password2.getText().toString());

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                rq.add(stringRequest);
            }
        });
    }
}
