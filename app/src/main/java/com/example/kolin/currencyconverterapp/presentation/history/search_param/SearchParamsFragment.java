package com.example.kolin.currencyconverterapp.presentation.history.search_param;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.domain.model.SearchParamsRenderer;
import com.example.kolin.currencyconverterapp.presentation.util.AppFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_ALL;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_CUSTOM;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_MONTH;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_WEEK;


public class SearchParamsFragment extends Fragment implements SearchParamsView {

    public static final String TAG = SearchParamsFragment.class.getSimpleName();

    //Views
    private RecyclerView rvCurrencies;
    private Spinner spinnerPeriod;
    private ProgressBar progressBar;

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private SearchParamsRecyclerAdapter adapter;

    private int lastPickedSpinnerItem = 0;

    private SearchParamsPresenter presenter;

    public SearchParamsFragment() {
    }

    public interface SearchParamsFragmentListener {
        void onPickSearchParams(long timeFrom, long timeTo, List<Integer> pickedIds);
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

        presenter = new SearchParamsPresenter();
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
//        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                clickSpinnerItem(position);
//            }
//            public void onNothingSelected(AdapterView<?> parent) {}
//        });
        spinnerPeriod.setAdapter(spinnerAdapter);

        rvCurrencies = view.findViewById(R.id.fragment_params_rv_currencies);
        rvCurrencies.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.HORIZONTAL, false));
        rvCurrencies.setAdapter(adapter);

        progressBar = view.findViewById(R.id.fragment_params_progress);
        progressBar.setVisibility(View.GONE);

        presenter.bindView(this);
        if (adapter.getItemCount() == 0)
            presenter.loadRateParams();
    }

    private void clickSpinnerItem(int position) {
        if (lastPickedSpinnerItem == position)
            return;

        performActionWithSpinnerPosition(position);
        lastPickedSpinnerItem = position;
    }

    private void clickRecyclerAdapter() {
        performActionWithSpinnerPosition(spinnerPeriod.getSelectedItemPosition());
    }

    private void performActionWithSpinnerPosition(int position) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();

        switch (position) {
            case PERIOD_ALL:
                getParentFragmentListener().onPickSearchParams(-1, -1, new ArrayList<>(adapter.getCheckedIds()));
                break;
            case PERIOD_WEEK:
                getParentFragmentListener().onPickSearchParams(
                        AppFormatter.fromateWeekCloseDatePeriod(getContext(), timeInMillis),
                        timeInMillis,
                        new ArrayList<>(adapter.getCheckedIds()));
                break;
            case PERIOD_MONTH:
                getParentFragmentListener().onPickSearchParams(
                        AppFormatter.fromateMonthCloseDatePeriod(getContext(), timeInMillis),
                        timeInMillis,
                        new ArrayList<>(adapter.getCheckedIds()));
                break;
            case PERIOD_CUSTOM:

                break;
        }
    }

    @Override
    public void renderSearchPramsView(SearchParamsRenderer renderer) {
        if (renderer.isLoading()){
            progressBar.setVisibility(View.VISIBLE);
        }

        if (renderer.getError() != null){
            progressBar.setVisibility(View.GONE);
        }

        if (renderer.getData() != null && renderer.getSearchParam() != null){
            progressBar.setVisibility(View.GONE);
            adapter.setCheckedIds(renderer.getSearchParam().getCheckedCurrencies());
            adapter.addAll(renderer.getData());
            spinnerPeriod.setSelection(renderer.getSearchParam().getType());
        }
    }

    private SearchParamsFragmentListener getParentFragmentListener() {
        Fragment parentFragment = getParentFragment();
        return parentFragment instanceof SearchParamsFragmentListener ? (SearchParamsFragmentListener) parentFragment : null;
    }
}
