package com.example.kolin.currencyconverterapp.presentation.history.search_param;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.example.customviewlibrary.CustomSpinner;
import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.domain.model.SearchParamsRenderer;
import com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam;
import com.example.kolin.currencyconverterapp.presentation.util.AppFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_ALL;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_CUSTOM;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_MONTH;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_WEEK;

/**
 * Fragment that render {@link SearchParamsRenderer}
 */
public class SearchParamsFragment extends MvpAppCompatFragment implements SearchParamsView {

    public static final String TAG = SearchParamsFragment.class.getSimpleName();

    @InjectPresenter
    SearchParamsPresenter presenter;

    //Views
    private RecyclerView rvCurrencies;
    private CustomSpinner spinnerPeriod;
    private ProgressBar progressBar;
    private TextView textPeriod;

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private SearchParamsRecyclerAdapter adapter;

    private int lastPickedSpinnerItem = 0;

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener callBack;

    private boolean disableSpinnner = false;

    private long timePickedDialogTo;
    private long timePickedDialogFrom;

    public SearchParamsFragment() {
    }

    public interface SearchParamsFragmentListener {
        void onPickSearchParams(@TypeSearchPeriodParam int period, List<Integer> pickedIds);

        void onPickCustonPeriodSearchPams(long timeFrom, long timeTo, List<Integer> pickedIds);
    }

    public static SearchParamsFragment newInstance() {
        return new SearchParamsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new SearchParamsRecyclerAdapter();
        adapter.setPickedListener(this::clickRecyclerAdapter);

        spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.search_period_params, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        callBack = (view, year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd) -> onDatePick(year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd);
        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(callBack, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnCancelListener(dialog -> onCancelDialog());
        datePickerDialog.setOnDismissListener(dialog -> onCancelDialog());

        presenter.loadParams();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_params_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerPeriod = view.findViewById(R.id.fragment_params_spinner_period);
        spinnerPeriod.setAdapter(spinnerAdapter);
        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!disableSpinnner)
                    clickSpinnerItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        disableSpinnner = true;
        spinnerPeriod.setSelection(lastPickedSpinnerItem);
        disableSpinnner = false;

        textPeriod = view.findViewById(R.id.fragment_params_text_period);
        setTextPeriod(spinnerPeriod.getSelectedItemPosition());

        rvCurrencies = view.findViewById(R.id.fragment_params_rv_currencies);

        int spanCount = getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE ? 2 : 4;
        rvCurrencies.setLayoutManager(new GridLayoutManager(getContext(), spanCount, GridLayoutManager.HORIZONTAL, false));
        rvCurrencies.setAdapter(adapter);

        progressBar = view.findViewById(R.id.fragment_params_progress);
        progressBar.setVisibility(View.GONE);
    }

    private void clickSpinnerItem(int position) {
        if (position != 3 && lastPickedSpinnerItem == position)
            return;

        performActionWithSpinnerPosition(position);

        if (position != 3)
            lastPickedSpinnerItem = position;
    }

    @SuppressWarnings("ConstantConditions")
    private void clickRecyclerAdapter() {
        int selectedItemPosition = spinnerPeriod.getSelectedItemPosition();
        if (selectedItemPosition != PERIOD_CUSTOM)
            performActionWithSpinnerPosition(selectedItemPosition);
        else
            getParentFragmentListener().onPickCustonPeriodSearchPams(timePickedDialogFrom, timePickedDialogTo, new ArrayList<>(adapter.getCheckedIds()));
    }

    @SuppressWarnings("ConstantConditions")
    private void performActionWithSpinnerPosition(int position) {
        switch (position) {
            case PERIOD_ALL:
                setTextPeriod(PERIOD_ALL);
                getParentFragmentListener().onPickSearchParams(PERIOD_ALL, new ArrayList<>(adapter.getCheckedIds()));
                break;
            case PERIOD_WEEK:
                setTextPeriod(PERIOD_WEEK);
                getParentFragmentListener().onPickSearchParams(
                        PERIOD_WEEK,
                        new ArrayList<>(adapter.getCheckedIds()));
                break;
            case PERIOD_MONTH:
                setTextPeriod(PERIOD_MONTH);
                getParentFragmentListener().onPickSearchParams(
                        PERIOD_MONTH,
                        new ArrayList<>(adapter.getCheckedIds()));
                break;
            case PERIOD_CUSTOM:
                openDatePickedDialog();
                break;
        }
    }

    private void setTextPeriod(int period) {
        switch (period) {
            case PERIOD_ALL:
                textPeriod.setText(spinnerAdapter.getItem(PERIOD_ALL));
                break;
            case PERIOD_WEEK:
                textPeriod.setText(spinnerAdapter.getItem(PERIOD_WEEK));

                break;
            case PERIOD_MONTH:
                textPeriod.setText(spinnerAdapter.getItem(PERIOD_MONTH));

                break;
            case PERIOD_CUSTOM:
                textPeriod.setText(AppFormatter.formateDateRange(SimpleDateFormat.getDateInstance(), timePickedDialogFrom, timePickedDialogTo, " - "));
                break;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void onDatePick(int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYear1, int dayOfMonth1) {
        lastPickedSpinnerItem = spinnerPeriod.getSelectedItemPosition();

        Date first = AppFormatter.getDate(year, monthOfYear, dayOfMonth);
        Date second = AppFormatter.getDate(yearEnd, monthOfYear1, dayOfMonth1);

        Date temp;

        if (first.after(second)) {
            temp = first;
            first = second;
            second = temp;
        }

//        else {
//            temp = second;
//            second = first;
//            first = temp;
//        }

        timePickedDialogFrom = first.getTime();
        timePickedDialogTo = second.getTime();


        setTextPeriod(PERIOD_CUSTOM);

        getParentFragmentListener().onPickCustonPeriodSearchPams(timePickedDialogFrom, timePickedDialogTo, new ArrayList<>(adapter.getCheckedIds()));
    }

    private void onCancelDialog() {
        disableSpinnner = true;
        spinnerPeriod.setSelection(lastPickedSpinnerItem);
        disableSpinnner = false;
    }

    private void openDatePickedDialog() {
        if (datePickerDialog != null)
            datePickerDialog.show(getActivity().getFragmentManager(), "asd");
    }

    @Override
    public void renderSearchPramsView(SearchParamsRenderer renderer) {
        if (renderer.isLoading()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        if (renderer.getError() != null) {
            progressBar.setVisibility(View.GONE);
        }

        if (renderer.getData() != null && renderer.getSearchParam() != null) {
            progressBar.setVisibility(View.GONE);
            adapter.setCheckedIds(renderer.getSearchParam().getCheckedCurrencies());
            adapter.addAll(renderer.getData());
            lastPickedSpinnerItem = renderer.getSearchParam().getType();

            if (lastPickedSpinnerItem != PERIOD_CUSTOM)
                setTextPeriod(lastPickedSpinnerItem);
            else {
                timePickedDialogFrom = renderer.getSearchParam().getTimeFrom();
                timePickedDialogTo = renderer.getSearchParam().getTimeTo();
                setTextPeriod(PERIOD_CUSTOM);
            }

            disableSpinnner = true;
            spinnerPeriod.setSelection(lastPickedSpinnerItem);
            disableSpinnner = false;
        }
    }

    @Override
    public void onDestroyView() {
        //help gc

        spinnerPeriod = null;
        rvCurrencies = null;
        progressBar = null;

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        datePickerDialog = null;
        super.onDestroy();
    }

    private SearchParamsFragmentListener getParentFragmentListener() {
        Fragment parentFragment = getParentFragment();
        return parentFragment instanceof SearchParamsFragmentListener ? (SearchParamsFragmentListener) parentFragment : null;
    }
}
