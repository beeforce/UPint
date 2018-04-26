package com.example.bee.upint2.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bee.upint2.MakeclassActivity;
import com.example.bee.upint2.R;
import com.example.bee.upint2.calendar.DrawableCalendarEvent;
import com.example.bee.upint2.calendar.DrawableEventRenderer;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
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

public class Schedulefragment extends android.support.v4.app.Fragment implements CalendarPickerController {


    private AgendaCalendarView mAgendaCalendarView;
    private TextView month;
    private List<Course> course;
    private ApiService mAPIService;
    private List<CalendarEvent> eventList = new ArrayList<>();
    private static final String TAG = "Schedulefragment";
    private SweetAlertDialog pDialog;
    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        mAgendaCalendarView = (AgendaCalendarView) rootView.findViewById(R.id.agenda_calendar_view);


        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.DATE, -1);
        maxDate.add(Calendar.YEAR, 1);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        mAgendaCalendarView.setScrollbarFadingEnabled(true);

        month = (TextView) rootView.findViewById(R.id.month);
        if (course == null) {
            getClassdetailwithId();
        }

        return rootView;
    }

    public void RefreshFragment() {
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.setReorderingAllowed(false);
        t.detach(this).attach(this).commitAllowingStateLoss();
    }


    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {

    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        month.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));

    }

    public void getClassdetailwithId() {
        showProgressDialog();
        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        mAPIService = ApiUtils.getAPIService();
        mAPIService.courseListening(user_id).enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    course = response.body();
                    onSuccess(course);
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

    public void onSuccess(List<Course> courseList) {
        final Date[] d1 = new Date[1];
        final Date[] d2 = new Date[1];

        // sort by date
        Collections.sort(courseList, new Comparator<Course>() {

            @Override
            public int compare(Course o1, Course o2) {
                if (o1.getDate() == null || o2.getDate() == null)
                    return 0;

                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    d1[0] = input.parse(o1.getDate()+" "+o1.getStart_time());
                    d2[0] = input.parse(o2.getDate()+" "+o2.getStart_time());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return d1[0].compareTo(d2[0]);
            }

        });
        makeEventlist(courseList);
    }

    public void makeEventlist(List<Course> courseList){
        for (Course each : courseList) {
            if (each != null){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Log.w(TAG, "courselisten" + each.getCourse_name());
                Date date = null;
                try {
                    date = format.parse(each.getDate());
                    Log.w(TAG, "try-catch: Success" + date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                //string part timestart
                String timestartst = each.getStart_time();
                String[] timestartpart = timestartst.split(":");
                String timestart1 = timestartpart[0];
                String timestart2 = timestartpart[1];
                String timestart3 = timestartpart[2];
                String timestart = timestart1 + "." + timestart2;
                //string part timefinish
                String timefinishst = each.getFinish_time();
                String[] timefinishpart = timefinishst.split(":");
                String timefinish1 = timefinishpart[0];
                String timefinish2 = timefinishpart[1];
                String timefinish3 = timefinishpart[2];
                String timefinish = timefinish1 + "." + timefinish2;

                int hour = Integer.parseInt(timestart1) % 12;
                int hourfinish = (Integer.parseInt(timefinish1)) % 12;
                int finishhour = Integer.parseInt(timefinish1);
                int finishminute = Integer.parseInt(timefinish2);
                int starthour = Integer.parseInt(timestart1);
                int startminute = Integer.parseInt(timestart2);
                String finishtime = String.format("%02d.%02d %s", hourfinish == 0 ? 12 : hourfinish,
                        finishminute, finishhour < 12 ? "am" : "pm");
                String starttime = String.format("%02d.%02d %s", hour == 0 ? 12 : hour,
                        startminute, starthour < 12 ? "am" : "pm");
                mockList(eventList, each.getCourse_name(), starttime + " - " + finishtime, each.getPlace(), cal);
                RefreshFragment();
                dismissProgressDialog();
                Log.w(TAG, "onResponse: " + each.getCourse_name().toString());
            } else {
                dismissProgressDialog();
            }

        }
    }

    private void mockList(List<CalendarEvent> eventList, String coursename, String time, String place, Calendar date) {
        eventList.add(new DrawableCalendarEvent(coursename, time, place,
                ContextCompat.getColor(getActivity(), R.color.UPgreen), date, date, false, android.R.drawable.ic_dialog_info));
        dismissProgressDialog();

    }

//    private void mockList(List<CalendarEvent> eventList) {
//        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
//                ContextCompat.getColor(mContext, R.color.colorPrimary), startTime3, endTime3, false, R.drawable.ic_account_circle);
//        eventList.add(event3);
//    }

    private void showProgressDialog() {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

}