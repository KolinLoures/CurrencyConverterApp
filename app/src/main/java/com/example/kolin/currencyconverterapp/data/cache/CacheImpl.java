package com.example.kolin.currencyconverterapp.data.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.domain.model.RatePojo;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import io.reactivex.Observable;

/**
 * Created by kolin on 28.10.2017.
 */

public class CacheImpl implements FileCache {

    private static final String TAG = CacheImpl.class.getSimpleName();

    private static CacheImpl instanse;

    private Context context;
    private File cacheDir;
    private FileWriterReader fileWriterReader;


    private PreferenceManager preferenceManager;

    private Gson gson;

    private static final long CACHE_TIME = 60 * 6 * 1000;

    private CacheImpl(Context context) {
        this.context = context;
        cacheDir = this.context.getCacheDir();
        preferenceManager = PreferenceManager.getInstance();
        fileWriterReader = new FileWriterReader();
        gson = new Gson();
    }

    public static void initializeWithContext(Context context){
        if (instanse == null)
            instanse = new CacheImpl(context);
    }

    public static CacheImpl getInstanse(){

        return instanse;
    }

    @Override
    public void putRateToCache(@NonNull RatePojo rate) throws IOException {

        if (isCacheExpired())
            fileWriterReader.clearDirectory(cacheDir);

        File file = createFile(rate.getCurrencyFrom(), rate.getCurrencyTo());

        if (!file.exists()) {
            fileWriterReader.writeToFile(file, gson.toJson(rate, RatePojo.class));
        }

        setLastTimeUpdatedCache();
    }

    @Override
    public Observable<RatePojo> getRateFromCache(@NonNull String currencyFrom,
                                                 @NonNull String currencyTo) {


//        File file = createFile(currencyFrom, currencyTo);
//        String fileContent = fileWriterReader.readFromFile(file);
//
//        RatePojo rate = gson.fromJson(fileContent, RatePojo.class);

        return Observable
                .fromCallable(() -> fileWriterReader.readFromFile(createFile(currencyFrom, currencyTo)))
                .doOnError(throwable -> Log.e(TAG, "getRateFromCache: Error read from cache!"))
                .map(this::deserialize);
    }

    @Override
    public boolean isCached(String currencyFrom, String currencyTo) {
        return !isCacheExpired() && createFile(currencyFrom, currencyTo).exists();
    }

    private void setLastTimeUpdatedCache() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        preferenceManager.writeLongToPreference(PreferenceManager.KEY_PREF_CACHE_TIME, currentTime);
    }

    private boolean isCacheExpired() {
        long lastTime = preferenceManager.readLongPreference(PreferenceManager.KEY_PREF_CACHE_TIME);
        long currentTime = Calendar.getInstance().getTimeInMillis();

        return currentTime - lastTime > CACHE_TIME;
    }

    private File createFile(String from, String to) {
        String name = cacheDir.getPath() + File.separator + from + "_" + to;
        return new File(name);
    }

    private String serealize(RatePojo ratePojo){
        return gson.toJson(ratePojo, RatePojo.class);
    }

    private RatePojo deserialize(String t){
        return gson.fromJson(t, RatePojo.class);
    }
}
