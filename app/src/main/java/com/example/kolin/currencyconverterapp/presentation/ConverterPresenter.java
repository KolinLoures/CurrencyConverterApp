package com.example.kolin.currencyconverterapp.presentation;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.GetSupportCurrencies;
import com.example.kolin.currencyconverterapp.domain.PutRemoveFavoriteCurrency;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by kolin on 04.11.2017.
 */

public class ConverterPresenter extends BasePresenter<ConverterFragment> {

    public static final String TAG = ConverterPresenter.class.getSimpleName();

    private GetSupportCurrencies getSupportCurrencies;
    private PutRemoveFavoriteCurrency putRemoveFavoriteCurrency;

    public ConverterPresenter() {
        getSupportCurrencies = new GetSupportCurrencies();
        putRemoveFavoriteCurrency = new PutRemoveFavoriteCurrency();
    }

    public void loadCurrencies(){
        getSupportCurrencies.execute(new DisposableObserver<CurrencyEntity>() {
            @Override
            public void onNext(CurrencyEntity entity) {
                getView().showSupportCurrency(entity);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: eee", e);
            }

            @Override
            public void onComplete() {

            }
        }, "");
    }

    public void putRemoveFavoriteCurrency (int id, boolean remove){
        putRemoveFavoriteCurrency.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                //stub
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: eror remove or put im favorite", e);
            }
        }, PutRemoveFavoriteCurrency.PutRemoveFavoriteParams.getParamObj(id, remove));
    }

}
