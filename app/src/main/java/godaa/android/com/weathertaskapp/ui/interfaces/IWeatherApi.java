package godaa.android.com.weathertaskapp.ui.interfaces;

import java.util.List;


import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by akash on 10/10/17.
 */

public interface IWeatherApi {

    @GET("forecasts/v1/daily/5day/{key}?metric=true")
    Call<AccuWeather5DayModel> getAccuWeatherData5days(@Path("key") String cityKey, @Query("apikey") String appId);

    @GET("currentconditions/v1/{key}")
    Call<List<AccuWeatherModel>> getAccuWeatherData(@Path("key") String cityKey, @Query("apikey") String appId);

    @GET("locations/v1/cities/autocomplete")
    Call<List<LocationSearchModel>> getAccuWeatherCities(@Query("apikey") String appId, @Query("q") String query);

    @GET("locations/v1/cities/geoposition/search")
    Call<LocationSearchModel> getAccuWeatherBylocation(@Query("apikey") String appId, @Query("q") String query);
}
