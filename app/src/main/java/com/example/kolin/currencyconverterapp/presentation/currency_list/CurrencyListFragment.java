package com.example.kolin.currencyconverterapp.presentation.currency_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.customviewlibrary.CustomLoadingToolbar;
import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.model.CurrencyListRenderer;
import com.example.kolin.currencyconverterapp.presentation.common.SpaceRecyclerDividerItem;

import java.util.ArrayList;


public class CurrencyListFragment extends Fragment implements CurrencyListView {

    public static final String TAG = CurrencyListFragment.class.getSimpleName();

    private static final String KEY_PICKED = "PICKED";
    private static final String KEY_ADAPTER_DATA = "ADAPTER_DATA";

    private CurrencyRecyclerAdapter adapter;
    private CurrencyListPresenter presenter;

    private RecyclerView recyclerView;
    private FrameLayout pickedContainer;
    private TextView pickedCurrencyName;
    private CustomLoadingToolbar toolbar;
    private ImageButton imgBtnClear;
    private View error;
    private TextView textError;
    private ImageButton imgBtnError;

    private CurrencyListFragmentListener listener;

    public CurrencyListFragment() {
        // Required empty public constructor
    }

    public static CurrencyListFragment newInstance() {
        return new CurrencyListFragment();
    }

    public interface CurrencyListFragmentListener {
        void onPickCurrenciesPair(CurrencyEntity from, CurrencyEntity to);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new CurrencyListPresenter();
        presenter.bindView(CurrencyListFragment.this);

        adapter = new CurrencyRecyclerAdapter();
        adapter.setClickItemListener(this::clickAdapterItem);
        adapter.setLongPressItemListener(this::longPressAdapterItem);
        adapter.setCheckFavoriteListener(this::checkAdapterItem);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.fragment_currency_list_toolbar);
        toolbar.getToolbar().setTitle(R.string.currencies);
        toolbar.getToolbar().setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.hideProgressBar();

        recyclerView = view.findViewById(R.id.fragment_currency_list_recycler_view);
        recyclerView.addItemDecoration(new SpaceRecyclerDividerItem(12));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        pickedContainer = view.findViewById(R.id.fragment_currency_list_pick_container);
        pickedCurrencyName = view.findViewById(R.id.fragment_currency_list_picked_text_view);

        imgBtnClear = view.findViewById(R.id.fragment_currency_list_button_convert);
        imgBtnClear.setOnClickListener(this::clickClearPicked);

        error = view.findViewById(R.id.fragment_currency_error);

        textError = view.findViewById(R.id.error_text);
        textError.setText(R.string.problems_with_connection);

        imgBtnError = view.findViewById(R.id.error_action);
        imgBtnError.setOnClickListener(v -> presenter.loadCurrencies());

        if (savedInstanceState != null) {
            adapter.addAllData(savedInstanceState.getParcelableArrayList(KEY_ADAPTER_DATA));
            setPickedEntity(savedInstanceState.getParcelable(KEY_PICKED));
        } else {
            if (adapter.getItemCount() == 0)
                presenter.loadCurrencies();

            setPickedEntity(presenter.getPickedEntity());
        }
    }

    @Override
    public void renderListView(CurrencyListRenderer currencyListRenderer) {
        if (currencyListRenderer.isLoading()) {
            toolbar.showProgressBar();
            show(error, View.GONE);
        }

        if (currencyListRenderer.getError() != null) {
            toolbar.hideProgressBar();
            show(error, View.VISIBLE);
        }

        if (currencyListRenderer.getData() != null) {
            show(error, View.GONE);
            toolbar.hideProgressBar();
            adapter.addAllData(currencyListRenderer.getData());
        }

    }

    private void clickClearPicked(View v){
        setPickedEntity(null);
    }

    private void setPickedEntity(CurrencyEntity entity) {
        if (presenter.getPickedEntity() != null)
            adapter.addData(presenter.getPickedEntity());

        if (entity != null) {
            pickedCurrencyName.setText(entity.getName());
            show(pickedContainer, View.VISIBLE);
        } else
            show(pickedContainer, View.GONE);

        presenter.setPickedEntity(entity);
    }

    private void clickAdapterItem(CurrencyEntity entity) {

        CurrencyEntity from = presenter.getPickedEntity() != null ? presenter.getPickedEntity() : entity;
        CurrencyEntity to = presenter.getPickedEntity() != null ? entity : adapter.getFavoriteAfter(entity);

        if (listener != null)
            listener.onPickCurrenciesPair(from, to);
    }

    private void longPressAdapterItem(CurrencyEntity entity){
        setPickedEntity(entity);
    }

    private void checkAdapterItem(CurrencyEntity entity, boolean check){
        presenter.putRemoveFavoriteCurrency(entity, check);
    }

    private void show(View v, int visibility) {
        v.setVisibility(visibility);
    }

    @Override
    public void onDestroyView() {
        //help gc
        recyclerView = null;
        imgBtnClear = null;
        pickedCurrencyName = null;
        toolbar = null;
        pickedContainer = null;

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CurrencyListFragmentListener)
            listener = (CurrencyListFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement CurrencyListFragmentListener");
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_ADAPTER_DATA, new ArrayList<>(adapter.getData()));
        outState.putParcelable(KEY_PICKED, presenter.getPickedEntity());

        super.onSaveInstanceState(outState);
    }
}
