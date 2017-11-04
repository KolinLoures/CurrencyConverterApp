package com.example.kolin.currencyconverterapp.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolin on 04.11.2017.
 */

public class CurrencyRecyclerAdapter extends RecyclerView.Adapter<CurrencyRecyclerAdapter.CurrencyRecyclerViewHolder>{

    private List<CurrencyEntity> data = new ArrayList<>();

    public CurrencyRecyclerAdapter() {
    }

    @Override
    public CurrencyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CurrencyRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency_list, parent, false));
    }

    @Override
    public void onBindViewHolder(CurrencyRecyclerViewHolder holder, int position) {
        CurrencyEntity current = data.get(position);

        holder.currencyName.setText(current.getName());
        holder.checkFavorite.setChecked(current.isFavorite());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(CurrencyEntity entity){
        data.add(entity);
        notifyItemInserted(data.size() - 1);
    }


    class CurrencyRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView currencyName;
        private CheckBox checkFavorite;

        public CurrencyRecyclerViewHolder(View itemView) {
            super(itemView);

            currencyName = itemView.findViewById(R.id.item_currency_list_name);
            checkFavorite = itemView.findViewById(R.id.item_currency_list_favorite);
        }
    }
}
