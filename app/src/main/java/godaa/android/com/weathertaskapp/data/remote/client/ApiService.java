package godaa.android.com.weathertaskapp.data.remote.client;

import java.util.List;

import godaa.android.com.weathertaskapp.data.remote.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.remote.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.remote.model.LocationSearchModel;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



// https://api.apixu.com/v1/forecast.json?key=ENTER_KEY&q=Los%20Angeles&days=1
public interface ApiService {


    @GET("currentconditions/v1/{key}")
    Single<List<AccuWeatherModel>> getAccuWeatherData(@Path("key") String cityKey/*, @Query("apikey") String appId*/);
    @GET("locations/v1/cities/autocomplete")
    Call<List<LocationSearchModel>> getAccuWeatherCities(/*@Query("apikey") String appId,*/ @Query("q") String query);
    @GET("forecasts/v1/daily/5day/{key}?metric=true")
    Single<AccuWeather5DayModel> getAccuWeatherData5days(@Path("key") String cityKey);
    @GET("locations/v1/cities/geoposition/search")
    Single<LocationSearchModel> getAccuWeatherBylocation(@Query("q") String query);

}

