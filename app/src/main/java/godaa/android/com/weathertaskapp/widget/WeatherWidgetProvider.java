package godaa.android.com.weathertaskapp.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;

import godaa.android.com.weathertaskapp.R;
import godaa.android.com.weathertaskapp.data.local.prefs.WeatherSharedPreference;
import godaa.android.com.weathertaskapp.ui.MainActivity;
import godaa.android.com.weathertaskapp.ui.weatherCities.WeatherCitiesFragment;


/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidgetProvider extends AppWidgetProvider {

    private PendingIntent pendingIntent;

    private AlarmManager manager;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        WeatherSharedPreference sharedPreferences = new WeatherSharedPreference(context);
        String defaultValue = context.getString(R.string.no_data);
        String conditionText = sharedPreferences.retrieveStringFromSharedPreference(WeatherCitiesFragment.WIDGET_TEXT)!=null?sharedPreferences.retrieveStringFromSharedPreference(WeatherCitiesFragment.WIDGET_TEXT):defaultValue;
        String location = sharedPreferences.retrieveStringFromSharedPreference(WeatherCitiesFragment.WIDGET_LOCATION)!=null?sharedPreferences.retrieveStringFromSharedPreference(WeatherCitiesFragment.WIDGET_LOCATION):defaultValue;
        int iconUrl = sharedPreferences.retrieveStringFromSharedPreference(WeatherCitiesFragment.WIDGET_ICON)!=null?Integer.parseInt(sharedPreferences.retrieveStringFromSharedPreference(WeatherCitiesFragment.WIDGET_ICON)):0;


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        views.setTextViewText(R.id.appwidget_location, location);
        views.setTextViewText(R.id.appwidget_condition, context.getString(R.string.widget_forecast, conditionText));

        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_root, pendingIntent);

        // Display weather condition icon using Glide
        showWeatherIcon(context, appWidgetId, iconUrl, views);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WeatherWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            WeatherWidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void showWeatherIcon(Context context, int appWidgetId, int iconUrl, RemoteViews views) {
        AppWidgetTarget widgetTarget = new AppWidgetTarget(context, R.id.appwidget_icon, views, appWidgetId) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                super.onResourceReady(resource, transition);
            }
        };

        RequestOptions options = new RequestOptions().
                override(300, 300).placeholder(R.drawable.day).error(R.drawable.day);

        Glide.with(context.getApplicationContext())
                .asBitmap()
                /*.load("http:" + iconUrl)*/
                .load("http://apidev.accuweather.com/developers/Media/Default/WeatherIcons/" + String.format("%02d", iconUrl) + "-s" + ".png")

                .apply(options)
                .into(widgetTarget);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            startWidgetUpdateService(context);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void startWidgetUpdateService(Context context) {
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent updateIntent = new Intent(context, WidgetUpdateService.class);

        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, updateIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, pendingIntent);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
        if (manager != null) {
            manager.cancel(pendingIntent);
        }
    }
}

