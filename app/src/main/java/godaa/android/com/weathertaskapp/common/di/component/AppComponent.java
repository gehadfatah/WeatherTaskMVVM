package godaa.android.com.weathertaskapp.common.di.component;

import dagger.Component;
import godaa.android.com.weathertaskapp.common.di.modules.AppModule;
import godaa.android.com.weathertaskapp.common.di.modules.NetworkModule;
import godaa.android.com.weathertaskapp.common.di.modules.PreferenceUtilsModule;
import godaa.android.com.weathertaskapp.common.di.modules.ViewModelModule;
import godaa.android.com.weathertaskapp.common.di.scope.ApplicationScope;
import godaa.android.com.weathertaskapp.data.local.prefs.PreferenceUtils;
import godaa.android.com.weathertaskapp.presentation.viewmodel.factory.ViewModelFactory;


@ApplicationScope
@Component(modules = {AppModule.class, NetworkModule.class,ViewModelModule.class, PreferenceUtilsModule.class})
public interface AppComponent {


    ViewModelFactory getViewModelFactory();

    PreferenceUtils getPreferenceUtils();
}
