package com.example.kolin.currencyconverterapp.presentation.currency_list;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.GetCurrencyList;
import com.example.kolin.currencyconverterapp.domain.PutRemoveFavoriteCurrency;
import com.example.kolin.currencyconverterapp.presentation.BaseCompositPresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 04.11.2017.
 */

public class CurrencyListPresenter extends BaseCompositPresenter<CurrencyListFragment> {

    public static final String TAG = CurrencyListPresenter.class.getSimpleName();

    private GetCurrencyList getCurrencyList;
    private PutRemoveFavoriteCurrency putRemoveFavoriteCurrency;

    private CurrencyEntity pickedEntity;

    public CurrencyListPresenter() {
        getCurrencyList = new GetCurrencyList();
        putRemoveFavoriteCurrency = new PutRemoveFavoriteCurrency();
    }

    public void loadCurrencies() {
        Disposable di = getCurrencyList
                .createUseCase()
                .subscribe(currencyListRenderer -> getView().renderListView(currencyListRenderer));

        super.addDisposable(di);
    }

    public void putRemoveFavoriteCurrency(CurrencyEntity entity, boolean check) {
        putRemoveFavoriteCurrency.setParams(PutRemoveFavoriteCurrency.PutRemoveFavoriteParams.getParamObj(entity.getId(), check));
        Disposable di = putRemoveFavoriteCurrency
                .createCompletable()
                .subscribe(
                        () -> { /*stub*/},
                        throwable -> Log.e(TAG, "Error - putRemoveFavoriteCurrency", throwable));

        super.addDisposable(di);
    }

    @Override
    public void unbindView() {
        super.clearDisposables();
        super.unbindView();
    }

    public CurrencyEntity getPickedEntity() {
        return pickedEntity;
    }

    public void setPickedEntity(CurrencyEntity pickedEntity) {
        this.pickedEntity = pickedEntity;
    }
}
