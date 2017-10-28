package com.example.kolin.currencyconverterapp.data.cache;

import android.content.Context;

import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.domain.RatePojo;
import com.example.kolin.currencyconverterapp.domain.ResponsePojo;
import com.google.gson.Gson;

import java.io.File;
import java.util.Calendar;

/**
 * Created by kolin on 28.10.2017.
 */

public class CacheImpl implements FileCache {

    private static final String TAG = CacheImpl.class.getSimpleName();


    private Context context;
    private File cacheDir;
    private FileWriterReader fileWriterReader;

    private Calendar calendar;

    private PreferenceManager preferenceManager;

    private Gson gson;

    private static final long CACHE_TIME = 60 * 6 * 1000;

    public CacheImpl(Context context) {
        this.context = context;
        cacheDir = this.context.getCacheDir();
        preferenceManager = new PreferenceManager();
        calendar = Calendar.getInstance();
        fileWriterReader = new FileWriterReader();
        gson = new Gson();
    }

    @Override
    public void putRateToCache(ResponsePojo responsePojo) {
        if (responsePojo != null) {

            if (isCacheExpired())
                fileWriterReader.clearDirectory(cacheDir);

            File file = createFile(responsePojo.getFromCurrency(),
                    responsePojo.getToCurrency().getRates().entrySet().iterator().next().getKey());

            if (!file.exists()){
                fileWriterReader.writeToFile(file, gson.toJson(responsePojo, ResponsePojo.class));
            }

            setLastTimeUpdatedCache();
        }
    }

    @Override
    public ResponsePojo getRateFromCache(String currencyFrom, String currencyTo) {
        if (currencyFrom != null && currencyTo != null){
            File file = createFile(currencyFrom, currencyTo);
            String fileContent = fileWriterReader.readFromFile(file);

            ResponsePojo responsePojo = gson.fromJson(fileContent, ResponsePojo.class);
            return responsePojo;
        }
        return null;
    }

    private void setLastTimeUpdatedCache() {
        long currentTime = calendar.getTimeInMillis();
        preferenceManager.writeLongToPreference(context, PreferenceManager.KEY_PREF_CACHE_TIME, currentTime);
    }

    private boolean isCacheExpired() {
        long lastTime = preferenceManager.readLongPreference(context, PreferenceManager.KEY_PREF_CACHE_TIME);
        long currentTime = calendar.getTimeInMillis();

        return currentTime - lastTime > CACHE_TIME;
    }

    private File createFile(String from, String to){
        String name = cacheDir.getPath() + File.separator + from + "_" + to;
        return new File(name);
    }
}
