package com.example.kolin.currencyconverterapp.presentation.currency_list;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolin on 04.11.2017.
 */

public class CurrencyRecyclerAdapter extends RecyclerView.Adapter<CurrencyRecyclerAdapter.CurrencyRecyclerViewHolder> {

    private static final String DEF_LANG_1 = "EUR";
    private static final String DEF_LANG_2 = "USD";

    private CurrencyEntity DEF_CURR_1 = null;
    private CurrencyEntity DEF_CURR_2 = null;


    private SortedList<CurrencyEntity> data;

    private CurrencyRecyclerCheckFavoriteListener checkFavoriteListener;
    private CurrencyRecyclerClickItemListener clickItemListener;
    private CurrencyRecyclerLongPressItemListener longPressItemListener;

    public CurrencyRecyclerAdapter() {
        data = new SortedList<>(CurrencyEntity.class, new SortedListCallback(this));
    }

    public interface CurrencyRecyclerCheckFavoriteListener {
        void onCheckFavorite(CurrencyEntity entity, boolean check);
    }

    public interface CurrencyRecyclerClickItemListener {
        void onClickItem(CurrencyEntity currencyEntity);
    }

    public interface CurrencyRecyclerLongPressItemListener {
        void onLongPressItem(CurrencyEntity currencyEntity);
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

    public void addAllData(List<CurrencyEntity> data) {
        this.initDefCurrencies(data);
        this.data.beginBatchedUpdates();
        this.data.addAll(data);
        this.data.endBatchedUpdates();
    }

    public void clearData(){
        this.data.beginBatchedUpdates();
        this.data.clear();
        this.data.endBatchedUpdates();
    }

    public CurrencyEntity getFavoriteAfter(CurrencyEntity entity) {
        for (int i = 0; i < data.size(); i++) {
            CurrencyEntity currencyEntity = data.get(i);
//            return currencyEntity.isFavorite() && currencyEntity.getId() != entity.getId()
//                    ? currencyEntity
//                    : entity.getId() != DEF_CURR_1.getId() ? DEF_CURR_1 : DEF_CURR_2;

            if (currencyEntity.isFavorite() && currencyEntity.getId() != entity.getId())
                return currencyEntity;

            if (!currencyEntity.isFavorite())
                return entity.getId() != DEF_CURR_1.getId() ? DEF_CURR_1 : DEF_CURR_2;

        }
        return null;
    }

    private void initDefCurrencies(List<CurrencyEntity> data) {
        for (CurrencyEntity c : data) {
            if (DEF_CURR_1 == null)
                DEF_CURR_1 = TextUtils.equals(c.getName(), DEF_LANG_1) ? c : null;
            if (DEF_CURR_2 == null)
                DEF_CURR_2 = TextUtils.equals(c.getName(), DEF_LANG_2) ? c : null;

            if (DEF_CURR_1 != null && DEF_CURR_2 != null)
                return;
        }
    }

    public List<CurrencyEntity> getData() {
        List<CurrencyEntity> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
            list.add(data.get(i));

        return list;
    }

    public void setCheckFavoriteListener(CurrencyRecyclerCheckFavoriteListener checkFavoriteListener) {
        this.checkFavoriteListener = checkFavoriteListener;
    }

    public void setClickItemListener(CurrencyRecyclerClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public void setLongPressItemListener(CurrencyRecyclerLongPressItemListener longPressItemListener) {
        this.longPressItemListener = longPressItemListener;
    }

    class CurrencyRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView currencyName;
        private CheckBox checkFavorite;

        public CurrencyRecyclerViewHolder(View itemView) {
            super(itemView);

            currencyName = itemView.findViewById(R.id.item_currency_list_name);
            checkFavorite = itemView.findViewById(R.id.item_currency_list_favorite);

            itemView.setOnLongClickListener(this::onLongPress);
            itemView.setOnClickListener(this::onClick);
            checkFavorite.setOnCheckedChangeListener(this::onCheckedChange);

        }

        private void onCheckedChange(CompoundButton btn, boolean isChecked) {
            CurrencyEntity item = data.get(getAdapterPosition());
            item.setFavorite(isChecked);
            data.recalculatePositionOfItemAt(getAdapterPosition());

            if (checkFavoriteListener != null)
                checkFavoriteListener.onCheckFavorite(item, isChecked);
        }

        private boolean onLongPress(View v) {
            if (longPressItemListener != null)
                longPressItemListener.onLongPressItem(data.removeItemAt(getAdapterPosition()));
            return true;
        }

        private void onClick(View v) {
            if (clickItemListener != null)
                clickItemListener.onClickItem(data.get(getAdapterPosition()));
        }
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
            if (result != 0) return result / Math.abs(result);
            result = Long.compare(o2.getLastUse(), o1.getLastUse());
            if (result != 0) return result / Math.abs(result);
            result = o1.getName().compareTo(o2.getName());
            return (result != 0) ? result / Math.abs(result) : 0;
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
