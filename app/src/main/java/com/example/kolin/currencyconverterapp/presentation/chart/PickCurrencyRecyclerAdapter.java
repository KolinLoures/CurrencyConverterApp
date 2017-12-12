package com.example.kolin.currencyconverterapp.presentation.chart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.kolin.currencyconverterapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolin on 05.12.2017.
 */

public class PickCurrencyRecyclerAdapter extends RecyclerView.Adapter<PickCurrencyRecyclerAdapter.PickCurrencyViewHolder> {

    private List<String> data;
    private String checkedCurr;

    private RadioButton lastPickButton;

    private PickCurrencyRecyclerAdapterClickListener listener;

    public interface PickCurrencyRecyclerAdapterClickListener {
        void onClickCurrencyItem(String pickedCurr);
    }

    public PickCurrencyRecyclerAdapter() {
        data = new ArrayList<>();
    }

    @Override
    public PickCurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PickCurrencyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_chart_list, parent, false));
    }

    @Override
    public void onBindViewHolder(PickCurrencyViewHolder holder, int position) {
        String text = data.get(position);
        holder.radioBtn.setText(text);
        if (checkedCurr != null)
            holder.radioBtn.setChecked(text.equals(checkedCurr));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setChecked(String name) {
        checkedCurr = name;
    }

    public void addAll(List<String> data) {
        int oldSize = this.data.size() + 1;
        this.data.addAll(data);
        notifyItemRangeInserted(oldSize, data.size());
    }


    public void setListener(PickCurrencyRecyclerAdapterClickListener listener) {
        this.listener = listener;
    }

    class PickCurrencyViewHolder extends RecyclerView.ViewHolder {

        private RadioButton radioBtn;

        public PickCurrencyViewHolder(View itemView) {
            super(itemView);

            radioBtn = itemView.findViewById(R.id.item_dialog_chart_list_radio_btn);

            radioBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (lastPickButton != null)
                    lastPickButton.setChecked(false);

                if (isChecked)
                    lastPickButton = radioBtn;
            });

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onClickCurrencyItem(data.get(getAdapterPosition()));
            });
        }
    }

}
