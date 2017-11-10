package com.example.kolin.currencyconverterapp.domain;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kolin on 03.11.2017.
 */

public abstract class BaseCompletableUseCase<P> implements BaseUseCase<DisposableCompletableObserver, P> {

    private CompositeDisposable container;

    public BaseCompletableUseCase() {
        container = new CompositeDisposable();
    }

    protected abstract Completable createCompletable(P param);

    @Override
    public void execute(DisposableCompletableObserver observer, P param) {
        DisposableCompletableObserver obs =
                createCompletable(param)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer);

        container.add(obs);
    }

    @Override
    public void dispose() {
        if (container != null && !container.isDisposed())
            container.dispose();
    }

    @Override
    public void clear() {
        if (container != null && !container.isDisposed())
            container.clear();
    }
}
