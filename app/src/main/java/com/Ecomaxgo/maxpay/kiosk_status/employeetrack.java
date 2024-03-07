package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class employeetrack extends AppCompatActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {
    private GoogleMap mMap;
    AutoCompleteTextView servyReprt;
    ProgressDialog progressDialog2;
    RequestQueue rq;
    ArrayList id,name,phonenumber,address,gender,device,entry_date,gpsLoc,gpsLocAddress,gpsTime;
    FragmentManager fm;
    ArrayList lat,lang;
    ArrayList<usermodel> uu;
    String key="30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeetrack);
        DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("location").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Iterable<DataSnapshot> ad=dataSnapshot.getChildren();
////                uu=new ArrayList<>();
////                for (int p=0;p<dataSnapshot.getChildrenCount();p++)
////                {
////                    uu.add(dataSnapshot.);
////                }

//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        mDatabase.child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uu=new ArrayList<>();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {

                    uu.add(ds.getValue(usermodel.class));
                }
               // Toast.makeText(employeetrack.this, uu.get(0).location, Toast.LENGTH_SHORT).show();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragment2);
                mapFragment.getMapAsync(employeetrack.this);
//                usermodel user = dataSnapshot.getValue(usermodel.class);
//                Toast.makeText(employeetrack.this, user.toString(), Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "User name: " + user.getName() + ", email " + user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(employeetrack.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onMapReady(GoogleMap googleMap) {
//        mMap.clear();

        mMap = googleMap;
mMap.clear();
        String lat[]=uu.get(0).location.split((","));
        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat[0]),Double.parseDouble(lat[1])),16) );

        for (int i=0;i<uu.size();i++)
        {
            //BitmapDescriptor bmp=BitmapDescriptorFactory.fromPath("https://cdn3.iconfinder.com/data/icons/human-resources-management/512/business_man_office_male-512.png");
            String lang[]=uu.get(i).location.split(",");

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(lang[0]),Double.parseDouble(lang[1])))
                    .title(uu.get(i).name)





            ).setTag(i);
            // .setTag(i);
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
        //marker.showInfoWindow();
        return false;
    }
}
