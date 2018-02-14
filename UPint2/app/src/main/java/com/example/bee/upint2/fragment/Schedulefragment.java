package com.example.bee.upint2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bee.upint2.LoginActivity;
import com.example.bee.upint2.MakeclassActivity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.Register;

/**
 * Created by Bee on 1/30/2018.
 */

public class Schedulefragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String truckDriverId;
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        TextView tvProjectName = rootView.findViewById(R.id.tvProjectName3);
        tvProjectName.setText(Html.fromHtml("<b>Schedule</b>"));
        Button makeclass_button = (Button) rootView.findViewById(R.id.makeclassbt);
        makeclass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeclassActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }


}