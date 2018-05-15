package com.example.bee.upint2.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bee on 3/7/2018.
 */

public class Validator {

    public final static boolean isConnected(Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public final static boolean isActiveOverHour(Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        String current = simpleDateFormat.format(new Date());
        String lastActive = MySharedPreference.getPref(MySharedPreference.LAST_ACTIVE_TIME, context);
        Boolean result = false;

        if (lastActive != null) {
            try {
                Date lastFetchDate = simpleDateFormat.parse(lastActive);
                Date currentDate = simpleDateFormat.parse(current);

                long diffMin = (int) (currentDate.getTime() - lastFetchDate.getTime()) / (1000 * 60);


                if (diffMin >= 60) {
                    result = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }
}
