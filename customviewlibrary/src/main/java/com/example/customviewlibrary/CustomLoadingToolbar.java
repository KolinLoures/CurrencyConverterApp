package com.example.customviewlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.Toolbar;

/**
 * Created by kolin on 11.11.2017.
 */

public class CustomLoadingToolbar extends ConstraintLayout {

    private ProgressBar progressBar;
    private Toolbar toolbar;

    public CustomLoadingToolbar(Context context) {
        super(context);

        init();
    }

    public CustomLoadingToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLoadingToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_loading_toolbar,  this);

        progressBar = findViewById(R.id.custom_view_progress_view);
        toolbar = findViewById(R.id.custom_view_toolbar);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void showProgressBar(){
        progressBar.setVisibility(VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(INVISIBLE);
    }

}
