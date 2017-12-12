package com.example.kolin.currencyconverterapp.presentation.chart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.customviewlibrary.CustomLoadingToolbar;
import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.model.ChartParam;
import com.example.kolin.currencyconverterapp.data.model.RatePojo;
import com.example.kolin.currencyconverterapp.domain.model.ChartParamRenderer;
import com.example.kolin.currencyconverterapp.domain.model.ChartRenderer;
import com.example.kolin.currencyconverterapp.presentation.common.CustomXAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment implements ChartView, PickDialogFragment.PickDialogFragmentListener {

    public static final String TAG = ChartFragment.class.getSimpleName();

    private Button btnCurrFrom;
    private Button btnCurrTo;
    private Spinner spinnerPeriod;
    private LineChart chart;

    private ArrayAdapter spinnerChartPeriodAdapter;

    private ChartPresenter presenter;
    private CustomLoadingToolbar toolbar;

    private boolean blockSpinner = false;

    public ChartFragment() {
        // Required empty public constructor
    }

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ChartPresenter();
        presenter.bindView(this);

        spinnerChartPeriodAdapter = ArrayAdapter.createFromResource(getContext(), R.array.chart_period, R.layout.spinner_item);
        spinnerChartPeriodAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.fragment_chart_toolbar);
        toolbar.getToolbar().setTitle(R.string.analytics);

        btnCurrFrom = view.findViewById(R.id.fragment_chart_button_from);
        btnCurrFrom.setEnabled(false);
        btnCurrFrom.setOnClickListener(this::performClick);

        btnCurrTo = view.findViewById(R.id.fragment_chart_button_to);
        btnCurrTo.setEnabled(false);
        btnCurrTo.setOnClickListener(this::performClick);

        spinnerPeriod = view.findViewById(R.id.fragment_chart_spinner_chart_period);
        spinnerPeriod.setAdapter(spinnerChartPeriodAdapter);
        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!blockSpinner)
                    presenter.loadChartDataWithParams(presenter.getCurrFrom(), presenter.getCurrTo(), position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        chart = view.findViewById(R.id.fragment_chart_line_chart);
        chart.getDescription().setEnabled(false);

        if (!presenter.checkCurrentParams())
            presenter.loadChartParams();
        else {
            renderChartParamLoadedView(presenter.getCurrFrom(), presenter.getCurrTo(), presenter.getPeriod());
        }

        presenter.loadDefaultChartData();
    }

    private void performClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_chart_button_from:
                showDialogCurrencyPicker(presenter.getCurrNames(), presenter.getCurrFrom(), false);
                break;
            case R.id.fragment_chart_button_to:
                showDialogCurrencyPicker(presenter.getCurrNames(), presenter.getCurrTo(), true);
                break;
        }
    }

    @Override
    public void renderChartParamsView(ChartParamRenderer renderer) {
        if (renderer.isLoading()) {
            toolbar.showProgressBar();
            btnCurrFrom.setEnabled(false);
            btnCurrTo.setEnabled(false);
            spinnerPeriod.setEnabled(false);
        }

        if (renderer.getError() != null) {
            toolbar.hideProgressBar();

        }

        if (renderer.getData() != null) {
            toolbar.hideProgressBar();

            String currFrom = renderer.getChartParam().getCurrFrom();
            String currTo = renderer.getChartParam().getCurrTo();
            int period = renderer.getChartParam().getPeriod();

            renderChartParamLoadedView(currFrom, currTo, period);
        }
    }

    private void renderChartParamLoadedView(String textBtnFrom, String textBtnTo, int spinnerPeriod) {
        btnCurrFrom.setEnabled(true);
        btnCurrTo.setEnabled(true);
        this.spinnerPeriod.setEnabled(true);
        btnCurrFrom.setText(textBtnFrom);
        btnCurrTo.setText(textBtnTo);
        blockSpinner = true;
        this.spinnerPeriod.setSelection(spinnerPeriod);
        blockSpinner = false;
    }


    @Override
    public void renderChart(ChartRenderer renderer) {
        if (renderer.isLoading()) {
            toolbar.showProgressBar();
            chart.clear();
            chart.setNoDataText(getString(R.string.loading));
        }

        if (renderer.getError() != null) {
            toolbar.hideProgressBar();
            chart.clear();
            chart.setNoDataText(getString(R.string.error));
        }

        if (renderer.getData() != null) {
            toolbar.hideProgressBar();
            renderChartView(renderer.getData(), renderer.getChartParam());
        }
    }

    private void renderChartView(List<RatePojo> rate, ChartParam chartParam) {
        List<Entry> entries = new ArrayList<>();
        List<String> xLabels = new ArrayList<>();

        for (int i = 0; i < rate.size(); i++) {
            xLabels.add(rate.get(i).getDate());
            entries.add(new Entry(i, rate.get(i).getRate()));
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new CustomXAxisValueFormatter(xLabels));


        LineDataSet lineDataSet = new LineDataSet(entries, chartParam.getCurrFrom() + " / " + chartParam.getCurrTo());
        lineDataSet.setColor(R.color.colorAccent);
        LineData lineData = new LineData(lineDataSet);

        chart.setData(lineData);
        chart.invalidate();
    }

    private void showDialogCurrencyPicker(List<String> currencies, String curr, boolean reverse) {
        ArrayList<String> currNames = new ArrayList<>(currencies);

        if (!reverse)
            currNames.remove(presenter.getCurrTo());
        else
            currNames.remove(presenter.getCurrFrom());

        PickDialogFragment pickDialogFragment = PickDialogFragment.newInstance(currNames, curr, reverse);
        pickDialogFragment.show(getChildFragmentManager(), PickDialogFragment.TAG);
    }

    @Override
    public void onClickOkButton(String pickedCurrency, boolean reverse) {
        if (!reverse) {
            btnCurrFrom.setText(pickedCurrency);
            presenter.loadChartDataWithParams(pickedCurrency, presenter.getCurrTo(), spinnerPeriod.getSelectedItemPosition());
        } else {
            btnCurrTo.setText(pickedCurrency);
            presenter.loadChartDataWithParams(presenter.getCurrFrom(), pickedCurrency, spinnerPeriod.getSelectedItemPosition());
        }
    }

    @Override
    public void onDestroyView() {
        presenter.clearDisposables();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }


}
