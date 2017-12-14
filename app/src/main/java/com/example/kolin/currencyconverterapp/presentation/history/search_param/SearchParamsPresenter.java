package com.example.kolin.currencyconverterapp.presentation.history.search_param;

import com.arellomobile.mvp.InjectViewState;
import com.example.kolin.currencyconverterapp.domain.GetSearchParams;
import com.example.kolin.currencyconverterapp.presentation.BasePresenter;

import io.reactivex.disposables.Disposable;

/**
 * Presenter for {@link SearchParamsFragment}
 */
@InjectViewState
public class SearchParamsPresenter extends BasePresenter<SearchParamsView> {

    public static final String TAG = SearchParamsPresenter.class.getSimpleName();

    private GetSearchParams getSearchParams;

    public SearchParamsPresenter() {
        getSearchParams = new GetSearchParams();
    }

    public void  loadParams(){
        Disposable di = getSearchParams
                .createUseCase()
                .subscribe(renderer -> getViewState().renderSearchPramsView(renderer));

        super.addDisposable(di);
    }
}
