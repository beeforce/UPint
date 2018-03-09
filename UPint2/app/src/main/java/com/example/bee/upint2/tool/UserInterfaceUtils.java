package com.example.bee.upint2.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Bee on 3/7/2018.
 */

public class UserInterfaceUtils {

    public static void showToast(Context context, String text) {

        Toast toast = Toast.makeText(context,
                text,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
