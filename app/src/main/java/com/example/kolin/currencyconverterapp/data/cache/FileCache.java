package com.example.kolin.currencyconverterapp.data.cache;

import com.example.kolin.currencyconverterapp.domain.RatePojo;

import java.io.IOException;

import io.reactivex.Observable;

/**
 * Created by kolin on 28.10.2017.
 */

public interface FileCache {

    void putRateToCache(RatePojo rate) throws IOException;

    Observable<RatePojo> getRateFromCache(String currencyFrom, String currencyTo);

}
