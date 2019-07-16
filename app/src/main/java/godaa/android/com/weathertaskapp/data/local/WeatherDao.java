package godaa.android.com.weathertaskapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import io.reactivex.Flowable;

@Dao
public interface WeatherDao {

    @Query("select * from weather")
    Flowable<List<AccuWeatherDb>> getWeather();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherResponse(AccuWeatherDb weather);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AccuWeatherDb... weathers);
    @Query("DELETE FROM weather")
    public  void clearAllData();

}
