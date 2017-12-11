package com.example.kolin.currencyconverterapp.presentation.history;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.customviewlibrary.CustomDropButton;
import com.example.customviewlibrary.CustomLoadingToolbar;
import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.domain.model.HistoryRenderer;
import com.example.kolin.currencyconverterapp.presentation.common.SpaceRecyclerDividerItem;
import com.example.kolin.currencyconverterapp.presentation.history.search_param.SearchParamsFragment;

import java.util.List;

public class HistoryFragment extends Fragment implements HistoryView, SearchParamsFragment.SearchParamsFragmentListener {

    public static final String TAG = HistoryFragment.class.getCanonicalName();

    private RecyclerView recyclerView;
    private CustomDropButton searchParamsButton;
    private FrameLayout childContainer;
    private CustomLoadingToolbar toolbar;
    private TextView emptyText;

    private HistoryRecyclerAdapter adapter;

    private HistoryPresenter presenter;

    private AnimatorSet backgroundFadeIn;
    private AnimatorSet backgroundFadeOut;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new HistoryRecyclerAdapter(getResources().getStringArray(R.array.search_period_params));
        presenter = new HistoryPresenter();
        presenter.bindView(this);

        Log.i(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated: ");

        emptyText = view.findViewById(R.id.fragment_history_empty_text);

        toolbar = view.findViewById(R.id.fragment_history_toolbar);
        toolbar.getToolbar().setTitle(R.string.history);
        toolbar.hideProgressBar();

        searchParamsButton = view.findViewById(R.id.fragment_history_search_params_button);
        searchParamsButton.setOnClickListener(this::clickPerform);

        childContainer = view.findViewById(R.id.fragment_history_container);
        childContainer.setOnClickListener(this::clickPerform);

        backgroundFadeIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.black_in);
        backgroundFadeIn.setTarget(childContainer);
        backgroundFadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                childContainer.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });

        backgroundFadeOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.black_out);
        backgroundFadeOut.setTarget(childContainer);
        backgroundFadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                childContainer.setVisibility(View.GONE);
                super.onAnimationEnd(animation);
            }
        });

        recyclerView = view.findViewById(R.id.fragment_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceRecyclerDividerItem(12));
        recyclerView.setAdapter(adapter);

        presenter.loadHistory();
    }

    private void clickPerform(View v) {
        switch (v.getId()) {
            case R.id.fragment_history_search_params_button:
                clickSearchParamsBtn();
                break;
            case R.id.fragment_history_container:
                searchParamsButton.performClick();
                break;
        }
    }

    private void clickSearchParamsBtn() {
        Fragment frg = getFragmentInManager(SearchParamsFragment.TAG) != null
                        ? getFragmentInManager(SearchParamsFragment.TAG)
                        : SearchParamsFragment.newInstance();


        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.popBackStack();

        FragmentTransaction fragmentTransaction = childFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.animator.fragment_slide_down, R.animator.fragment_slide_top, R.animator.fragment_slide_down, R.animator.fragment_slide_top);

        if (!searchParamsButton.isDropDown()) {
            fragmentTransaction = fragmentTransaction.replace(R.id.fragment_history_container, frg, SearchParamsFragment.TAG);
            fadeInContainerBackground();
        } else {
            fragmentTransaction = fragmentTransaction.remove(frg).addToBackStack(null);
            fadeOutContainerBackground();
        }

        fragmentTransaction.commit();
    }

    @Override
    public void renderHistoryView(HistoryRenderer renderer) {
        if (renderer.isLoading()){
            toolbar.showProgressBar();
            emptyText.setVisibility(View.GONE);
        }

        if (renderer.getError() != null){
            toolbar.hideProgressBar();
            emptyText.setVisibility(View.VISIBLE);
        }

        if (renderer.getData() != null){
            toolbar.hideProgressBar();
            emptyText.setVisibility(View.GONE);

            adapter.clear();
            adapter.addFilter(renderer.getSearchParam());

            if (!renderer.getData().isEmpty()) {
                adapter.addAll(renderer.getData());
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onPickSearchParams(int period, List<Integer> pickedIds) {
        presenter.loadHistory(period, pickedIds);
    }

    @Override
    public void onPickCustonPeriodSearchPams(long timeFrom, long timeTo, List<Integer> pickedIds) {
        presenter.loadHistory(timeFrom, timeTo, pickedIds);
    }

    private Fragment getFragmentInManager(String tag) {
        return getChildFragmentManager().findFragmentByTag(tag);
    }

    private void fadeInContainerBackground() {
        if (backgroundFadeOut.isRunning())
            backgroundFadeOut.cancel();

        backgroundFadeIn.start();
    }

    private void fadeOutContainerBackground() {
        if (backgroundFadeIn.isRunning())
            backgroundFadeIn.cancel();

        backgroundFadeOut.start();
    }

    @Override
    public void onDestroyView() {
        backgroundFadeIn.removeAllListeners();
        backgroundFadeOut.removeAllListeners();

        Log.i(TAG, "onDestroyView: ");

        super.onDestroyView();
    }


}
