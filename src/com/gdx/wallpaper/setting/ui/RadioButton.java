package com.gdx.wallpaper.setting.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class RadioButton extends AppCompatRadioButton {

    private OnCheckedChangeListener listener;

    public RadioButton(Context context) {
        super(context);
    }

    public RadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        if (listener != null) {
            listener.onCheckedChanged(this, checked);
        }
    }

    public void setCheckedProgrammatically(boolean checked) {
        super.setChecked(checked);
    }
}
