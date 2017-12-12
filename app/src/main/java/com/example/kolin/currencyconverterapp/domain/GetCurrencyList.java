package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.domain.model.CurrencyListRenderer;

import io.reactivex.Observable;

/**
 * Class Use Case to get currencies
 */

public class GetCurrencyList implements BaseObservableUseCase<CurrencyListRenderer> {

    public static final String TAG = GetCurrencyList.class.getSimpleName();

    private DAO queries;
    private Api api;

    public GetCurrencyList() {
        queries = new DataBaseQueries();
        api = ApiManager.getInstance();
    }

    @Override
    public Observable<CurrencyListRenderer> createUseCase() {
        return queries
                .getAllCurrency()
                .filter(currencyEntities -> !currencyEntities.isEmpty())
                .switchIfEmpty(api.getRates()
                        .doOnNext(s -> queries.addCurrency(s.getListCurrencies()))
                        .flatMap(supportCurrenciesPojo -> queries.getAllCurrency()))
                .flatMap(currencyEntities -> Observable.just(CurrencyListRenderer.getDataObject(currencyEntities)))
                .startWith(CurrencyListRenderer.getLoadingObject(true))
                .onErrorReturn(CurrencyListRenderer::getErrorObject)
                .compose(applySchedulers());
    }
}
