package com.example.kolin.currencyconverterapp.domain.common;

import android.support.annotation.NonNull;

import com.example.kolin.currencyconverterapp.data.model.ChartParam;
import com.example.kolin.currencyconverterapp.data.preference.BasePreference;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceKeysEnum;
import com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to get from and put {@link ChartParam} object
 */

public class ChartParamsPreference {

    /**
     * Get {@link ChartParam} from {@link BasePreference}
     *
     * @param preference {@link BasePreference} object
     * @return {@link ChartParam} object
     */
    @SuppressWarnings("unchecked")
    public ChartParam getChartParamsFromPreference(BasePreference preference) {
        ChartParam chartParam = new ChartParam();

        int period = (int) preference.getFromPreference(PreferenceKeysEnum.KEY_CHART_PARAM_PERIOD, PreferenceTypeEnum.INTEGER, 0);
        chartParam.setPeriod(period);
        List<String> pickedCurr = (List<String>) preference.getFromPreference(PreferenceKeysEnum.KEY_CHART_PARAM_PICKED_CURR, PreferenceTypeEnum.STRING_ARRAY_LIST, Collections.EMPTY_LIST);

        if (!pickedCurr.isEmpty()) {
            String currFrom = pickedCurr.get(0).isEmpty() ? null : pickedCurr.get(0);
            String currTo = pickedCurr.get(1).isEmpty() ? null : pickedCurr.get(1);
            chartParam.setCurrFrom(currFrom);
            chartParam.setCurrTo(currTo);
        }

        return chartParam;
    }

    /**
     * Put {@link ChartParam} to {@link BasePreference}
     *
     * @param preference {@link BasePreference} object
     * @param param {@link ChartParam} object
     */
    public void putChartParamsFromPreference(BasePreference preference, @NonNull ChartParam param) {
        preference.putToPreference(PreferenceKeysEnum.KEY_CHART_PARAM_PERIOD, PreferenceTypeEnum.INTEGER, param.getPeriod());
        ArrayList<String> valuePicked = new ArrayList<>();
        valuePicked.add(0, param.getCurrFrom());
        valuePicked.add(1, param.getCurrTo());
        preference.putToPreference(PreferenceKeysEnum.KEY_CHART_PARAM_PICKED_CURR, PreferenceTypeEnum.STRING_ARRAY_LIST, valuePicked);
    }

}
