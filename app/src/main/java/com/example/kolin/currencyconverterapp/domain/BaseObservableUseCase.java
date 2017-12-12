package com.example.kolin.currencyconverterapp.domain;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Base use case for {@link Observable}
 */

public interface BaseObservableUseCase<T> {

    /**
     * Create {@link Observable} source
     *
     * @return {@link Observable} object
     */
    Observable<T> createUseCase();

    /**
     * Apply default schedulers for {@link Observable}
     *
     * @return {@link Observable} object
     */
    default ObservableTransformer<T, T> applySchedulers(){
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
    
}
