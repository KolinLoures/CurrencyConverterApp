package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by kolin on 09.12.2017.
 */

public class GetCurrencyList implements BaseUseCase<List<CurrencyEntity>> {

    public static final String TAG = GetCurrencyList.class.getSimpleName();

    private DAO queries;
    private Api api;

    public GetCurrencyList() {
        queries = new DataBaseQueries();
        api = ApiManager.getInstance();
    }

    @Override
    public Observable<List<CurrencyEntity>> createUseCase() {
        return queries
                .getAllCurrency()
                .switchIfEmpty(api.getRates()
                .doOnNext(s -> queries.addCurrency(s.getListCurrencies()))
                .flatMap(supportCurrenciesPojo -> queries.getAllCurrency()))
                .compose(applySchedulers());
    }
}
