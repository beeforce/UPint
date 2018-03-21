package com.example.bee.upint2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bee.upint2.LoginActivity;
import com.example.bee.upint2.Main2Activity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.RecycleAdapterCourse;
import com.example.bee.upint2.adapter.RecycleAdapterListening;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bee on 1/30/2018.
 */

public class Listenfragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {

    private static final String TAG = "Listenfragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Course> course;
    private List<Course> courselistenList = new ArrayList<>();
    private Course courselisten;
    //    private List<Integer> courseid = new ArrayList<Integer>();
    private RecycleAdapterListening adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;
    private ApiService mAPIService2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_litening, container, false);
        mAPIService = ApiUtils.getAPIService();
        if (course != null) {
            course.clear();
            courselistenList.clear();
        }

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {


        swipeRefreshLayout = rootView.findViewById(R.id.refresh2);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        getClassdetailwithId();

    }

    @Override
    public void onRefresh() {
        course.clear();
        courselistenList.clear();
        getClassdetailwithId();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        adapter = new RecycleAdapterListening(getActivity(), this);
    }

    public void getClassdetailwithId() {
        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        mAPIService.courseDetailsOfuser(user_id).enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    course = response.body();
                    for (int i = 0; i < course.size(); i++) {
                        onSuccess(course.get(i).getCourse_id());
                    }
                    Log.w(TAG, "onResponse: " + course.size());
                } else {

                    Log.w(TAG, "onResponseFail: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void onSuccess(int course_id) {
        mAPIService2 = ApiUtils.getAPIService();
        mAPIService2.courseDetailswithId(course_id).enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                if (response.isSuccessful()) {
                    courselisten = response.body();
                    if (courselistenList.size() <= course.size()) {
                        courselistenList.add(courselisten);

                        final Date[] d1 = new Date[1];
                        final Date[] d2 = new Date[1];

                        // sort by date
                        Collections.sort(courselistenList, new Comparator<Course>() {

                            @Override
                            public int compare(Course o1, Course o2) {
                                if (o1.getDate() == null || o2.getDate() == null)
                                    return 0;

                                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    d1[0] = input.parse(o1.getDate());
                                    d2[0] = input.parse(o2.getDate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return d1[0].compareTo(d2[0]);
                            }

                        });

                        adapter = new RecycleAdapterListening(courselistenList, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                    Log.w(TAG, "onResponse: " + courselisten.getCourse_name().toString());
                }
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {

            }
        });

    }
}
