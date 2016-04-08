package com.gdx.wallpaper.setting.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.badlogic.gdx.math.MathUtils;
import com.gdx.wallpaper.R;

public class SeekerEdit extends RelativeLayout {

    public interface ChangeListener {

        void onChange(int newValue);
    }

    private SeekBar seekbar;
    private EditText valueEdit;

    private float minValue;
    private float maxValue;
    private float value;

    private OnSeekerChangeListener listener;

    public SeekerEdit(Context context) {
        super(context, null, 0);

        init(context, null, 0);
    }

    public SeekerEdit(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        init(context, attrs, 0);
    }

    public SeekerEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SeekerEdit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SeekerEdit, defStyle, 0);

        try {
            value = a.getFloat(R.styleable.SeekerEdit_defaultValue, 0);
            maxValue = a.getFloat(R.styleable.SeekerEdit_minValue, 0);
            maxValue = a.getFloat(R.styleable.SeekerEdit_maxValue, 0);
        } finally {
            a.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.seeker_edit_impl, this);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        valueEdit = (EditText) findViewById(R.id.editText);

        setMinValue(minValue);
        setMaxValue(maxValue);
        setValue(value);

        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (listener != null) {
                    listener.onStopTrackingTouch(SeekerEdit.this);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (listener != null) {
                    listener.onStartTrackingTouch(SeekerEdit.this);
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    value =
                            Math.round(
                                    ((((float) progress / 100f)) * (maxValue - minValue) +
                                            minValue) *
                                            100.0f) / 100.0f;
                    value = MathUtils.clamp(value, minValue, maxValue);
                    valueEdit.setText(String.valueOf(value));
                }

                if (listener != null) {
                    listener.onProgressChanged(SeekerEdit.this, value, fromUser);
                }
            }
        });

        valueEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        value = Float.valueOf(v.getText().toString());
                    } catch (NumberFormatException e) {
                        value = 0;
                    }
                    setValue(value);
                    v.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager) v.getContext()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }

                return false;
            }
        });
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        value = MathUtils.clamp(value, minValue, maxValue);
        int intValue = (int) (((value - minValue) / (maxValue - minValue)) * 100);
        seekbar.setProgress(intValue);
        valueEdit.setText(String.valueOf(value));
    }

    public void setListener(OnSeekerChangeListener listener) {
        this.listener = listener;
    }

    public interface OnSeekerChangeListener {

        void onStopTrackingTouch(SeekerEdit seekerEdit);

        void onStartTrackingTouch(SeekerEdit seekerEdit);

        void onProgressChanged(SeekerEdit seekerEdit, float progress, boolean fromUser);
    }
}