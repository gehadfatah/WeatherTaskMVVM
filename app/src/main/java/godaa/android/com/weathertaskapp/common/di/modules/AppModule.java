package godaa.android.com.weathertaskapp.common.di.modules;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import godaa.android.com.weathertaskapp.app.WeatherApplication;
import godaa.android.com.weathertaskapp.common.provider.scheduler.AppSchedulerProvider;
import godaa.android.com.weathertaskapp.data.local.WeatherDatabase;


@Module/*(includes = {ViewModelModule.class, NetworkModule.class})*/
public class AppModule {
    Context context;

    public AppModule(Context mContext) {
        this.context = mContext;
    }

    @Provides
    Context provideContext(/*WeatherApplication application*/) {
        return /*application.getApplicationContext()*/context;
    }

    @Provides
    AppSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    WeatherDatabase provideAppDatabase() {
        return Room.databaseBuilder(context,
                WeatherDatabase.class, WeatherDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}
