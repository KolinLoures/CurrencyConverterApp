package com.example.kolin.currencyconverterapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.presentation.converter.ConverterFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        Fragment fragmentByTag = null;

        if (savedInstanceState != null) {
            fragmentByTag = supportFragmentManager.findFragmentByTag(ConverterFragment.TAG);
        }

        if (fragmentByTag == null) {
            CurrencyEntity from = new CurrencyEntity();
            CurrencyEntity to = new CurrencyEntity();
            from.setName("EUR");
            to.setName("USD");
            fragmentByTag = ConverterFragment.newInstance(from, to);

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_activity_fragment_container, fragmentByTag, ConverterFragment.TAG)
                    .commit();
        }
    }
}
