package godaa.android.com.weathertaskapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.ui.detailWeather.DetailFragment;
import godaa.android.com.weathertaskapp.ui.dialog.ExitDialog;
import godaa.android.com.weathertaskapp.ui.weatherCities.WeatherCitiesFragment;

public class MainActivity extends AppCompatActivity {
    private static String FRAGMENT_CURRENT = "";
    Fragment currentFragment;

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
        replaceFragment(WeatherCitiesFragment.class, WeatherCitiesFragment.TAG, new Bundle());

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        if (currentFragment instanceof WeatherCitiesFragment) {
           // finish();
            showAlertSureExit();

        }else if (currentFragment instanceof DetailFragment){
            getSupportFragmentManager().popBackStack();
            //super.onBackPressed();
            currentFragment.onDestroy();

        }else if (currentFragment==null) {
            super.onBackPressed();
        }
    }
    private void showAlertSureExit() {
        ExitDialog exitDialog = new ExitDialog(this);
        exitDialog.show();
    }
    public void replaceFragment(Class<?> fragmentClass, String tag, Bundle bundle) {

        //if it the same running fragment do nothing
      /*  if (FRAGMENT_CURRENT.equals(fragmentClass.getName()))
            return;*/

        FRAGMENT_CURRENT = fragmentClass.getName();
        // create instance of the fragment
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
            currentFragment = fragment;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        //replace the ragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment,
                tag).commit();
    }

}
