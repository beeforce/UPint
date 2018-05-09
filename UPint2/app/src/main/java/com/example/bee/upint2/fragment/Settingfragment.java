package com.example.bee.upint2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.bee.upint2.AppfeedActivity;
import com.example.bee.upint2.MainActivity;
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

/**
 * Created by Bee on 1/30/2018.
 */

public class Settingfragment extends android.support.v4.app.Fragment{

    private Animation righttoleft, centertoleft;
    private LinearLayout setting_page, feedback_page;
    private ApiService mAPIService;
    private EditText comment;
    private Button submit, resubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        //Animation
        righttoleft = AnimationUtils.loadAnimation(getContext(),R.anim.righttoleftanim);
        centertoleft = AnimationUtils.loadAnimation(getContext(),R.anim.centertoleft);

        RelativeLayout gotoFeedback = rootView.findViewById(R.id.feedbackbt);
        RelativeLayout signOut = rootView.findViewById(R.id.signoutbt);
        setting_page = rootView.findViewById(R.id.setting_page);
        feedback_page = rootView.findViewById(R.id.feedback_page);

        getFeedbackComment();
        initInstances(rootView);

        gotoFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_page.setVisibility(View.GONE);
                setting_page.startAnimation(centertoleft);
                feedback_page.setVisibility(View.VISIBLE);
                feedback_page.startAnimation(righttoleft);

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAPIService = ApiUtils.getAPIService();
                logout();

            }
        });

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
                            Log.w("Settingfragment", "onSuccess: 200 ");
                        }else if (response.code() == 201){
                            Log.w("Settingfragment", "onSuccess: 201 ");
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Log.w("Settingfragment", "onFailure: ");

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

    private void logout(){
        sendOject o = new sendOject();
        mAPIService.Logout(o.getAccesstoken()).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.code() == 200){
                    Intent i = new Intent(getActivity(),MainActivity.class);
                    startActivity(i);
                }else if (response.code() == 401){
                }else {

                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });

    }
}
