package com.example.kolin.currencyconverterapp.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kolin on 27.10.2017.
 */

public class PreferenceManager {

    private static final String PREFERENCE_NAME = "com.example.kolin.currencyconverterapp.PREFERENCE";

    public static final String KEY_PREF_CURR_FROM = "key_pref_currency_from";
    public static final String KEY_PREF_CURR_TO = "key_pref_currency_to";

    public static final String KEY_PREF_CACHE_TIME = "key_pref_cache_time";
    public static final String KEY_PREF_BASE_CURR = "key_pref_base_curr";



    public void writeLongToPreference(Context context, String key, long value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(key, value);
        editor.apply();
    }

    public void writeStringToPreference(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public long readLongPreference(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public String readStringPreference(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

}
