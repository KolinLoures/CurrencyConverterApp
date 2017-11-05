package com.example.kolin.currencyconverterapp.presentation;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.entity.CurrencyEntity;

/**
 * Created by kolin on 04.11.2017.
 */

public class CurrencyRecyclerAdapter extends RecyclerView.Adapter<CurrencyRecyclerAdapter.CurrencyRecyclerViewHolder> {

    private SortedList<CurrencyEntity> data;
    private CurrencyRecyclerListener listener;

    public CurrencyRecyclerAdapter() {
        data = new SortedList<>(CurrencyEntity.class, new SortedListCallback(this));
    }

    public interface CurrencyRecyclerListener {
        void onClickFavorite(int id, boolean check);
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

    public void addData(CurrencyEntity entity) {
        data.add(entity);
//        notifyItemInserted(data.size() - 1);
    }


    class CurrencyRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView currencyName;
        private CheckBox checkFavorite;

        public CurrencyRecyclerViewHolder(View itemView) {
            super(itemView);

            currencyName = itemView.findViewById(R.id.item_currency_list_name);
            checkFavorite = itemView.findViewById(R.id.item_currency_list_favorite);

            checkFavorite.setOnClickListener(v -> {
                if (listener != null) {
                    CurrencyEntity item = data.get(getAdapterPosition());
                    if (item.isFavorite()) item.setFavorite(false); else item.setFavorite(true);
                    data.recalculatePositionOfItemAt(getAdapterPosition());

                    boolean check = !((CheckBox) v).isChecked();
                    listener.onClickFavorite(item.getId(), check);
//                    data.updateItemAt(getAdapterPosition(), item);
                }
            });
        }
    }

    public void setListener(CurrencyRecyclerListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        listener = null;
    }

    class SortedListCallback extends SortedListAdapterCallback<CurrencyEntity> {

        /**
         * Creates a {@link SortedList.Callback} that will forward data change events to the provided
         * Adapter.
         *
         * @param adapter The Adapter instance which should receive events from the SortedList.
         */
        public SortedListCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(CurrencyEntity o1, CurrencyEntity o2) {
            int result = Boolean.compare(o2.isFavorite(), o1.isFavorite());
            if (result != 0) return result/Math.abs(result);
                result = Long.compare(o2.getLastUse(), o1.getLastUse());
            if (result != 0) return result/Math.abs(result);
                result = o1.getName().compareTo(o2.getName());
            return (result != 0) ? result/Math.abs(result) : 0;
        }

        @Override
        public boolean areContentsTheSame(CurrencyEntity oldItem, CurrencyEntity newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areItemsTheSame(CurrencyEntity item1, CurrencyEntity item2) {
            return item1.getId() == item2.getId();
        }

    }
}
