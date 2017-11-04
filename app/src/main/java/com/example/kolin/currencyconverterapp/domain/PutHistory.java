package com.example.kolin.currencyconverterapp.domain;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.db.DAO;
import com.example.kolin.currencyconverterapp.data.db.DataBaseQueries;

import io.reactivex.Completable;

/**
 * Created by kolin on 03.11.2017.
 */

public class PutHistory extends BaseCompletableUseCase<PutHistory.PutHistoryParams> {

    public static final String TAG = PutHistory.class.getSimpleName();

    private DAO.HistoryCurrencyDAO db;

    public PutHistory() {
        this.db = DataBaseQueries.getInstance();
    }

    @Override
    protected Completable createCompletable(PutHistoryParams param) {
        return Completable
                .fromAction(() -> db.addHistory(param.currencyFrom, param.currencyTo, param.sumFrom, param.sumTo, param.rate))
                .doOnError(throwable -> Log.e(TAG, "Fail to put history to data base", throwable));
    }

    static class PutHistoryParams {
        private String currencyFrom;
        private String currencyTo;
        private int sumFrom;
        private int sumTo;
        private float rate;

        private PutHistoryParams(String currencyFrom, String currencyTo, int sumFrom, int sumTo, float rate) {
            this.currencyFrom = currencyFrom;
            this.currencyTo = currencyTo;
            this.sumFrom = sumFrom;
            this.sumTo = sumTo;
            this.rate = rate;
        }

        public static PutHistoryParams getParamObj(String currencyFrom, String currencyTo, int sumFrom, int sumTo, float rate){
            return new PutHistoryParams(currencyFrom, currencyTo, sumFrom, sumTo, rate);
        }
    }
}
