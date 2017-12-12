package com.example.kolin.currencyconverterapp.presentation.history.search_param;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.SearchParamsRenderer;

/**
 * View for {@link SearchParamsFragment}
 */

public interface SearchParamsView extends MvpView {

    /**
     * Render view {@link SearchParamsFragment}
     *
     * @param renderer {@link SearchParamsFragment}
     */
    void renderSearchPramsView(SearchParamsRenderer renderer);
}
