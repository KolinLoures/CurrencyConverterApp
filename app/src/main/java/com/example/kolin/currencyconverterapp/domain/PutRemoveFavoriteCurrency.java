package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.dao.DAO;
import com.example.kolin.currencyconverterapp.data.dao.DataBaseQueries;

import io.reactivex.Completable;

/**
 * Class Use Case to Out or Remove favorite currencies
 */

public class PutRemoveFavoriteCurrency implements BaseCompletableUseCase, ParamsUseCase<PutRemoveFavoriteCurrency.PutRemoveFavoriteParams> {

    private DAO queries;
    private PutRemoveFavoriteParams params;

    public PutRemoveFavoriteCurrency() {
        queries = new DataBaseQueries();
    }

    @Override
    public Completable createCompletable() {
        return Completable.fromAction(() -> {
            if (params.check)
                queries.addCurrencyToFavorite(params.id);
            else
                queries.removeCurrencyFromFavorite(params.id);
        }).compose(applySchedulers());
    }

    @Override
    public void setParams(PutRemoveFavoriteParams params) {
        this.params = params;
    }

    @Override
    public PutRemoveFavoriteParams getParams() {
        return params;
    }

    public static class PutRemoveFavoriteParams implements Params {
        private int id;
        private boolean check;

        private PutRemoveFavoriteParams(int id, boolean check) {
            this.check = check;
            this.id = id;
        }

        public static PutRemoveFavoriteParams getParamObj(int id, boolean remove) {
            return new PutRemoveFavoriteParams(id, remove);
        }
    }
}
