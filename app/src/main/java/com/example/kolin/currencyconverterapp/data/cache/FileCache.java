package com.example.kolin.currencyconverterapp.data.cache;

import com.example.kolin.currencyconverterapp.data.model.RatePojo;

import java.io.IOException;

import io.reactivex.Observable;

/**
 * Interface for work with cache.
 */

public interface FileCache {

    /**
     * Put {@link RatePojo} object to file cache
     *
     * @param rate object
     * @throws IOException file not found
     */
    void putRateToCache(RatePojo rate) throws IOException;

    /**
     * Get {@link RatePojo} object from cache.
     *
     * @param currencyFrom name of currency from
     * @param currencyTo name of currency to
     * @return {@link Observable} source of {@link RatePojo} object
     */
    Observable<RatePojo> getRateFromCache(String currencyFrom, String currencyTo);

    /**
     * Check if pair of currencies was cached
     *
     * @param currencyFrom name of currency from
     * @param currencyTo name of currency to
     * @return true - if was cached, false - if was't cached
     */
    boolean isCached(String currencyFrom, String currencyTo);
}
