package godaa.android.com.weathertaskapp.data.local;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import godaa.android.com.weathertaskapp.data.WeatherDao;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.DailyForecast;

@Database(entities = {AccuWeatherDb.class, DailyForecast.class}, version = 1, exportSchema = false)
@TypeConverters({WeatherTypeConverter.class})
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();
}
