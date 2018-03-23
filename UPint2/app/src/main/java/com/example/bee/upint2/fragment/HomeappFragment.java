package com.example.bee.upint2.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.MyRecyclerViewAdapter;
import com.example.bee.upint2.adapter.RecycleAdapterCourse;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.Course_user;
import com.example.bee.upint2.model.sendOject;
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
 * Created by Bee on 3/22/2018.
 */

public class HomeappFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Course> course;
    private List<Course_user> courseEnroll;
//    private RecycleAdapterCourse adapter;
    private static final String TAG = "HomeappFragment";
    private ApiService mAPIService;
    private MyRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_homeapp, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
//        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Integer> viewColoers = new ArrayList<>();
        viewColoers.add(Color.BLUE);
        viewColoers.add(Color.YELLOW);
        viewColoers.add(Color.MAGENTA);
        viewColoers.add(Color.RED);
        viewColoers.add(Color.BLACK);

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        recyclerView = rootView.findViewById(R.id.recyclerView_star_tutor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new MyRecyclerViewAdapter(getActivity(), viewColoers, animalNames);
        recyclerView.setAdapter(adapter);



//        getClassdetail();

    }


//    public void getClassdetail() {
//        mAPIService = ApiUtils.getAPIService();
//        mAPIService.getCoursedetail().enqueue(new Callback<List<Course>>() {
//            @Override
//            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
//                course = response.body();
//                searchClass();
//
//
//                Log.w(TAG, "onResponse: ");
//            }
//
//            @Override
//            public void onFailure(Call<List<Course>> call, Throwable t) {
//                Log.w(TAG, "get fail");
//            }
//        });
//    }
//
//    private void onSuccess(List<Course> courseList) {
//        List<Course> filteredJob = new ArrayList<Course>();
//
//        final Date[] d1 = new Date[1];
//        final Date[] d2 = new Date[1];
//
//        // sort by date
//        Collections.sort(courseList, new Comparator<Course>() {
//
//            @Override
//            public int compare(Course o1, Course o2) {
//                if (o1.getDate() == null || o2.getDate() == null)
//                    return 0;
//
//                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    d1[0] = input.parse(o1.getDate());
//                    d2[0] = input.parse(o2.getDate());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                return d1[0].compareTo(d2[0]);
//            }
//
//        });
//
//        // sort by id
//        for (Course each : courseList) {
//            for (Course_user each1 : courseEnroll) {
//                if (each.getId() == each1.getCourse_id()){
//                    filteredJob.add(each);
//                    Log.w(TAG, "find course id"+each.getId()+"  "+each1.getCourse_id());
//                }
//            }
//        }
//        courseList.removeAll(filteredJob);
//        adapter = new RecycleAdapterCourse(courseList, getActivity());
//        recyclerView.setAdapter(adapter);
//    }
//
//    public void searchClass() {
//
//        sendOject o = new sendOject();
//        int user_id = Integer.parseInt(o.getUser_id());
//        mAPIService = ApiUtils.getAPIService();
//        mAPIService.searchclassEnroll(user_id).enqueue(new Callback<List<Course_user>>() {
//            @Override
//            public void onResponse(Call<List<Course_user>> call, Response<List<Course_user>> response) {
//                if (response.isSuccessful()) {
//                    courseEnroll = response.body();
//                    onSuccess(course);
//                } else
//                    Log.w(TAG, "Search class onResponse:fail");
//            }
//
//            @Override
//            public void onFailure(Call<List<Course_user>> call, Throwable t) {
//                Log.w(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//    }
}
