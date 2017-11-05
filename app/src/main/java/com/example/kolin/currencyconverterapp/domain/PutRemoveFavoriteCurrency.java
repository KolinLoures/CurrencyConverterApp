package com.example.kolin.currencyconverterapp.domain;

import com.example.kolin.currencyconverterapp.data.db.DAO;
import com.example.kolin.currencyconverterapp.data.db.DataBaseQueries;

import io.reactivex.Completable;

/**
 * Created by kolin on 05.11.2017.
 */

public class PutRemoveFavoriteCurrency extends BaseCompletableUseCase<PutRemoveFavoriteCurrency.PutRemoveFavoriteParams> {

    private DAO.CurrencyCatalogDAO db;

    public PutRemoveFavoriteCurrency() {
        db = DataBaseQueries.getInstance();
    }

    @Override
    protected Completable createCompletable(PutRemoveFavoriteParams param) {
        return Completable.fromAction(() -> {
            if (param.remove)
                db.removeCurrencyFromFavorite(param.id);
            else
                db.addCurrencyToFavorite(param.id);
        });
    }

    public static class PutRemoveFavoriteParams {
        private int id;
        private boolean remove;

        private PutRemoveFavoriteParams(int id, boolean remove) {
            this.remove = remove;
            this.id = id;
        }

        public static PutRemoveFavoriteParams getParamObj(int id, boolean remove){
            return new PutRemoveFavoriteParams(id, remove);
        }
    }
}
