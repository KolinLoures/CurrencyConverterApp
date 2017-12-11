package com.example.kolin.currencyconverterapp.presentation;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 09.12.2017.
 */

public class BaseCompositPresenter<V> extends BasePresenter<V> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void addDisposable(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    protected void clearDisposables() {
        compositeDisposable.clear();
    }

    @Override
    public void unbindView() {
        super.unbindView();
        compositeDisposable.dispose();
    }
}
