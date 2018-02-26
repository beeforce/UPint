package com.example.bee.upint2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.bee.upint2.fragment.Schedulefragment;
import com.example.bee.upint2.fragment.Upcomingfragment;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";


    private ApiService mAPIService;
    private Call<AccessToken> call;
    private SweetAlertDialog pDialog;
    private Button login;
    private TextInputLayout tillemail, tillpassword;
    private Vibrator vibrator;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Bundle bundle = getIntent().getExtras();
        String user_type = bundle.getString("Usertype_login");
        login = (Button) findViewById(R.id.btn_login);
        tillemail = (TextInputLayout) findViewById(R.id.til_email2);
        tillpassword = (TextInputLayout) findViewById(R.id.til_password2);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_shake);
        anim.setRepeatCount(2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEmail() || !checkpassword()) {
                    tillemail.setAnimation(anim);
                    tillpassword.setAnimation(anim);
                    checkEmail();
                    checkpassword();
                    vibrator.vibrate(120);
                    return;
                }else {
                    showProgressDialog();
                    mAPIService = ApiUtils.getAPIService();
                    final String email = tillemail.getEditText().getText().toString();
                    String password = tillpassword.getEditText().getText().toString();

                    RequestBody emailR = RequestBody.create(MultipartBody.FORM, email);
                    RequestBody passwordR = RequestBody.create(MultipartBody.FORM, password);

                    mAPIService.login(emailR, passwordR).enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            Log.w(TAG, "onResponse: " + response);
                            dismissProgressDialog();
                            if (response.isSuccessful()) {
                                Log.w(TAG, "onResponse: " + response.body().getAccessToken());
                                Log.w(TAG, "user id: " + response.body().getUser_id());
                                sendOject se = new sendOject();
                                se.setAccesstoken(response.body().getAccessToken());
                                sendOject se2 = new sendOject();
                                se2.setUser_id(response.body().getUser_id());
                                dismissProgressDialog();
                                showProgressDialogSuccess();
                            } else {
                                dismissProgressDialog();
                                showProgressDialogWarning();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            Log.w(TAG, "onFailure: " + t.getMessage());
                            dismissProgressDialog();
                            showProgressDialogWarningConnection();

                        }
                    });
                }
            }
        });
    }

    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null) {
            call.cancel();
            call = null;
        }
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

    private void showProgressDialogSuccess(){
        pDialog =new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText("SUCCESS!");
        pDialog.setContentText("You're logged in!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        startActivity(new Intent(LoginActivity.this, Main2Activity.class));
                        finish();
                    }
                });
    }

    private void showProgressDialogWarning(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ERROR!")
                .setContentText("Can not login to the system. Please enter the correct email and password")
                .setConfirmText("Ok!")
                .show();
    }

    private void showProgressDialogWarningConnection(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ERROR!")
                .setContentText("Connection Failed")
                .setConfirmText("Ok!")
                .show();
    }

    private void sendData() {
        Bundle bundle = new Bundle();
        bundle.putString("userEmail","555555");
        Upcomingfragment fragment = new Upcomingfragment();
        fragment.setArguments(bundle);
    }
    private boolean checkEmail() {
        if (tillemail.getEditText().getText().toString().trim().isEmpty()) {
            tillemail.setErrorEnabled(true);
            tillemail.setError("Please enter a E-mail");
            tillemail.getEditText().setError("Please enter a correct E-mail");
            return false;
        }
        if (!isValidEmail(tillemail.getEditText().getText().toString())) {
            tillemail.setErrorEnabled(true);
            tillemail.setError("Please enter a correct E-mail");
            tillemail.getEditText().setError("Please enter a correct E-mail");
            return false;
        }
        tillemail.setErrorEnabled(false);
        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean checkpassword() {
        if (tillpassword.getEditText().getText().toString().trim().isEmpty()) {
            tillpassword.setErrorEnabled(true);
            tillpassword.setError("Please enter a Password");
            return false;
        }
        else if (tillpassword.getEditText().getText().toString().length() < 6) {
            tillpassword.setErrorEnabled(true);
            tillpassword.setError("Password must more than 5 Characters");
            return false;
        }
        tillpassword.setErrorEnabled(false);
        return true;
    }
}
