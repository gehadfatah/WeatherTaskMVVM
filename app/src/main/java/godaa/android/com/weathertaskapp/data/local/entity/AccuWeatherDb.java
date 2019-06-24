package godaa.android.com.weathertaskapp.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import godaa.android.com.weathertaskapp.data.model.DailyForecast;
import godaa.android.com.weathertaskapp.data.model.Temperature;

@Entity(tableName = "weather")

public class AccuWeatherDb {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String weatherText;
    private int weatherIcon;

    private List<DailyForecast> dailyForecasts;
    @Embedded
    private Temperature temperature;

    public String getWeatherText() {
        return weatherText;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public AccuWeatherDb() {
        super();
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }
}
