package com.example.bee.upint2.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bee.upint2.ClassamountStatistic;
import com.example.bee.upint2.HourStatistic;
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
    private List<CourseStatistic> course, course2, course3;
    private GraphView graph, graph2, graph3;
    private DataPoint[] newlist, newlist2, newlist3;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

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
        TextView seeall_hourstat = rootView.findViewById(R.id.seeall_hourstat);
        seeall_hourstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), HourStatistic.class);
                startActivity(i);
            }
        });

        getStatisticdetail();
        getClasscountStatistic();
        getHourcountStatistic();

        return rootView;
    }


    public void getStatisticdetail() {
        sendOject o = new sendOject();
        mAPIService = ApiUtils.getAPIService();
        mAPIService.get7daysStat(o.getUser_id()).enqueue(new Callback<List<CourseStatistic>>() {
            @Override
            public void onResponse(Call<List<CourseStatistic>> call, Response<List<CourseStatistic>> response) {
                course = response.body();
                if (course.size() > 0) {
                    getGraphinformation(course);
                } else {
                    newlist = new DataPoint[8];
                    int date_index = 0;
                    for (int i = newlist.length - 1; i >= 0; i--) {
                        Date day = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, date_index);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
                        int day_int = Integer.parseInt(day_3);
                        newlist[i] = new DataPoint(c.getTime(), 0);
                        date_index--;
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist);
                    graph3.addSeries(series);
                    graph3.setCursorMode(true);
                    Date day = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(day);
                    c.add(Calendar.DATE, 1);
                    day = c.getTime();
                    graph3.getViewport().setMaxX(day.getTime());
                    graph3.setTitle("Income in 7 days");
                    graph3.setTitleColor(Color.parseColor("#98c428"));
                    graph3.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if (isValueX) {
                                return sdf.format(new Date((long) value));
                            }
                            return super.formatLabel(value, isValueX);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<CourseStatistic>> call, Throwable t) {

            }
        });
    }

    public void getGraphinformation(List<CourseStatistic> courseList) {
        newlist = new DataPoint[8];
        int date_index = 0;
        for (int i = newlist.length - 1; i >= 0; i--) {
            Date day = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.add(Calendar.DATE, date_index);
            day = c.getTime();
            String day_3 = (String) DateFormat.format("dd", day);
            String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
            int day_int = Integer.parseInt(day_3);
            newlist[i] = new DataPoint(c.getTime(), 0);
            date_index--;
            for (CourseStatistic each : courseList) {
                if (each.getDate().equals(day_3_format)) {
                    int price = Integer.parseInt(each.getPrice_per_student());
                    newlist[i] = new DataPoint(c.getTime(), price);
                }
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist);
        graph3.addSeries(series);
        graph3.setCursorMode(true);
        Date day = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DATE, 1);
        day = c.getTime();
        graph3.getViewport().setMaxX(day.getTime());
        graph3.setTitle("Income in 7 days");
        graph3.setTitleColor(Color.parseColor("#98c428"));
        graph3.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
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
                if (course2.size() > 0) {
                    getGraphinformationCount(course2);
                } else {
                    newlist2 = new DataPoint[8];
                    int date_index = 0;
                    for (int i = newlist2.length - 1; i >= 0; i--) {
                        Date day = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, date_index);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
                        int day_int = Integer.parseInt(day_3);
                        newlist2[i] = new DataPoint(c.getTime(), 0);
                        date_index--;
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist2);
                    graph.addSeries(series);
                    graph.setCursorMode(true);
                    Date day = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(day);
                    c.add(Calendar.DATE, 1);
                    day = c.getTime();
                    graph.getViewport().setMaxX(day.getTime());
                    graph.setTitle("Class amount in 7 days");
                    graph.setTitleColor(Color.parseColor("#98c428"));
                    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if (isValueX) {
                                return sdf.format(new Date((long) value));
                            }
                            return super.formatLabel(value, isValueX);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<CourseStatistic>> call, Throwable t) {

            }
        });
    }

    public void getGraphinformationCount(List<CourseStatistic> courseList) {
        newlist2 = new DataPoint[8];
        int date_index = 0;
        for (int i = newlist2.length - 1; i >= 0; i--) {
            Date day = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.add(Calendar.DATE, date_index);
            day = c.getTime();
            String day_3 = (String) DateFormat.format("dd", day);
            String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
            int day_int = Integer.parseInt(day_3);
            newlist2[i] = new DataPoint(day.getTime(), 0);
            date_index--;
            for (CourseStatistic each : courseList) {
                if (each.getDate().equals(day_3_format)) {
                    newlist2[i] = new DataPoint(day.getTime(), each.getCount());
                }
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist2);
        graph.addSeries(series);
        graph.setCursorMode(true);
        Date day = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DATE, 1);
        day = c.getTime();
        graph.getViewport().setMaxX(day.getTime());
        graph.setTitle("Class amount in 7 days");
        graph.setTitleColor(Color.parseColor("#98c428"));
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                }
                return super.formatLabel(value, isValueX);
            }
        });
    }

    public void getHourcountStatistic() {
        sendOject o = new sendOject();
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getAllStatHour(o.getUser_id()).enqueue(new Callback<List<CourseStatistic>>() {
            @Override
            public void onResponse(Call<List<CourseStatistic>> call, Response<List<CourseStatistic>> response) {
                course3 = response.body();
                if (course3.size() > 0) {
                    getGraphinformationHour(course3);
                } else {
                    newlist3 = new DataPoint[8];
                    int date_index = 0;
                    for (int i = newlist3.length - 1; i >= 0; i--) {
                        Date day = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(day);
                        c.add(Calendar.DATE, date_index);
                        day = c.getTime();
                        String day_3 = (String) DateFormat.format("dd", day);
                        newlist3[i] = new DataPoint(c.getTime(), 0);
                        date_index--;
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist3);
                    graph2.addSeries(series);
                    graph2.setCursorMode(true);
                    Date day = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(day);
                    c.add(Calendar.DATE, 1);
                    day = c.getTime();
                    graph2.getViewport().setMaxX(day.getTime());
                    graph2.setTitle("Hour amount in 7 days");
                    graph2.setTitleColor(Color.parseColor("#98c428"));
                    graph2.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if (isValueX) {
                                return sdf.format(new Date((long) value));
                            }
                            return super.formatLabel(value, isValueX);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<CourseStatistic>> call, Throwable t) {
                Log.w(TAG, "onFailure: " );

            }
        });
    }

    public void getGraphinformationHour(List<CourseStatistic> courseList) {
        newlist3 = new DataPoint[8];
        int date_index = 0;
        for (int i = newlist3.length - 1; i >= 0; i--) {
            Date day = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.add(Calendar.DATE, date_index);
            day = c.getTime();
            String day_3 = (String) DateFormat.format("dd", day);
            String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
            newlist3[i] = new DataPoint(day.getTime(), 0);
            date_index--;
            for (CourseStatistic each : courseList) {
                if (each.getDate().equals(day_3_format)) {
                    newlist3[i] = new DataPoint(day.getTime(), each.getHour());
                }
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist3);
        graph2.addSeries(series);
        graph2.setCursorMode(true);
        Date day = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.add(Calendar.DATE, 1);
        day = c.getTime();
        graph2.getViewport().setMaxX(day.getTime());
        graph2.setTitle("Hour amount in 7 days");
        graph2.setTitleColor(Color.parseColor("#98c428"));
        graph2.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                }
                return super.formatLabel(value, isValueX);
            }
        });
    }


}
