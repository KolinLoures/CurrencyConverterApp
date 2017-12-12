package com.example.kolin.currencyconverterapp;

import android.app.Application;

import com.example.kolin.currencyconverterapp.data.cache.CacheImpl;
import com.example.kolin.currencyconverterapp.data.db.DataBaseHelper;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.facebook.stetho.Stetho;

/**
 * Created by kolin on 26.10.2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DataBaseHelper.initWithContext(this);
        ApiManager.getInstance();
        CacheImpl.initializeWithContext(this);
        Stetho.initializeWithDefaults(this);
    }
}
