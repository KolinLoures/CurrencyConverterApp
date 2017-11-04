package com.example.kolin.currencyconverterapp.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;


public class ConverterFragment extends Fragment implements ConverterView {

    public static final String TAG = ConverterFragment.class.getSimpleName();

    private CurrencyRecyclerAdapter adapter;
    private ConverterPresenter presenter;

    public ConverterFragment() {
        // Required empty public constructor
    }

    public static ConverterFragment newInstance() {
        return new ConverterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CurrencyRecyclerAdapter();
        presenter = new ConverterPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_converter_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        presenter.bindView(ConverterFragment.this);
        presenter.loadCurrencies();
    }

    @Override
    public void showSupportCurrency(CurrencyEntity currency) {
        adapter.addData(currency);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {

        presenter.unbindView();

        super.onDetach();
    }
}
