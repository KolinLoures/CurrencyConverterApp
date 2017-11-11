package com.example.kolin.currencyconverterapp.presentation.converter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.customviewlibrary.CustomEditText;
import com.example.customviewlibrary.CustomLoadingToolbar;
import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;

import java.util.Locale;


public class ConverterFragment extends Fragment implements ConverterView {

    public static final String TAG = ConverterFragment.class.getSimpleName();

    private static final String ARG_FROM = "param_from";
    private static final String ARG_TO = "param_to";

    private ConverterPresenter presenter;

    private TextView textRate;
    private TextView textFrom;
    private TextView textTo;
    private CustomEditText editFrom;
    private CustomEditText editTo;
    private Snackbar errorSnackBar;
    private Snackbar attentionSnackBar;


    private TextWatcher textWatcherFrom;
    private TextWatcher textWatcherTo;

    private boolean blockTextWatcher = false;
    private CustomLoadingToolbar customLoadingToolbar;

    public ConverterFragment() {
    }

    /**
     * @param from currency from
     * @param to   currency from
     */
    public static ConverterFragment newInstance(CurrencyEntity from, CurrencyEntity to) {
        ConverterFragment fragment = new ConverterFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FROM, from);
        args.putParcelable(ARG_TO, to);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            CurrencyEntity from = getArguments().getParcelable(ARG_FROM);
            CurrencyEntity to = getArguments().getParcelable(ARG_TO);

            presenter = new ConverterPresenter();
            presenter.setCurrencies(from, to);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        customLoadingToolbar = view.findViewById(R.id.my_toolbar);
        customLoadingToolbar.getToolbar().setTitle(R.string.converter);
        customLoadingToolbar.showProgressBar();

        textRate = view.findViewById(R.id.fragment_converter_currency_rate);
        textFrom = view.findViewById(R.id.fragment_converter_currency_name_from);
        textTo = view.findViewById(R.id.fragment_converter_currency_name_to);
        editFrom = view.findViewById(R.id.fragment_converter_amount_from);
        editTo = view.findViewById(R.id.fragment_converter_amount_to);
        errorSnackBar = Snackbar
                .make(view, R.string.rate_error, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, v -> presenter.loadRate());

        attentionSnackBar = Snackbar
                .make(view, R.string.rate_from_cache, BaseTransientBottomBar.LENGTH_INDEFINITE);


        textFrom.setText(presenter.getFrom().getName());
        textTo.setText(presenter.getTo().getName());

        presenter.bindView(ConverterFragment.this);
        presenter.loadRate();

        initEditText();
    }

    private void initEditText() {
        textWatcherFrom = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!blockTextWatcher & !s.toString().isEmpty())
                    presenter.direct(validateFloat(s.toString()));

                if (s.toString().isEmpty()) {
                    blockTextWatcher = true;
                    editTo.setText("0");
                    blockTextWatcher = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        textWatcherTo = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!blockTextWatcher & !s.toString().isEmpty())
                    presenter.indirect(validateFloat(s.toString()));

                if (s.toString().isEmpty()) {
                    blockTextWatcher = true;
                    editFrom.setText("0");
                    blockTextWatcher = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editFrom.setListener(() -> editFrom.clearFocus());
        editTo.setListener(() -> editTo.clearFocus());

        TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                editFrom.clearFocus();
                editTo.clearFocus();
            }
            return false;
        };

        editFrom.setOnEditorActionListener(onEditorActionListener);
        editTo.setOnEditorActionListener(onEditorActionListener);

        editFrom.addTextChangedListener(textWatcherFrom);
        editTo.addTextChangedListener(textWatcherTo);
    }

    @Override
    public void showRate(float from, float to) {
        textRate.setText(
                String.format("%s %s = %s %s",
                        formatFloat(from),
                        presenter.getFrom().getName(),
                        formatFloat(to),
                        presenter.getTo().getName()
                )
        );
    }

    @Override
    public void setRateFrom(float from) {
        editFrom.setText(formatFloat(from));
    }

    @Override
    public void setRateTo(float to) {
        editTo.setText(formatFloat(to));
    }

    @Override
    public void blockInput(boolean b) {
        blockTextWatcher = b;
    }

    @Override
    public void showLoading(boolean show) {
        if (show)
            customLoadingToolbar.showProgressBar();
        else
            customLoadingToolbar.hideProgressBar();
    }

    @Override
    public void showError(boolean show) {
        if (show)
            errorSnackBar.show();
        else if (errorSnackBar.isShown())
            errorSnackBar.dismiss();
    }

    @Override
    public void showAttention(boolean show) {
        if (show)
            attentionSnackBar.show();
        else if (attentionSnackBar.isShown())
            attentionSnackBar.dismiss();
    }

    private String formatFloat(float f) {
        return String.format(Locale.getDefault(), "%.2f", f);
    }

    private float validateFloat(String s) {
        return Float.parseFloat(s);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        editTo.removeTextChangedListener(textWatcherTo);
        editFrom.removeTextChangedListener(textWatcherFrom);

        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
