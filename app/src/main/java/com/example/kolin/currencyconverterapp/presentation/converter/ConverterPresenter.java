package com.example.kolin.currencyconverterapp.presentation.converter;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.GetRate;
import com.example.kolin.currencyconverterapp.domain.PutHistory;
import com.example.kolin.currencyconverterapp.presentation.BaseCompositPresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 07.11.2017.
 */

public class ConverterPresenter extends BaseCompositPresenter<ConverterFragment> {

    public static final String TAG = ConverterPresenter.class.getSimpleName();

    //Use case to get rate
    private GetRate getRate;
    //Use case to insert to DB history
    private PutHistory putHistory;

    //Current entities for exchange
    private CurrencyEntity from;
    private CurrencyEntity to;

    public ConverterPresenter() {
        getRate = new GetRate();
        putHistory = new PutHistory();

        initPutHistoryObserver();
    }

    private void initPutHistoryObserver() {
        putHistory
                .createCompletable()
                .subscribe(() -> {/*stub*/}, throwable -> Log.e(TAG, "initPutHistoryObserver: ", throwable));
    }

    public void setCurrencies(CurrencyEntity from, CurrencyEntity to) {
        this.from = from;
        this.to = to;
    }

    public void loadRate(float value, boolean reverse) {
        if (from != null && to != null) {
            super.clearDisposables();
            getRate.setParams(GetRate.GetRateParams.getParamObject(from.getName(), to.getName(), value, reverse));
            Disposable di = getRate.createUseCase().subscribe(converterRateRender -> {
                getView().renderRateView(converterRateRender);
                if (converterRateRender.getData() != null)
                    putHistory.getReceiver().onReceive(PutHistory.PutHistoryParams.getParamObj(from.getId(), to.getId(), value, converterRateRender.getResult(), converterRateRender.getData().getRate()));
            });
            super.addDisposable(di);
        }
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
    }
}
