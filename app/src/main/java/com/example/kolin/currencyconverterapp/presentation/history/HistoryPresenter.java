package com.example.kolin.currencyconverterapp.presentation.history;

import com.arellomobile.mvp.InjectViewState;
import com.example.kolin.currencyconverterapp.domain.GetHistory;
import com.example.kolin.currencyconverterapp.presentation.BasePresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Presenter for {@link HistoryFragment}
 */
@InjectViewState
public class HistoryPresenter extends BasePresenter<HistoryView> {

    public static final String TAG = HistoryPresenter.class.getSimpleName();

    private GetHistory getHistory;

    public HistoryPresenter() {
        getHistory = new GetHistory();
    }

    public void loadHistory() {
        Disposable di = getHistory.createUseCase()
                .subscribe(renderer -> getViewState().renderHistoryView(renderer));

        super.addDisposable(di);
    }

    public void loadHistory(int period, List<Integer> checkedIds) {
        getHistory.setParams(GetHistory.GetHistoryParams.getParamsObj(period, checkedIds));
        loadHistory();
    }

    public void loadHistory(long timeFrom, long timeTo, List<Integer> checkedIds){
        getHistory.setParams(GetHistory.GetHistoryParams.getCustomParamsObj(timeFrom, timeTo, checkedIds));
        loadHistory();
    }

}
