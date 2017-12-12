package com.example.kolin.currencyconverterapp.presentation.converter;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.ConverterRateRender;

/**
 * Created by kolin on 07.11.2017.
 */

public interface ConverterView extends MvpView {

    void renderRateView(ConverterRateRender render);

}
