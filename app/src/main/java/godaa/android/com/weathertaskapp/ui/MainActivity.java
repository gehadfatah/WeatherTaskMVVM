package godaa.android.com.weathertaskapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.lang.ref.WeakReference;

import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.ui.detailWeather.DetailFragment;
import godaa.android.com.weathertaskapp.ui.dialog.ExitDialog;
import godaa.android.com.weathertaskapp.ui.weatherCities.WeatherCitiesFragment;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity/* implements SwipeRefreshLayout.OnRefreshListener */{
    private static String FRAGMENT_CURRENT = "";
    Fragment currentFragment;
   // protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mSwipeRefreshLayout = findViewById(R.id.swiperefresh);

       // mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

    }

    /**
     * fun for reload fragment again for if network status change to true
     *
     * @param currentFragment
     */
    private void refreshCurrentFragment(WeakReference<Fragment> currentFragment) {
        Fragment fragment = currentFragment.get();
        if (getSupportFragmentManager() != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                getSupportFragmentManager().beginTransaction().detach(fragment).commitNow();
                getSupportFragmentManager().beginTransaction().attach(fragment).commitNow();
            } else {
                getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
            }

    }

    /* @Override
     public void onBackPressed() {
         *//*super.onBackPressed();*//*
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
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        //replaceFragment(WeatherCitiesFragment.class, WeatherCitiesFragment.TAG, new Bundle());

    }
/*
    @Override
    public void onRefresh() {
        Timber.d("onRefresh: ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                NavHostFragment fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_graph);
                if (fragment != null) {
                    fragment.getChildFragmentManager().getFragments();
                }

                *//*if (getSupportFragmentManager().)
                    refreshCurrentFragment(WeatherCitiesFragment.getFragmnt());
*//*
                Timber.d("run: ");
            }
        }, 1000);


    }*/
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
        // getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mSwipeRefreshLayout = null;

    }
}
