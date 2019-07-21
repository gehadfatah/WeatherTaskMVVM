package godaa.android.com.weathertaskapp.common.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import godaa.android.com.weathertaskapp.data.local.prefs.PreferenceUtils;


@Module
public class PreferenceUtilsModule {

    private Context mContext;

    public PreferenceUtilsModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    PreferenceUtils providePreferenceUtils() {
        return new PreferenceUtils(mContext);
    }
}
