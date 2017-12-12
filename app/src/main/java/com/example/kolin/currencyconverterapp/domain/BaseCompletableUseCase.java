package com.example.kolin.currencyconverterapp.domain;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Base use case for {@link Completable}
 */

public interface BaseCompletableUseCase {

    /**
     * Create {@link Completable} source
     *
     * @return {@link Completable} object
     */
    Completable createCompletable();

    /**
     * Apply default schedulers for {@link Completable}
     *
     * @return {@link CompletableTransformer} object
     */
    default CompletableTransformer applySchedulers() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
}
