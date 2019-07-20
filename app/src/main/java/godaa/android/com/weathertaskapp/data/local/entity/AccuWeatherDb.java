package godaa.android.com.weathertaskapp.data.local.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import godaa.android.com.weathertaskapp.data.remote.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeatherModel;


@Entity(tableName = "weather")

public class AccuWeatherDb {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;

    private String weatherText;
    private String city;
    private String keyLocation;
    private String country;
    private int weatherIcon;

    private List<AccuWeather5DayModel.DailyForecast> dailyForecasts;
    private double temperature;

    @NonNull
    public long getId() {
        return id;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;

    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;

    }

    public void setDailyForecasts(List<AccuWeather5DayModel.DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;

    }

    public void setCountry(String country) {
        this.country = country;

    }

    public void setCity(String city) {
        this.city = city;

    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;

    }

    public String getCity() {
        return city;
    }

    public void setKeyLocation(String keyLocation) {
        this.keyLocation = keyLocation;
    }

    public String getKeyLocation() {
        return keyLocation;
    }

    public String getCountry() {
        return country;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public double getTemperature() {
        return temperature;
    }

    public List<AccuWeather5DayModel.DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }


    public AccuWeatherDb() {
        super();
    }

    public AccuWeatherDb(String weatherText, String city, String country, String keyLocation, int weatherIcon, List<AccuWeather5DayModel.DailyForecast> dailyForecasts, @Nullable double temperature) {
        this.weatherText = weatherText;
        this.city = city;
        this.keyLocation = keyLocation;
        this.country = country;
        this.weatherIcon = weatherIcon;
        this.dailyForecasts = dailyForecasts;
        this.temperature = temperature;

    }

    public void setId(long id) {
        this.id = id;
    }
}
