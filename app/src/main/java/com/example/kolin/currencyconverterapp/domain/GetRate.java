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

import io.reactivex.Observable;

/**
 * Created by kolin on 03.11.2017.
 */

public class GetRate extends BaseObservableUseCase<RatePojo, GetRate.GetRateParams>{

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
                .doOnNext(ratePojo -> cache.putRateToCache(ratePojo))
                .doOnError(throwable -> Log.e(TAG, "createObservable: error", throwable))
                .onErrorResumeNext(throwable -> {return cache.getRateFromCache(params.from, params.to);});
    }

    static class GetRateParams {
        private String from;
        private String to;

        private GetRateParams(String from, String to){
            this.from = from;
            this.to = to;
        }

        public static GetRateParams getParamObject(@NonNull String from, @NonNull String to){
            return new GetRateParams(from, to);
        }
    }
}
