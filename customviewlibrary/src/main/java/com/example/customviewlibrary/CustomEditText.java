package com.example.customviewlibrary;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by kolin on 11.11.2017.
 */

public class CustomEditText extends AppCompatEditText {

    private OnKeyboardHiddenListener listener;

    public interface OnKeyboardHiddenListener {
        void onKeyboardHidden();
    }

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (listener != null )
                listener.onKeyboardHidden();
        }

        return super.onKeyPreIme(keyCode, event);
    }

    public void setListener(OnKeyboardHiddenListener listener) {
        this.listener = listener;
    }
}
