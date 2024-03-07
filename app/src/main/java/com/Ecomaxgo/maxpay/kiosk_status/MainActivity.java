package com.Ecomaxgo.maxpay.kiosk_status;


import android.animation.ArgbEvaluator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.Ecomaxgo.maxpay.kiosk_status.Voucher.EmployeeList;
import com.Ecomaxgo.maxpay.kiosk_status.Voucher.Expensemain;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 1;
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    FragmentManager fm;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ImageView[] indicators;
    private int[] colorList;
    private ArgbEvaluator evaluator;

    private LinearLayout btnkioskstatus;
    private LinearLayout btntranscation;
    private LinearLayout btnwalletuser;
    private LinearLayout btnkioskuser;
    private LinearLayout btnattendace;
    private LinearLayout btnwastcollection;
    private LinearLayout btnsimdetails;
    private LinearLayout btntrackuser;
    private LinearLayout btnbilldetails;
    private LinearLayout expense;
    private LinearLayout expenseaudit;
    private LinearLayout logout;


    ProgressDialog progressDialog;

    private Button btnSkip;
    private Button btnFinish;
    private ImageButton btnNext;
    private int page;
    TextView wallet;
    RequestQueue rq;
    SharedPreferences sp;
    AdView mAdView;
    private void wallet()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://newapi.maxpaywallet.com/index.php/Testing/back/Panel_Rest/getEmpWallet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        progressDialog.dismiss();
                        try {
//                                    g.setVisibility(View.GONE);
                            JSONObject obj = new JSONObject(response);
                            //String status1 = obj.getString("status");
                            wallet.setText( "\u20B9  "+obj.getString("wallet"));





                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, e + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //Adding parameters to request
                params.put("id",sp.getString("id","") );
                params.put("token", sp.getString("token",""));
                params.put("skey","30b81092491d81c5e90990bb06d875498be3b83f8eb9d432d458324e5b4731225e6600cd27ae6e");
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wallet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this, "ca-app-pub-5258083952445606~8114830990");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        private fun uploadStuffToDatabase(){
            String value = "val";

            //DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
//
            //String userid=mDatabase.push().getKey();
//            usermodel u=new usermodel("rohit","rohit@fh.in");
//                    mDatabase.child("location").setValue(u);
//        mDatabase.child("location").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                usermodel user = dataSnapshot.getValue(usermodel.class);
//                Toast.makeText(MainActivity.this, user.email, Toast.LENGTH_SHORT).show();
//                //Log.d(TAG, "User name: " + user.getName() + ", email " + user.getEmail());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                //Log.w(TAG, "Failed to read value.", error.toException());
//                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
//            }
//        });

//        }
//        fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        kioskstatus k = new kioskstatus();
//        ft.replace(R.id.fl, k);
//        ft.commit();
wallet=findViewById(R.id.wallet);
    rq=Volley.newRequestQueue(this);
    sp=getSharedPreferences("login",MODE_PRIVATE);
        startService(new Intent(this,updatelocservice.class));
        wallet();
        //RequestQueue requestQueue = Volley.newRequestQueue(getContext());



        indicators = new ImageView[]{
                (ImageView) findViewById(R.id.footer_control_indicator_1),
                (ImageView) findViewById(R.id.footer_control_indicator_2),
                (ImageView) findViewById(R.id.footer_control_indicator_3)};

        colorList = new int[]{
                Color.parseColor("#FFC107"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#8BC34A")};
        evaluator = new ArgbEvaluator();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setBackgroundColor(colorList[0]);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset,
                        colorList[position], position == colorList.length-1 ? colorList[position] : colorList[position+1]);
                mViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                // update indicator
                page = position;
                updateIndicator(position);
                mViewPager.setBackgroundColor(colorList[position]);


//                btnSkip.setVisibility(position == 2? View.INVISIBLE: View.VISIBLE);
//                btnNext.setVisibility(position == 2? View.GONE : View.VISIBLE);
//                btnFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

inIt();

    }

    private void inIt() {
        btnkioskstatus = (LinearLayout) findViewById(R.id.btnkioskstatus);


        btntranscation = (LinearLayout) findViewById(R.id.btntranscation);


        btnwalletuser = (LinearLayout) findViewById(R.id.btnwalletuser);


        btnkioskuser = (LinearLayout) findViewById(R.id.btnkioskuser);


        btnattendace = (LinearLayout) findViewById(R.id.btnattendace);


        btnwastcollection = (LinearLayout) findViewById(R.id.btnwastcollection);


        btnsimdetails = (LinearLayout) findViewById(R.id.btnsimdetails);


        btntrackuser = (LinearLayout) findViewById(R.id.btntrackuser);

        expenseaudit = (LinearLayout) findViewById(R.id.expenseaudit);



        btnbilldetails = (LinearLayout) findViewById(R.id.btnbilldetails);
        btnbilldetails.setOnClickListener(this);

        expense = (LinearLayout) findViewById(R.id.expense);
        if (sp.getString("desination","").equalsIgnoreCase("admin")) {
            expense.setOnClickListener(this);
            btnkioskstatus.setOnClickListener(this);
            btntranscation.setOnClickListener(this);
            btnwalletuser.setOnClickListener(this);
            btnkioskuser.setOnClickListener(this);
            btnattendace.setOnClickListener(this);
            btnwastcollection.setOnClickListener(this);
            btnsimdetails.setOnClickListener(this);
            btntrackuser.setOnClickListener(this);
            expenseaudit.setOnClickListener(this);
            btntrackuser.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    startActivity(new Intent(MainActivity.this,employeetrack.class));
                    return true;
                }
            });

        }
        else if (sp.getString("desination","").equalsIgnoreCase("promoter")){
            expense.setOnClickListener(this);
            btnkioskstatus.setOnClickListener(this);
           // btntranscation.setOnClickListener(this);
           // btnwalletuser.setOnClickListener(this);
            btnkioskuser.setOnClickListener(this);
            btnattendace.setOnClickListener(this);
           // btnwastcollection.setOnClickListener(this);
           // btnsimdetails.setOnClickListener(this);
           // btntrackuser.setOnClickListener(this);
           // expenseaudit.setOnClickListener(this);
        }
        else if (sp.getString("desination","").equalsIgnoreCase("finance"))
        {
            expense.setOnClickListener(this);
            btnkioskstatus.setOnClickListener(this);
             btntranscation.setOnClickListener(this);
             btnwalletuser.setOnClickListener(this);
            btnkioskuser.setOnClickListener(this);
//            btnattendace.setOnClickListener(this);
//             btnwastcollection.setOnClickListener(this);
             btnsimdetails.setOnClickListener(this);
            // btntrackuser.setOnClickListener(this);
             expenseaudit.setOnClickListener(this);
        }
        else
        {
            expense.setOnClickListener(this);
            btnkioskstatus.setOnClickListener(this);
            btntranscation.setOnClickListener(this);
//            btnwalletuser.setOnClickListener(this);
            btnkioskuser.setOnClickListener(this);
            btnattendace.setOnClickListener(this);
             btnwastcollection.setOnClickListener(this);
//            btnsimdetails.setOnClickListener(this);
            // btntrackuser.setOnClickListener(this);
//            expenseaudit.setOnClickListener(this);
        }

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edt=sp.edit();
                edt.putString("id","");
                edt.commit();
                startActivity(new Intent(MainActivity.this,Loginactivity.class));
                finish();
            }
        });
    }

    private void updateIndicator(int position){
        for (int i = 0; i < indicators.length; i++) {
            if(i == position){
                indicators[i].setImageResource(R.drawable.indicator_selected);
            }else
                indicators[i].setImageResource(R.drawable.indicator_unselected);
        }
    }

    @Override
    public void onClick(View v) {

        page++;
        mViewPager.setCurrentItem(page, true);

        int vId = v.getId();



        if (vId == R.id.btnkioskstatus) {
            fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            kioskstatus tr = new kioskstatus();
            fragmentTransaction.replace(R.id.fl, tr);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        } else if (vId == R.id.btntranscation) {

            fm = getSupportFragmentManager();
            FragmentTransaction fragmenntwallet = fm.beginTransaction();
            transactiondetails w = new transactiondetails();
            fragmenntwallet.replace(R.id.fl, w);
            fragmenntwallet.addToBackStack(null);
            fragmenntwallet.commit();

        } else if (vId == R.id.btnwalletuser) {

            fm = getSupportFragmentManager();
            FragmentTransaction fragmentkioskstatus = fm.beginTransaction();
            Walletuser attendence = new Walletuser();
            fragmentkioskstatus.replace(R.id.fl, attendence);
            fragmentkioskstatus.addToBackStack(null);
            fragmentkioskstatus.commit();


        } else if (vId == R.id.btnkioskuser) {

            fm = getSupportFragmentManager();
            FragmentTransaction fragmentkioskstatus = fm.beginTransaction();
            userdetail k = new userdetail();
            fragmentkioskstatus.replace(R.id.fl, k);
            fragmentkioskstatus.addToBackStack(null);
            fragmentkioskstatus.commit();

        } else if (vId == R.id.btnattendace) {

//            fm = getSupportFragmentManager();
//            FragmentTransaction fragmentsimdetail = fm.beginTransaction();
//            Attendancefragment s = new Attendancefragment();
//            fragmentsimdetail.replace(R.id.fl, s);
//            fragmentsimdetail.addToBackStack(null);
//            fragmentsimdetail.commit();
            Bundle b = new Bundle();
            b.putString("id1", sp.getString("id",""));
            b.putString("token1", sp.getString("token",""));


            Intent intent = new Intent(MainActivity.this, LoginDashboard.class);
            intent.putExtras(b);
            startActivity(intent);

        }else if (vId == R.id.btnwastcollection) {

            fm = getSupportFragmentManager();
            FragmentTransaction fragmentkioskdetails = fm.beginTransaction();
            WastCollection u = new WastCollection();
            fragmentkioskdetails.replace(R.id.fl, u);
            fragmentkioskdetails.addToBackStack(null);
            fragmentkioskdetails.commit();

        } else if (vId == R.id.btnsimdetails) {

            fm = getSupportFragmentManager();
            FragmentTransaction fragmentkioskdetails = fm.beginTransaction();
            SimDetail u = new SimDetail();
            fragmentkioskdetails.replace(R.id.fl, u);
            fragmentkioskdetails.addToBackStack(null);
            fragmentkioskdetails.commit();

        } else if (vId == R.id.btntrackuser) {

            fm = getSupportFragmentManager();
            startActivity(new Intent(MainActivity.this, trackuser.class));

        }
        else if (vId == R.id.expense) {


            startActivity(new Intent(MainActivity.this, Expensemain.class));

        }
        else if (vId == R.id.expenseaudit) {


            startActivity(new Intent(MainActivity.this, EmployeeList.class));

        }
        else if (vId == R.id.btnbilldetails) {
//
           startActivity(new Intent(MainActivity.this,work.class));

        }


//        switch (v.getId()) {
//            case R.id.footer_control_button_next:
//                page++;
//                mViewPager.setCurrentItem(page, true);
//                break;
//        }

    }
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.slider_image);

            int sectNumb = getArguments().getInt(ARG_SECTION_NUMBER);
            int[] images = {R.drawable.forests, R.drawable.forests, R.drawable.forests};
            imageView.setImageResource(images[sectNumb]);
//            textView.setText(getString(R.string.section_format, sectNumb));

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.status:
                fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                kioskstatus k = new kioskstatus();
                ft.replace(R.id.fl, k);
                ft.commit();
                break;
            case R.id.user:
                fm = getSupportFragmentManager();
                FragmentTransaction ft2 = fm.beginTransaction();
                userdetail u = new userdetail();
                ft2.replace(R.id.fl, u);
                ft2.commit();
                break;
            case R.id.trans:
                fm = getSupportFragmentManager();
                FragmentTransaction ft3 = fm.beginTransaction();
                transactiondetails tr = new transactiondetails();
                ft3.replace(R.id.fl, tr);
                ft3.commit();
                break;
            case R.id.track:
                fm = getSupportFragmentManager();
                startActivity(new Intent(MainActivity.this, trackuser.class));
                break;
            case R.id.wallet:
                fm = getSupportFragmentManager();
                FragmentTransaction ft5 = fm.beginTransaction();
                Walletuser w = new Walletuser();
                ft5.replace(R.id.fl, w);
                ft5.commit();
                break;
            case R.id.sim:
                fm = getSupportFragmentManager();
                FragmentTransaction ft6 = fm.beginTransaction();
                SimDetail s = new SimDetail();
                ft6.replace(R.id.fl, s);
                ft6.commit();
                break;
            case R.id.employeeattendace:
                Bundle b = new Bundle();
                b.putString("id1", sp.getString("id1",""));
                b.putString("token1", sp.getString("token1",""));


                Intent intent = new Intent(MainActivity.this, LoginDashboard.class);
                intent.putExtras(b);
                startActivity(intent);
                break;

            case R.id.wastcollection:
                fm = getSupportFragmentManager();
                FragmentTransaction ft8 = fm.beginTransaction();
                WastCollection wastCollection = new WastCollection();
                ft8.replace(R.id.fl, wastCollection);
                ft8.commit();
                break;
            case R.id.leaverequest:
                fm = getSupportFragmentManager();
                FragmentTransaction ft9 = fm.beginTransaction();
                Attendance leaveRequest = new Attendance();
                ft9.replace(R.id.fl, leaveRequest);
                ft9.commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
