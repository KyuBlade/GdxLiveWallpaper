package com.gdx.wallpaper.setting.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.gdx.wallpaper.R;

public class SeekerEdit extends RelativeLayout {

    public interface ChangeListener {

        void onChange(int newValue);
    }

    private TextView title;
    private SeekBar seekbar;
    private EditText valueEdit;

    private int maxValue;
    private int value;

    private ChangeListener listener;

    public SeekerEdit(Context context) {
        this(context, null, 0);
    }

    public SeekerEdit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekerEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray _a = context.obtainStyledAttributes(attrs, R.styleable.SeekerEdit, defStyle, 0);

        String _title = null;
        int _defaultValue;
        try {
            _title = _a.getString(R.styleable.SeekerEdit_editTitle);
            _defaultValue = _a.getInt(R.styleable.SeekerEdit_defaultValue, 0);
            maxValue = _a.getInt(R.styleable.SeekerEdit_maxValue, 0);
        } finally {
            _a.recycle();
        }

        LayoutInflater _inflater = LayoutInflater.from(context);
        _inflater.inflate(R.layout.seeker_edit, this);

        title = (TextView) findViewById(R.id.title);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        valueEdit = (EditText) findViewById(R.id.editText);

        title.setText(_title);
        setMaxValue(maxValue);
        setValue(_defaultValue);

        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (listener != null) {
                    listener.onChange(value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;

                if (fromUser) {
                    valueEdit.setText(String.valueOf(progress));
                }
            }
        });

        valueEdit.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    value = Integer.valueOf(v.getText().toString());
                    if (value > maxValue) {
                        value = maxValue;

                        v.setText(String.valueOf(value));
                    }
                    seekbar.setProgress(value);

                    if (listener != null) {
                        listener.onChange(value);
                    }
                }

                return false;
            }
        });
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;

        seekbar.setMax(maxValue);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;

        seekbar.setProgress(value);
        valueEdit.setText(String.valueOf(value));
    }

    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }
}