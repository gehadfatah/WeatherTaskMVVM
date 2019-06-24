package godaa.android.com.weathertaskapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;

@Dao
public interface WeatherDao {

    @Query("select * from weather")
    LiveData<List<AccuWeatherDb>> getWeather();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherResponse(AccuWeatherDb weather);
}
