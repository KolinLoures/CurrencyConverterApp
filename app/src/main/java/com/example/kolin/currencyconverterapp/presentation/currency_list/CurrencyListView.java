package com.example.kolin.currencyconverterapp.presentation.currency_list;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.CurrencyListRenderer;

/**
 * Created by kolin on 05.11.2017.
 */

public interface CurrencyListView extends MvpView {

    void renderListView(CurrencyListRenderer currencyListRenderer);

}
