package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.preference.BasePreference;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum;
import com.example.kolin.currencyconverterapp.domain.model.SearchParamsRenderer;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_CUSTOM;

/**
 * Created by kolin on 10.12.2017.
 */

public class GetSearchParams implements BaseObservableUseCase<SearchParamsRenderer> {

    private BasePreference preference;
    private DAO queries;

    public GetSearchParams() {
        this.preference = new PreferenceManager();
        queries =  new DataBaseQueries();
    }

    @Override
    public Observable<SearchParamsRenderer> createUseCase() {
        return Observable
                .zip(queries.getAllCurrency(), Observable.fromCallable(this::getSearchParamsFromPreference), SearchParamsRenderer::getDataObject)
                .startWith(SearchParamsRenderer.getLoadingObject(true))
                .onErrorReturn(SearchParamsRenderer::getErrorObject)
                .compose(applySchedulers());

    }

    @SuppressWarnings("unchecked")
    private SearchParam getSearchParamsFromPreference(){
        int period = (int) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_PERIOD, PreferenceTypeEnum.INTEGER, 0);
        long timeFrom = -1;
        long timeTo = -1;
        if (period == PERIOD_CUSTOM){
            timeFrom = (long) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_FROM_TIME, PreferenceTypeEnum.LONG, -1L);
            timeTo = (long) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_TO_TIME, PreferenceTypeEnum.LONG, -1L);
        }
        List<Integer> checkedIds = (List<Integer>) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_PICKED_CURR, PreferenceTypeEnum.INTEGER_ARRAY_LIST, Collections.EMPTY_LIST);
        return new SearchParam(period, timeFrom, timeTo, checkedIds);
    }
}
