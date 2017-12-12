package com.example.kolin.currencyconverterapp.presentation.converter;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.ConverterRateRender;

/**
 * View for {@link ConverterFragment}
 */

public interface ConverterView extends MvpView {

    /**
     * Render view
     *
     * @param render {@link ConverterRateRender}
     */
    void renderRateView(ConverterRateRender render);

}
