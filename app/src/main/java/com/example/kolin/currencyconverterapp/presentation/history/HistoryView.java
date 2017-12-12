package com.example.kolin.currencyconverterapp.presentation.history;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.HistoryRenderer;

/**
 * View for {@link HistoryFragment}
 */

public interface HistoryView extends MvpView {

    /**
     * Render view of {@link HistoryRenderer}
     *
     * @param renderer {@link HistoryRenderer} object
     */
    void  renderHistoryView(HistoryRenderer renderer);
}
