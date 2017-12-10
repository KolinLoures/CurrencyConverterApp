package com.example.kolin.currencyconverterapp.domain;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kolin on 02.11.2017.
 */

public interface BaseObservableUseCase<T> {

    Observable<T> createUseCase();

    default ObservableTransformer<T, T> applySchedulers(){
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
    
}
