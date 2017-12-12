package com.example.kolin.currencyconverterapp.presentation.currency_list;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.CurrencyListRenderer;

/**
 * View for {@link CurrencyListFragment}
 */

public interface CurrencyListView extends MvpView {

    /**
     * Render view of {@link CurrencyListFragment}
     *
     * @param currencyListRenderer {@link CurrencyListFragment} object
     */
    void renderListView(CurrencyListRenderer currencyListRenderer);

}
