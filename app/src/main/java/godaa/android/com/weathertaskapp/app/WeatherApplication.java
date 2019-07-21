package godaa.android.com.weathertaskapp.app;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import godaa.android.com.weathertaskapp.common.di.component.AppComponent;
import godaa.android.com.weathertaskapp.common.di.component.DaggerAppComponent;
import godaa.android.com.weathertaskapp.common.di.modules.AppModule;
import godaa.android.com.weathertaskapp.common.di.modules.PreferenceUtilsModule;


public class WeatherApplication extends MultiDexApplication {
    private static WeatherApplication mInstance;
    private AppComponent mAppComponent;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mAppComponent = DaggerAppComponent.builder().
                preferenceUtilsModule(new PreferenceUtilsModule(getInstance()))
                .appModule(new AppModule(getInstance()))
                .build();


    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static synchronized WeatherApplication getInstance() {
        return mInstance;
    }
}