package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.db.DAO;
import com.example.kolin.currencyconverterapp.data.db.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    }

    @Override
    protected Observable<CurrencyEntity> createObservable(String params) {
        return api
                .getRates()
                .doOnNext(supportCurrenciesPojo -> {
                    List<String> listCurrencies = supportCurrenciesPojo.getListCurrencies();
                    for (String currName : listCurrencies)
                        db.addCurrency(currName);
                })
                .flatMap(supportCurrenciesPojo -> db.getAllCurrency())
                .delay(2, TimeUnit.SECONDS);
    }
}
