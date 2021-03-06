package com.example.kolin.currencyconverterapp.presentation.currency_list;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.GetCurrencyList;
import com.example.kolin.currencyconverterapp.domain.PutRemoveFavoriteCurrency;
import com.example.kolin.currencyconverterapp.presentation.BasePresenter;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Presenter for {@link CurrencyListFragment}
 */
@InjectViewState
public class CurrencyListPresenter extends BasePresenter<CurrencyListView> {

    public static final String TAG = CurrencyListPresenter.class.getSimpleName();

    private GetCurrencyList getCurrencyList;
    private PutRemoveFavoriteCurrency putRemoveFavoriteCurrency;

    private CurrencyEntity pickedEntity;

    public CurrencyListPresenter() {
        getCurrencyList = new GetCurrencyList();
        putRemoveFavoriteCurrency = new PutRemoveFavoriteCurrency();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadCurrencies();
    }

    public void loadCurrencies() {
        Disposable di = getCurrencyList
                .createUseCase()
                .subscribe(currencyListRenderer -> {
                    removePickedCurrencyFromList(currencyListRenderer.getData());
                    getViewState().renderListView(currencyListRenderer);
                });

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

    private void removePickedCurrencyFromList(List<CurrencyEntity> currencyEntities){
        if (pickedEntity != null && currencyEntities != null){
            int i = Collections.binarySearch(currencyEntities, pickedEntity, (o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
            currencyEntities.remove(i);
        }
    }

    public CurrencyEntity getPickedEntity() {
        return pickedEntity;
    }

    public void setPickedEntity(CurrencyEntity pickedEntity) {
        this.pickedEntity = pickedEntity;
    }
}
