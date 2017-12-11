package com.example.kolin.currencyconverterapp.domain.common;

import android.support.annotation.NonNull;

import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.preference.BasePreference;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_CUSTOM;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_MONTH;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_WEEK;

/**
 * Created by kolin on 11.12.2017.
 */

public class SearchParamPreference {


    @SuppressWarnings("unchecked")
    public SearchParam getSearchParamsFromPreference(BasePreference preference) {
        int period = (int) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_PERIOD, PreferenceTypeEnum.INTEGER, 0);
        long timeFrom = -1;
        long timeTo = -1;

        if (period == PERIOD_CUSTOM) {
            timeFrom = (long) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_FROM_TIME, PreferenceTypeEnum.LONG, -1L);
            timeTo = (long) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_TO_TIME, PreferenceTypeEnum.LONG, -1L);
        }

        if (period == PERIOD_WEEK) {
            timeTo = Calendar.getInstance().getTimeInMillis();
            timeFrom = DateHelper.getWeekCloseDatePeriod(timeTo);
        }

        if (period == PERIOD_MONTH) {
            timeTo = Calendar.getInstance().getTimeInMillis();
            timeFrom = DateHelper.getMonthCloseDatePeriod(timeTo);
        }

        List<Integer> checkedIds = (List<Integer>) preference.getFromPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_PICKED_CURR, PreferenceTypeEnum.INTEGER_ARRAY_LIST, Collections.EMPTY_LIST);
        return new SearchParam(period, timeFrom, timeTo, checkedIds);
    }

    public void putSearchParamsToPreference(BasePreference preference, @NonNull SearchParam param) {
        preference.putToPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_PERIOD, PreferenceTypeEnum.INTEGER, param.getType());
//        if (!param.getCheckedCurrencies().isEmpty())
        preference.putToPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_PICKED_CURR, PreferenceTypeEnum.INTEGER_ARRAY_LIST, param.getCheckedCurrencies());

        if (param.getType() == PERIOD_CUSTOM) {
            preference.putToPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_FROM_TIME, PreferenceTypeEnum.LONG, param.getTimeFrom());
            preference.putToPreference(PreferenceKeysEnum.KEY_SEARCH_PARAM_TO_TIME, PreferenceTypeEnum.LONG, param.getTimeTo());
        }
    }

}
