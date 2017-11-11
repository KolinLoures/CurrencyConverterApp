package com.example.kolin.currencyconverterapp.domain;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kolin.currencyconverterapp.data.cache.CacheImpl;
import com.example.kolin.currencyconverterapp.data.cache.FileCache;
import com.example.kolin.currencyconverterapp.data.db.DAO;
import com.example.kolin.currencyconverterapp.data.db.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.domain.model.RatePojo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by kolin on 03.11.2017.
 */

public class GetRate extends BaseObservableUseCase<RatePojo, GetRate.GetRateParams> {

    private static final String TAG = GetRate.class.getSimpleName();

    private Api api;
    private DAO.HistoryCurrencyDAO db;
    private FileCache cache;

    public GetRate() {
        api = ApiManager.getInstance();
        db = DataBaseQueries.getInstance();
        cache = CacheImpl.getInstanse();
    }

    @Override
    protected Observable<RatePojo> createObservable(GetRate.GetRateParams params) {
        return api
                .getLatestRate(params.from, params.to)
                .doOnNext(ratePojo -> {
                    Log.e(TAG, "createObservable: pojo " + ratePojo.toString());

                    if (!cache.isCached(ratePojo.getCurrencyFrom(), ratePojo.getCurrencyTo()))
                        cache.putRateToCache(ratePojo);

                    ratePojo.setFromCache(false);
                })
                .onErrorResumeNext(throwable -> {
                    Log.e(TAG, "createObservable: on error resume next", throwable);

                    if (throwable instanceof ConnectException || throwable instanceof UnknownHostException
                            || throwable instanceof SocketTimeoutException) {
                        return cache
                                .getRateFromCache(params.from, params.to)
                                .map(ratePojo -> {
                                    ratePojo.setFromCache(true);
                                    return ratePojo;
                                });
                    }
                    return null;
                })
                .delaySubscription(700, TimeUnit.MILLISECONDS);
    }

    public static class GetRateParams {
        private String from;
        private String to;

        private GetRateParams(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public static GetRateParams getParamObject(@NonNull String from, @NonNull String to) {
            return new GetRateParams(from, to);
        }
    }
}
