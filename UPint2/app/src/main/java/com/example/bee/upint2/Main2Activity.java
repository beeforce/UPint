package com.example.bee.upint2;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.example.bee.upint2.model.sendOject;

import java.lang.reflect.Field;
import java.util.Locale;

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
        //set language
        String languageToLoad = "en_US"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale.ENGLISH; //set locale language to english
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
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
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
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
class BottomNavigationViewHelper {

    @SuppressLint("RestrictedApi")
    static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
}
