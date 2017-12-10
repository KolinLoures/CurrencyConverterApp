package com.example.kolin.currencyconverterapp.presentation.history.search_param;

import com.example.kolin.currencyconverterapp.domain.GetSearchParams;
import com.example.kolin.currencyconverterapp.presentation.BaseCompositPresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 02.12.2017.
 */

public class SearchParamsPresenter extends BaseCompositPresenter<SearchParamsFragment> {

    public static final String TAG = SearchParamsPresenter.class.getSimpleName();

    private GetSearchParams getSearchParams;

    public SearchParamsPresenter() {
        getSearchParams = new GetSearchParams();
    }

    public void loadRateParams(){
        Disposable di = getSearchParams
                .createUseCase()
                .subscribe(renderer -> getView().renderSearchPramsView(renderer));

        super.addDisposable(di);
    }

    @Override
    public void unbindView() {
        super.unbindView();
    }
}
