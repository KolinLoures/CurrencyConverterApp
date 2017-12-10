package com.example.kolin.currencyconverterapp.domain;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kolin.currencyconverterapp.data.cache.CacheImpl;
import com.example.kolin.currencyconverterapp.data.cache.FileCache;
import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.domain.model.ConverterRateRender;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by kolin on 03.11.2017.
 */

public class GetRate implements BaseObservableUseCase<ConverterRateRender>, ParamsUseCase<GetRate.GetRateParams> {

    private static final String TAG = GetRate.class.getSimpleName();

    private static final long RATE_TIME = 60 * 1000;
    private static long RATE_LAST_TIME = 0;

    private Api api;
    private FileCache cache;
    private DAO queries;

    private GetRate.GetRateParams params;

    public GetRate() {
        api = ApiManager.getInstance();
        cache = CacheImpl.getInstance();
        queries = new DataBaseQueries();
    }

    @Override
    public void setParams(GetRateParams params) {
        this.params = params;
    }

    @Override
    public GetRateParams getParams() {
        return params;
    }

    @Override
    public Observable<ConverterRateRender> createUseCase() {

        return api
                .getLatestRate(params.from, params.to)
                .doOnNext(ratePojo -> {
                    if (!cache.isCached(ratePojo.getCurrencyFrom(), ratePojo.getCurrencyTo()))
                        cache.putRateToCache(ratePojo);
                    updateLocalCacheTime();
                    Log.i(TAG, "createUseCase: " + ratePojo.toString());
                })
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    return cache
                            .getRateFromCache(params.from, params.to)
                            .map(ratePojo -> {
                                ratePojo.setFromCache(true);
                                return ratePojo;
                            });
                })
                .map(ratePojo -> {
                    float res = !params.reverse ? params.value * ratePojo.getRate() : params.value / ratePojo.getRate();
                    return ConverterRateRender.getDataObject(ratePojo, res, params.reverse, isRateExpired());
                })
                .startWith(ConverterRateRender.getLoadingObject(true))
                .onErrorReturn(ConverterRateRender::getErrorObject)
                .delaySubscription(500, TimeUnit.MILLISECONDS)
                .compose(applySchedulers());
    }

    private boolean isRateExpired() {
        return RATE_LAST_TIME == 0 || Calendar.getInstance().getTimeInMillis() - RATE_LAST_TIME > RATE_TIME;
    }

    private void updateLocalCacheTime() {
        RATE_LAST_TIME = Calendar.getInstance().getTimeInMillis();
    }

    public static class GetRateParams implements Params {
        private String from;
        private String to;
        //value that need to be converter
        private float value;
        //flag for value that will mult or divided
        private boolean reverse;

        private GetRateParams(String from, String to, float value, boolean reverse) {
            this.from = from;
            this.to = to;
            this.value = value;
            this.reverse = reverse;
        }

        public static GetRateParams getParamObject(@NonNull String from, @NonNull String to, float value, boolean reverse) {
            return new GetRateParams(from, to, value, reverse);
        }
    }
}
