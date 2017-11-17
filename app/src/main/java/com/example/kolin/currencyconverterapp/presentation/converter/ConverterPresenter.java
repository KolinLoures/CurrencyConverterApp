package com.example.kolin.currencyconverterapp.presentation.converter;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.GetRate;
import com.example.kolin.currencyconverterapp.domain.PutHistory;
import com.example.kolin.currencyconverterapp.domain.model.RatePojo;
import com.example.kolin.currencyconverterapp.presentation.BasePresenter;

import java.util.Calendar;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by kolin on 07.11.2017.
 */

public class ConverterPresenter extends BasePresenter<ConverterFragment> {

    public static final String TAG = ConverterPresenter.class.getSimpleName();

    //Expiration time for rate
    private static final long RATE_TIME = 60 * 1000;
    //Time for rate, when it was updated
    private static long RATE_LAST_TIME = 0;

    //Use case to get rate
    private GetRate getRate;
    //Use case to insert to DB history
    private PutHistory putHistory;

    //Current entities for exchange
    private CurrencyEntity from;
    private CurrencyEntity to;

    //values for exchange (value from)
    private float valueF = -1;
    //values for exchange (value to)
    private float valueT = -1;

    //current rate object
    private RatePojo rate;

    public ConverterPresenter() {
        getRate = new GetRate();
        putHistory = new PutHistory();

        initPutHistoryObserver();
    }

    private void initPutHistoryObserver() {
        putHistory.executeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                //stub
//                getView().showError(true);
            }

            @Override
            public void onError(Throwable e) {
                //stub
            }
        });
    }

    public void setCurrencies(CurrencyEntity from, CurrencyEntity to) {
        this.from = from;
        this.to = to;
    }

    public void loadRate() {
        if (from != null && to != null) {
            getView().showLoading(true);

            getRate.clear();

            getRate.execute(new DisposableObserver<RatePojo>() {
                @Override
                public void onNext(RatePojo ratePojo) {
                    showLoadedRate(ratePojo);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: ", e);
                    getView().showLoading(false);
                    getView().showError(true);
                    showErrorMessage();
                }

                @Override
                public void onComplete() {
                    getView().showLoading(false);
                }
            }, GetRate.GetRateParams.getParamObject(from.getName(), to.getName()));
        }
    }

    private void showErrorMessage() {

    }

    private void showLoadedRate(RatePojo rate) {
        getView().blockInput(true);
        getView().showError(false);

        this.rate = rate;

        getView().showRate(1, rate.getRate());

        //check is rate from cache then update time of rate
        if (!rate.isFromCache()) {
            RATE_LAST_TIME = Calendar.getInstance().getTimeInMillis();
            getView().showAttention(false);
        }
        //check is rate from cache and current rate is expired then show attention
        else if (rate.isFromCache() && isRateExpired())
            getView().showAttention(true);

        // if pair -1 and -1 then it is first launch, therefore need show rate
        // if valueT = -1 then we are looking for valueTO, valueFrom is set by user
        // if valueF = -1 then we are looking for valueFROM, valueTo is set by user
        if (valueF == -1 && valueT == -1) {
            getView().setRateFrom(1);
            getView().setRateTo(rate.getRate());
        }
        else if (valueT == -1) {
            this.valueT = directRate(valueF);
            getView().setRateTo(valueT);

            putToHistory(valueF, valueT, rate.getRate());
        } else {
            this.valueF = indirectRate(valueT);
            getView().setRateFrom(valueF);

            putToHistory(valueF, valueT, rate.getRate());
        }

        getView().blockInput(false);
    }

    public void direct(float valueF) {
        this.valueF = valueF;
        this.valueT = -1;

        if (isRateExpired()) {
            loadRate();
        } else {

            valueT = directRate(valueF);

            getView().blockInput(true);
            getView().setRateTo(valueT);
            getView().blockInput(false);

            putToHistory(this.valueF, this.valueT, rate.getRate());
        }
    }

    public void indirect(float valueT) {
        this.valueT = valueT;
        this.valueF = -1;

        if (isRateExpired()) {
            loadRate();
        } else {
            valueF = indirectRate(valueT);

            getView().blockInput(true);
            getView().setRateFrom(valueF);
            getView().blockInput(false);

            putToHistory(this.valueF, this.valueT, rate.getRate());
        }
    }


    private void putToHistory(float valueF, float valueT, float rate) {
        putHistory.getReceiver().onReceive(PutHistory.PutHistoryParams.getParamObj(from.getId(), to.getId(), valueF, valueT, rate));
    }


    private float directRate(float value) {
        return Math.abs(value * rate.getRate());
    }

    private float indirectRate(float value) {
        return Math.abs(value / rate.getRate());
    }

    private boolean isRateExpired() {
        return rate == null || Calendar.getInstance().getTimeInMillis() - RATE_LAST_TIME > RATE_TIME;
    }


    public CurrencyEntity getFrom() {
        return from;
    }

    public CurrencyEntity getTo() {
        return to;
    }

    @Override
    public void unbindView() {
        super.unbindView();

        getRate.dispose();
        putHistory.dispose();
    }
}
