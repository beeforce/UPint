package com.example.bee.upint2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bee.upint2.model.sendOject;
import com.example.bee.upint2.network.AccessToken;
import com.example.bee.upint2.network.ApiService;
import com.example.bee.upint2.network.ApiUtils;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Classdetail extends AppCompatActivity {

    private ImageView back;
    private Button book;
    private TextView classdetailcourse_name, levelofdifficult, descriptionclassdetail,
            costclassdetail, classdetailschedule, classdetailscheduletime, classdetailplace,
            termclassdetail, numberclassdetail;
    private String course_id, user_id;
    private ApiService mAPIService;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classdetail);

        classdetailcourse_name = (TextView) findViewById(R.id.classdetailcourse_name);
        levelofdifficult = (TextView) findViewById(R.id.levelofdifficult);
        descriptionclassdetail = (TextView) findViewById(R.id.descriptionclassdetail);
        costclassdetail = (TextView) findViewById(R.id.costclassdetail);
        classdetailschedule = (TextView) findViewById(R.id.classdetailschedule);
        classdetailscheduletime = (TextView) findViewById(R.id.classdetailscheduletime);
        classdetailplace = (TextView) findViewById(R.id.classdetailplace);
        termclassdetail = (TextView) findViewById(R.id.termclassdetail);
        numberclassdetail = (TextView) findViewById(R.id.numberclassdetail);

        classdetailcourse_name.setText(getIntent().getStringExtra("course_name"));
        levelofdifficult.setText(getIntent().getStringExtra("level"));
        descriptionclassdetail.setText(getIntent().getStringExtra("description"));
        costclassdetail.setText(getIntent().getStringExtra("cost")+"B");
        classdetailschedule.setText(getIntent().getStringExtra("scheduledate"));
        classdetailscheduletime.setText(getIntent().getStringExtra("scheduletime"));
        classdetailplace.setText(getIntent().getStringExtra("place"));
        termclassdetail.setText(getIntent().getStringExtra("term"));
        numberclassdetail.setText(getIntent().getStringExtra(  "totalstudent"));

        course_id = getIntent().getStringExtra("course_id");
        sendOject o = new sendOject();
        user_id = o.getUser_id();


        book = (Button) findViewById(R.id.book);
        back = (ImageView) findViewById(R.id.backclassdetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                mAPIService = ApiUtils.getAPIService();
                RequestBody user_idR = RequestBody.create(MultipartBody.FORM, user_id);
                RequestBody course_idR = RequestBody.create(MultipartBody.FORM, course_id);

                mAPIService.bookClass(user_idR,course_idR).enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        dismissProgressDialog();
                        if (response.isSuccessful()) {
                            if (response.body().isSuccess()) {
                                showProgressDialogSuccess();
                            }else {
                                showProgressDialogWarning();
                            }

                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        showProgressDialogerrorconnection();

                    }
                });
            }
        });
    }
    private void showProgressDialog() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
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

    private void showProgressDialogSuccess() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("SUCCESS!");
        pDialog.setContentText("You're request are successful!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(Classdetail.this, Main2Activity.class));
                finish();
            }
        });
    }
    private void showProgressDialogWarning(){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("ERROR!");
        pDialog.setContentText("Can not book a class");
        pDialog.setConfirmText("Ok!");
        pDialog.show();
    }
    private void showProgressDialogerrorconnection(){
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Connection Error!");
        pDialog.setContentText("Please check your connection");
        pDialog.setConfirmText("Ok!");
        pDialog.show();
    }
}
