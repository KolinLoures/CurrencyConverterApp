package com.example.kolin.currencyconverterapp.data.cache;

import com.example.kolin.currencyconverterapp.domain.RatePojo;
import com.example.kolin.currencyconverterapp.domain.ResponsePojo;

/**
 * Created by kolin on 28.10.2017.
 */

public interface FileCache {

    void putRateToCache(ResponsePojo responsePojo);

    ResponsePojo getRateFromCache(String currencyFrom, String currencyTo);

}
