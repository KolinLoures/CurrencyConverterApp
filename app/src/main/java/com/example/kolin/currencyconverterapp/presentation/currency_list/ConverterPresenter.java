package com.example.kolin.currencyconverterapp.presentation.currency_list;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.GetSupportCurrencies;
import com.example.kolin.currencyconverterapp.domain.PutRemoveFavoriteCurrency;
import com.example.kolin.currencyconverterapp.presentation.BasePresenter;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by kolin on 04.11.2017.
 */

public class ConverterPresenter extends BasePresenter<CurrencyListFragment> {

    public static final String TAG = ConverterPresenter.class.getSimpleName();

    private GetSupportCurrencies getSupportCurrencies;
    private PutRemoveFavoriteCurrency putRemoveFavoriteCurrency;

    private CurrencyEntity pickedEntity;

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
                Log.e(TAG, "onError: smth wrong with getSupportUseCase", e);
            }

            @Override
            public void onComplete() {}
        }, "");
    }

    public void putRemoveFavoriteCurrency (CurrencyEntity entity, boolean remove){
        putRemoveFavoriteCurrency.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                //stub
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: eror remove or put im favorite", e);
            }
        }, PutRemoveFavoriteCurrency.PutRemoveFavoriteParams.getParamObj(entity.getId(), remove));
    }

    public CurrencyEntity getPickedEntity() {
        return pickedEntity;
    }

    public void setPickedEntity(CurrencyEntity pickedEntity) {
        this.pickedEntity = pickedEntity;
    }
}
