package godaa.android.com.weathertaskapp;

import android.app.Activity;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDexApplication;

import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Inject;


import godaa.android.com.weathertaskapp.di.AppInjector;
import timber.log.Timber;

/**
 * @author tobennaezike
 */
public class WeatherApplication extends MultiDexApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {

        AppInjector.init(this);

        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        AndroidThreeTen.init(this);


    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
