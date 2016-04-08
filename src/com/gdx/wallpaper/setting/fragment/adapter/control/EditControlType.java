package com.gdx.wallpaper.setting.fragment.adapter.control;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public enum EditControlType {

    SEEKER, DURATION_PICKER;

    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        switch (this) {
            case SEEKER:
                return SeekerEditControl.createViewHolder(parent);
            case DURATION_PICKER:
                return DurationPickerControl.createViewHolder(parent);
            default:
                return null;
        }
    }
}