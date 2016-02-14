package com.gdx.wallpaper.setting.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.gdx.wallpaper.R;

public class CheckableListItem extends FrameLayout {

    private static final String STATE_SUPER_CLASS = "SuperClass";
    private static final String STATE_CHECKBOX = "Checkbox";

    private final CheckBox checkbox;
    private final TwoLineListItem twoLineListItem;

    public CheckableListItem(Context context) {
        this(context, null);
    }

    public CheckableListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckableListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.layout.checkable_list_item);
    }

    public CheckableListItem(Context context, AttributeSet attrs, int defStyleAttr,
                             int defStyleRes) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context
                .obtainStyledAttributes(attrs, R.styleable.CheckableListItem, defStyleAttr,
                                        0);

        String title = null;
        String description = null;
        try {
            title = a.getString(R.styleable.CheckableListItem_title);
            description = a.getString(R.styleable.CheckableListItem_description);
        } finally {
            a.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(defStyleRes, this);
        checkbox = (CheckBox) rootView.findViewById(R.id.checkBox);
        twoLineListItem = (TwoLineListItem) rootView.findViewById(R.id.twoLineListItem);

        twoLineListItem.setText1(title);
        twoLineListItem.setText2(description);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putParcelable(STATE_SUPER_CLASS, super.onSaveInstanceState());
        bundle.putBoolean(STATE_CHECKBOX, checkbox.isChecked());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER_CLASS));
            checkbox.setChecked(bundle.getBoolean(STATE_CHECKBOX, false));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        checkbox.setChecked(!checkbox.isChecked());

        return true;
    }

    public boolean isChecked() {
        return checkbox.isChecked();
    }

    public void setChecked(boolean checked) {
        checkbox.setChecked(checked);
    }

    public void setTitle(int stringRes) {
        twoLineListItem.setText1(stringRes);
    }

    public void setDescription(int stringRes) {
        twoLineListItem.setText2(stringRes);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        checkbox.setOnCheckedChangeListener(listener);
    }
}
