package com.Ecomaxgo.maxpay.kiosk_status;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class AssignWorkFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ImageView imageView;
    TextView addimage;
    EditText work;
    String imageString="";
    RequestQueue rq;
    ListView    listView;
    ArrayList a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, data;
    public static String value;
    private final int PICK_IMAGE_MULTIPLE =1;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;

    private LinearLayout lnrImages;
//    Spinner spinner;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    TextView addemploye;
    String status, message, works;
//    List<String> categories = new ArrayList<String>();

    Uri uri;
    List<String> list = new ArrayList<String>();
    private OnFragmentInteractionListener mListener;

    public AssignWorkFragment() {
    }

    public static AssignWorkFragment newInstance(String param1, String param2) {
        AssignWorkFragment fragment = new AssignWorkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assign_work, container, false);
        imageView = v.findViewById(R.id.imageView);
        work = v.findViewById(R.id.work);

        addemploye=v.findViewById(R.id.addemploye);
        addemploye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                empList();



            }
        });

        addimage = v.findViewById(R.id.addimage);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 2);

            }
        });




        return v;
    }

    private void empList() {
        a1 = new ArrayList();
        a2 = new ArrayList();
        a3 = new ArrayList();
        a4 = new ArrayList();
        a5 = new ArrayList();
        a6 = new ArrayList();
        a7 = new ArrayList();
        a8 = new ArrayList();
        data = new ArrayList();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/listEmp", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {




                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray ary = obj.getJSONArray("data");
                    for (int i = 0; i < ary.length(); i++) {
                        JSONObject object = ary.getJSONObject(i);
                        a1.add(object.getString("id"));
                        a2.add(object.getString("title"));
                        a3.add(object.getString("name"));
                        a4.add(object.getString("entryDate"));
                        a5.add(object.getString("desination"));
                        a6.add(object.getString("image"));
                        a7.add(object.getString("wallet"));
                        a8.add(object.getString("status"));
                        data.add(object.getString("id") + " " + object.getString("title") + "   " + object.getString("name") + "\n" + object.getString("entryDate") + "\n" + object.getString("desination") + " " + object.getString("image") + " " + object.getString("wallet") + "" + object.getString("status"));


                    }



                    LayoutInflater li = LayoutInflater.from(getContext());
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    View dialog = li.inflate(R.layout.custom, null);
                    alertDialogBuilder.setView(dialog);
                    Button dialogButton = (Button)dialog.findViewById(R.id.btnsubmit);
                    listView=(ListView)dialog.findViewById(R.id.employelist);
                    listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
                    listView.setTextFilterEnabled(true);
//
                    setListAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_checked,a1));
                    alertDialogBuilder.setCancelable(false).setPositiveButton("Assign",new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int id) {


                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/assignWork", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        status = jsonObject.getString("status");
                                        message = jsonObject.getString("message");
                                        works = jsonObject.getString("work");
                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    Toast.makeText(getContext(), "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                                    params.put("work", work.getText().toString());
                                    params.put("empIds",value);
                                    params.put("image", imageString);

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

                   refine();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();

                }
            }

            private void setListAdapter(ArrayAdapter<String> stringArrayAdapter) {
                Employedatalistadapter kk=new Employedatalistadapter(getActivity(),a1,a2,a3,a4,a5,a6,a7,a8);
                listView.setAdapter(kk);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();

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

    private void ImageCropFunction() {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(uri, "image/*");

            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 180);
            intent.putExtra("outputY", 180);
            intent.putExtra("aspectX", 3);
            intent.putExtra("aspectY", 4);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode ==PICK_IMAGE_MULTIPLE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                imageView.setImageURI(Uri.parse(imageString));
                // Toast.makeText(getActivity().getApplicationContext(), "Launch", Toast.LENGTH_LONG).show();
                empList();


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

//
//        if (resultCode== RESULT_OK) {
//
//            ImageCropFunction();
//
//        } else if (requestCode == 2) {
//
//            if (data != null) {
//
//                uri = data.getData();
//
//                ImageCropFunction();
//
//            }
//        } else if (requestCode == 1) {
//
//            if (data != null) {
//
//                Bundle bundle = data.getExtras();
//
//                Bitmap bitmap = bundle.getParcelable("data");
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] imageBytes = baos.toByteArray();
//                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//                imageView.setImageBitmap(bitmap);
//
//            }
//        }
//        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

//        int vId = v.getId();
//        if (vId == R.id.btnsend) {


//            RequestQueue queue = Volley.newRequestQueue(getContext());
//            StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/c/Panel_Rest/assignWork", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String s) {
//
//                    try {
//
//
//                        JSONObject jsonObject = new JSONObject(s);
//                        status = jsonObject.getString("status");
//                        message = jsonObject.getString("message");
//                        works = jsonObject.getString("work");
//                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//
//                    Toast.makeText(getContext(), "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();
////                progressDialog.dismiss();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//                    Map<String, String> params = new HashMap<String, String>();
//
//                    params.put("skey", "30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
//                    params.put("work", work.getText().toString());
//                    params.put("empIds", "1");
//                    params.put("image", imageString);
//
//                    return params;
//                }
//            };
//            sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            queue.add(sr);


//            RequestQueue queue = Volley.newRequestQueue(getContext());
//            StringRequest sr = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/empLeave", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String s) {
//
//                    try {
//
//
//                        JSONObject jsonObject=new JSONObject(s);
//                        status = jsonObject.getString("status");
//                        message = jsonObject.getString("message");
//                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//
//                    Toast.makeText(getContext(), "something went wrong" + volleyError, Toast.LENGTH_SHORT).show();
////                progressDialog.dismiss();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//
//                    Map<String, String>  params = new HashMap<String, String>();
//
//                    params.put("skey","30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
//                    params.put("work", work.getText().toString());
//                    params.put("empIds",categories.get(0));
////                    params.put("image", imageView.toString());
//
//                    return params;
//                }
//            };
//            sr.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 0,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            queue.add(sr);
//        }


    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    private void refine()
    {
        Employedatalistadapter kk=new Employedatalistadapter(getActivity(),a1,a2,a3,a4,a5,a6,a7,a8);
        listView.setAdapter(kk);

    }
}
