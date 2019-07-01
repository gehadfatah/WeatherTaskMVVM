package godaa.android.com.weathertaskapp.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel.DailyForecast;

public class WeatherTypeConverter {
    @TypeConverter
    public String fromDailyForecastList(List<DailyForecast> dailyForecasts) {
        if (dailyForecasts == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DailyForecast>>() {
        }.getType();
        String json = gson.toJson(dailyForecasts, type);
        return json;
    }

    @TypeConverter
    public List<DailyForecast> toDailyForecastList(String dailyForecasts) {
        if (dailyForecasts == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<DailyForecast>>() {
        }.getType();
        List<DailyForecast> productCategoriesList = gson.fromJson(dailyForecasts, type);
        return productCategoriesList;
    }
}
