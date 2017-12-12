package com.example.kolin.currencyconverterapp.domain;

import android.util.Log;

import com.example.kolin.currencyconverterapp.data.model.ChartParam;
import com.example.kolin.currencyconverterapp.data.net.Api;
import com.example.kolin.currencyconverterapp.data.net.ApiManager;
import com.example.kolin.currencyconverterapp.data.preference.BasePreference;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceManager;
import com.example.kolin.currencyconverterapp.domain.common.ChartParamsPreference;
import com.example.kolin.currencyconverterapp.domain.common.DateHelper;
import com.example.kolin.currencyconverterapp.domain.model.ChartRenderer;
import com.example.kolin.currencyconverterapp.domain.model.TypePeriodChart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static com.example.kolin.currencyconverterapp.domain.model.TypePeriodChart.PERIOD_MONTH;
import static com.example.kolin.currencyconverterapp.domain.model.TypePeriodChart.PERIOD_TWO_WEEK;
import static com.example.kolin.currencyconverterapp.domain.model.TypePeriodChart.PERIOD_WEEK;

/**
 * Use case to get data for chart
 */

public class GetChartData implements BaseObservableUseCase<ChartRenderer>, ParamsUseCase<GetChartData.GetChartDataParams> {

    private static final String TAG = GetChartData.class.getSimpleName();

    private Api api;
    private BasePreference preference;
    private ChartParamsPreference chartParamsPreference;

    private GetChartDataParams params;

    //date format for server
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public GetChartData() {
        api = ApiManager.getInstance();
        preference = new PreferenceManager();
        chartParamsPreference = new ChartParamsPreference();
    }

    @Override
    public Observable<ChartRenderer> createUseCase() {
        return Observable
                .fromCallable(() -> {
                    if (getParams() != null)
                        chartParamsPreference.putChartParamsFromPreference(
                                preference,
                                new ChartParam(getParams().currFrom, getParams().currTo, getParams().period)
                        );
                    return chartParamsPreference.getChartParamsFromPreference(preference);
                })
                .flatMap(param -> {
                    List<Date> daysForPeriod = getDaysForPeriod(param.getPeriod());
                    Log.e(TAG, "createUseCase:" + daysForPeriod.toString() );
                    return Observable.intervalRange(0, daysForPeriod.size(), 0, 350, TimeUnit.MILLISECONDS)
                            .doOnNext(aLong -> Log.e(TAG, "accept: " + sdf.format(daysForPeriod.get((int) aLong.longValue()))))
                            .flatMap(aLong -> api.getDateRate(sdf.format(daysForPeriod.get((int) aLong.longValue())), param.getCurrFrom(), param.getCurrTo())).toList()
                            .flatMapObservable(ratePojos -> Observable.just(ChartRenderer.getDataObject(ratePojos, param)));
                })
                .startWith(ChartRenderer.getLoadingObject(true))
                .onErrorReturn(ChartRenderer::getErrorObject)
                .compose(applySchedulers());
    }

    @Override
    public void setParams(GetChartDataParams params) {
        this.params = params;
    }

    @Override
    public GetChartDataParams getParams() {
        return params;
    }

    /**
     * Class with parameter for chart
     */
    public static class GetChartDataParams implements Params {
        String currFrom;
        String currTo;
        int period;

        private GetChartDataParams(String currFrom, String currTo, @TypePeriodChart int period) {
            this.currFrom = currFrom;
            this.currTo = currTo;
            this.period = period;
        }

        public static GetChartDataParams getChartDataParams(String currFrom, String currTo, @TypePeriodChart int period) {
            return new GetChartDataParams(currFrom, currTo, period);
        }
    }

    private List<Date> getDaysForPeriod(int period) {
        Calendar now = Calendar.getInstance();

        switch (period) {
            case PERIOD_WEEK:
                return DateHelper.getDaysBetweenTwoDates(DateHelper.getWeekCloseDatePeriod(now.getTimeInMillis()), now.getTimeInMillis());
            case PERIOD_TWO_WEEK:
                return DateHelper.getDaysBetweenTwoDates(DateHelper.getTwoWeekCLoaseDatePeriod(now.getTimeInMillis()), now.getTimeInMillis());
            case PERIOD_MONTH:
                return DateHelper.getDaysBetweenTwoDates(DateHelper.getMonthCloseDatePeriod(now.getTimeInMillis()), now.getTimeInMillis());
        }
        return null;
    }
}
