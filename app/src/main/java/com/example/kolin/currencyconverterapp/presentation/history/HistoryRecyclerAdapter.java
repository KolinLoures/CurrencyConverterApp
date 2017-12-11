package com.example.kolin.currencyconverterapp.presentation.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kolin.currencyconverterapp.R;
import com.example.kolin.currencyconverterapp.data.model.SearchParam;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;
import com.example.kolin.currencyconverterapp.presentation.util.AppFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kolin on 19.11.2017.
 */

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_FILTER = 1;

    private List<CurrencyHistoryEntity> data;
    private String[] periods;
    private SearchParam searchParam;

    private DateFormat simpleDateFormat;

    public HistoryRecyclerAdapter(String[] periods) {
        this.periods = periods;
        data = new ArrayList<>();
        simpleDateFormat = SimpleDateFormat.getDateInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_FILTER) {
            return new HistoryRecyclerFilterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_filter, parent, false));
        } else
            return new HistoryRecyclerVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemType = getItemViewType(position);

        if (itemType == ITEM_TYPE_NORMAL) {

            CurrencyHistoryEntity current = data.get(position - 1);

            ((HistoryRecyclerVH) holder).currNameFrom.setText(current.getCurrencyFrom());
            ((HistoryRecyclerVH) holder).currNameTo.setText(current.getCurrencyTo());
            ((HistoryRecyclerVH) holder).currValueFrom.setText(String.format("%s %s", AppFormatter.formatRate(current.getSumFrom()), current.getCurrencyFrom()));
            ((HistoryRecyclerVH) holder).currValueTo.setText(String.format("%s %s", AppFormatter.formatRate(current.getSumTo()), current.getCurrencyTo()));
            ((HistoryRecyclerVH) holder).textDate.setText(simpleDateFormat.format(new Date(current.getTime())));

        } else {
            if (searchParam != null) {
                ((HistoryRecyclerFilterVH) holder).textPeriod.setText(periods[searchParam.getType()]);
                ((HistoryRecyclerFilterVH) holder).textPickedCurr.setText(Integer.toString(searchParam.getCheckedCurrencies().size()));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_FILTER : ITEM_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public void addFilter(SearchParam searchParam){
        this.searchParam = searchParam;
        notifyItemChanged(0);
    }

    public void addAll(List<CurrencyHistoryEntity> data) {
        int size = getItemCount() + 1;
        this.data.addAll(data);
        notifyItemRangeInserted(size, data.size());
    }

    public void clear() {
        int size = this.data.size();
        this.data.clear();
        notifyItemRangeRemoved(0, size);
    }

    class HistoryRecyclerVH extends RecyclerView.ViewHolder {

        private TextView currNameFrom;
        private TextView currNameTo;
        private TextView currValueFrom;
        private TextView currValueTo;
        private TextView rate;
        private TextView textDate;


        public HistoryRecyclerVH(View itemView) {
            super(itemView);

            currNameFrom = itemView.findViewById(R.id.item_history_text_curr_from);
            currNameTo = itemView.findViewById(R.id.item_history_text_curr_to);
            currValueFrom = itemView.findViewById(R.id.item_history_text_value_from);
            currValueTo = itemView.findViewById(R.id.item_history_text_value_to);
            textDate = itemView.findViewById(R.id.item_history_text_date);
        }
    }

    class HistoryRecyclerFilterVH extends RecyclerView.ViewHolder {
        private TextView textPeriod;
        private TextView textPickedCurr;

        public HistoryRecyclerFilterVH(View itemView) {
            super(itemView);

            textPeriod = itemView.findViewById(R.id.item_history_filter_period);
            textPickedCurr = itemView.findViewById(R.id.item_history_filter_picked_curr);
        }
    }
}
