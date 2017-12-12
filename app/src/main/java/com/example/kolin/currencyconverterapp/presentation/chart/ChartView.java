package com.example.kolin.currencyconverterapp.presentation.chart;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.kolin.currencyconverterapp.domain.model.ChartParamRenderer;
import com.example.kolin.currencyconverterapp.domain.model.ChartRenderer;

/**
 * Created by kolin on 04.12.2017.
 */

public interface ChartView extends MvpView {

    void renderChartParamsView(ChartParamRenderer renderer);

    @StateStrategyType(SingleStateStrategy.class)
    void renderChart(ChartRenderer chartRenderer);

}
