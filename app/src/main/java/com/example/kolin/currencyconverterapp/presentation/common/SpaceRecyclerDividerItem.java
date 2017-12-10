package com.example.kolin.currencyconverterapp.presentation.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kolin on 10.12.2017.
 */

public class SpaceRecyclerDividerItem extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public SpaceRecyclerDividerItem(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}
