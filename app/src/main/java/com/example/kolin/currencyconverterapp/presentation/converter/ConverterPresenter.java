package com.example.kolin.currencyconverterapp.presentation.converter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.GetRate;
import com.example.kolin.currencyconverterapp.domain.PutHistory;
import com.example.kolin.currencyconverterapp.presentation.BasePresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 07.11.2017.
 */
@InjectViewState
public class ConverterPresenter extends BasePresenter<ConverterView> {

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
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initPutHistoryObserver();
        loadRate(1, false);
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
            getRate.setParams(GetRate.GetRateParams.getParamObject(from.getName(), to.getName(), value, reverse));
            Disposable di = getRate.createUseCase().subscribe(converterRateRender -> {
                getViewState().renderRateView(converterRateRender);
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

}
