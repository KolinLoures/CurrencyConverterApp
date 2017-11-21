package com.example.customviewlibrary;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;


public class CustomDropButton extends android.support.v7.widget.AppCompatButton {

    private boolean isDropDown = true;

    private AnimatedVectorDrawableCompat drawableUP = AnimatedVectorDrawableCompat.create(getContext(), R.drawable.animated_ic_drop_up);
    private AnimatedVectorDrawableCompat drawableDOWN = AnimatedVectorDrawableCompat.create(getContext(), R.drawable.animated_ic_drop_down);
    private Drawable toolsDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_keyboard_arrow_down_black_24dp);

    public CustomDropButton(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomDropButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDropButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableUP, null);
        this.setOnClickListener(v -> setDropDown(!isDropDown));

        if (isInEditMode())
            this.setCompoundDrawablesWithIntrinsicBounds(null, null, toolsDrawable, null);

        setDropDown(true);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        OnClickListener onClickListener = v -> {
            setDropDown(!isDropDown);
            if (l != null)
                l.onClick(v);
        };
        super.setOnClickListener(onClickListener);
    }

    public boolean isDropDown() {
        return isDropDown;
    }

    public void setDropDown(boolean dropDown) {
        boolean rotate = isDropDown ^ dropDown;
        this.isDropDown = dropDown;
        if (rotate)
            if (dropDown) {
                this.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableDOWN, null);
                ((AnimatedVectorDrawableCompat) this.getCompoundDrawables()[2]).start();
            } else {
                this.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableUP, null);
                ((AnimatedVectorDrawableCompat) this.getCompoundDrawables()[2]).start();
            }
    }
}
