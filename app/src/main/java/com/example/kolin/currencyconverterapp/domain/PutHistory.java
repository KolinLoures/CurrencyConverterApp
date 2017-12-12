package com.example.kolin.currencyconverterapp.domain;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Class Use Case to get history
 */

public class PutHistory implements BaseCompletableUseCase {

    public static final String TAG = PutHistory.class.getSimpleName();

    private DAO db;

    public PutHistory() {
        this.db = new DataBaseQueries();
    }

    private Completable emitter = null;

    private PutHistoryReceiver receiver;

    @Override
    public Completable createCompletable() {
        return getEmitter();
    }

    public interface PutHistoryReceiver {
        void onReceive(PutHistoryParams params);
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
                                    .doOnNext(params ->
                                            {
                                                db.addHistory(params.idCurrencyFrom, params.idCurrencyTo, params.sumFrom, params.sumTo, params.rate);
                                                db.updateCurrencyTime(params.idCurrencyFrom);
                                                db.updateCurrencyTime(params.idCurrencyTo);
                                            }
                                    )
                    ).doOnError(throwable -> Log.e(TAG, "Fail to put history into data base", throwable));
    }

    public PutHistoryReceiver getReceiver() {
        return receiver;
    }

    public static class PutHistoryParams implements Params {
        private int idCurrencyFrom;
        private int idCurrencyTo;
        private float sumFrom;
        private float sumTo;
        private float rate;

        private PutHistoryParams(int idCurrencyFrom, int idCurrencyTo, float sumFrom, float sumTo, float rate) {
            this.idCurrencyFrom = idCurrencyFrom;
            this.idCurrencyTo = idCurrencyTo;
            this.sumFrom = sumFrom;
            this.sumTo = sumTo;
            this.rate = rate;
        }

        public static PutHistoryParams getParamObj(int idCurrencyFrom, int idCurrencyTo, float sumFrom, float sumTo, float rate) {
            return new PutHistoryParams(idCurrencyFrom, idCurrencyTo, sumFrom, sumTo, rate);
        }
    }
}
