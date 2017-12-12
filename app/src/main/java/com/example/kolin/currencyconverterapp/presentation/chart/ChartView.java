package com.example.kolin.currencyconverterapp.presentation.chart;

import com.example.kolin.currencyconverterapp.domain.model.ChartParamRenderer;
import com.example.kolin.currencyconverterapp.domain.model.ChartRenderer;

/**
 * Created by kolin on 04.12.2017.
 */

public interface ChartView {

    void renderChartParamsView(ChartParamRenderer renderer);

    void renderChart(ChartRenderer chartRenderer);

}
