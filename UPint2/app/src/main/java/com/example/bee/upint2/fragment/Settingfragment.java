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

import com.example.bee.upint2.AppfeedActivity;
import com.example.bee.upint2.MakeclassActivity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.SeachclassActivity;
import com.example.bee.upint2.SearchclassActivity_Keyword;

/**
 * Created by Bee on 1/30/2018.
 */

public class Settingfragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;

    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        initInstances(rootView);
        return rootView;
    }


    private void initInstances(View rootView) {

        TextView tvProjectName = rootView.findViewById(R.id.tvProjectName4);
        Button button = rootView.findViewById(R.id.searchclassbt);
        tvProjectName.setText(Html.fromHtml("<b>Setting</b>"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeachclassActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = rootView.findViewById(R.id.searchclasskeywordbt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchclassActivity_Keyword.class);
                startActivity(intent);
            }
        });
        Button button3 = rootView.findViewById(R.id.Appffeedbt);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppfeedActivity.class);
                startActivity(intent);
            }
        });



    }
}