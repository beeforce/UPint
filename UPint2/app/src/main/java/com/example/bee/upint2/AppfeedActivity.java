package com.example.bee.upint2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bee.upint2.adapter.SectionPageAdapter;
import com.example.bee.upint2.fragment.HomeappFragment;
import com.example.bee.upint2.fragment.Listenfragment;
import com.example.bee.upint2.fragment.Schedulefragment;
import com.example.bee.upint2.fragment.Settingfragment;
import com.example.bee.upint2.fragment.Statusfragment;
import com.example.bee.upint2.fragment.UpcomingStudentFragment;
import com.example.bee.upint2.fragment.Upcomingfragment;
import com.example.bee.upint2.model.CustomViewPager;
import com.example.bee.upint2.model.UserProfile;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppfeedActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private CustomViewPager viewPager;
    private HomeappFragment homeappFragment;
    private Upcomingfragment upcomingfragment;
    private Schedulefragment schedulefragment;
    private Statusfragment statusfragment;
    private Settingfragment settingfragment;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ApiService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set language
        String languageToLoad = "en_US"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale.ENGLISH; //set locale language to english
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
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
        navigationView.getMenu().getItem(0).setChecked(true);
        final View hView =  navigationView.getHeaderView(0);
        final TextView username = hView.findViewById(R.id.user_name);
        final TextView user_university = hView.findViewById(R.id.user_university);
        final ImageView profile_image = hView.findViewById(R.id.profile_image);
        sendOject o = new sendOject();
        String user_id = o.getUser_id();
        mAPIService = ApiUtils.getAPIService();
        mAPIService.userDetailswithId(user_id).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                username.setText(response.body().getFirst_name()+" "+response.body().getLast_name());
                user_university.setText(response.body().getSchool()+" University");

                String string = response.body().getImage();
                String[] parts = string.split("/");
                String part1 = parts[0];
                String part4 = parts[3];
                String part5 = parts[4];
                String part6 = parts[5];
                String part7 = parts[6];
                String part8 = parts[7];
                String part9 = parts[8];
                String url_image = part1 + "//192.168.1.13/" + part4 + "/" + part5 + "/" + part6 + "/" + part7 + "/" + part8 + "/" + part9;
                Glide.with(hView.getContext())
                        .load(url_image)
                        .override(600, 600)
                        .centerCrop()
                        .into(profile_image);


            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                viewPager.setCurrentItem(0);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;

                            case R.id.nav_upcoming:
                                viewPager.setCurrentItem(1);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;

                            case R.id.nav_explore:
                                viewPager.setCurrentItem(2);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;
                            case R.id.nav_feedback:
                                viewPager.setCurrentItem(3);
//                                toolbar.setTitle(Html.fromHtml("<font color='#6caa22'><b>UP</b></font><font color='#559e2e'><i>int<i></font>"));
                                return true;
                            case R.id.nav_logout:
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
        upcomingfragment = new Upcomingfragment();
        schedulefragment = new Schedulefragment();
        statusfragment = new Statusfragment();
        settingfragment = new Settingfragment();

        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.addFragment(homeappFragment);
        adapter.addFragment(upcomingfragment);
        adapter.addFragment(schedulefragment);
        adapter.addFragment(statusfragment);
        adapter.addFragment(settingfragment);


        viewPager.setAdapter(adapter);
    }
}
