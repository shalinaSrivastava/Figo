package com.Ecomaxgo.maxpay.kiosk_status;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.List;
import java.util.Locale;

/**
 * Created by Rohit on 19/01/2018.
 */

public class updatelocservice extends Service implements LocationListener {
    RequestQueue rq;
    TextToSpeech tts;
    String sadd;
   // public static String cityname;
    SharedPreferences preferences;
    LocationManager locationManager;
   public static Location location;
    @Override
    public void onCreate() {
        rq=Volley.newRequestQueue(getApplicationContext());
        preferences =getSharedPreferences("login",MODE_PRIVATE);

locationupdate();
    }
//

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    private void locationupdate(){



        ConnectivityManager conn= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=conn.getActiveNetworkInfo();
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(updatelocservice.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(updatelocservice.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//       location= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);

       // Toast.makeText(this, locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)+"", Toast.LENGTH_SHORT).show();
        }


    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }




    @Override
    public void onLocationChanged(Location location) {
        ConnectivityManager conn= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=conn.getActiveNetworkInfo();
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        if (location!=null && net!=null && net.isConnected()==true)
        {
            //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (null != addressList && addressList.size() > 0) {
                    sadd = addressList.get(0).getAddressLine(0);
                    //cityname=addressList.get(0).getLocality();
                }
               // Toast.makeText(this, sadd, Toast.LENGTH_SHORT).show();
                DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
//
                //String userid=mDatabase.push().getKey();
                usermodel u=new usermodel(preferences.getString("name",""),location.getLatitude()+","+location.getLongitude(),sadd);
                mDatabase.child("location").child(preferences.getString("name","")).setValue(u);

            } catch (Exception e) {
                //Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
            }
        }
        else {//Toast.makeText(this, "ok2", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
