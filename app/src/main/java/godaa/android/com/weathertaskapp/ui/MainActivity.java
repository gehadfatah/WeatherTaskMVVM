package godaa.android.com.weathertaskapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.ui.weatherCities.WeatherCitiesFragment;

public class MainActivity extends AppCompatActivity {
    private static String FRAGMENT_CURRENT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if this the first launch
       // if (savedInstanceState == null) {
     //   }
    }

    @Override
    protected void onResume() {
        super.onResume();
        replaceFragment(WeatherCitiesFragment.class, WeatherCitiesFragment.TAG);

    }

    public void replaceFragment(Class<?> fragmentClass, String tag) {

        //if it the same running fragment do nothing
        if (FRAGMENT_CURRENT.equals(fragmentClass.getName()))
            return;

        FRAGMENT_CURRENT = fragmentClass.getName();
        // create instance of the fragment
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        //replace the ragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

}
