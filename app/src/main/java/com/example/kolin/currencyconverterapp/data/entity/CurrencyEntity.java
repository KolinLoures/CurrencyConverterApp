package com.example.kolin.currencyconverterapp.data.entity;

/**
 * Created by kolin on 27.10.2017.
 */

public class CurrencyEntity {

    private int id;
    private String name;
    private boolean isFavorite;
    private long lastUse;

    public CurrencyEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public long getLastUse() {
        return lastUse;
    }

    public void setLastUse(long lastUse) {
        this.lastUse = lastUse;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isFavorite=" + isFavorite +
                ", lastUse=" + lastUse +
                '}';
    }
}
