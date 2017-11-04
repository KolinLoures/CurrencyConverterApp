package com.example.kolin.currencyconverterapp;

import android.app.Application;

import com.example.kolin.currencyconverterapp.data.cache.CacheImpl;
import com.example.kolin.currencyconverterapp.data.db.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;

/**
 * Created by kolin on 26.10.2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ApiManager.getInstance();
        DataBaseQueries.initializeInstanceWithContext(this);
        CacheImpl.initializeWithContext(this);
        PreferenceManager.initializeWithContext(this);
    }
}
