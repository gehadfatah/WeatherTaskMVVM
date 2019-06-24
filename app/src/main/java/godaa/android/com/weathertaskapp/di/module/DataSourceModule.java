package godaa.android.com.weathertaskapp.di.module;

import androidx.lifecycle.LiveData;

import dagger.Binds;
import dagger.Module;

import godaa.android.com.weathertaskapp.data.local.entity.AccuWeatherDb;
import godaa.android.com.weathertaskapp.utils.Resource;

@Module
public abstract class DataSourceModule {

    @Binds
    abstract LocalDataSource provideDataSource(LocalDataSourceImpl localDataSource);

    @Binds
    abstract BaseSource provideBaseSource(RemoteSourceImpl remoteSource);

    @Binds
    abstract WeatherRepository<LiveData<Resource<AccuWeatherDb>>> provideRepoImpl(WeatherRepositoryImpl repo);
}

