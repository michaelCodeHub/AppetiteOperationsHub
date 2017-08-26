package com.appetite.appetiteoperationsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
/**
 * Created by iammike on 26/03/16.
 */
public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String language = "All";

    private ViewPager pager;
    public static FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private DrawerLayout drawer2;
    private AppCompatCheckBox[] languages = new AppCompatCheckBox[8];
    private ImageView profile;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        new Sansation().overrideFonts(getApplicationContext() , findViewById(R.id.textView8));

//        if(!App.shared.getBoolean("login" , false))
//        {
//            startActivity(new Intent(Home.this  , Login.class));
//            finish();
//        }

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            findViewById(R.id.view).setVisibility(View.VISIBLE);
//            window.setStatusBarColor(Color.BLACK);
        }

        loadContent();
    }

//    private void updateFragments()
//    {
//        for(int i=0 ; i< App.categories.size()+1 ; i++)
//        {
//            try
//            {
//                Fragment page = getSupportFragmentManager().getFragments().get(i);
//                if (page instanceof Fragment1)
//                    ((Fragment1)page).refresh();
//                else if (page instanceof Fragment1)
//                    ((Fragment1)page).refresh();
//                else if(page instanceof Fragment1)
//                    ((Fragment1)page).refresh();
//            }
//            catch (Exception e)
//            {
//                Log.e("test",13+""+e.toString());
//            }
//        }
//    }

    private void loadContent() {
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 3) {
                    case 0:
                        return Fragment1.newInstance();
                    case 1:
                        return Fragment1.newInstance();
                    case 2:
                        return Fragment1.newInstance();
                    default:
                        return Fragment1.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if(position==0)
                {
                    return "Assigned";
                }
                else if(position==1)
                {
                    return  "Allocated";
                }
                else if(position==2)
                {
                    return  "Completed";
                }
                else
                {
                    return "New";
                }
            }
        });

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        tabs.setUnderlineColor(Color.WHITE);

        new Sansation().overrideFonts(getApplicationContext(), tabs);

        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();
    }

    private boolean getpermission() {
        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Home.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadContent();
                } else {
                    getpermission();
                }
                return;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
