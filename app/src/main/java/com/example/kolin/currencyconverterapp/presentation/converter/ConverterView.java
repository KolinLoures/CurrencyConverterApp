package com.example.kolin.currencyconverterapp.presentation.converter;

/**
 * Created by kolin on 07.11.2017.
 */

public interface ConverterView {

    void showRate(float from, float to);

    void setRateFrom(float from);

    void setRateTo(float to);

    void blockInput(boolean b);

    void showError(boolean show);

    void showAttention(boolean show);

    void showLoading(boolean show);
}
