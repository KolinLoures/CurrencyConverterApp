package com.example.kolin.currencyconverterapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.presentation.chart.ChartFragment;
import com.example.kolin.currencyconverterapp.presentation.common.Updatable;
import com.example.kolin.currencyconverterapp.presentation.converter.ConverterFragment;
import com.example.kolin.currencyconverterapp.presentation.currency_list.CurrencyListFragment;
import com.example.kolin.currencyconverterapp.presentation.history.HistoryFragment;

public class MainActivity extends AppCompatActivity
        implements CurrencyListFragment.CurrencyListFragmentListener, ConverterFragment.ConverterFragmentListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final String BACK_STACK_CONVERTER_FRAGMENT = "back_stack_converter_fragment";
    private static final String ARG_SELECTED_TAB = "selected_tab_bottom_navigation";

    private int fragmentContainerID = R.id.main_activity_fragment_container;

    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.main_activity_bottom_navigation);
        bottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.exchange), R.drawable.ic_loop_black_24dp));
        bottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.history), R.drawable.ic_history_black_24dp));
        bottomNavigation.addItem(new AHBottomNavigationItem(getResources().getString(R.string.analytics), R.drawable.ic_trending_up_black_24dp));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        bottomNavigation.setOnTabSelectedListener(this::selectBottomTab);

        if (savedInstanceState != null)
            selectBottomTab(savedInstanceState.getInt(ARG_SELECTED_TAB), false);
        else
            selectBottomTab(0, false);
    }

    @Override
    public void onPickCurrenciesPair(CurrencyEntity from, CurrencyEntity to) {
        bottomNavigation.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainerID, ConverterFragment.newInstance(from, to), ConverterFragment.TAG)
                .addToBackStack(BACK_STACK_CONVERTER_FRAGMENT)
                .commit();
    }

    private boolean selectBottomTab(int position, boolean wasSelected) {

        Fragment fragmentInManager = findFragmentInManager(getFragmentTagForPosition(position));

        if (fragmentInManager == null)
            instantiateFragmentForPosition(position);
        else
            showFragmentAtPosition(position);

        return true;
    }

    private Fragment findFragmentInManager(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    private void instantiateFragmentForPosition(int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                CurrencyListFragment currencyListFragment = CurrencyListFragment.newInstance();
                ft.add(fragmentContainerID, currencyListFragment, CurrencyListFragment.TAG);

                detachFragmentAtPosition(ft, 1);
                detachFragmentAtPosition(ft, 2);

                break;
            case 1:
                HistoryFragment historyFragment = HistoryFragment.newInstance();
                ft.add(fragmentContainerID, historyFragment, HistoryFragment.TAG);

                detachFragmentAtPosition(ft, 0);
                detachFragmentAtPosition(ft, 2);

                break;

            case 2:
                ChartFragment chartFragment = ChartFragment.newInstance();
                ft.add(fragmentContainerID, chartFragment, ChartFragment.TAG);

                detachFragmentAtPosition(ft, 0);
                detachFragmentAtPosition(ft, 1);

                break;
        }

        ft.commitNow();
    }

    private void showFragmentAtPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                attachFragmentAtPosition(ft, 0);
                detachFragmentAtPosition(ft, 1);
                detachFragmentAtPosition(ft, 2);
                break;
            case 1:
                attachFragmentAtPosition(ft, 1);
                detachFragmentAtPosition(ft, 0);
                detachFragmentAtPosition(ft, 2);
                break;
            case 2:
                attachFragmentAtPosition(ft, 2);
                detachFragmentAtPosition(ft, 1);
                detachFragmentAtPosition(ft, 0);
                break;
        }

        ft.commit();
    }

    private String getFragmentTagForPosition(int position) {
        switch (position) {
            case 0:
                return CurrencyListFragment.TAG;
            case 1:
                return HistoryFragment.TAG;
            case 2:
                return ChartFragment.TAG;
        }
        return null;
    }

    private void detachFragmentAtPosition(FragmentTransaction transaction, int position) {
        Fragment fragment1 = findFragmentInManager(getFragmentTagForPosition(position));
        if (fragment1 != null)
            transaction.detach(fragment1);
    }

    private void attachFragmentAtPosition(FragmentTransaction transaction, int position) {
        Fragment fragment = findFragmentInManager(getFragmentTagForPosition(position));
        if (fragment != null)
            transaction.attach(fragment);
    }


    @Override
    public void onBackPressed() {

        if (bottomNavigation.getCurrentItem() != 0)
            bottomNavigation.setCurrentItem(0);
        else {
            Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(ConverterFragment.TAG);
            if (fragmentByTag != null && fragmentByTag.isVisible()) {
                getSupportFragmentManager().popBackStack(BACK_STACK_CONVERTER_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                bottomNavigation.setVisibility(View.VISIBLE);
                ((Updatable) findFragmentInManager(getFragmentTagForPosition(0))).update();
            } else
                super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_SELECTED_TAB, bottomNavigation.getCurrentItem());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClickBack() {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(ConverterFragment.TAG);
        if (fragmentByTag != null && fragmentByTag.isVisible()) {
            getSupportFragmentManager().popBackStack(BACK_STACK_CONVERTER_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            bottomNavigation.setVisibility(View.VISIBLE);
            ((Updatable) findFragmentInManager(getFragmentTagForPosition(0))).update();
        }
    }
}
