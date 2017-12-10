package com.example.kolin.currencyconverterapp.domain;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kolin on 10.12.2017.
 */

public interface BaseCompletableUseCase {

    Completable createCompletable();

    default CompletableTransformer applySchedulers() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
}
