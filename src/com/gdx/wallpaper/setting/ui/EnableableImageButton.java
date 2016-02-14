package com.gdx.wallpaper.setting.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class EnableableImageButton extends ImageButton {

    public EnableableImageButton(Context context) {
        super(context);
    }

    public EnableableImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnableableImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EnableableImageButton(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled == isEnabled()) {
            return;
        }

        if (enabled) {
            setColorFilter(Color.WHITE);
        } else {
            setColorFilter(Color.GRAY);
        }
        setClickable(enabled);

        super.setEnabled(enabled);
    }
}
