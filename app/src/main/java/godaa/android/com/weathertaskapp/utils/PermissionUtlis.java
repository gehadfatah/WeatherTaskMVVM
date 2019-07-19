package godaa.android.com.weathertaskapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import godaa.android.com.weathertaskapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Pc1 on 30/05/2018.
 */

public class PermissionUtlis {
    private static final String PREFS_FILE_NAME = "permi";
    static Snackbar s;
  /*  public static void requestPermissions(Activity context, String[] PERMISSIONS, int REQUEST_PERMISSIONS_CODE) {

        ActivityCompat.requestPermissions(context,
                PERMISSIONS,
                REQUEST_PERMISSIONS_CODE);
    }*/

    public static boolean chckPermission(Activity context, String[] PERMISSIONS) {

        for (String Permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(context, Permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;

    }

    public static void settingPerm(final Activity activity, final int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M/* && !activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)*/) {    // Comment 2.
           // if (s != null && s.isShown()) return;

            s = Snackbar.make(activity.findViewById(android.R.id.content), "Please allow permissions.", Snackbar.LENGTH_SHORT)
                    .setAction("SETTINGS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            activity.startActivityForResult(intent, requestCode);     // Comment 3.
                        }
                    });
            View snackbarView = s.getView();
            TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
            textView.setMaxLines(2);    // Comment 4.
            s.show();
        }
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getBoolean(permission, true);
    }


}
