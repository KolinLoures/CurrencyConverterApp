package com.example.kolin.currencyconverterapp.presentation.chart;

import com.arellomobile.mvp.InjectViewState;
import com.example.kolin.currencyconverterapp.domain.GetChartData;
import com.example.kolin.currencyconverterapp.domain.GetChartParams;
import com.example.kolin.currencyconverterapp.presentation.BasePresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 04.12.2017.
 */
@InjectViewState
public class ChartPresenter extends BasePresenter<ChartView> {

    public static final String TAG = ChartPresenter.class.getSimpleName();

    private GetChartParams getChartParams;
    private GetChartData getChartData;

    private String currFrom = "";
    private String currTo = "";
    private int period = -1;
    private List<String> currNames;

    public ChartPresenter() {
        getChartParams = new GetChartParams();
        getChartData = new GetChartData();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadDefaultChartData();
        loadChartParams();
    }

    public void loadChartParams() {
        Disposable di = getChartParams
                .createUseCase()
                .subscribe(renderer -> {
                    if (renderer.getData() != null) {
                        this.currNames = renderer.getData();
                        this.currFrom = renderer.getChartParam().getCurrFrom();
                        this.currTo = renderer.getChartParam().getCurrTo();
                    }
                    getViewState().renderChartParamsView(renderer);
                });

        super.addDisposable(di);
    }

    private void loadDefaultChartData() {
        Disposable di = getChartData
                .createUseCase()
                .subscribe(chartRenderer -> getViewState().renderChart(chartRenderer));

        super.addDisposable(di);
    }

    public void loadChartDataWithParams(String currFrom, String currTo, int period) {
        if (isNewParams(currFrom, currTo, period)) {
            this.currFrom = currFrom;
            this.currTo = currTo;
            this.period = period;

            getChartData.setParams(GetChartData.GetChartDataParams.getChartDataParams(currFrom, currTo, period));
            loadDefaultChartData();
        }
    }

    private boolean isNewParams(String currFrom, String currTo, int period) {
        return !this.currFrom.equals(currFrom) || !this.currTo.equals(currTo) || this.period != period;
    }

    public boolean checkCurrentParams(){
        return !currFrom.isEmpty() && !currTo.isEmpty() && period != -1;
    }

    public List<String> getCurrNames() {
        return currNames;
    }

    public String getCurrFrom() {
        return currFrom;
    }

    public String getCurrTo() {
        return currTo;
    }

    public int getPeriod() {
        return period;
    }
}
