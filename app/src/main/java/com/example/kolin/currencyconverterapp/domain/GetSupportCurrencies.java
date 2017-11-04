package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.db.DAO;
import com.example.kolin.currencyconverterapp.data.db.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.domain.model.SupportCurrenciesPojo;

import io.reactivex.Observable;

/**
 * Created by kolin on 03.11.2017.
 */

public class GetSupportCurrencies extends BaseObservableUseCase<CurrencyEntity, String> {

    private Api api;
    private DAO.CurrencyCatalogDAO db;
    private PreferenceManager preferenceManager;

    public GetSupportCurrencies() {
        api = ApiManager.getInstance();
        db = DataBaseQueries.getInstance();
        preferenceManager = PreferenceManager.getInstance();
    }

    @Override
    protected Observable<CurrencyEntity> createObservable(String params) {

        boolean first = preferenceManager.readBoolPreference(PreferenceManager.KEY_PREF_FIRST_START);

//        return api
//                .getRates()
//                .doOnNext(supportCurrenciesPojo -> {
//                    List<String> listCurrencies = supportCurrenciesPojo.getListCurrencies();
//                    for (String currName : listCurrencies)
//                        db.addCurrency(currName);
//                })
//                .flatMap(supportCurrenciesPojo -> db.getAllCurrency())
//                .delay(2, TimeUnit.SECONDS);

        return Observable.just(first)
                .flatMap(aBoolean -> {
                    if (aBoolean)
                        return api
                                .getRates()
                                .flatMapIterable(SupportCurrenciesPojo::getListCurrencies)
                                .doOnNext(s -> db.addCurrency(s))
                                .flatMap(supportCurrenciesPojo -> db.getAllCurrency())
                                .doOnComplete(() -> preferenceManager.writeBoolPreference(PreferenceManager.KEY_PREF_FIRST_START, false));
                    else
                        return db.getAllCurrency();
                });
    }
}
