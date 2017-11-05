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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyEntity that = (CurrencyEntity) o;

        if (id != that.id) return false;
        if (isFavorite != that.isFavorite) return false;
        if (lastUse != that.lastUse) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isFavorite ? 1 : 0);
        result = 31 * result + (int) (lastUse ^ (lastUse >>> 32));
        return result;
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
