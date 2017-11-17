package com.example.kolin.currencyconverterapp.presentation;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by kolin on 05.11.2017.
 */

public abstract class BasePresenter<V> {

    private WeakReference<V> reference;

    @CallSuper
    public void bindView(@NonNull V view) {
        if (reference != null && reference.get() != null)
            throw new RuntimeException("View is bind!");
        else
            reference = new WeakReference<>(view);
    }

    @CallSuper
    public void unbindView() {
        if (reference != null) {
            reference.clear();
            reference = null;
        }
    }

    public boolean isViewBind() {
        return reference != null && reference.get() != null;
    }

    public V getView() {
        return reference != null ? reference.get() : null;
    }


}
