package com.example.bee.upint2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bee.upint2.R;
import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feedbackfragment extends android.support.v4.app.Fragment {

    private EditText comment;
    private Button submit, resubmit;
    private ApiService mAPIService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        initInstances(rootView);
        getFeedbackComment();
        return rootView;
    }


    private void initInstances(View rootView) {
        mAPIService = ApiUtils.getAPIService();
        comment = rootView.findViewById(R.id.comment);
        submit = rootView.findViewById(R.id.submit);
        resubmit = rootView.findViewById(R.id.resubmit);
        resubmit.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.setEnabled(false);
                submit.setVisibility(View.GONE);
                resubmit.setVisibility(View.VISIBLE);
                sendOject o = new sendOject();
                RequestBody user_id = RequestBody.create(MultipartBody.FORM, o.getUser_id());
                RequestBody commentR = RequestBody.create(MultipartBody.FORM, comment.getText().toString());
                mAPIService.addCommenttoFeedback(user_id,commentR).enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if (response.code() == 200){
                        }else if (response.code() == 201){
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {

                    }
                });
            }
        });
        resubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.setEnabled(true);
                submit.setVisibility(View.VISIBLE);
                resubmit.setVisibility(View.GONE);
            }
        });

    }

    private void getFeedbackComment(){
        mAPIService = ApiUtils.getAPIService();
        sendOject o = new sendOject();
        mAPIService.getCommentfromFeedback(o.getUser_id()).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.code() == 200){
                    comment.setText(response.body().getComment());
                    comment.setEnabled(false);
                    submit.setVisibility(View.GONE);
                    resubmit.setVisibility(View.VISIBLE);
                }else{
                    comment.setEnabled(true);
                    resubmit.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });

    }

//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) activity.getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(
//                activity.getCurrentFocus().getWindowToken(), 0);
//    }
}
