package com.example.kolin.currencyconverterapp.presentation.history.search_param;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.SearchParamsRenderer;

/**
 * Created by kolin on 02.12.2017.
 */

public interface SearchParamsView extends MvpView {

    void renderSearchPramsView(SearchParamsRenderer renderer);
}
