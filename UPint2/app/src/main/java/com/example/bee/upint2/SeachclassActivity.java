package com.example.bee.upint2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bee.upint2.adapter.ListViewAdapter;
import com.example.bee.upint2.model.Course;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeachclassActivity extends AppCompatActivity {

    private static final String TAG = "SeachclassActivity";
    List<Course> course;
    ArrayList<Course> courseArrayList = new ArrayList<Course>();
    EditText editText;
    ListViewAdapter adapter;
    ListView listView;
    TextView textViewPasswordStrength;
    private ApiService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seachclass);


        getClassdetail();

        listView = (ListView) findViewById(R.id.course_list_view);


        editText = (EditText) findViewById(R.id.searchclasset);
        textViewPasswordStrength = (TextView) findViewById(R.id.textViewPasswordStrengthIndiactor);

        editText.addTextChangedListener(mTextEditorWatcher);

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

    }
    private final TextWatcher  mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            // When No Password Entered
            textViewPasswordStrength.setText("Not Entered");
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
