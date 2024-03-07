package com.Ecomaxgo.maxpay.kiosk_status.Voucher;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.Ecomaxgo.maxpay.kiosk_status.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class expenseentry extends Fragment{
    RequestQueue rq;
    SharedPreferences sp;
    TextView name;
    ProgressDialog progressDialog;
    EditText amount,description;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String CAMERA_PREF = "camera_pref";
    public static final String ALLOW_KEY = "ALLOWED";
    ImageView image;
    Button b;
    Bitmap bmp;
    private Uri fileUri;
    FragmentManager fm;
    String ss="";
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
                 ss = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                // Toast.makeText(getActivity().getApplicationContext(), "Launch", Toast.LENGTH_LONG).show();



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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_expenseentry,container,false);
        rq=Volley.newRequestQueue(getContext());
        fm=getFragmentManager();
        sp=getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        name=v.findViewById(R.id.textView37);
        name.setText(sp.getString("name",""));
        amount=v.findViewById(R.id.editText2);
        description=v.findViewById(R.id.editText3);
        image=v.findViewById(R.id.imageView2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    selectimage();
                }
            }
        });
        b=v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry(ss);
            }
        });
        return v;
    }


    private void selectimage()
    {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        //Toast.makeText(getActivity().getApplicationContext(), "open camera", Toast.LENGTH_LONG).show();
//        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_CAMERA);
//
    }
    private void entry(final String img)
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        //RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/expenseEntryForEmp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
//                                    g.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("status").equalsIgnoreCase("1"))
                            {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            }
                            FragmentTransaction ft=fm.beginTransaction();
                            expensereport e=new expensereport();
                            ft.replace(R.id.fl,e);
                            ft.commit();

                            ss="";



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
                params.put("id", sp.getString("id",""));
                params.put("token", sp.getString("token",""));
                params.put("amount",amount.getText().toString());
                params.put("description",description.getText().toString());
                params.put("image",img);


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
