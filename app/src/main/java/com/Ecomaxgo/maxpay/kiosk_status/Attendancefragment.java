package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Attendancefragment extends Fragment {


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String preid = "id";
    public static final String pretoken = "token";
    Button btnCapture;
    private static final String TAG = "LoginActivity";
    private ProgressDialog pd;
    boolean small = false, capital = false, num = false, sym = false;
    ProgressDialog progressDialog;
//    SessionManager session;
    String status, message, title, name, mobile, desination, image, id, token;
    EditText etemailid, etpassword;
    private static String URL = "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/employeeLogin";
    ImageView imageView;
    private AwesomeValidation awesomeValidation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.camerarow,
                container, false);

        etemailid = (EditText) rootView.findViewById(R.id.etemailid);
        pd = new ProgressDialog(getActivity());
        etpassword = (EditText) rootView.findViewById(R.id.etpassword);
        btnCapture = (Button) rootView.findViewById(R.id.btnCapturePicture);
        //imageView = (ImageView) rootView.findViewById(R.id.imageview);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
//        session = new SessionManager(getContext().getApplicationContext());



        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String email = etemailid.getText().toString().trim();
                final String password = etpassword.getText().toString().trim();
                boolean b1 = false, b2 = false, b3 = false, b4 = false;

                if (etemailid.getText().toString().equals("") || etemailid.getText().toString().length() < 10) {
                    // Toast.makeText(Login.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                    b1 = true;

                    Toast.makeText(getActivity(), "Please Enter 10 Digit Phone Number", Toast.LENGTH_SHORT).show();
                    b2 = true;
//                    g.setVisibility(View.GONE);
                }
                if (etpassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Your Password", Toast.LENGTH_SHORT).show();
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
                if (!b1 && !b2 && !b3 && b4) {
                    small = false;
                    num = false;
                }
//                Toast.makeText(getContext(), etemailid.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), etpassword.getText().toString(), Toast.LENGTH_SHORT).show();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("please wait...");
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                                    Toast.makeText(getActivity(), "successfully login", Toast.LENGTH_LONG).show();

//                                    session.createLoginSession(id1, token1);

//                                    SM.sendData(id1);
//                                    SM.sendData(token1);



                                    Intent intent = new Intent(getActivity(), LoginDashboard.class);
                                    intent.putExtra("id1",id1);
                                    intent.putExtra("token1",token1);

                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e + "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {


                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //You can handle error here if you want
                                Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        //Adding parameters to request
                        params.put("phonenumber", etemailid.getText().toString());
                        params.put("password", etpassword.getText().toString());

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
            }


//                final String phonenumber = etemailid.getText().toString().trim();
//                final String password = etpassword.getText().toString().trim();
//
//
//                if (TextUtils.isEmpty(phonenumber)) {
//                    etemailid.setError("Please enter your Mobile Number");
//                    etemailid.requestFocus();
//                    etemailid.clearFocus();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    etpassword.setError("please enter password");
//                    etpassword.requestFocus();
//                    etpassword.clearFocus();
//                    return;
//                }
//
//                    RequestQueue queue = Volley.newRequestQueue(getContext());
//                    String response = null;
//                    pd.setMessage("Login . . .");
//                    pd.show();
//
//                    final String finalResponse = response;
//
//                    StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
//
//                            new Response.Listener<String>()
//                            {
//                                @Override
//                                public void onResponse(String response) {
//
//
//                                    pd.hide();
//
//
//
////                                    if(response.equals("employeeLogin")) {
//                                        Toast.makeText(getContext().getApplicationContext(),"Login",Toast.LENGTH_LONG).show();
//                                        startActivity(new Intent(getActivity(),LoginDashboard.class));
////                                    }
//
//
//                                }
//
//                            },
//                            new Response.ErrorListener()
//                            {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    // error
//
//                                    Toast.makeText(getContext().getApplicationContext(),"error",Toast.LENGTH_LONG).show();
//
//                                }
//                            }
//                    ) {
//                        @Override
//                        protected Map<String, String> getParams()
//                        {
//                            Map<String, String>  params = new HashMap<String, String>();
//                            params.put("phonenumber", etemailid.getText().toString());
//                            params.put("password", etpassword.getText().toString());
//                            return params;
//                        }
//                    };
//                    postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                    queue.add(postRequest);
//
//
//
////                    userLogin();
////                    submitForm();
//
////                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//
//            }


        });

        return rootView;

    }
//    interface SendMessage {
//        void sendData(String message);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            SM = (SendMessage) getActivity();
//        } catch (ClassCastException e) {
////            throw new ClassCastException("Error in retrieving data. Please try again");
//            Toast.makeText(getContext().getApplicationContext(),"error",Toast.LENGTH_LONG).show();
//        }
//    }

    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            Toast.makeText(getContext(), "Registration Successfull", Toast.LENGTH_LONG).show();

            //process the data further
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                imageView.setImageBitmap(bitmap);

            }
        }
    }
}
