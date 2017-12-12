package com.example.kolin.currencyconverterapp.presentation.chart;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.kolin.currencyconverterapp.domain.model.ChartParamRenderer;
import com.example.kolin.currencyconverterapp.domain.model.ChartRenderer;

/**
 * View interface for {@link ChartFragment}
 */

public interface ChartView extends MvpView {

    /**
     * Render view of {@link ChartParamRenderer}
     *
     * @param renderer {@link ChartParamRenderer} object
     */
    void renderChartParamsView(ChartParamRenderer renderer);

    /**
     * Reabder view of chart
     *
     * @param chartRenderer {@link ChartRenderer} object
     */
    @StateStrategyType(SingleStateStrategy.class)
    void renderChart(ChartRenderer chartRenderer);

}
