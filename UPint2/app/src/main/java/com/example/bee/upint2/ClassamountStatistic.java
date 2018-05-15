package com.example.bee.upint2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.example.bee.upint2.model.CourseStatistic;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassamountStatistic extends AppCompatActivity {

    private GraphView graph;
    private ApiService mAPIService;
    private List<CourseStatistic> course;
    private DataPoint[] newlist;
    private Date StartDate, LastDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classamount_statistic);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        graph = findViewById(R.id.graph);
        getStatisticdetail();
    }

    public void getStatisticdetail() {
        sendOject o = new sendOject();
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getclassStat(o.getUser_id()).enqueue(new Callback<List<CourseStatistic>>() {
            @Override
            public void onResponse(Call<List<CourseStatistic>> call, Response<List<CourseStatistic>> response) {
                course = response.body();
                if (course.size() > 0) {
                    getGraphinformation(course);
                } else {
                    Date today = Calendar.getInstance().getTime();
                    newlist = new DataPoint[1];
                    newlist[0] = new DataPoint(today.getTime(),0);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist);
                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                    graph.setTitle("No Statistic yet");
                    graph.setTitleColor(Color.parseColor("#98c428"));
                    graph.getGridLabelRenderer().setNumHorizontalLabels(1);
                }

            }

            @Override
            public void onFailure(Call<List<CourseStatistic>> call, Throwable t) {

            }
        });
    }

    public void getGraphinformation(List<CourseStatistic> courseList) {
        String start_date = courseList.get(0).getDate();
        String last_date = courseList.get(courseList.size() - 1).getDate();
        Date today = new Date();
        Calendar ctd = Calendar.getInstance();
        ctd.setTime(today);
        ctd.add(Calendar.DATE, 2);
        today = ctd.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            StartDate = format.parse(start_date);
            LastDate = format.parse(last_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (today.getTime() > StartDate.getTime()) {
            long diff = today.getTime() - StartDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            int date_remain = (Math.round(days));
            newlist = new DataPoint[date_remain + 1];
            int date_index = 1;
            int index_listview = newlist.length - 1;
            for (int i = newlist.length - 1; i >= 0; i--) {
                Date day = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(day);
                c.add(Calendar.DATE, date_index);
                day = c.getTime();
                String day_3_format = (String) DateFormat.format("yyyy-MM-dd", day);
                date_index--;
                newlist[index_listview] = new DataPoint(day.getTime(), 0);
                for (CourseStatistic each : courseList) {
                    if (each.getDate().equals(day_3_format)) {
                        newlist[index_listview] = new DataPoint(day.getTime(), each.getCount());
                    }
                }
                index_listview--;
            }


            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(newlist);
            graph.addSeries(series);
            graph.setCursorMode(true);
            Date day = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(day);
            c.add(Calendar.DATE, 2);
            day = c.getTime();
            graph.getViewport().setMaxX(day.getTime());
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        }


    }
}
