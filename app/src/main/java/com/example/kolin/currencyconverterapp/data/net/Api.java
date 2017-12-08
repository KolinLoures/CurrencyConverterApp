package com.example.kolin.currencyconverterapp.data.net;

import com.example.kolin.currencyconverterapp.data.model.RatePojo;
import com.example.kolin.currencyconverterapp.data.model.SupportCurrenciesPojo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kolin on 28.10.2017.
 */

public interface Api {

    String BASE_URL = "https://api.fixer.io/";

    @GET("latest")
    Observable<RatePojo> getLatestRate(@Query("base") String from,
                                       @Query("symbols") String to);

    @GET("{rate_date}")
    Observable<RatePojo> getDateRate(@Path(value = "rate_date") String date,
                                     @Query("base") String from,
                                     @Query("symbols") String to);

    @GET("latest")
    Observable<SupportCurrenciesPojo> getRates();
}
