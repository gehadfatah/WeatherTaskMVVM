package godaa.android.com.weathertaskapp.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import javax.inject.Inject;

import godaa.android.com.weathertaskapp.R;


public class PreferenceUtils {
    private Context mContext;

    @Inject
    public PreferenceUtils(Context mContext) {
        this.mContext = mContext;
    }


}
