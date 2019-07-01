package godaa.android.com.weathertaskapp.data.local.entity;

import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;


@Entity(tableName = "weather")

public class AccuWeatherDb {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String weatherText;
    private String location;
    private int weatherIcon;

    private List<AccuWeather5DayModel.DailyForecast> dailyForecasts;
    @Embedded
    private AccuWeatherModel.Temperature temperature;

    public String getWeatherText() {
        return weatherText;
    }
    public String getLocation() {
        return location;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public AccuWeatherModel.Temperature getTemperature() {
        return temperature;
    }

    public List<AccuWeather5DayModel.DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }



    public AccuWeatherDb() {
        super();
    }
    public AccuWeatherDb(String weatherText, String location, int weatherIcon, List<AccuWeather5DayModel.DailyForecast> dailyForecasts, @Nullable AccuWeatherModel.Temperature temperature) {
        this.weatherText = weatherText;
        this.location = location;
        this.weatherIcon = weatherIcon;
        this.dailyForecasts = dailyForecasts;
        this.temperature = temperature;

    }
}
