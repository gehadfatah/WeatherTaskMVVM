package godaa.android.com.weathertaskapp.common.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import godaa.android.com.weathertaskapp.common.provider.scheduler.AppSchedulerProvider;
import godaa.android.com.weathertaskapp.data.local.WeatherDao;
import godaa.android.com.weathertaskapp.data.local.WeatherDatabase;
import godaa.android.com.weathertaskapp.data.remote.client.ApiService;
import godaa.android.com.weathertaskapp.data.repository.WeatherRepository;
import godaa.android.com.weathertaskapp.presentation.viewmodel.WeatherViewModel;
import godaa.android.com.weathertaskapp.presentation.viewmodel.factory.ViewModelFactory;


@Module(includes = {AppModule.class})
public abstract class ViewModelModule {
    @Provides
    static WeatherRepository provideShoutoutRepository(WeatherDatabase weatherDatabase, ApiService remote) {
        return new WeatherRepository(weatherDatabase.weatherDao(),remote);
    }

    @Provides
    static WeatherViewModel provided(WeatherRepository repository, AppSchedulerProvider appSchedulerProvider) {
        return new WeatherViewModel(appSchedulerProvider,repository);
    }
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    abstract ViewModel provideWeatherViewModel(WeatherViewModel weatherViewModel);
}
