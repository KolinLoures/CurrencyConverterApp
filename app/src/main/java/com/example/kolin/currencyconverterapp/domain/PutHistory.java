package com.example.kolin.currencyconverterapp.domain;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.db.DAO;
import com.example.kolin.currencyconverterapp.data.db.DataBaseQueries;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableCompletableObserver;

/**
 * Created by kolin on 03.11.2017.
 */

public class PutHistory extends BaseCompletableUseCase<PutHistory.PutHistoryParams> {

    public static final String TAG = PutHistory.class.getSimpleName();

    private DAO.HistoryCurrencyDAO db;

    public PutHistory() {
        this.db = DataBaseQueries.getInstance();
    }

    private Completable emitter = null;

    public PutHistoryReceiver receiver;

    public interface PutHistoryReceiver {
        void onReceive(PutHistoryParams params);
    }

    @Override
    protected Completable createCompletable(PutHistoryParams param) {
//        return Completable
//                .fromAction(() -> db.addHistory(param.currencyFrom, param.currencyTo, param.sumFrom, param.sumTo, param.rate))
//                .doOnError(throwable -> Log.e(TAG, "Fail to put history into data base", throwable));

        return getEmitter();
    }

    public void executeWith(DisposableCompletableObserver observer) {
        super.execute(observer, null);
    }

    /**
     * create emitter of Params object
     *
     * @return Completable sourse
     */
    private Completable getEmitter() {
        if (emitter != null)
            return emitter;
        else
            return emitter =
                    Completable.fromObservable(
                            Observable.create((ObservableOnSubscribe<PutHistoryParams>) e -> receiver = params -> {
                                if (params != null) {
                                    e.onNext(params);
                                } else
                                    e.onError(new Exception(TAG + ": Params is Null"));
                            })
                                    .debounce(1, TimeUnit.SECONDS)
                                    .doOnNext(params -> db.addHistory(params.currencyFrom, params.currencyTo, params.sumFrom, params.sumTo, params.rate))
                    ).doOnError(throwable -> Log.e(TAG, "Fail to put history into data base", throwable));
    }

    public PutHistoryReceiver getReceiver() {
        return receiver;
    }

    @Override
    public void dispose() {
        super.dispose();

        emitter = null;
        receiver = null;
    }

    public static class PutHistoryParams {
        private String currencyFrom;
        private String currencyTo;
        private float sumFrom;
        private float sumTo;
        private float rate;

        private PutHistoryParams(String currencyFrom, String currencyTo, float sumFrom, float sumTo, float rate) {
            this.currencyFrom = currencyFrom;
            this.currencyTo = currencyTo;
            this.sumFrom = sumFrom;
            this.sumTo = sumTo;
            this.rate = rate;
        }

        public static PutHistoryParams getParamObj(String currencyFrom, String currencyTo, float sumFrom, float sumTo, float rate) {
            return new PutHistoryParams(currencyFrom, currencyTo, sumFrom, sumTo, rate);
        }
    }
}
