package com.example.bee.upint2.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bee.upint2.ClassamountStatistic;
import com.example.bee.upint2.IncomeStatistic;
import com.example.bee.upint2.R;
import com.example.bee.upint2.model.CourseStatistic;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bee on 1/30/2018.
 */

public class Statusfragment extends android.support.v4.app.Fragment {

    private static final String TAG = "StatusfragmentTag";
    private ApiService mAPIService;
    private List<CourseStatistic> course, course2;
    private GraphView graph, graph2,graph3;
    private DataPoint[] newlist, newlist2;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

        getStatisticdetail();
        getClasscountStatistic();
        graph = (GraphView) rootView.findViewById(R.id.graph);
        graph2 = (GraphView) rootView.findViewById(R.id.graph2);
        graph3 = (GraphView) rootView.findViewById(R.id.graph3);
        TextView seeall_incomeStatistic = rootView.findViewById(R.id.seeall_incomeStatistic);
        seeall_incomeStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), IncomeStatistic.class);
                startActivity(i);
            }
        });
        TextView seeall_classamount = rootView.findViewById(R.id.seeall_classamount);
        seeall_classamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ClassamountStatistic.class);
                startActivity(i);
            }
        });
//                new DataPoint[] {
//                new DataPoint(1, 500),
//                new DataPoint(2, 1500),
//                new DataPoint(3, 0),
//                new DataPoint(4, 1000),
//                new DataPoint(5, 0),
//                new DataPoint(6, 0),
//                new DataPoint(7, 0),
//        });

        return rootView;
    }


    public void getStatisticdetail() {
        sendOject o = new sendOject();
        mAPIService = ApiUtils.getAPIService();
        mAPIService.get7daysStat(o.getUser_id()).enqueue(new Callback<List<CourseStatistic>>() {
            @Override
            public void onResponse(Call<List<CourseStatistic>> call, Response<List<CourseStatistic>> response) {
                course = response.body();
                getGraphinformation(course);

            }

            @Override
            public void onFailure(Call<List<CourseStatistic>> call, Throwable t) {

            }
        });
    }

    public void getGraphinformation(List<CourseStatistic> courseList){
        newlist = new DataPoint[8];
        int date_index = 0;
        for (int i = newlist.length-1;i >= 0; i--){
            Date day = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.add(Calendar.DATE, date_index);
            day = c.getTime();
            String day_3 = (String) DateFormat.format("dd", day);
            String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
            int day_int = Integer.parseInt(day_3);
            newlist[i] = new DataPoint(c.getTime(),0);
            date_index--;
            for (CourseStatistic each : courseList) {
                if (each.getDate().equals(day_3_format)){
                    int price = Integer.parseInt(each.getPrice_per_student());
                    newlist[i] = new DataPoint(c.getTime(),price);
                }
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist);
        graph3.addSeries(series);
        graph3.setTitle("Income in 7 days");
        graph3.setTitleColor(Color.parseColor("#98c428"));
        graph3.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX){
                    return sdf.format(new Date((long) value));
                }
                return super.formatLabel(value, isValueX);
            }
        });

    }

    public void getClasscountStatistic() {
        sendOject o = new sendOject();
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getclassStat(o.getUser_id()).enqueue(new Callback<List<CourseStatistic>>() {
            @Override
            public void onResponse(Call<List<CourseStatistic>> call, Response<List<CourseStatistic>> response) {
                course2 = response.body();
                getGraphinformationCount(course2);

            }

            @Override
            public void onFailure(Call<List<CourseStatistic>> call, Throwable t) {

            }
        });
    }

    public void getGraphinformationCount(List<CourseStatistic> courseList){
        newlist2 = new DataPoint[8];
        int date_index = 0;
        for (int i = newlist2.length-1;i >= 0; i--){
            Date day = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.add(Calendar.DATE, date_index);
            day = c.getTime();
            String day_3 = (String) DateFormat.format("dd", day);
            String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
            int day_int = Integer.parseInt(day_3);
            newlist2[i] = new DataPoint(day.getTime(),0);
            date_index--;
            for (CourseStatistic each : courseList) {
                if (each.getDate().equals(day_3_format)){
                    newlist2[i] = new DataPoint(day.getTime(),each.getCount());
                }
            }
        }
        Date day = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DATE, 0);
        day = c.getTime();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist2);
        graph.addSeries(series);
        graph.setTitle("Class amount in 7 days");
        graph.setTitleColor(Color.parseColor("#98c428"));
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX){
                    return sdf.format(new Date((long) value));
                }
                return super.formatLabel(value, isValueX);
            }
        });



    }


}
