package godaa.android.com.weathertaskapp;

import android.app.Activity;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;



import timber.log.Timber;

/**
 * @author tobennaezike
 */
public class WeatherApplication extends MultiDexApplication  {
    private static WeatherApplication mInstance;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }
    public static synchronized WeatherApplication getInstance() {
        return mInstance;
    }
}