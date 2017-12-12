package com.example.kolin.currencyconverterapp.data.net;

import com.example.kolin.currencyconverterapp.data.model.RatePojo;
import com.example.kolin.currencyconverterapp.data.model.SupportCurrenciesPojo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Api interface. More about api http://fixer.io/
 */

public interface Api {

    String BASE_URL = "https://api.fixer.io/";

    /**
     * Get latest rate between two currencies
     *
     * @param from currency name form
     * @param to currency name to
     * @return {@link Observable<RatePojo>} object with rate
     */
    @GET("latest")
    Observable<RatePojo> getLatestRate(@Query("base") String from,
                                       @Query("symbols") String to);

    /**
     * Get rate for day
     *
     * @param date
     * @param from currency name
     * @param to currency to
     * @return @return {@link Observable<RatePojo>} object with rate
     */
    @GET("{rate_date}")
    Observable<RatePojo> getDateRate(@Path(value = "rate_date") String date,
                                     @Query("base") String from,
                                     @Query("symbols") String to);

    /**
     * Get support currencies from server
     *
     * @return {@link Observable<SupportCurrenciesPojo>} object with currency names
     */
    @GET("latest")
    Observable<SupportCurrenciesPojo> getRates();
}
