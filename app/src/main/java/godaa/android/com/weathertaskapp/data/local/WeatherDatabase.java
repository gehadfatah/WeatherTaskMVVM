package godaa.android.com.weathertaskapp.data.local;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import godaa.android.com.weathertaskapp.WeatherApplication;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.DailyForecast;

@Database(entities = {AccuWeatherDb.class}, version = 1, exportSchema = false)
@TypeConverters({WeatherTypeConverter.class})
public abstract class WeatherDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "weather_database";
    private static final Object sLock = new Object();
    private static WeatherDatabase INSTANCE = null;

    public abstract WeatherDao weatherDao();

    public static WeatherDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(WeatherApplication.getInstance().getApplicationContext(),
                            WeatherDatabase.class, WeatherDatabase.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
