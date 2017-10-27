package com.example.kolin.currencyconverterapp.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kolin on 27.10.2017.
 */

public class PreferenceManager {

    private static final String PREFERENCE_NAME = "com.example.kolin.currencyconverterapp.PREFERENCE";

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
