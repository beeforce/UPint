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
import android.widget.Button;

import com.example.bee.upint2.R;
import com.example.bee.upint2.adapter.RecycleAdapterKeyword_search;
import com.example.bee.upint2.adapter.RecycleAdapterListening;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.util.ArrayList;
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
            buttontags5_searchclasskeyword, buttontags6_searchclasskeyword, buttontagsAll_searchclasskeyword;
    private static String TAG = "SearchclassActivity_Keyword";
    private String keywordbt1, keywordbt2, keywordbt3, keywordbt4, keywordbt5
            ,keywordbt6;
    private List<Course> course;
    private ArrayList<Course> courseArrayList = new ArrayList<>();
    private RecycleAdapterKeyword_search adapterKeyword_search;



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

        buttontagsAll_searchclasskeyword = rootView.findViewById(R.id.buttontagsAll_searchclasskeyword);
        buttontags1_searchclasskeyword = rootView.findViewById(R.id.buttontags1_searchclasskeyword);
        buttontags2_searchclasskeyword = rootView.findViewById(R.id.buttontags2_searchclasskeyword);
        buttontags3_searchclasskeyword = rootView.findViewById(R.id.buttontags3_searchclasskeyword);
        buttontags4_searchclasskeyword = rootView.findViewById(R.id.buttontags4_searchclasskeyword);
        buttontags5_searchclasskeyword = rootView.findViewById(R.id.buttontags5_searchclasskeyword);
        buttontags6_searchclasskeyword = rootView.findViewById(R.id.buttontags6_searchclasskeyword);

        buttontagsAll_searchclasskeyword.setSelected(true);

        initOnclick();
    }

    private void initOnclick(){

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
                    onSuccess(course);
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
                        onSuccess(course);
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
                        onSuccess(course);
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
                        onSuccess(course);
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
                        onSuccess(course);
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
                        onSuccess(course);
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
                        onSuccess(course);
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
                onSuccess(course);


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
        for (Course each : course){
            if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                courseArrayList.remove(each);
            }
    }
        for (Course each : course){
            if (each.getTags().toLowerCase(Locale.getDefault()).contains(name)){
                courseArrayList.add(each);
            }
        }

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
