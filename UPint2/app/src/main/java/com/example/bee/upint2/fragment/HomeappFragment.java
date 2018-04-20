package com.example.bee.upint2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bee.upint2.R;
import com.example.bee.upint2.SeachclassActivity;
import com.example.bee.upint2.adapter.MyRecyclerViewAdapter;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.Course_user;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class HomeappFragment extends android.support.v4.app.Fragment implements RecyclerViewClickListener {

    private RecyclerView recyclerView, recyclerView2;
    private RecyclerView.LayoutManager layoutManager;
    private List<Course> course;
    private List<Course_user> courseEnroll;
    private List<Course> filteredJob = new ArrayList<Course>();
    //    private RecycleAdapterCourse adapter;
    private static final String TAG = "HomeappFragment";
    private ApiService mAPIService;
    private MyRecyclerViewAdapter adapter;
    private FloatingActionButton searchfab;
    private TextView date, teacher, place, dayremain, classname_home, classname_home2, classname_home3, keyword_home1, keyword_home3, keyword_home2;
    private ImageView place_icon;
    private Date d;
    private RelativeLayout cardview_recent, cardview_recent2, cardview_recent3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_homeapp, container, false);

        initInstances(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (course!=null){
            course.removeAll(course);
            filteredJob.removeAll(filteredJob);
        }
        getClassdetail();
    }

    private void initInstances(View rootView) {
//        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);

        RelativeLayout upcomingLayout = rootView.findViewById(R.id.content_homeapp);
        upcomingLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getActivity(), UpcomingActivity.class);
                startActivity(intent);
                return true;
            }
        });

        //textview
        date = rootView.findViewById(R.id.date);
        teacher = rootView.findViewById(R.id.teacher);
        place = rootView.findViewById(R.id.place);
        place_icon = rootView.findViewById(R.id.place_icon);
        dayremain = rootView.findViewById(R.id.dayremain); //upcomingclass count

        //fab
        searchfab = rootView.findViewById(R.id.search_class_fab);
        searchfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeachclassActivity.class);
                startActivity(intent);
            }
        });

        //relativelayout
        cardview_recent = rootView.findViewById(R.id.cardview_recent);
        cardview_recent2 = rootView.findViewById(R.id.cardview_recent2);
        cardview_recent3 = rootView.findViewById(R.id.cardview_recent3);
        RelativeLayout content = rootView.findViewById(R.id.content);
        Animation uptodown = AnimationUtils.loadAnimation(getActivity(),R.anim.uptodownanim);
        content.setAnimation(uptodown);

        classname_home = rootView.findViewById(R.id.classname_home);
        classname_home2 = rootView.findViewById(R.id.classname_home2);
        classname_home3 = rootView.findViewById(R.id.classname_home3);
        keyword_home1 = rootView.findViewById(R.id.keyword_home1);
        keyword_home2 = rootView.findViewById(R.id.keyword_home2);
        keyword_home3 = rootView.findViewById(R.id.keyword_home3);

        //first recycle -> star tutor
        recyclerView = rootView.findViewById(R.id.recyclerView_star_tutor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //second recycle -> star tutor
        recyclerView2 = rootView.findViewById(R.id.recyclerView_star_tutor2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);


    }

    private void getclassidSearchRecent() {
        mAPIService = ApiUtils.getAPIService();
        sendOject o = new sendOject();
        mAPIService.getSearchRecent(o.getUser_id()).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.code() == 200) {
                    if (response.body().getSearch_recent() == "") {
                        cardview_recent.setVisibility(View.GONE);
                        cardview_recent2.setVisibility(View.GONE);
                        cardview_recent3.setVisibility(View.GONE);
                    } else {
                        String[] parts = response.body().getSearch_recent().split(",");
                        String part1 = parts[0];
                        if (parts.length == 1) {
                            for (Course each : course) {
                                if (each.getId().toString().equals(part1)) {
                                    classname_home.setText(each.getCourse_name());
                                    keyword_home1.setText(each.getTags());
                                }
                            }
                        } else if (parts.length == 2) {
                            String part2 = parts[1];
                            for (Course each : course) {
                                if (each.getId().toString().equals(part1)) {
                                    classname_home.setText(each.getCourse_name());
                                    keyword_home1.setText(each.getTags());
                                }
                                if (each.getId().toString().equals(part2)) {
                                    classname_home2.setText(each.getCourse_name());
                                    keyword_home2.setText(each.getTags());
                                }
                            }
                        } else if (parts.length == 3) {
                            String part2 = parts[1];
                            String part3 = parts[2];
                            for (Course each : course) {
                                if (each.getId().toString().equals(part1)) {
                                    classname_home.setText(each.getCourse_name());
                                    keyword_home1.setText(each.getTags());
                                }
                                if (each.getId().toString().equals(part2)) {
                                    classname_home2.setText(each.getCourse_name());
                                    keyword_home2.setText(each.getTags());
                                }
                                if (each.getId().toString().equals(part3)) {
                                    classname_home3.setText(each.getCourse_name());
                                    keyword_home3.setText(each.getTags());
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }

    //    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//        }
//    }

    public void getClassdetail() {
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getAllCoursedetail().enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                course = response.body();
                searchClass();
                getclassidSearchRecent();


                Log.w(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.w(TAG, "get fail");
            }
        });
    }

    private void onSuccess(List<Course> courseList) {
        List<Course> deletelist = new ArrayList<Course>();

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
                    d1[0] = input.parse(o1.getDate() + " " + o1.getStart_time());
                    d2[0] = input.parse(o2.getDate() + " " + o2.getStart_time());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return d1[0].compareTo(d2[0]);
            }

        });

        //set textview for first value

        // sort by id
        for (Course each : courseList) {
            for (Course_user each1 : courseEnroll) {
                if (each.getId().equals(each1.getCourse_id())) {
                    filteredJob.add(each);
//                    Log.w(TAG, "find course id" + each.getId() + "  " + each1.getCourse_id());
                }
            }
        }
//        Sortdate(courseList);

        // sort by id
        for (Course each : filteredJob) {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            Date today = Calendar.getInstance().getTime();
            try {
                d = input.parse(each.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //get remain date
            long date_remain = (d.getTime() - today.getTime());
            long seconds = date_remain / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            double days = hours / 24;
            Log.w(TAG, "date" + days);
            if (days >= 0 | !d.before(today)) {
            } else {
                deletelist.add(each);
            }
        }
        filteredJob.removeAll(deletelist);
        if (filteredJob.size() > 0) {
            place_icon.setVisibility(View.VISIBLE);
            place.setText(filteredJob.get(0).getPlace());
            //start time
            String timestartst = filteredJob.get(0).getStart_time();
            String[] timestartpart = timestartst.split(":");
            String timestart1 = timestartpart[0];
            String timestart2 = timestartpart[1];
            String timestart3 = timestartpart[2];
            String timestart = timestart1 + "." + timestart2;
//
            int hour = Integer.parseInt(timestart1) % 12;
            int starthour = Integer.parseInt(timestart1);
            int startminute = Integer.parseInt(timestart2);
            String starttime = String.format("%02d.%02d %s", hour == 0 ? 12 : hour,
                    startminute, starthour < 12 ? "am" : "pm");
            //start day
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy");
            Date today = Calendar.getInstance().getTime();
            try {
                d = input.parse(filteredJob.get(0).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
//        holder.date.setText("" + output.format(d));
            String scheduledate = output.format(d);
            String day = (String) DateFormat.format("dd", d);
            String to_day = (String) DateFormat.format("dd", today);

            Date day2 = new Date();//day 2
            Calendar c = Calendar.getInstance();
            c.setTime(day2);
            c.add(Calendar.DATE, 1);
            day2 = c.getTime();
            String day_2 = (String) DateFormat.format("dd", day2);

            if (day.equals(to_day)) {
                date.setText("Today " + starttime);
            } else if (day.equals(day_2)) {
                date.setText("Tomorrow " + starttime);
            } else {
                date.setText(scheduledate + " " + starttime);
            }

            teacher.setText(filteredJob.get(0).getCourse_name() + " class");
            dayremain.setText("" + filteredJob.size());

            Log.w(TAG, "upcoming class size: "+filteredJob.size());
            Log.w(TAG, "course size: "+course.size());
        } else {
            teacher.setText("No Upcoming class now");
            dayremain.setText("0");
            place.setText("");
            date.setText("");
            place_icon.setVisibility(View.INVISIBLE);
        }

        adapter = new MyRecyclerViewAdapter(courseList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);
    }


    public void searchClass() {

        sendOject o = new sendOject();
        int user_id = Integer.parseInt(o.getUser_id());
        mAPIService = ApiUtils.getAPIService();
        mAPIService.searchclassEnroll(user_id).enqueue(new Callback<List<Course_user>>() {
            @Override
            public void onResponse(Call<List<Course_user>> call, Response<List<Course_user>> response) {
                if (response.isSuccessful()) {
                    courseEnroll = response.body();
                    onSuccess(course);
                } else
                    Log.w(TAG, "Search class onResponse:fail");
            }

            @Override
            public void onFailure(Call<List<Course_user>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        v.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in));
        adapter = new MyRecyclerViewAdapter(getActivity(), this);

    }
}
