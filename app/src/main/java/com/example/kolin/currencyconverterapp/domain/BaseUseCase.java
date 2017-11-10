package com.example.kolin.currencyconverterapp.domain;

import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 02.11.2017.
 */

public interface BaseUseCase<T extends Disposable, P> {

    void execute(T observer, P param);

    void dispose();

    void clear();
}
