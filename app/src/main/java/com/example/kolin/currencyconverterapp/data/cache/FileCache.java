package com.example.kolin.currencyconverterapp.data.cache;

import com.example.kolin.currencyconverterapp.domain.RatePojo;

/**
 * Created by kolin on 28.10.2017.
 */

public interface FileCache {

    void putRateToCache(RatePojo rate);

    RatePojo getRateFromCache(String currencyFrom, String currencyTo);

}
