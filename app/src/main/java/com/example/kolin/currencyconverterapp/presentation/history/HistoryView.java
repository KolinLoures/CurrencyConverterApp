package com.example.kolin.currencyconverterapp.presentation.history;

import com.arellomobile.mvp.MvpView;
import com.example.kolin.currencyconverterapp.domain.model.HistoryRenderer;

/**
 * Created by kolin on 19.11.2017.
 */

public interface HistoryView extends MvpView {

    void  renderHistoryView(HistoryRenderer renderer);
}
