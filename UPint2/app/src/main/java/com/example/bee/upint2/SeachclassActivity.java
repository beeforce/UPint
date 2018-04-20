package com.example.bee.upint2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bee.upint2.adapter.ListViewAdapter;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeachclassActivity extends AppCompatActivity {

    private static final String TAG = "SeachclassActivity";
    private List<Course> course;
    private ArrayList<Course> courseArrayList = new ArrayList<Course>();
    private EditText editText;
    private ListViewAdapter adapter;
    private ListView listView;
    private ApiService mAPIService;
    private Date d;
    private String course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seachclass);


        getClassdetail();

        listView = (ListView) findViewById(R.id.course_list_view);


        editText = (EditText) findViewById(R.id.searchclasset);

        editText.addTextChangedListener(mTextEditorWatcher);

        ImageView back = (ImageView) findViewById(R.id.backclassdetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String who = editText.getText().toString().toLowerCase(Locale.getDefault());
//                adapter.myFilter(who);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getSearchRecentdata(position);
                String timestartst = course.get(position).getStart_time();
                String[] timestartpart = timestartst.split(":");
                String timestart1 = timestartpart[0];
                String timestart2 = timestartpart[1];
                String timestart3 = timestartpart[2];
                String timestart = timestart1 + "." + timestart2;
                //string part timefinish
                String timefinishst = course.get(position).getFinish_time();
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
                String scheduletime = starttime + " - " + finishtime;

                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy");
                Date today = Calendar.getInstance().getTime();
                try {
                    d = input.parse(course.get(position).getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String scheduledate = output.format(d);

                Intent i = new Intent(view.getContext(), Classdetail_searchclass.class);
                i.putExtra("course_id", course.get(position).getId().toString());
                i.putExtra("user_id", course.get(position).getUser_id());
                i.putExtra("tags", course.get(position).getTags());
                i.putExtra("target_year", course.get(position).getTarget_years());
                i.putExtra("scheduletime", scheduletime.toString());
                i.putExtra("course_name", course.get(position).getCourse_name().toString());
                i.putExtra("level", course.get(position).getLevel_of_difficult());
                i.putExtra("description", course.get(position).getDescription());
                i.putExtra("cost", course.get(position).getPrice_per_student());
                i.putExtra("scheduledate", scheduledate.toString());
                i.putExtra("place", course.get(position).getPlace());
                i.putExtra("term", course.get(position).getTerms());
                i.putExtra("totalstudent", course.get(position).getTotal_student().toString());
                i.putExtra("image_path", course.get(position).getCourse_image_path());
                view.getContext().startActivity(i);
            }
        });

    }

    private void getSearchRecentdata(final int position){
        mAPIService = ApiUtils.getAPIService();
        sendOject o = new sendOject();
        mAPIService.getSearchRecent(o.getUser_id()).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.code() == 200){
                    if (response.body().getSearch_recent() == ""){
                        course_id = course.get(position).getId().toString();
                    }else{
                        String[] parts = response.body().getSearch_recent().split(",");
                        String part1 = parts[0];
                        if (parts.length == 1){
                            if (course.get(position).getId().toString().equals(response.body().getSearch_recent())){
                                course_id = course.get(position).getId().toString();
                            }else{
                                course_id = course.get(position).getId().toString()+","+response.body().getSearch_recent();
                            }
                        }else if (parts.length == 2){
                            String part2 = parts[1];
                            if (part1.equals(course.get(position).getId().toString())){
                                course_id = response.body().getSearch_recent();
                            } else if (part2.equals(course.get(position).getId().toString())){
                                course_id = part2+","+part1;
                            }else{
                                course_id = course.get(position).getId().toString()+","+response.body().getSearch_recent();
                            }
                        }
                        else if (parts.length == 3){
                            String part2 = parts[1];
                            String part3 = parts[2];
                            if (part1.equals(course.get(position).getId().toString())){
                                course_id = response.body().getSearch_recent();
                            } else if (part2.equals(course.get(position).getId().toString())){
                                course_id = part2+","+part1+","+part3;
                            }else if (part3.equals(course.get(position).getId().toString())){
                                course_id = part3+","+part1+","+part2;
                            }else{
                                course_id = course.get(position).getId().toString()+","+part1+","+part2;
                            }
                        }
                    }
                    updateSearchRecentdata();
                } else {
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }

    private void updateSearchRecentdata(){

        sendOject o = new sendOject();
        RequestBody user_id = RequestBody.create(MultipartBody.FORM, o.getUser_id());
        RequestBody search_data = RequestBody.create(MultipartBody.FORM, course_id);
        mAPIService.updateSearchRecent(user_id, search_data).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
//                    if (response.code() == 200) {
//                        Log.w("SearchRecent", "onResponse 200");
//                    }else if (response.code() == 201) {
//                        Log.w("SearchRecent", "onResponse 201");
//                    }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.w("SearchRecent", "onResponsefail");

            }
        });

    }

    private final TextWatcher  mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        public void afterTextChanged(Editable s)
        {
            String who = editText.getText().toString().toLowerCase(Locale.getDefault());
            adapter.myFilter(who);
            listView.setAdapter(adapter);
        }
    };

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
        courseArrayList.addAll(courselist);

        adapter = new ListViewAdapter(this,courselist);

        listView.setAdapter(adapter);
    }

}
