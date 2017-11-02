package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.domain.model.SupportCurrenciesPojo;

import io.reactivex.Observable;

/**
 * Created by kolin on 03.11.2017.
 */

public class GetSupportCurrencies extends BaseObservableUseCase<SupportCurrenciesPojo, Void> {

    private Api api;

    public GetSupportCurrencies() {
        api = ApiManager.getInstance();
    }

    @Override
    protected Observable<SupportCurrenciesPojo> createObservable(Void params) {
        return api.getRates();
    }
}
