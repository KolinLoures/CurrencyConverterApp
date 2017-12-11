package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.preference.BasePreference;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.domain.common.SearchParamPreference;
import com.example.kolin.currencyconverterapp.domain.model.SearchParamsRenderer;

import io.reactivex.Observable;

/**
 * Created by kolin on 10.12.2017.
 */

public class GetSearchParams implements BaseObservableUseCase<SearchParamsRenderer> {

    private BasePreference preference;
    private DAO queries;
    private SearchParamPreference searchParamPreference;

    public GetSearchParams() {
        searchParamPreference = new SearchParamPreference();
        preference = new PreferenceManager();
        queries =  new DataBaseQueries();
    }

    @Override
    public Observable<SearchParamsRenderer> createUseCase() {
        return Observable
                .zip(   queries.getAllCurrency(),
                        Observable.fromCallable(() -> searchParamPreference.getSearchParamsFromPreference(preference)),
                        SearchParamsRenderer::getDataObject)
                .startWith(SearchParamsRenderer.getLoadingObject(true))
                .onErrorReturn(SearchParamsRenderer::getErrorObject)
                .compose(applySchedulers());

    }
}
