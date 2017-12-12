package com.example.kolin.currencyconverterapp.presentation.common;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Custom label for x axis (Line Chart)
 */

public class CustomXAxisValueFormatter implements IAxisValueFormatter {

    private List<String> values;

    public CustomXAxisValueFormatter(List<String> values) {
        this.values = values;
        makeAfterOneList();
    }

    private void makeAfterOneList() {
        int k = 1;
        for (int i = 1; i < values.size(); i++){
            if (k != 2)
                values.set(i, " ");
            else
                k = 0;
            k++;
        }
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return values.get((int) value);
    }
}
