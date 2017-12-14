package com.example.kolin.currencyconverterapp.presentation.history.search_param;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kolin on 18.11.2017.
 */

public class SearchParamsRecyclerAdapter extends RecyclerView.Adapter<SearchParamsRecyclerAdapter.CurrencyDialogVH> {

    private List<CurrencyEntity> data;
    private Set<Integer> checkedIds;

    private SearchParamsAdapterListener listener;

    public interface SearchParamsAdapterListener{
        void onPickCurrency();
    }

    public SearchParamsRecyclerAdapter() {
        this.data = new ArrayList<>();
        this.checkedIds = new HashSet<>();
    }

    @Override
    public CurrencyDialogVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CurrencyDialogVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_currency_list, parent, false));
    }

    @Override
    public void onBindViewHolder(CurrencyDialogVH holder, int position) {
        CurrencyEntity current = data.get(position);

        holder.checkBox.setText(current.getName());
        holder.checkBox.setChecked(checkedIds.contains(current.getId()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<CurrencyEntity> data) {
        int old = this.data.size() + 1;
        this.data.addAll(data);
        notifyItemRangeInserted(old, data.size());
    }

    public void add(CurrencyEntity currencyEntity) {
        this.data.add(currencyEntity);
        notifyItemInserted(this.data.size() - 1);
    }

    public void clearChekedIds(){
        this.checkedIds.clear();
    }

    public void setCheckedIds(List<Integer> checkedIds){
        this.checkedIds.addAll(checkedIds);
    }

    class CurrencyDialogVH extends RecyclerView.ViewHolder {

        private CheckBox checkBox;

        public CurrencyDialogVH(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.item_dialog_currency_list_check_box);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked)
                    checkedIds.add(data.get(getAdapterPosition()).getId());
                else
                    checkedIds.remove(data.get(getAdapterPosition()).getId());
            });

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onPickCurrency();
            });
        }
    }

    public Set<Integer> getCheckedIds() {
        return checkedIds;
    }

    public void setPickedListener(SearchParamsAdapterListener listener){
        this.listener = listener;
    }
}
