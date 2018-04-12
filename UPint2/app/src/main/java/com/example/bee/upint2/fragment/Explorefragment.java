package com.example.bee.upint2.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.RecycleAdapterKeyword_search;
import com.example.bee.upint2.adapter.RecycleAdapterListening;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Explorefragment extends android.support.v4.app.Fragment implements  RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleAdapterListening adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;
    private Button buttontags1_searchclasskeyword, buttontags2_searchclasskeyword,
            buttontags3_searchclasskeyword, buttontags4_searchclasskeyword,
            buttontags5_searchclasskeyword, buttontags6_searchclasskeyword, buttontagsAll_searchclasskeyword,
            buttonDate1_searchclasskeyword,buttonDate2_searchclasskeyword,buttonDate3_searchclasskeyword,buttonDate4_searchclasskeyword,
            buttonDate5_searchclasskeyword,buttonDate6_searchclasskeyword,buttonDate7_searchclasskeyword;
    private static String TAG = "Explorefragment";
    private String keywordbt1, keywordbt2, keywordbt3, keywordbt4, keywordbt5
            ,keywordbt6;
    private List<Course> course;
    private ArrayList<Course> courselist = new ArrayList<>();
    private ArrayList<Course> courseArrayList = new ArrayList<>();
    private RecycleAdapterKeyword_search adapterKeyword_search;
    private Date d;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        mAPIService = ApiUtils.getAPIService();
        getClassdetail();
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView2);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        buttontagsAll_searchclasskeyword = rootView.findViewById(R.id.buttontagsAll_searchclasskeyword);
        buttontags1_searchclasskeyword = rootView.findViewById(R.id.buttontags1_searchclasskeyword);
        buttontags2_searchclasskeyword = rootView.findViewById(R.id.buttontags2_searchclasskeyword);
        buttontags3_searchclasskeyword = rootView.findViewById(R.id.buttontags3_searchclasskeyword);
        buttontags4_searchclasskeyword = rootView.findViewById(R.id.buttontags4_searchclasskeyword);
        buttontags5_searchclasskeyword = rootView.findViewById(R.id.buttontags5_searchclasskeyword);
        buttontags6_searchclasskeyword = rootView.findViewById(R.id.buttontags6_searchclasskeyword);

        buttonDate1_searchclasskeyword = rootView.findViewById(R.id.buttonDate1_searchclasskeyword);
        buttonDate2_searchclasskeyword = rootView.findViewById(R.id.buttonDate2_searchclasskeyword);
        buttonDate3_searchclasskeyword = rootView.findViewById(R.id.buttonDate3_searchclasskeyword);
        buttonDate4_searchclasskeyword = rootView.findViewById(R.id.buttonDate4_searchclasskeyword);
        buttonDate5_searchclasskeyword = rootView.findViewById(R.id.buttonDate5_searchclasskeyword);
        buttonDate6_searchclasskeyword = rootView.findViewById(R.id.buttonDate6_searchclasskeyword);
        buttonDate7_searchclasskeyword = rootView.findViewById(R.id.buttonDate7_searchclasskeyword);

        buttontagsAll_searchclasskeyword.setSelected(true);
        buttonDate1_searchclasskeyword.setSelected(true);

        initOnclick();
    }

    private void initOnclick(){


        buttonDate1_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonDate1_searchclasskeyword.isSelected()) {
                    buttonDate1_searchclasskeyword.setSelected(true);
                    if (buttonDate2_searchclasskeyword.isSelected()){
                        buttonDate2_searchclasskeyword.setSelected(false);
                        buttonDate2_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 2
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 1);
                        day = c.getTime();
                        String day_2 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_2);
                    }
                    if (buttonDate3_searchclasskeyword.isSelected()){
                        buttonDate3_searchclasskeyword.setSelected(false);
                        buttonDate3_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 3
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 2);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_3);
                    }
                    if (buttonDate4_searchclasskeyword.isSelected()){
                        buttonDate4_searchclasskeyword.setSelected(false);
                        buttonDate4_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 4
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 3);
                        day = c.getTime();
                        String day_4 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_4);
                    }
                    if (buttonDate5_searchclasskeyword.isSelected()){
                        buttonDate5_searchclasskeyword.setSelected(false);
                        buttonDate5_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 5
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 4);
                        day = c.getTime();
                        String day_5 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_5);
                    }
                    if (buttonDate6_searchclasskeyword.isSelected()){
                        buttonDate6_searchclasskeyword.setSelected(false);
                        buttonDate6_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 6
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 5);
                        day = c.getTime();
                        String day_6 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_6);
                    }
                    if (buttonDate7_searchclasskeyword.isSelected()){
                        buttonDate7_searchclasskeyword.setSelected(false);
                        buttonDate7_searchclasskeyword.setPressed(false);
                        Date day = new Date(); //day7
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 6);
                        day = c.getTime();
                        String day_7 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_7);
                    }

                    Date today = Calendar.getInstance().getTime();// today
                    String to_day = (String) DateFormat.format("dd", today);
                    FilterbyDate(to_day);

                }

            }
        });

        buttonDate2_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonDate2_searchclasskeyword.isSelected()){
                    buttonDate2_searchclasskeyword.setSelected(true);
                    if (buttonDate1_searchclasskeyword.isSelected()){
                        buttonDate1_searchclasskeyword.setSelected(false);
                        buttonDate1_searchclasskeyword.setPressed(false);
                        Date today = Calendar.getInstance().getTime();// today
                        String to_day = (String) DateFormat.format("dd", today);
                        FilterdeleteDate(to_day);
                    }
                    if (buttonDate3_searchclasskeyword.isSelected()){
                        buttonDate3_searchclasskeyword.setSelected(false);
                        buttonDate3_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 3
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 2);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_3);
                    }
                    if (buttonDate4_searchclasskeyword.isSelected()){
                        buttonDate4_searchclasskeyword.setSelected(false);
                        buttonDate4_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 4
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 3);
                        day = c.getTime();
                        String day_4 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_4);

                    }
                    if (buttonDate5_searchclasskeyword.isSelected()){
                        buttonDate5_searchclasskeyword.setSelected(false);
                        buttonDate5_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 5
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 4);
                        day = c.getTime();
                        String day_5 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_5);
                    }
                    if (buttonDate6_searchclasskeyword.isSelected()){
                        buttonDate6_searchclasskeyword.setSelected(false);
                        buttonDate6_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 6
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 5);
                        day = c.getTime();
                        String day_6 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_6);
                    }
                    if (buttonDate7_searchclasskeyword.isSelected()){
                        buttonDate7_searchclasskeyword.setSelected(false);
                        buttonDate7_searchclasskeyword.setPressed(false);
                        Date day = new Date(); //day7
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 6);
                        day = c.getTime();
                        String day_7 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_7);
                    }

                    Date day2 = new Date();//day 2
                    Calendar c = Calendar.getInstance();
                    c.setTime(day2);
                    c.add(Calendar.DATE, 1);
                    day2 = c.getTime();
                    String day_2 = (String) DateFormat.format("dd", day2);
                    FilterbyDate(day_2);

                }
            }
        });

        buttonDate3_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonDate3_searchclasskeyword.isSelected()){
                    buttonDate3_searchclasskeyword.setSelected(true);
                    if (buttonDate1_searchclasskeyword.isSelected()){
                        buttonDate1_searchclasskeyword.setSelected(false);
                        buttonDate1_searchclasskeyword.setPressed(false);
                        Date today = Calendar.getInstance().getTime();// today
                        String to_day = (String) DateFormat.format("dd", today);
                        FilterdeleteDate(to_day);
                    }
                    if (buttonDate2_searchclasskeyword.isSelected()){
                        buttonDate2_searchclasskeyword.setSelected(false);
                        buttonDate2_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 2
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 1);
                        day = c.getTime();
                        String day_2 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_2);
                    }
                    if (buttonDate4_searchclasskeyword.isSelected()){
                        buttonDate4_searchclasskeyword.setSelected(false);
                        buttonDate4_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 4
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 3);
                        day = c.getTime();
                        String day_4 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_4);
                    }
                    if (buttonDate5_searchclasskeyword.isSelected()){
                        buttonDate5_searchclasskeyword.setSelected(false);
                        buttonDate5_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 5
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 4);
                        day = c.getTime();
                        String day_5 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_5);
                    }
                    if (buttonDate6_searchclasskeyword.isSelected()){
                        buttonDate6_searchclasskeyword.setSelected(false);
                        buttonDate6_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 6
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 5);
                        day = c.getTime();
                        String day_6 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_6);
                    }
                    if (buttonDate7_searchclasskeyword.isSelected()){
                        buttonDate7_searchclasskeyword.setSelected(false);
                        buttonDate7_searchclasskeyword.setPressed(false);
                        Date day = new Date(); //day7
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 6);
                        day = c.getTime();
                        String day_7 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_7);
                    }

                    Date day3 = new Date();//day 3
                    Calendar c3 = Calendar.getInstance();
                    c3.setTime(day3);
                    c3.add(Calendar.DATE, 2);
                    day3 = c3.getTime();
                    String day_3 = (String) DateFormat.format("dd", day3);
                    FilterbyDate(day_3);
                }
            }
        });

        buttonDate4_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonDate4_searchclasskeyword.isSelected()){
                    buttonDate4_searchclasskeyword.setSelected(true);
                    if (buttonDate1_searchclasskeyword.isSelected()){
                        buttonDate1_searchclasskeyword.setSelected(false);
                        buttonDate1_searchclasskeyword.setPressed(false);
                        Date today = Calendar.getInstance().getTime();// today
                        String to_day = (String) DateFormat.format("dd", today);
                        FilterdeleteDate(to_day);
                    }
                    if (buttonDate2_searchclasskeyword.isSelected()){
                        buttonDate2_searchclasskeyword.setSelected(false);
                        buttonDate2_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 2
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 1);
                        day = c.getTime();
                        String day_2 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_2);
                    }
                    if (buttonDate3_searchclasskeyword.isSelected()){
                        buttonDate3_searchclasskeyword.setSelected(false);
                        buttonDate3_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 3
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 2);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_3);
                    }
                    if (buttonDate5_searchclasskeyword.isSelected()){
                        buttonDate5_searchclasskeyword.setSelected(false);
                        buttonDate5_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 5
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 4);
                        day = c.getTime();
                        String day_5 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_5);
                    }
                    if (buttonDate6_searchclasskeyword.isSelected()){
                        buttonDate6_searchclasskeyword.setSelected(false);
                        buttonDate6_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 6
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 5);
                        day = c.getTime();
                        String day_6 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_6);
                    }
                    if (buttonDate7_searchclasskeyword.isSelected()){
                        buttonDate7_searchclasskeyword.setSelected(false);
                        buttonDate7_searchclasskeyword.setPressed(false);
                        Date day = new Date(); //day7
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 6);
                        day = c.getTime();
                        String day_7 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_7);
                    }

                    Date day4 = new Date();//day 4
                    Calendar c4 = Calendar.getInstance();
                    c4.setTime(day4);
                    c4.add(Calendar.DATE, 3);
                    day4 = c4.getTime();
                    String day_4 = (String) DateFormat.format("dd", day4);
                    FilterbyDate(day_4);
                }
            }
        });

        buttonDate5_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonDate5_searchclasskeyword.isSelected()){
                    buttonDate5_searchclasskeyword.setSelected(true);
                    if (buttonDate1_searchclasskeyword.isSelected()){
                        buttonDate1_searchclasskeyword.setSelected(false);
                        buttonDate1_searchclasskeyword.setPressed(false);
                        Date today = Calendar.getInstance().getTime();// today
                        String to_day = (String) DateFormat.format("dd", today);
                        FilterdeleteDate(to_day);
                    }
                    if (buttonDate2_searchclasskeyword.isSelected()){
                        buttonDate2_searchclasskeyword.setSelected(false);
                        buttonDate2_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 2
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 1);
                        day = c.getTime();
                        String day_2 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_2);
                    }
                    if (buttonDate3_searchclasskeyword.isSelected()){
                        buttonDate3_searchclasskeyword.setSelected(false);
                        buttonDate3_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 3
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 2);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_3);
                    }
                    if (buttonDate4_searchclasskeyword.isSelected()){
                        buttonDate4_searchclasskeyword.setSelected(false);
                        buttonDate4_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 4
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 3);
                        day = c.getTime();
                        String day_4 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_4);
                    }
                    if (buttonDate6_searchclasskeyword.isSelected()){
                        buttonDate6_searchclasskeyword.setSelected(false);
                        buttonDate6_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 6
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 5);
                        day = c.getTime();
                        String day_6 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_6);
                    }
                    if (buttonDate7_searchclasskeyword.isSelected()){
                        buttonDate7_searchclasskeyword.setSelected(false);
                        buttonDate7_searchclasskeyword.setPressed(false);
                        Date day = new Date(); //day7
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 6);
                        day = c.getTime();
                        String day_7 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_7);
                    }

                    Date day5 = new Date();//day 5
                    Calendar c5 = Calendar.getInstance();
                    c5.setTime(day5);
                    c5.add(Calendar.DATE, 4);
                    day5 = c5.getTime();
                    String day_5 = (String) DateFormat.format("dd", day5);
                    FilterbyDate(day_5);
                }
            }
        });

        buttonDate6_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonDate6_searchclasskeyword.isSelected()){
                    buttonDate6_searchclasskeyword.setSelected(true);
                    if (buttonDate1_searchclasskeyword.isSelected()){
                        buttonDate1_searchclasskeyword.setSelected(false);
                        buttonDate1_searchclasskeyword.setPressed(false);
                        Date today = Calendar.getInstance().getTime();// today
                        String to_day = (String) DateFormat.format("dd", today);
                        FilterdeleteDate(to_day);
                    }
                    if (buttonDate2_searchclasskeyword.isSelected()){
                        buttonDate2_searchclasskeyword.setSelected(false);
                        buttonDate2_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 2
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 1);
                        day = c.getTime();
                        String day_2 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_2);
                    }
                    if (buttonDate3_searchclasskeyword.isSelected()){
                        buttonDate3_searchclasskeyword.setSelected(false);
                        buttonDate3_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 3
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 2);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_3);
                    }
                    if (buttonDate4_searchclasskeyword.isSelected()){
                        buttonDate4_searchclasskeyword.setSelected(false);
                        buttonDate4_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 4
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 3);
                        day = c.getTime();
                        String day_4 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_4);
                    }
                    if (buttonDate5_searchclasskeyword.isSelected()){
                        buttonDate5_searchclasskeyword.setSelected(false);
                        buttonDate5_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 5
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 4);
                        day = c.getTime();
                        String day_5 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_5);
                    }
                    if (buttonDate7_searchclasskeyword.isSelected()){
                        buttonDate7_searchclasskeyword.setSelected(false);
                        buttonDate7_searchclasskeyword.setPressed(false);
                        Date day = new Date(); //day7
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 6);
                        day = c.getTime();
                        String day_7 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_7);
                    }

                    Date day6 = new Date();//day 6
                    Calendar c6 = Calendar.getInstance();
                    c6.setTime(day6);
                    c6.add(Calendar.DATE, 5);
                    day6 = c6.getTime();
                    String day_6 = (String) DateFormat.format("dd", day6);
                    FilterbyDate(day_6);
                }
            }
        });

        buttonDate7_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonDate7_searchclasskeyword.isSelected()){
                    buttonDate7_searchclasskeyword.setSelected(true);
                    if (buttonDate1_searchclasskeyword.isSelected()){
                        buttonDate1_searchclasskeyword.setSelected(false);
                        buttonDate1_searchclasskeyword.setPressed(false);
                        Date today = Calendar.getInstance().getTime();// today
                        String to_day = (String) DateFormat.format("dd", today);
                        FilterdeleteDate(to_day);
                    }
                    if (buttonDate2_searchclasskeyword.isSelected()){
                        buttonDate2_searchclasskeyword.setSelected(false);
                        buttonDate2_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 2
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 1);
                        day = c.getTime();
                        String day_2 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_2);
                    }
                    if (buttonDate3_searchclasskeyword.isSelected()){
                        buttonDate3_searchclasskeyword.setSelected(false);
                        buttonDate3_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 3
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 2);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_3);
                    }
                    if (buttonDate4_searchclasskeyword.isSelected()){
                        buttonDate4_searchclasskeyword.setSelected(false);
                        buttonDate4_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 4
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 3);
                        day = c.getTime();
                        String day_4 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_4);
                    }
                    if (buttonDate5_searchclasskeyword.isSelected()){
                        buttonDate5_searchclasskeyword.setSelected(false);
                        buttonDate5_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 5
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 4);
                        day = c.getTime();
                        String day_5 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_5);
                    }
                    if (buttonDate6_searchclasskeyword.isSelected()){
                        buttonDate6_searchclasskeyword.setSelected(false);
                        buttonDate6_searchclasskeyword.setPressed(false);
                        Date day = new Date();//day 6
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, 5);
                        day = c.getTime();
                        String day_6 = (String) DateFormat.format("dd", day);
                        FilterdeleteDate(day_6);
                    }

                    Date day7 = new Date(); //day7
                    Calendar c7 = Calendar.getInstance();
                    c7.setTime(day7);
                    c7.add(Calendar.DATE, 6);
                    day7 = c7.getTime();
                    String day_7 = (String) DateFormat.format("dd", day7);
                    FilterbyDate(day_7);
                }
            }
        });


        buttontagsAll_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontagsAll_searchclasskeyword.isSelected()){
                    buttontagsAll_searchclasskeyword.setSelected(true);
                    buttontags1_searchclasskeyword.setSelected(false);
                    buttontags1_searchclasskeyword.setPressed(false);
                    buttontags2_searchclasskeyword.setSelected(false);
                    buttontags2_searchclasskeyword.setPressed(false);
                    buttontags3_searchclasskeyword.setSelected(false);
                    buttontags3_searchclasskeyword.setPressed(false);
                    buttontags4_searchclasskeyword.setSelected(false);
                    buttontags4_searchclasskeyword.setPressed(false);
                    buttontags5_searchclasskeyword.setSelected(false);
                    buttontags5_searchclasskeyword.setPressed(false);
                    buttontags6_searchclasskeyword.setSelected(false);
                    buttontags6_searchclasskeyword.setPressed(false);
                    onSuccess(courselist);
                }
            }
        });

        buttontags1_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags1_searchclasskeyword.isSelected()){
                    buttontags1_searchclasskeyword.setSelected(true);
                    buttontagsAll_searchclasskeyword.setSelected(false);
                    buttontagsAll_searchclasskeyword.setPressed(false);
                    keywordbt1 ="#Writing";
                    myFilter(keywordbt1);
                    onSuccess(courseArrayList);
                }
                else {
                    buttontags1_searchclasskeyword.setSelected(false);
                    buttontags1_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt1);
                    onSuccess(courseArrayList);
                    if (!buttontags1_searchclasskeyword.isSelected() &&
                            !buttontags2_searchclasskeyword.isSelected() &&
                            !buttontags3_searchclasskeyword.isSelected() &&
                            !buttontags4_searchclasskeyword.isSelected() &&
                            !buttontags5_searchclasskeyword.isSelected() &&
                            !buttontags6_searchclasskeyword.isSelected()){
                        buttontagsAll_searchclasskeyword.setSelected(true);
                        onSuccess(courselist);
                    }
                    if (buttontags2_searchclasskeyword.isSelected()){
                        myFilter(keywordbt2);
                    }
                    if (buttontags3_searchclasskeyword.isSelected()){
                        myFilter(keywordbt3);
                    }
                    if (buttontags4_searchclasskeyword.isSelected()){
                        myFilter(keywordbt4);
                    }
                    if (buttontags5_searchclasskeyword.isSelected()){
                        myFilter(keywordbt5);
                    }
                    if (buttontags6_searchclasskeyword.isSelected()){
                        myFilter(keywordbt6);
                    }
                }
            }
        });

        buttontags2_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags2_searchclasskeyword.isSelected()){
                    buttontags2_searchclasskeyword.setSelected(true);
                    buttontagsAll_searchclasskeyword.setSelected(false);
                    buttontagsAll_searchclasskeyword.setPressed(false);
                    keywordbt2 ="#Communication";
                    myFilter(keywordbt2);
                    onSuccess(courseArrayList);
                }else {
                    buttontags2_searchclasskeyword.setSelected(false);
                    buttontags2_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt2);
                    onSuccess(courseArrayList);
                    if (!buttontags1_searchclasskeyword.isSelected() &&
                            !buttontags2_searchclasskeyword.isSelected() &&
                            !buttontags3_searchclasskeyword.isSelected() &&
                            !buttontags4_searchclasskeyword.isSelected() &&
                            !buttontags5_searchclasskeyword.isSelected() &&
                            !buttontags6_searchclasskeyword.isSelected()){
                        buttontagsAll_searchclasskeyword.setSelected(true);
                        onSuccess(courselist);
                    }
                    if (buttontags1_searchclasskeyword.isSelected()){
                        myFilter(keywordbt1);
                    }
                    if (buttontags3_searchclasskeyword.isSelected()){
                        myFilter(keywordbt3);
                    }
                    if (buttontags4_searchclasskeyword.isSelected()){
                        myFilter(keywordbt4);
                    }
                    if (buttontags5_searchclasskeyword.isSelected()){
                        myFilter(keywordbt5);
                    }
                    if (buttontags6_searchclasskeyword.isSelected()){
                        myFilter(keywordbt6);
                    }
                }

            }
        });

        buttontags3_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags3_searchclasskeyword.isSelected()){
                    buttontags3_searchclasskeyword.setSelected(true);
                    buttontagsAll_searchclasskeyword.setSelected(false);
                    buttontagsAll_searchclasskeyword.setPressed(false);
                    keywordbt3 ="#Interpretive Practices";
                    myFilter(keywordbt3);
                    onSuccess(courseArrayList);
                }else {
                    buttontags3_searchclasskeyword.setSelected(false);
                    buttontags3_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt3);
                    onSuccess(courseArrayList);
                    if (!buttontags1_searchclasskeyword.isSelected() &&
                            !buttontags2_searchclasskeyword.isSelected() &&
                            !buttontags3_searchclasskeyword.isSelected() &&
                            !buttontags4_searchclasskeyword.isSelected() &&
                            !buttontags5_searchclasskeyword.isSelected() &&
                            !buttontags6_searchclasskeyword.isSelected()){
                        buttontagsAll_searchclasskeyword.setSelected(true);
                        onSuccess(courselist);
                    }
                    if (buttontags1_searchclasskeyword.isSelected()){
                        myFilter(keywordbt1);
                    }
                    if (buttontags2_searchclasskeyword.isSelected()){
                        myFilter(keywordbt2);
                    }
                    if (buttontags4_searchclasskeyword.isSelected()){
                        myFilter(keywordbt4);
                    }
                    if (buttontags5_searchclasskeyword.isSelected()){
                        myFilter(keywordbt5);
                    }
                    if (buttontags6_searchclasskeyword.isSelected()){
                        myFilter(keywordbt6);
                    }
                }

            }
        });

        buttontags4_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttontags4_searchclasskeyword.isSelected()){
                    buttontags4_searchclasskeyword.setSelected(true);
                    buttontagsAll_searchclasskeyword.setSelected(false);
                    buttontagsAll_searchclasskeyword.setPressed(false);
                    keywordbt4 ="#Professional Seminar";
                    myFilter(keywordbt4);
                    onSuccess(courseArrayList);
                }else {
                    buttontags4_searchclasskeyword.setSelected(false);
                    buttontags4_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt4);
                    onSuccess(courseArrayList);
                    if (!buttontags1_searchclasskeyword.isSelected() &&
                            !buttontags2_searchclasskeyword.isSelected() &&
                            !buttontags3_searchclasskeyword.isSelected() &&
                            !buttontags4_searchclasskeyword.isSelected() &&
                            !buttontags5_searchclasskeyword.isSelected() &&
                            !buttontags6_searchclasskeyword.isSelected()){
                        buttontagsAll_searchclasskeyword.setSelected(true);
                        onSuccess(courselist);
                    }
                    if (buttontags1_searchclasskeyword.isSelected()){
                        myFilter(keywordbt1);
                    }
                    if (buttontags2_searchclasskeyword.isSelected()){
                        myFilter(keywordbt2);
                    }
                    if (buttontags3_searchclasskeyword.isSelected()){
                        myFilter(keywordbt3);
                    }
                    if (buttontags5_searchclasskeyword.isSelected()){
                        myFilter(keywordbt5);
                    }
                    if (buttontags6_searchclasskeyword.isSelected()){
                        myFilter(keywordbt6);
                    }
                }

            }
        });

        buttontags5_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags5_searchclasskeyword.isSelected()){
                    buttontags5_searchclasskeyword.setSelected(true);
                    buttontagsAll_searchclasskeyword.setSelected(false);
                    buttontagsAll_searchclasskeyword.setPressed(false);
                    keywordbt5 ="#Tutoring";
                    myFilter(keywordbt5);
                    onSuccess(courseArrayList);
                }else {
                    buttontags5_searchclasskeyword.setSelected(false);
                    buttontags5_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt5);
                    onSuccess(courseArrayList);
                    if (!buttontags1_searchclasskeyword.isSelected() &&
                            !buttontags2_searchclasskeyword.isSelected() &&
                            !buttontags3_searchclasskeyword.isSelected() &&
                            !buttontags4_searchclasskeyword.isSelected() &&
                            !buttontags5_searchclasskeyword.isSelected() &&
                            !buttontags6_searchclasskeyword.isSelected()){
                        buttontagsAll_searchclasskeyword.setSelected(true);
                        onSuccess(courselist);
                    }
                    if (buttontags1_searchclasskeyword.isSelected()){
                        myFilter(keywordbt1);
                    }
                    if (buttontags2_searchclasskeyword.isSelected()){
                        myFilter(keywordbt2);
                    }
                    if (buttontags3_searchclasskeyword.isSelected()){
                        myFilter(keywordbt3);
                    }
                    if (buttontags4_searchclasskeyword.isSelected()){
                        myFilter(keywordbt4);
                    }
                    if (buttontags6_searchclasskeyword.isSelected()){
                        myFilter(keywordbt6);
                    }
                }

            }
        });

        buttontags6_searchclasskeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!buttontags6_searchclasskeyword.isSelected()){
                    buttontags6_searchclasskeyword.setSelected(true);
                    buttontagsAll_searchclasskeyword.setSelected(false);
                    buttontagsAll_searchclasskeyword.setPressed(false);
                    keywordbt6 ="#Argument";
                    myFilter(keywordbt6);
                    onSuccess(courseArrayList);
                }else {
                    buttontags6_searchclasskeyword.setSelected(false);
                    buttontags6_searchclasskeyword.setPressed(false);
                    myFilterdelete(keywordbt6);
                    onSuccess(courseArrayList);
                    if (!buttontags1_searchclasskeyword.isSelected() &&
                            !buttontags2_searchclasskeyword.isSelected() &&
                            !buttontags3_searchclasskeyword.isSelected() &&
                            !buttontags4_searchclasskeyword.isSelected() &&
                            !buttontags5_searchclasskeyword.isSelected() &&
                            !buttontags6_searchclasskeyword.isSelected()){
                        buttontagsAll_searchclasskeyword.setSelected(true);
                        onSuccess(courselist);
                    }
                    if (buttontags1_searchclasskeyword.isSelected()){
                        myFilter(keywordbt1);
                    }
                    if (buttontags2_searchclasskeyword.isSelected()){
                        myFilter(keywordbt2);
                    }
                    if (buttontags3_searchclasskeyword.isSelected()){
                        myFilter(keywordbt3);
                    }
                    if (buttontags4_searchclasskeyword.isSelected()){
                        myFilter(keywordbt4);
                    }
                    if (buttontags5_searchclasskeyword.isSelected()){
                        myFilter(keywordbt5);
                    }
                }

            }
        });
    }

    public void getClassdetail() {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getCoursedetail().enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                course = response.body();
                Date today = Calendar.getInstance().getTime();// today
                String to_day = (String) DateFormat.format("dd", today);
                FilterbyDate(to_day);
                buttonDate1_searchclasskeyword.setText(to_day);

                Date day2 = new Date();//day 2
                Calendar c = Calendar.getInstance();
                c.setTime(day2);
                c.add(Calendar.DATE, 1);
                day2 = c.getTime();
                String day_2 = (String) DateFormat.format("dd", day2);
                buttonDate2_searchclasskeyword.setText(day_2);

                Date day3 = new Date();//day 3
                Calendar c3 = Calendar.getInstance();
                c3.setTime(day3);
                c3.add(Calendar.DATE, 2);
                day3 = c3.getTime();
                String day_3 = (String) DateFormat.format("dd", day3);
                buttonDate3_searchclasskeyword.setText(day_3);

                Date day4 = new Date();//day 4
                Calendar c4 = Calendar.getInstance();
                c4.setTime(day4);
                c4.add(Calendar.DATE, 3);
                day4 = c4.getTime();
                String day_4 = (String) DateFormat.format("dd", day4);
                buttonDate4_searchclasskeyword.setText(day_4);

                Date day5 = new Date();//day 5
                Calendar c5 = Calendar.getInstance();
                c5.setTime(day5);
                c5.add(Calendar.DATE, 4);
                day5 = c5.getTime();
                String day_5 = (String) DateFormat.format("dd", day5);
                buttonDate5_searchclasskeyword.setText(day_5);

                Date day6 = new Date();//day 6
                Calendar c6 = Calendar.getInstance();
                c6.setTime(day6);
                c6.add(Calendar.DATE, 5);
                day6 = c6.getTime();
                String day_6 = (String) DateFormat.format("dd", day6);
                buttonDate6_searchclasskeyword.setText(day_6);

                Date day7 = new Date(); //day7
                Calendar c7 = Calendar.getInstance();
                c7.setTime(day7);
                c7.add(Calendar.DATE, 6);
                day7 = c7.getTime();
                String day_7 = (String) DateFormat.format("dd", day7);
                buttonDate7_searchclasskeyword.setText(day_7);


                Log.w(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "get fail");
            }
        });
    }

    private void onSuccess(List<Course> courselist) {
//        courselist.clear();
//        courselist.addAll(courseArrayList);
        adapterKeyword_search = new RecycleAdapterKeyword_search(courselist,getActivity());
        recyclerView.setAdapter(adapterKeyword_search);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        adapterKeyword_search = new RecycleAdapterKeyword_search(getActivity(), this);
    }

    public void myFilter(String name){

        name = name.toLowerCase(Locale.getDefault());
        for (Course each : courselist){
            if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                courseArrayList.remove(each);
            }
    }
        for (Course each : courselist){
            if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                courseArrayList.add(each);
            }
        }

    }

    public void FilterbyDate(String date){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        for (Course each : course){
            try {
                d = input.parse(each.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String day = (String) DateFormat.format("dd", d);
            if (day.equals(date)){
                courselist.remove(each);
            }
        }
        for (Course each : course){
            try {
                d = input.parse(each.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String day = (String) DateFormat.format("dd", d);
            if (day.equals(date)){
                courselist.add(each);
            }
        }
        onSuccess(courselist);

    }

    public void FilterdeleteDate(String date){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Course> courselist_copy = new ArrayList<>();
        for (Course each : courselist){
            try {
                d = input.parse(each.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String day = (String) DateFormat.format("dd", d);
            if (day.equals(date)){
                courselist_copy.add(each);
            }
        }
        courselist.removeAll(courselist_copy);
    }

    public void myFilterdelete(String name){
        ArrayList<Course> courselist_copy = new ArrayList<>();
        name = name.toLowerCase(Locale.getDefault());
        for (Course each : courseArrayList){
            if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                courselist_copy.add(each);
            }
        }
        courseArrayList.removeAll(courselist_copy);
    }
}
