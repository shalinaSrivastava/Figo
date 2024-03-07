package com.Ecomaxgo.maxpay.kiosk_status;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;


public class Attendance extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private Uri fileUri;
    TextView t;
    String ids, tokens1;
    GoogleMap mMap;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String TAG = Attendance.class.getSimpleName();
    AppLocationService appLocationService;
    LocationAddress locationAddress;

    ProgressDialog progressDialog;
    String getTvAddress = "28.636273,77.218316";
    String id, token, status, message;
    //    SessionManager session;
    String id1, token1;
    //    OnHeadlineSelectedListener mCallback;
    private String mParam1;
    double latitude = 0.0, longitude = 0.0;
    private String mParam2;
    LocationManager lm;
    Location location;
    String cityName = " Inner circle,Connaught circle, Middle Cir, Block E, Connaught Place, New Delhi, Delhi 110001";

    private OnFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_attendance,container,false);
        id1 = getArguments().getString("id1");
        token1 = getArguments().getString("token1");
//        Toast.makeText(getContext(), id + "1", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), token + "1", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(getContext(), ALLOW_KEY)) {
                showSettingsAlert();

            } else if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA)

                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CAMERA)) {
                    showAlert();

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }


            }


        } else {
            t=v.findViewById(R.id.textView40);
            t.setText(id1+" "+token1);

            openCamera();
        }
        return v;


//

    }


    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(getActivity());
                    }
                });

        alertDialog.show();
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        getActivity(), permission);

                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(getActivity(), ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() {

        ConnectivityManager conn= (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=conn.getActiveNetworkInfo();
        lm= (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location= lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        // Toast.makeText(this, locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)+"", Toast.LENGTH_SHORT).show();
        if (location!=null && net!=null && net.isConnected()==true)
        {
            getTvAddress=location.getLatitude()+","+ location.getLongitude();
            //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (null != addressList && addressList.size() > 0) {
                    cityName = addressList.get(0).getAddressLine(0);
                    //cityname=addressList.get(0).getLocality();
                }

            } catch (Exception e) {
                //Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
            }
        }
        else {//Toast.makeText(this, "ok2", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        //Toast.makeText(getActivity().getApplicationContext(), "open camera", Toast.LENGTH_LONG).show();
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_CAMERA);
//

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);

        Toast.makeText(getActivity().getApplicationContext(), "save instace", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


//        Toast.makeText(getActivity().getApplicationContext(), "result", Toast.LENGTH_LONG).show();
        // if the result is capturing Image
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                // Toast.makeText(getActivity().getApplicationContext(), "Launch", Toast.LENGTH_LONG).show();
                launchUploadActivity(imageString);


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
//                Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();

            } else {
                // failed to capture image
//                Toast.makeText(getActivity(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
            }

        }
    }

    private void launchUploadActivity(final String isImage) {

        t.setText(t.getText()+" "+getTvAddress+" "+cityName);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

//        HashMap<String, String> user = session.getUserDetails();
//
//         name
//        id = user.get(SessionManager.KEY_ID);

        // email
//        token = user.get(SessionManager.KEY_TOKEN);

        // displaying user data

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/makeEmployeeAttendance", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    status = jsonObject.getString("status");
                    message = jsonObject.getString("message");
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(appLocationService, ""+e, Toast.LENGTH_SHORT).show();
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

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id1);
                params.put("token", token1);
                params.put("gpsLoc", getTvAddress);
                params.put("gpsLocAddress", cityName);
                params.put("image", isImage);

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
//        Intent i = new Intent(getActivity(), Attendance.class);
//        i.putExtra("filePath", fileUri.getPath());
//        i.putExtra("isImage", isImage);
//        startActivity(i);
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {


        return Uri.fromFile(getOutputMediaFile(type));
    }


    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
//            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

    }


    private class GeocoderHandler extends Handler implements com.Ecomaxgo.maxpay.kiosk_status.GeocoderHandler {
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Toast.makeText(getActivity().getApplicationContext(), locationAddress, Toast.LENGTH_LONG).show();

//                getTvAddress.setText(locationAddress);
        }

        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }
}
