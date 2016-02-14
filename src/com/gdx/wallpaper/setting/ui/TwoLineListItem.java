package com.gdx.wallpaper.setting.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gdx.wallpaper.R;

public class TwoLineListItem extends FrameLayout {

    private static final String STATE_SUPER_CLASS = "SuperClass";
    private static final String STATE_TEXT_1 = "Text1";
    private static final String STATE_TEXT_2 = "Text2";

    private TextView textView1;
    private TextView textView2;

    public TwoLineListItem(Context context) {
        this(context, null, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.layout.two_line_list_item);
    }

    public TwoLineListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context
                .obtainStyledAttributes(attrs, R.styleable.TwoLineListItem, defStyleAttr,
                                        0);

        String text1;
        String text2;
        try {
            text1 = a.getString(R.styleable.TwoLineListItem_text1);
            text2 = a.getString(R.styleable.TwoLineListItem_text2);
        } finally {
            a.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(defStyleRes, this);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);

        setText1(text1);
        setText2(text2);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putParcelable(STATE_SUPER_CLASS, super.onSaveInstanceState());
        bundle.putString(STATE_TEXT_1, getText1().toString());
        bundle.putString(STATE_TEXT_2, getText2().toString());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER_CLASS));
            setText1(bundle.getString(STATE_TEXT_1));
            setText2(bundle.getString(STATE_TEXT_2));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public CharSequence getText1() {
        return textView1.getText();
    }

    public final void setText1(String text) {
        if (text == null && textView1.getVisibility() != GONE) {
            textView1.setVisibility(GONE);
        } else if (text != null && textView1.getVisibility() == GONE) {
            textView1.setVisibility(VISIBLE);
        }

        textView1.setText(text);
    }

    public final void setText1(int resId) {
        setText1(getContext().getString(resId));
    }

    public CharSequence getText2() {
        return textView2.getText();
    }

    public final void setText2(String text) {
        if (text == null && textView2.getVisibility() != GONE) {
            textView2.setVisibility(GONE);
        } else if (text != null && textView2.getVisibility() == GONE) {
            textView2.setVisibility(VISIBLE);
        }

        textView2.setText(text);
    }

    public final void setText2(int resId) {
        setText2(getContext().getString(resId));
    }
}