package com.example.kolin.currencyconverterapp.presentation.converter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.customviewlibrary.CustomEditText;
import com.example.customviewlibrary.CustomLoadingToolbar;
import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.model.RatePojo;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.domain.model.ConverterRateRender;
import com.example.kolin.currencyconverterapp.presentation.util.AppFormatter;


public class ConverterFragment extends Fragment implements ConverterView {

    public static final String TAG = ConverterFragment.class.getSimpleName();

    private static final String ARG_FROM = "param_currency_from";
    private static final String ARG_TO = "param_currency_to";

    private ConverterPresenter presenter;

    private TextView textFrom;
    private TextView textTo;
    private CustomEditText editFrom;
    private CustomEditText editTo;
    private CustomLoadingToolbar toolbar;
    private TextView textInformation;
    private ImageButton imgBtnInformationAction;

    private TextWatcher textWatcherFrom;
    private TextWatcher textWatcherTo;

    private boolean blockTextFromWatcher = false;
    private boolean blockTextToWatcher = false;

    private ConverterFragmentListener listener;

    public ConverterFragment() {
    }

    public interface ConverterFragmentListener{
        void onClickBack();
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

        presenter.bindView(ConverterFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @SuppressLint("CutPasteId")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        toolbar = view.findViewById(R.id.fragment_converter_toolbar);
        toolbar.getToolbar().setTitle(R.string.converter);
        toolbar.getToolbar().setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.getToolbar().setNavigationOnClickListener(this::performClick);
        toolbar.hideProgressBar();

        textFrom = view.findViewById(R.id.fragment_converter_input_view_from).findViewById(R.id.input_currency_text_name);
        textTo = view.findViewById(R.id.fragment_converter_input_view_to).findViewById(R.id.input_currency_text_name);

        editFrom = view.findViewById(R.id.fragment_converter_input_view_from).findViewById(R.id.input_currency_edit_amount);
        editFrom.setText("1");

        editTo = view.findViewById(R.id.fragment_converter_input_view_to).findViewById(R.id.input_currency_edit_amount);

        textInformation = view.findViewById(R.id.information_text);
        imgBtnInformationAction = view.findViewById(R.id.information_action);
        imgBtnInformationAction.setOnClickListener(this::performClick);


        textFrom.setText(presenter.getFrom().getName());
        textTo.setText(presenter.getTo().getName());

        presenter.loadRate(1, false);

        initEditText();
    }

    private void initEditText() {
        textWatcherFrom = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!blockTextFromWatcher & !s.toString().isEmpty())
                    presenter.loadRate(validateFloat(s.toString()), false);

                if (s.toString().isEmpty()) {
                    blockTextToWatcher = true;
                    editTo.setText("0");
                    blockTextToWatcher = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        textWatcherTo = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!blockTextToWatcher & !s.toString().isEmpty())
                    presenter.loadRate(validateFloat(s.toString()), true);

                if (s.toString().isEmpty()) {
                    blockTextFromWatcher = true;
                    editFrom.setText("0");
                    blockTextFromWatcher = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        editFrom.setListener(() -> editFrom.clearFocus());
        editTo.setListener(() -> editTo.clearFocus());

        TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.clearFocus();
            }
            return false;
        };

        editFrom.setOnEditorActionListener(onEditorActionListener);
        editTo.setOnEditorActionListener(onEditorActionListener);

        editFrom.addTextChangedListener(textWatcherFrom);
        editTo.addTextChangedListener(textWatcherTo);
    }

    private void performClick(View v) {
        switch (v.getId()){
            case R.id.fragment_converter_toolbar:
                if (listener != null)
                    listener.onClickBack();
                break;
            case R.id.information_action:
                onErrorRepeat();
                break;
        }
    }

    public void blockInputFrom(boolean b) {
        blockTextFromWatcher = b;
    }

    public void blockInputTo(boolean b) {
        blockTextToWatcher = b;
    }

    private float validateFloat(String s) {
        return Float.parseFloat(s);
    }

    private void onErrorRepeat() {
        String s = editFrom.getText().toString();
        if (!s.isEmpty())
            presenter.loadRate(validateFloat(s), false);
        else {
            blockInputFrom(true);
            editFrom.setText("1");
            blockInputFrom(false);

            presenter.loadRate(1, false);
        }
    }

    @Override
    public void renderRateView(ConverterRateRender render) {
        if (render.isLoading()) {
            toolbar.showProgressBar();
//            initEmptyRateView();
        }

        if (render.getError() != null) {
            toolbar.hideProgressBar();
            initErrorRateView();
        }

        RatePojo data = null;
        if ((data = render.getData()) != null) {
            toolbar.hideProgressBar();

            if (data.isFromCache() && render.isRateExpired()) {
                initAttentitionRateView(1, data.getRate());
            } else
                initNormalRateView(1, data.getRate());

            if (!render.isReverse()) {
                blockInputTo(true);
                editTo.setText(AppFormatter.formatRate(render.getResult()));
                blockInputTo(false);
            } else {
                blockInputFrom(true);
                editFrom.setText(AppFormatter.formatRate(render.getResult()));
                blockInputFrom(false);
            }

        }
    }

    private void initErrorRateView() {
        textInformation.setText(R.string.problems_with_connection);
        imgBtnInformationAction.setVisibility(View.VISIBLE);
    }

    private void initEmptyRateView() {
        textInformation.setText("");
        imgBtnInformationAction.setVisibility(View.INVISIBLE);
    }

    private void initAttentitionRateView(float from, float to) {
        String text = String.format("%s %s",
                AppFormatter.formatRate(from, to, presenter.getFrom().getName(), presenter.getTo().getName(), " = "),
                getString(R.string.rate_from_cache));

        textInformation.setText(text);
        imgBtnInformationAction.setVisibility(View.INVISIBLE);
    }

    private void initNormalRateView(float from, float to) {
        imgBtnInformationAction.setVisibility(View.INVISIBLE);
        textInformation.setText(AppFormatter.formatRate(from, to, presenter.getFrom().getName(), presenter.getTo().getName(), " = "));
    }


    @Override
    public void onDestroyView() {
        //help gc

        editTo.removeTextChangedListener(textWatcherTo);
        editFrom.removeTextChangedListener(textWatcherFrom);

        textInformation = null;
        imgBtnInformationAction = null;
        editTo = null;
        editFrom = null;
        textFrom = null;
        textTo = null;
        toolbar = null;

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
        if (context instanceof ConverterFragmentListener)
            listener = (ConverterFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement ConverterFragmentListener!");
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}
