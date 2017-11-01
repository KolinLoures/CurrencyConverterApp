package com.example.kolin.currencyconverterapp.data.net;

import android.support.annotation.NonNull;

import com.example.kolin.currencyconverterapp.data.ResponseDeserializer;
import com.example.kolin.currencyconverterapp.data.SupportCurrenciesDeserializer;
import com.example.kolin.currencyconverterapp.domain.RatePojo;
import com.example.kolin.currencyconverterapp.domain.SupportCurrencies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolin on 28.10.2017.
 */

public class ApiManager {

    private static Api instance = null;

    private ApiManager() {
    }


    public static Api getInstance() {
        if (instance == null)
            instance = getApi();

        return instance;
    }

    private static OkHttpClient getOkHttpClient(){
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getOkHttpClient())
                .baseUrl(Api.BASE_URL)
                .build();
    }

    private static Gson getGson() {
        return new GsonBuilder()
                    .registerTypeAdapter(SupportCurrencies.class, new SupportCurrenciesDeserializer())
                    .registerTypeAdapter(RatePojo.class, new ResponseDeserializer())
                    .create();
    }

    private static Api getApi(){
        return getRetrofit().create(Api.class);
    }
}
