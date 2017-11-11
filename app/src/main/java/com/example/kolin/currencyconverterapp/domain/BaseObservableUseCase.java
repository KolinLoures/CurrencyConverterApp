package com.example.kolin.currencyconverterapp.domain;

import android.support.annotation.CallSuper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kolin on 03.11.2017.
 */

public abstract class BaseObservableUseCase<T, P> implements BaseUseCase<DisposableObserver<T>, P> {

    private CompositeDisposable container;

    public BaseObservableUseCase() {
        this.container = new CompositeDisposable();
    }

    protected abstract Observable<T> createObservable(P params);

    @Override
    public void execute(DisposableObserver<T> observer, P param) {
        DisposableObserver<T> obs = createObservable(param)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(observer);

        container.add(obs);
    }

    @CallSuper
    @Override
    public void dispose(){
        if (container != null && !container.isDisposed())
            container.dispose();
    }

    @CallSuper
    @Override
    public void clear() {
        if (container != null && !container.isDisposed())
            container.clear();
    }
}
