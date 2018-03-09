package com.example.bee.upint2.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bee on 1/30/2018.
 */

public class Statusfragment extends android.support.v4.app.Fragment implements CalendarPickerController {

    private AgendaCalendarView mAgendaCalendarView;
    private TextView month;
    private List<Course> course;
    private ApiService mAPIService;
    private ApiService mAPIService2;
    private List<CalendarEvent> eventList = new ArrayList<>();
    private Course courselisten;
    private static final String TAG = "Statusfragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);

        mAPIService = ApiUtils.getAPIService();
        mAPIService2 = ApiUtils.getAPIService();
        mAgendaCalendarView = (AgendaCalendarView) rootView.findViewById(R.id.agenda_calendar_view);
        month = (TextView) rootView.findViewById(R.id.month);
        if (course == null) {
            getClassdetailwithId();
        }

//        customCalendar = (CustomCalendar) rootView.findViewById(R.id.customCalendar);
//
//        String[] arr = {"2018-03-07", "2018-03-08", "2018-03-09", "2018-03-10", "2018-03-11"};
//        ArrayList<EventData> eventData = new ArrayList<EventData>();
//        ArrayList<ArrayList<EventData>> eventData2 = new ArrayList<ArrayList<EventData>> ();
//        ArrayList<dataAboutDate> dataAboutDates = new ArrayList<dataAboutDate>();
//        dataAboutDate b = new dataAboutDate();
//        b.setTitle("Title");
//        b.setSubject("Subject");
//        b.setSubmissionDate("2018-03-05");
//        b.setRemarks("remark");
//        dataAboutDate c = new dataAboutDate();
//        c.setTitle("Title2");
//        c.setSubject("Subject2");
//        c.setSubmissionDate("2018-03-05");
//        c.setRemarks("remark2");
//        dataAboutDates.add(b);
//        dataAboutDates.add(c);
//        EventData a = new EventData();
//        a.setSection("test");
//        a.setData(dataAboutDates);
//        eventData.add(a);
//        eventData2.add(eventData);
//        for (int i = 0; i < arr.length; i++) {
//            int eventCount = 1;
//            if (i <eventData2.size()) {
//                customCalendar.addAnEvent(arr[i], eventCount, eventData2.get(i));
//            }
//        }


//

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);


        // Sync way
        /*
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        */
        //Async way


//        //////// This can be done once in another thread
//        CalendarManager calendarManager = CalendarManager.getInstance(getApplicationContext());
//        calendarManager.buildCal(minDate, maxDate, Locale.getDefault(), new DayItem(), new WeekItem();
//        calendarManager.loadEvents(eventList, new BaseCalendarEvent());
//        ////////
//
//        List<CalendarEvent> readyEvents = calendarManager.getEvents();
//        List<IDayItem> readyDays = calendarManager.getDays();
//        List<WeekItem> readyWeeks = calendarManager.getWeeks();
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        mAgendaCalendarView.setScrollbarFadingEnabled(true);

        initInstances(rootView);

        return rootView;
    }


    private void initInstances(View rootView) {

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
        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        mAPIService.courseDetailsOfuser(user_id).enqueue(new Callback<List<Course>>() {
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

    public void onSuccess(List<Course> courseList){
        for (Course each: courseList) {
            mAPIService2.courseDetailswithId(each.getCourse_id()).enqueue(new Callback<Course>() {
                @Override
                public void onResponse(Call<Course> call, Response<Course> response) {
                    if (response.isSuccessful()) {
                        courselisten = response.body();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                            date = format.parse(courselisten.getDate());
                            Log.w(TAG, "try-catch: Success" + date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        //string part timestart
                        String timestartst = courselisten.getStart_time();
                        String[] timestartpart = timestartst.split(":");
                        String timestart1 = timestartpart[0];
                        String timestart2 = timestartpart[1];
                        String timestart3 = timestartpart[2];
                        String timestart = timestart1 + "." + timestart2;
                        //string part timefinish
                        String timefinishst = courselisten.getFinish_time();
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
                        mockList(eventList, courselisten.getCourse_name(), starttime + " - " + finishtime, courselisten.getPlace(),cal);
//                                    if (courselistenList.size() <= course.size()) {
//                                        courselistenList.add(courselisten);
//                                    }
                        Log.w(TAG, "onResponse: " + courselisten.getCourse_name().toString());
                    }
                }

                @Override
                public void onFailure(Call<Course> call, Throwable t) {

                }
            });
        }
    }

    private void mockList(List<CalendarEvent> eventList , String coursename, String time, String place, Calendar date) {
        eventList.add(new DrawableCalendarEvent(coursename, time, place,
                ContextCompat.getColor(getActivity(), R.color.UPgreen), date, date, false, android.R.drawable.ic_dialog_info));

    }

//    private void mockList(List<CalendarEvent> eventList) {
//        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
//                ContextCompat.getColor(mContext, R.color.colorPrimary), startTime3, endTime3, false, R.drawable.ic_account_circle);
//        eventList.add(event3);
//    }
}
