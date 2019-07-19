package godaa.android.com.weathertaskapp.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Pc1 on 10/05/2018.
 */

public class WeatherSharedPreference {

    private SharedPreferences mPreference;

    /**
     * constructor for creating instance of SharedPreference class for saving data
     */
    public WeatherSharedPreference(Context context) {
        if (context != null)
            mPreference = PreferenceManager.getDefaultSharedPreferences(context);
        /*mPreference = PreferenceManager.getDefaultSharedPreferences();*/
    }

    /**
     * save String value in sp
     */
    public void saveStringToSharedPreference(String key, String value) {
        SharedPreferences.Editor mEditor = mPreference.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

    /**
     * save boolean value in sp
     */
    public void saveBooleanToSharedPreference(String key, boolean value) {

        SharedPreferences.Editor mEditor = mPreference.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    /**
     * retrieve stored String value in sp
     */
    public String retrieveStringFromSharedPreference(String key) {

        return mPreference.getString(key, "");
    }

    /**
     * retrieve stored boolean value in sp
     */
    public boolean retrieveBooleanFromSharedPreference(String key) {

        return mPreference.getBoolean(key, false);
    }







    public void saveObjectToSharedPreference(String serializedObjectKey, Object object) {
        SharedPreferences.Editor mEditor = mPreference.edit();

        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        mEditor.putString(serializedObjectKey, serializedObject);
        mEditor.apply();
    }

    public <GenericClass> GenericClass retrieveSavedObjectFromPreference(String preferenceKey, Class<GenericClass> classType) {
        if (mPreference.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(mPreference.getString(preferenceKey, ""), classType);
        }
        return null;
    }

    /**
     * save array of String Values in sp
     */
    public void saveArrayStringToSharedPreference(String superKey, String subKey,
                                            ArrayList<String> givenList) {
        /** superKey : is the ArrayListSize wanted To be stored
         *  subKey : is The item of This ArrayList
         *  I stored each item of arrayList in a shared Preference ,
         *  then i made a for Loop To save each item according to array size
         *  */
        SharedPreferences.Editor mEditor = mPreference.edit();
        mEditor.putInt(superKey, givenList.size());
        /** Saving the size of required ArrayList*/
        for (int i = 0; i < givenList.size(); i++) {
            mEditor.putString((subKey + i), givenList.get(i));
        }
        mEditor.apply();
    }
    /**
     * retrieve stored array of String Values from sp
     */
    public ArrayList<String> retrieveArrayStringFromSharedPreference(String superKey, String subKey) {

        /** Retrieve this list items with same scenario ,
         * Loop for each item according the ArrayList Size
         * */

        int listSize = mPreference.getInt(superKey, 0);

        ArrayList<String> finalList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            finalList.add(mPreference.getString((subKey + i), null));
        }
        return finalList;
    }

    /**
     * delete shared preferences stored in the app sp file
     */
    public void removeSharedPreference(String key) {
        SharedPreferences.Editor mEditor = mPreference.edit();
        mEditor.remove(key);
        mEditor.apply();
    }

    /**
     * clear all shared preferences stored in the app sp file
     */
    public void clearSharedPreferences() {

        SharedPreferences.Editor mEditor = mPreference.edit();
        mEditor.clear();
        mEditor.apply();
    }
}

