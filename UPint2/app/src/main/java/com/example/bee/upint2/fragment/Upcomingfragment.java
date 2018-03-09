package com.example.bee.upint2.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.RecycleAdapterCourse;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.Course_user;
import com.example.bee.upint2.model.UserProfile;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class Upcomingfragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {


    private static final String TAG = "Upcomingfragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Course> course;
    private List<Course_user> courseEnroll;
    private RecycleAdapterCourse adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService mAPIService;
    private Date d;
    public static String scheduledate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_upcoming, container, false);

        initInstances(rootView);
//        Userdetail();
        return rootView;
    }

    @Override
    public void onRefresh() {
        recyclerView.removeAllViewsInLayout();
        getClassdetail();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initInstances(View rootView) {


        swipeRefreshLayout = rootView.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);


        getClassdetail();

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        adapter = new RecycleAdapterCourse(getActivity(), this);

    }

    public void getClassdetail() {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getCoursedetail().enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                course = response.body();
                searchClass();


                Log.w(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "get fail");
            }
        });
    }

    private void onSuccess(List<Course> courseList) {
        List<Course> filteredJob = new ArrayList<Course>();

        final Date[] d1 = new Date[1];
        final Date[] d2 = new Date[1];

        // sort by date
        Collections.sort(courseList, new Comparator<Course>() {

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

        // sort by id
        for (Course each : courseList) {
            for (Course_user each1 : courseEnroll) {
                if (each.getId() == each1.getCourse_id()){
                    filteredJob.add(each);
                    Log.w(TAG, "find course id"+each.getId()+"  "+each1.getCourse_id());
                }
            }
        }
        courseList.removeAll(filteredJob);
        adapter = new RecycleAdapterCourse(courseList, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void searchClass() {

        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        mAPIService = ApiUtils.getAPIService();
        mAPIService.searchclassEnroll(user_id).enqueue(new Callback<List<Course_user>>() {
            @Override
            public void onResponse(Call<List<Course_user>> call, Response<List<Course_user>> response) {
                if (response.isSuccessful()) {
                    courseEnroll = response.body();
                    onSuccess(course);
                } else
                    Log.w(TAG, "Search class onResponse:fail");
            }

            @Override
            public void onFailure(Call<List<Course_user>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    //ยังไม่เสด
//    private void Userdetail() {
//
//        mAPIService = ApiUtils.getAPIService();
//        mAPIService.userDetail("Bee@hotmail.com").enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                if (response.isSuccessful()) {
//                    Log.w(TAG, "onResponse: " + response.body() );
//                    onSuccess(response.body());
//                } else {
//                    Log.w(TAG, "onResponsefail ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserProfile> call, Throwable t) {
//                Log.w(TAG, "onfailure"+t.getMessage());
//            }
//        });
//    }
//
//    private void onSuccess(UserProfile userProfile){
//
//        tvName.setText(userProfile.getFirst_name()+" | "+userProfile.getLast_name());
//        tvTruckId.setText(userProfile.getSchool()+" | "+userProfile.getMajor()+" | "+userProfile.getDate_graduated());
//        tvAddress.setText(userProfile.getImage());
//
//    }

}
