package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.preference.BasePreference;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.domain.common.ChartParamsPreference;
import com.example.kolin.currencyconverterapp.domain.model.ChartParamRenderer;

import io.reactivex.Observable;

/**
 * Created by kolin on 11.12.2017.
 */

public class GetChartParams implements BaseObservableUseCase<ChartParamRenderer> {

    private BasePreference preference;
    private ChartParamsPreference chartParamsPreference;
    private DAO queries;


    public GetChartParams() {
        preference = new PreferenceManager();
        queries = new DataBaseQueries();
        chartParamsPreference = new ChartParamsPreference();
    }

    @Override
    public Observable<ChartParamRenderer> createUseCase() {
        return Observable
                .zip(queries.getNames(),
                        Observable.fromCallable(() -> chartParamsPreference.getChartParamsFromPreference(preference)),
                        (strings, param) -> {
                            if (param.getCurrFrom() == null && param.getCurrTo() == null) {
                                param.setCurrFrom(strings.get(0));
                                param.setCurrTo(strings.get(1));
                            }
                            return ChartParamRenderer.getDataObject(strings, param);
                        })
                .startWith(ChartParamRenderer.getLoadingObject(true))
                .onErrorReturn(ChartParamRenderer::getErrorObject)
                .compose(applySchedulers());
    }
}
