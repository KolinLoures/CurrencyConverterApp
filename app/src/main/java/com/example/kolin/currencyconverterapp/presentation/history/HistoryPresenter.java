package com.example.kolin.currencyconverterapp.presentation.history;

import com.example.kolin.currencyconverterapp.domain.GetHistory;
import com.example.kolin.currencyconverterapp.presentation.BaseCompositPresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by kolin on 19.11.2017.
 */

public class HistoryPresenter extends BaseCompositPresenter<HistoryFragment> {

    public static final String TAG = HistoryPresenter.class.getSimpleName();

    private GetHistory getHistory;

    public HistoryPresenter() {
        getHistory = new GetHistory();
    }

    public void loadHistory() {
        Disposable di = getHistory.createUseCase()
                .subscribe(renderer -> getView().renderHistoryView(renderer));

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

    @Override
    public void unbindView() {
//        getHistory.dispose();
        super.unbindView();
    }
}
