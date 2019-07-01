package godaa.android.com.weathertaskapp.utils;

import android.content.Context;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ActivityUtils {
    private ActivityUtils() {
    }



    public static void setVisibility(int v, View... views) {
        for (View view : views) {
            view.setVisibility(v);
        }
    }
    public static void showToast(Context context, int stringId) {
        showToast(context,context.getString(stringId));
    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }




    /**
     * convert the string time to different format like ( 1 hour ago ,  yesterday ...etc)
     *
     * @param time The string time fetched from the api
     * @return
     */
    public static String calculateTimeDiff(String time) {
        try {
            //get the first 11 chars ti match the format yyyy-MM-dd
            time = time.substring(0, 10);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(time);

            String diff = (String) DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), 0);
            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



}
