package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.db.dao.DAO;
import com.example.kolin.currencyconverterapp.data.db.dao.DataBaseQueries;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */

public class GetHistory extends BaseObservableUseCase<List<CurrencyHistoryEntity>, GetHistory.GetHistoryParams> {

    public static final String TAG = GetHistory.class.getSimpleName();

    private DAO queries;

    public GetHistory() {
        queries = DataBaseQueries.getInstance();
    }

    @Override
    protected Observable<List<CurrencyHistoryEntity>> createObservable(GetHistoryParams params) {
        return observableFactory(params);
    }

    private Observable<List<CurrencyHistoryEntity>> observableFactory(GetHistoryParams params) {
        if (params == null)
            return queries.getHistory();

        if (params.currencyIds.isEmpty() && params.timeFrom == -1 && params.timeTo == -1)
            return queries.getHistory();

        if (params.currencyIds.isEmpty() && params.timeFrom != -1 && params.timeTo != -1)
            return queries.getHistory(params.timeFrom, params.timeTo);

        if (!params.currencyIds.isEmpty() && params.timeFrom != -1 && params.timeTo != -1)
            return queries.getHistory(params.currencyIds, params.timeFrom, params.timeTo);

        if (!params.currencyIds.isEmpty() && params.timeFrom == -1 && params.timeTo == -1)
            return queries.getHistory(params.currencyIds);

        return null;
    }

    public static class GetHistoryParams {

        private long timeFrom;
        private long timeTo;
        private List<Integer> currencyIds;

        private GetHistoryParams(long timeFrom, long timeTo, List<Integer> currencyIds) {
            this.timeFrom = timeFrom;
            this.timeTo = timeTo;
            this.currencyIds = currencyIds;
        }

        public static GetHistoryParams getParamsObj(long timeFrom, long timeTo, List<Integer> currencyIds) {
            return new GetHistoryParams(timeFrom, timeTo, currencyIds);
        }
    }
}
