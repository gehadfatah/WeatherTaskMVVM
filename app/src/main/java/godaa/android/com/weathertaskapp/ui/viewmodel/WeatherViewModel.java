package godaa.android.com.weathertaskapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.reactivestreams.Subscription;

import java.util.List;

import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.data.model.Accu5DayWeatherModelViewState;
import godaa.android.com.weathertaskapp.data.model.AccuDbInsertViewState;
import godaa.android.com.weathertaskapp.data.model.AccuWeather5DayModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModel;
import godaa.android.com.weathertaskapp.data.model.AccuWeatherModelViewState;
import godaa.android.com.weathertaskapp.data.model.LocationSearchModel;
import godaa.android.com.weathertaskapp.data.model.NetworkState;
import godaa.android.com.weathertaskapp.data.model.WeatherCitiesViewState;
import godaa.android.com.weathertaskapp.data.model.WeatherDbModelsViewState;
import godaa.android.com.weathertaskapp.repository.WeatherRepository;
import godaa.android.com.weathertaskapp.ui.base.BaseViewModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WeatherViewModel extends BaseViewModel {
    WeatherRepository weatherRepository;
    private MutableLiveData<AccuDbInsertViewState> accuDbInsertViewStateMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<WeatherDbModelsViewState> weatherDbModelsViewStateMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<AccuWeatherModelViewState> accuWeatherModelViewStateMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<WeatherCitiesViewState> locationSearchModelsLiveData=new MutableLiveData<>();
    private MutableLiveData<Accu5DayWeatherModelViewState> accu5DayWeatherModelViewStateMutableLiveData=new MutableLiveData<>();

    public WeatherViewModel(Scheduler subscribeOn, Scheduler observeOn, WeatherRepository weatherRepository) {
        super(subscribeOn, observeOn);

        this.weatherRepository = weatherRepository;

    }

    public LiveData<WeatherDbModelsViewState> getAccuWeatherDbLiveData() {
       // return weatherRepository.getWeather();
        WeatherDbModelsViewState weatherDbModelsViewState = new WeatherDbModelsViewState();
        execute(
                subscription -> {
                    weatherDbModelsViewState.setNetworkState(NetworkState.LOADING);
                    weatherDbModelsViewStateMutableLiveData.postValue(weatherDbModelsViewState);
                },
                accuWeatherDbList -> {
                    weatherDbModelsViewState.setNetworkState(NetworkState.LOADED);
                    weatherDbModelsViewState.setAccuWeatherModels(accuWeatherDbList);
                    weatherDbModelsViewStateMutableLiveData.postValue(weatherDbModelsViewState);
                },
                throwable -> {
                    weatherDbModelsViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    weatherDbModelsViewStateMutableLiveData.postValue(weatherDbModelsViewState);
                },
                weatherRepository.getWeather()
        );

        return weatherDbModelsViewStateMutableLiveData;
    }
   /* public LiveData<WeatherCitiesViewState> getRemoteListCitiesWeather(String q) {
        // return weatherRepository.getWeather();
        WeatherCitiesViewState weatherCitiesViewState = new WeatherCitiesViewState();
        execute(
                subscription -> {
                    weatherCitiesViewState.setNetworkState(NetworkState.LOADING);
                    locationSearchModelsLiveData.postValue(weatherCitiesViewState);
                },
                locationSearchModels -> {
                    weatherCitiesViewState.setNetworkState(NetworkState.LOADED);
                    weatherCitiesViewState.setLocationSearchModels(locationSearchModels);
                    locationSearchModelsLiveData.postValue(weatherCitiesViewState);
                },
                throwable -> {
                    weatherCitiesViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    locationSearchModelsLiveData.postValue(weatherCitiesViewState);
                },
                weatherRepository.getRemoteListCitiesWeather(q)
        );

        return locationSearchModelsLiveData;
    }*/


    public LiveData<AccuWeatherModelViewState> getRemotegetAccuWeatherData(String cityKey) {
       // return weatherRepository.getRemotegetAccuWeatherData(cityKey);
        AccuWeatherModelViewState accuWeatherModelViewState = new AccuWeatherModelViewState();
        execute(
                new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        accuWeatherModelViewState.setNetworkState(NetworkState.LOADING);
                        accuWeatherModelViewStateMutableLiveData.postValue(accuWeatherModelViewState);
                    }
                },
                accuWeatherModels -> {
                    accuWeatherModelViewState.setNetworkState(NetworkState.LOADED);
                    accuWeatherModelViewState.setAccuWeatherModels(accuWeatherModels);
                    accuWeatherModelViewStateMutableLiveData.postValue(accuWeatherModelViewState);
                },
                throwable -> {
                    accuWeatherModelViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    accuWeatherModelViewStateMutableLiveData.postValue(accuWeatherModelViewState);
                },
                weatherRepository.getRemotegetAccuWeatherData(cityKey)
        );
        return accuWeatherModelViewStateMutableLiveData;

    }

  /*  public LiveData<AccuWeather5DayModel> getRemotegetAccu5DayWeatherData(String cityKey) {
        return weatherRepository.getAccuWeatherData5days(cityKey);
    }*/
    public LiveData<Accu5DayWeatherModelViewState> getRemotegetAccu5DayWeatherData(String cityKey) {

        Accu5DayWeatherModelViewState accu5DayWeatherModelViewState = new Accu5DayWeatherModelViewState();
        execute(
                new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        accu5DayWeatherModelViewState.setNetworkState(NetworkState.LOADING);
                        accu5DayWeatherModelViewStateMutableLiveData.postValue(accu5DayWeatherModelViewState);
                    }
                },
                accuWeather5DayModel -> {
                    accu5DayWeatherModelViewState.setNetworkState(NetworkState.LOADED);
                    accu5DayWeatherModelViewState.setAccuWeather5DayModel(accuWeather5DayModel);
                    accu5DayWeatherModelViewStateMutableLiveData.postValue(accu5DayWeatherModelViewState);
                },
                throwable -> {
                    accu5DayWeatherModelViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    accu5DayWeatherModelViewStateMutableLiveData.postValue(accu5DayWeatherModelViewState);
                },
                weatherRepository.getAccuWeatherData5days(cityKey).toFlowable()
        );
        return accu5DayWeatherModelViewStateMutableLiveData;
    }

    public LiveData<AccuDbInsertViewState> insertWeatherCity(AccuWeatherDb weatherDb) {
       // weatherRepository.insertWeatherCity(weatherDb);
        AccuDbInsertViewState accuDbInsertViewState = new AccuDbInsertViewState();
        execute(
               /* new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        accuDbInsertViewState.setNetworkState(NetworkState.LOADING);
                        accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                    }
                }*/new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        accuDbInsertViewState.setNetworkState(NetworkState.LOADING);
                        accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                    }
                },
                aBoolean -> {
                    accuDbInsertViewState.setNetworkState(NetworkState.LOADED);
                    accuDbInsertViewState.setaBoolean(aBoolean);
                    accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                },
                throwable -> {
                    accuDbInsertViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                },
                weatherRepository.insertWeatherCity(weatherDb)
        );
        return accuDbInsertViewStateMutableLiveData;
    }
}
