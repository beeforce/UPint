package com.example.bee.upint2;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bee.upint2.adapter.SectionPageAdapter;
import com.example.bee.upint2.fragment.Listenfragment;
import com.example.bee.upint2.fragment.Schedulefragment;
import com.example.bee.upint2.fragment.Settingfragment;
import com.example.bee.upint2.fragment.Staticfragment;
import com.example.bee.upint2.fragment.Upcomingfragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private Upcomingfragment upcomingfragmentFrag;
    private Listenfragment listenfragment;
    private Schedulefragment schedulefragment;
    private Staticfragment staticfragment;
    private Settingfragment settingfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initBottomNavigation();
        initViewPager();
        setupViewPager();


    }
    private void initBottomNavigation() {

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_upcoming:
                                viewPager.setCurrentItem(0);
                                break;

                            case R.id.action_listening:
                                viewPager.setCurrentItem(1);
                                break;

                            case R.id.action_schedule:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.action_static:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.action_setting:
                                viewPager.setCurrentItem(4);
                                break;


                        }
                        return true;
                    }
                });
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_upcoming);
                        break;
                    case 1:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_listening);
                        break;
                    case 2:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_schedule);
                        break;
                    case 3:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_static);
                        break;
                    case 4:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_setting);
                        break;

                }
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setupViewPager() {
        upcomingfragmentFrag = new Upcomingfragment();
        listenfragment = new Listenfragment();
        schedulefragment = new Schedulefragment();
        staticfragment = new Staticfragment();
        settingfragment = new Settingfragment();

        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.addFragment(upcomingfragmentFrag);
        adapter.addFragment(listenfragment);
        adapter.addFragment(schedulefragment);
        adapter.addFragment(staticfragment);
        adapter.addFragment(settingfragment);


        viewPager.setAdapter(adapter);
    }




}
