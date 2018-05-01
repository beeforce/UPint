package com.example.bee.upint2.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bee.upint2.R;
import com.example.bee.upint2.calendar.DrawableCalendarEvent;
import com.example.bee.upint2.calendar.DrawableEventRenderer;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bee on 1/30/2018.
 */

public class Statusfragment extends android.support.v4.app.Fragment {

    private static final String TAG = "StatusfragmentTag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        GraphView graph2 = (GraphView) rootView.findViewById(R.id.graph2);
        GraphView graph3 = (GraphView) rootView.findViewById(R.id.graph3);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 500),
                new DataPoint(1, 1500),
                new DataPoint(2, 0),
                new DataPoint(3, 1000),
                new DataPoint(4, 0),
                new DataPoint(5, 0),
                new DataPoint(6, 0),
                new DataPoint(7, 1500),
                new DataPoint(8, 1500),
        });
        graph.addSeries(series);
        graph2.addSeries(series);
        graph3.addSeries(series);

        return rootView;
    }


}
