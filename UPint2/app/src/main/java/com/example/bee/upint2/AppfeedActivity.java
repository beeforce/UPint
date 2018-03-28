package com.example.bee.upint2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.bee.upint2.adapter.SectionPageAdapter;
import com.example.bee.upint2.fragment.HomeappFragment;
import com.example.bee.upint2.fragment.Listenfragment;
import com.example.bee.upint2.fragment.Schedulefragment;
import com.example.bee.upint2.fragment.Settingfragment;
import com.example.bee.upint2.fragment.Statusfragment;
import com.example.bee.upint2.model.CustomViewPager;

public class AppfeedActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private CustomViewPager viewPager;
    private HomeappFragment homeappFragment;
    private Listenfragment listenfragment;
    private Schedulefragment schedulefragment;
    private Statusfragment statusfragment;
    private Settingfragment settingfragment;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appfeed);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black);

        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.action_upcoming:
                                viewPager.setCurrentItem(0);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;

                            case R.id.action_listening:
                                viewPager.setCurrentItem(1);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;

                            case R.id.action_schedule:
                                viewPager.setCurrentItem(2);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;
                            case R.id.action_static:
                                viewPager.setCurrentItem(3);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;
                            case R.id.action_setting:
                                viewPager.setCurrentItem(4);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;

                        }

                        return true;
                    }
                });

        initViewPager();
        setupViewPager();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager_appfeed);
        viewPager.setPagingEnabled(false);

           }
    private void setupViewPager() {
        homeappFragment = new HomeappFragment();
        listenfragment = new Listenfragment();
        schedulefragment = new Schedulefragment();
        statusfragment = new Statusfragment();
        settingfragment = new Settingfragment();

        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.addFragment(homeappFragment);
        adapter.addFragment(listenfragment);
        adapter.addFragment(schedulefragment);
        adapter.addFragment(statusfragment);
        adapter.addFragment(settingfragment);


        viewPager.setAdapter(adapter);
    }
}
