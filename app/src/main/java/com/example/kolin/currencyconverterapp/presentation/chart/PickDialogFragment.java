package com.example.kolin.currencyconverterapp.presentation.chart;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.kolin.currencyconverterapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickDialogFragment extends DialogFragment {

    public static final String TAG = PickDialogFragment.class.getSimpleName();

    public static final String ARG_LIST_CURR = "arg_currencies_list";
    public static final String ARG_CURR = "currency_name_picked_from";
    public static final String ARG_REVERSE = "currency_name_picked_to";

    private RecyclerView recyclerView;
    private Button btnOk;
    private Button btnCancel;

    private String currentPickedCurrency;
    private boolean reverse;
    private ArrayList<String> currNames;

    private PickCurrencyRecyclerAdapter adapter;

    public interface PickDialogFragmentListener {
        void onClickOkButton(String pickedCurrency, boolean reverse);
    }

    public PickDialogFragment() {
        // Required empty public constructor
    }

    public static PickDialogFragment newInstance(ArrayList<String> currencies, String lastCheckedCurr, boolean reverse) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_LIST_CURR, currencies);
        args.putString(ARG_CURR, lastCheckedCurr);
        args.putBoolean(ARG_REVERSE, reverse);

        PickDialogFragment fragment = new PickDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new PickCurrencyRecyclerAdapter();
        adapter.setListener(pickedCurr -> currentPickedCurrency = pickedCurr);

        if (getArguments() != null) {
            currNames = getArguments().getStringArrayList(ARG_LIST_CURR);
            currentPickedCurrency = getArguments().getString(ARG_CURR);
            reverse = getArguments().getBoolean(ARG_REVERSE);

            adapter.setChecked(currentPickedCurrency);
            adapter.addAll(currNames);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_pick, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnCancel = view.findViewById(R.id.fragment_pick_dialog_btn_cancel);
        btnCancel.setOnClickListener(this::performClick);

        btnOk = view.findViewById(R.id.fragment_pick_dialog_btn_ok);
        btnOk.setOnClickListener(this::performClick);


        recyclerView = view.findViewById(R.id.fragment_pick_dialog_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayout.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        //help gc
        recyclerView = null;
        btnOk = null;
        btnCancel = null;

        super.onDestroyView();
    }

    private void performClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_pick_dialog_btn_cancel:
                this.dismiss();
                break;
            case R.id.fragment_pick_dialog_btn_ok:
                if (getParentFragment() instanceof PickDialogFragmentListener){
                    ((PickDialogFragmentListener) getParentFragment()).onClickOkButton(currentPickedCurrency, reverse);
                    dismiss();
                }
                break;
        }
    }
}
