package godaa.android.com.weathertaskapp.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import godaa.android.com.weathertaskapp.common.provider.scheduler.AppSchedulerProvider;
import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.presentation.model.viewState.Accu5DayWeatherModelViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.AccuDbInsertViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.AccuWeatherModelViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.LocationWeatherModelViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.NetworkState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.WeatherCitiesViewState;
import godaa.android.com.weathertaskapp.presentation.model.viewState.WeatherDbModelsViewState;
import godaa.android.com.weathertaskapp.data.repository.WeatherRepository;
import godaa.android.com.weathertaskapp.presentation.viewmodel.BaseViewModel;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class WeatherViewModel extends BaseViewModel {
    WeatherRepository weatherRepository;
    private MutableLiveData<AccuDbInsertViewState> accuDbInsertViewStateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<AccuDbInsertViewState> delete = new MutableLiveData<>();
    private MutableLiveData<WeatherDbModelsViewState> weatherDbModelsViewStateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<AccuWeatherModelViewState> accuWeatherModelViewStateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WeatherCitiesViewState> locationSearchModelsLiveData = new MutableLiveData<>();
    private MutableLiveData<Accu5DayWeatherModelViewState> accu5DayWeatherModelViewStateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LocationWeatherModelViewState> locationWeatherModelViewStateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> reloadTrigger = new MutableLiveData<Boolean>();

    @Inject
    public WeatherViewModel(AppSchedulerProvider appSchedulerProvider, WeatherRepository weatherRepository) {
        super(appSchedulerProvider.io(), appSchedulerProvider.ui());
        this.weatherRepository = weatherRepository;
        refreshUsers();

    }

    public void refreshUsers() {
        reloadTrigger.setValue(true);
    }

    public LiveData<WeatherDbModelsViewState> getAccuWeatherDbLiveData() {
        WeatherDbModelsViewState weatherDbModelsViewState = new WeatherDbModelsViewState();
        executeFlowable(
                subscription -> {
                    weatherDbModelsViewState.setNetworkState(NetworkState.LOADING);
                    weatherDbModelsViewStateMutableLiveData.postValue(weatherDbModelsViewState);
                },
                //this list is same with retured getweather
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
     /* return   Transformations.switchMap(reloadTrigger) {                    weatherDbModelsViewStateMutableLiveData.postValue(weatherDbModelsViewState);
              }*/
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
        executeSingle(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
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

    public LiveData<Accu5DayWeatherModelViewState> getRemotegetAccu5DayWeatherData(String cityKey) {

        Accu5DayWeatherModelViewState accu5DayWeatherModelViewState = new Accu5DayWeatherModelViewState();
        executeSingle(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
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
                weatherRepository.getAccuWeatherData5days(cityKey)
        );
        return accu5DayWeatherModelViewStateMutableLiveData;
    }

    public LiveData<LocationWeatherModelViewState> getAccuWeatherBylocation(String latLong) {

        LocationWeatherModelViewState locationWeatherModelViewState = new LocationWeatherModelViewState();
        executeSingle(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        locationWeatherModelViewState.setNetworkState(NetworkState.LOADING);
                        locationWeatherModelViewStateMutableLiveData.postValue(locationWeatherModelViewState);
                    }
                },
                locationSearchModel -> {
                    locationWeatherModelViewState.setNetworkState(NetworkState.LOADED);
                    locationWeatherModelViewState.setLocationSearchModel(locationSearchModel);
                    locationWeatherModelViewStateMutableLiveData.postValue(locationWeatherModelViewState);
                },
                throwable -> {
                    locationWeatherModelViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    locationWeatherModelViewStateMutableLiveData.postValue(locationWeatherModelViewState);
                },
                weatherRepository.getAccuWeatherBylocation(latLong)
        );
        return locationWeatherModelViewStateMutableLiveData;
    }
    //insert and return Completable

    public LiveData<AccuDbInsertViewState> insertWeatherCityComplete(AccuWeatherDb weatherDb) {
        AccuDbInsertViewState accuDbInsertViewState = new AccuDbInsertViewState();
        executeCompletable(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        accuDbInsertViewState.setNetworkState(NetworkState.LOADING);
                        accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                    }
                },
                new Action() {
                    @Override
                    public void run() throws Exception {
                        accuDbInsertViewState.setNetworkState(NetworkState.LOADED);
                        accuDbInsertViewState.setaBoolean(true);
                        accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                    }
                },
                throwable -> {
                    accuDbInsertViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                },
                weatherRepository.insertUsingCompleteWeatherCity(weatherDb)
        );
        return accuDbInsertViewStateMutableLiveData;
    }

    public LiveData<AccuDbInsertViewState> deleteWeather(String key) {
        AccuDbInsertViewState accuDbInsertViewState = new AccuDbInsertViewState();
        executeCompletable(
                new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        accuDbInsertViewState.setNetworkState(NetworkState.LOADING);
                        delete.postValue(accuDbInsertViewState);
                    }
                },
                new Action() {
                    @Override
                    public void run() throws Exception {
                        accuDbInsertViewState.setNetworkState(NetworkState.LOADED);
                        accuDbInsertViewState.setaBoolean(true);
                        delete.postValue(accuDbInsertViewState);
                    }
                },
                throwable -> {
                    accuDbInsertViewState.setNetworkState(NetworkState.error(throwable.getMessage()));
                    delete.postValue(accuDbInsertViewState);
                },
                weatherRepository.deleteWeather(key)
        );
        return delete;

    }

    //insert and return observable
    public LiveData<AccuDbInsertViewState> insertWeatherCity(AccuWeatherDb weatherDb) {
        // weatherRepository.insertWeatherCity(weatherDb);
        AccuDbInsertViewState accuDbInsertViewState = new AccuDbInsertViewState();
        executeObservable(new Consumer<Disposable>() {
                              @Override
                              public void accept(Disposable disposable) throws Exception {
                                  accuDbInsertViewState.setNetworkState(NetworkState.LOADING);
                                  accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                              }
                          },
                new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        accuDbInsertViewState.setNetworkState(NetworkState.LOADED);
                        accuDbInsertViewState.setaBoolean(aBoolean);
                        accuDbInsertViewStateMutableLiveData.postValue(accuDbInsertViewState);
                    }
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
