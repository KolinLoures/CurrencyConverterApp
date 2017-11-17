package com.example.kolin.currencyconverterapp.presentation.currency_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;

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

        adapter = new CurrencyRecyclerAdapter();
        presenter = new CurrencyListPresenter();
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

        presenter.bindView(CurrencyListFragment.this);

        recyclerView = view.findViewById(R.id.fragment_currency_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        pickedContainer = view.findViewById(R.id.fragment_currency_list_pick_container);
        pickedCurrencyName = view.findViewById(R.id.fragment_currency_list_picked_text_view);

        ImageButton clearPickedEntity = view.findViewById(R.id.fragment_currency_list_button_convert);
        clearPickedEntity.setOnClickListener(v -> {
            adapter.addData(presenter.getPickedEntity());
            setPickedEntity(null);
        });

        initRecyclerViewAdapterListener();

        if (savedInstanceState != null) {
            adapter.addAllData(savedInstanceState.getParcelableArrayList(KEY_ADAPTER_DATA));
            setPickedEntity(savedInstanceState.getParcelable(KEY_PICKED));
        } else {
            presenter.loadCurrencies();
        }
    }

    private void initRecyclerViewAdapterListener() {
        adapter.setListener(new CurrencyRecyclerAdapter.CurrencyRecyclerListener() {
            @Override
            public void onClickFavorite(CurrencyEntity entity, boolean check) {
                presenter.putRemoveFavoriteCurrency(entity, check);
            }

            @Override
            public void onClick(CurrencyEntity entity) {

                CurrencyEntity from = null;
                CurrencyEntity to = null;

                if (presenter.getPickedEntity() != null) {
                    from = presenter.getPickedEntity();
                    to = entity;
                } else {
                    //todo set here with one click
                }

                if (listener != null)
                    listener.onPickCurrenciesPair(from, to);
            }

            @Override
            public void onLongPressed(CurrencyEntity entity) {
                if (presenter.getPickedEntity() != null)
                    adapter.addData(presenter.getPickedEntity());
                setPickedEntity(entity);
            }
        });
    }

    private void setPickedEntity(CurrencyEntity entity) {
        presenter.setPickedEntity(entity);

        if (entity != null) {
            pickedCurrencyName.setText(entity.getName());
            show(pickedContainer, View.VISIBLE);
        } else
            show(pickedContainer, View.GONE);
    }

    private void show(View v, int visibility) {
        v.setVisibility(visibility);
    }

    @Override
    public void showSupportCurrency(CurrencyEntity currency) {
        adapter.addData(currency);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void notifyUser(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
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
        presenter.unbindView();
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
