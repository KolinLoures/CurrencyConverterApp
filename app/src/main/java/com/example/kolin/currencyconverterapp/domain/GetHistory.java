package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.preference.BasePreference;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.domain.common.DateHelper;
import com.example.kolin.currencyconverterapp.domain.common.SearchParamPreference;
import com.example.kolin.currencyconverterapp.domain.model.HistoryRenderer;
import com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 *
 */

public class GetHistory implements BaseObservableUseCase<HistoryRenderer>, ParamsUseCase<GetHistory.GetHistoryParams> {

    public static final String TAG = GetHistory.class.getSimpleName();

    private DAO queries;
    private BasePreference preference;
    private SearchParamPreference searchParamPreference;

    private GetHistoryParams params;

    public GetHistory() {
        queries = new DataBaseQueries();
        preference = new PreferenceManager();
        searchParamPreference = new SearchParamPreference();
    }

    private SearchParam createSearchParamObjFromParams() {
        SearchParam searchParam = new SearchParam();

        searchParam.setType(getParams().period);
        if (getParams().period == TypeSearchPeriodParam.PERIOD_CUSTOM) {
            searchParam.setTimeFrom(DateHelper.getStartOfTheDay(new Date(getParams().timeFrom)));
            searchParam.setTimeTo(DateHelper.getEndOfTheDay(new Date(getParams().timeTo)));
        }

        searchParam.setCheckedCurrencies(getParams().currencyIds);
        return searchParam;
    }

    @Override
    public void setParams(GetHistoryParams params) {
        this.params = params;
    }

    @Override
    public GetHistoryParams getParams() {
        return params;
    }

    @Override
    public Observable<HistoryRenderer> createUseCase() {
        return Observable
                .fromCallable(() -> {
                    if (getParams() != null) searchParamPreference.putSearchParamsToPreference(preference, createSearchParamObjFromParams());
                    return searchParamPreference.getSearchParamsFromPreference(preference);
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                })
                .flatMap(searchParam -> queries.getHistory(searchParam).map(currencyHistoryEntities -> HistoryRenderer.getDataObject(currencyHistoryEntities, searchParam)))
                .startWith(HistoryRenderer.getLoadingObject(true))
                .onErrorReturn(HistoryRenderer::getErrorObject)
                .compose(applySchedulers());
    }

    public static class GetHistoryParams implements Params {

        private int period;
        private long timeFrom;
        private long timeTo;
        private List<Integer> currencyIds;

        private GetHistoryParams(int period, List<Integer> currencyIds) {
            this.period = period;
            this.timeFrom = -1;
            this.timeTo = -1;
            this.currencyIds = currencyIds;
        }

        private GetHistoryParams(long timeFrom, long timeTo, List<Integer> currencyIds) {
            this.period = TypeSearchPeriodParam.PERIOD_CUSTOM;
            this.timeFrom = timeFrom;
            this.timeTo = timeTo;
            this.currencyIds = currencyIds;
        }

        public static GetHistoryParams getParamsObj(@TypeSearchPeriodParam int pertiod, List<Integer> currencyIds) {
            return new GetHistoryParams(pertiod, currencyIds);
        }

        public static GetHistoryParams getCustomParamsObj(long timeFrom, long timeTo, List<Integer> currencyIds){
            return new GetHistoryParams(timeFrom, timeTo, currencyIds);
        }
    }

}
