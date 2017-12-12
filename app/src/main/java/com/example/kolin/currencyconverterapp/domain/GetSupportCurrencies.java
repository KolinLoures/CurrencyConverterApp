package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;

import io.reactivex.Observable;

/**
 * Created by kolin on 03.11.2017.
 */

public class GetSupportCurrencies {

    public static final String TAG = GetSupportCurrencies.class.getSimpleName();

    private Api api;
    private DAO db;
    private PreferenceManager preferenceManager;

    public GetSupportCurrencies() {
        api = ApiManager.getInstance();
        db = new DataBaseQueries();
//        preferenceManager = PreferenceManager.getInstance();
    }

    protected Observable<CurrencyEntity> createObservable(String params) {

//        boolean first = preferenceManager.readBoolPreference(PreferenceManager.KEY_PREF_FIRST_START);

//        return api
//                .getRates()
//                .doOnNext(supportCurrenciesPojo -> {
//                    List<String> listCurrencies = supportCurrenciesPojo.getListCurrencies();
//                    for (String currName : listCurrencies)
//                        db.addCurrency(currName);
//                })
//                .flatMap(supportCurrenciesPojo -> db.getAllCurrency())
//                .delay(2, TimeUnit.SECONDS);

//        return Observable.just(first)
//                .flatMap(aBoolean -> {
//                    if (aBoolean)
//                        return api
//                                .getRates()
//                                .doOnNext(s -> db.addCurrency(s.getListCurrencies()))
//                                .flatMap(supportCurrenciesPojo -> db.getAllCurrency())
//                                .doOnNext(currencyEntity -> Log.i(TAG, "after db: " + currencyEntity.getName()))
//                                .doOnComplete(() -> preferenceManager.writeBoolPreference(PreferenceManager.KEY_PREF_FIRST_START, false));
//                    else
//                        return db.getAllCurrency();
//                });

        return null;
    }
}
