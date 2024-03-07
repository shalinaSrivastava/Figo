package com.Ecomaxgo.maxpay.kiosk_status;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class LoginDashboard extends AppCompatActivity  {

    private ViewPager viewPager;
//    private TabsPagerAdapter mAdapter;
    private android.app.ActionBar actionBar;
//    MyAdapter mAdapter;
//    ViewPager mPager;
    FragmentManager fm;
    String id;
    String token;
    String id1;
    String token1;

    // Tab titles
    private String[] tabs = { "Attendance", "Leave Request" };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(LoginDashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION );

        int result2 = ContextCompat.checkSelfPermission(LoginDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result2 == PackageManager.PERMISSION_GRANTED&&result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginDashboard.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(LoginDashboard.this, android.Manifest.permission.ACCESS_FINE_LOCATION )) {
            Toast.makeText(LoginDashboard.this, "Write External Storage permission allows us to fetch images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(LoginDashboard.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            ActivityCompat.requestPermissions(LoginDashboard.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
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
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
if (checkPermission())
{
    work();
}
else {
    requestPermission();
}




    }

private void work
        ()
{
    Intent intent2 = getIntent();
    Bundle bundle = intent2.getExtras();
    id = getIntent().getStringExtra("id1");
    token = getIntent().getStringExtra("token1");

    fm = getSupportFragmentManager();
//    FragmentTransaction ft5 = fm.beginTransaction();
//    Bundle b = new Bundle();
//    b.putString("id1", id);
//    b.putString("token1", token);
//    Attendance w = new Attendance();
//    w.setArguments(b);
//    ft5.replace(R.id.fn, w);
//    ft5.commit();



//        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();


//        mAdapter = new MyAdapter(getSupportFragmentManager());

//        mPager = findViewById(R.id.pager);
//        mPager.setAdapter(mAdapter);

    findViewById(R.id.goto_first).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            fm = getSupportFragmentManager();
            FragmentTransaction ft5 = fm.beginTransaction();
            Bundle b = new Bundle();
            b.putString("id1", id);
            b.putString("token1", token);
            Attendance a = new Attendance();
            a.setArguments(b);
            ft5.replace(R.id.fn, a);
            ft5.commit();
        }

    });
    findViewById(R.id.delete_current).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            fm = getSupportFragmentManager();
            FragmentTransaction ft5 = fm.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("id1", id);
            bundle.putString("token1", token);
            LeaveRequest w = new LeaveRequest();
            w.setArguments(bundle);
            ft5.replace(R.id.fn, w);
            ft5.commit();
        }
    });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);




//        if (savedInstanceState == null) {
//
//            // withholding the previously created fragment from being created again
//            // On orientation change, it will prevent fragment recreation
//            // its necessary to reserve the fragment stack inside each tab
//            initScreen();
//
//        } else {
//            // restoring the previously created fragment
//            // and getting the reference
//            CarouselFragment carouselFragment = (CarouselFragment) getSupportFragmentManager().getFragments().get(0);
//        }
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
////        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        viewPager.setAdapter(new TabsPagerAdapter(this));//        actionBar = getActionBar();
//        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
////
//        viewPager.setAdapter(mAdapter);
//        actionBar.setHomeButtonEnabled(false);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        // Adding Tabs
//        for (String tab_name : tabs) {
//            actionBar.addTab(actionBar.newTab().setText(tab_name)
//                    .setTabListener((android.app.ActionBar.TabListener) this));
//
//            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//        }
//


    }

//    private class MyPagerAdapter extends FragmentPagerAdapter {
//
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int pos) {
//            switch(pos) {
//
//                case 0: return LeaveRequest.newInstance("LeaveRequest");
////                case 1: return Attendance.newInstance("Attebdance");
//
//                default: return LeaveRequest.newInstance("ThirdFragment, ");
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 5;
//        }
//    }
}
