package com.gdx.wallpaper.setting.fragment.adapter.control.holder;

import android.view.View;
import android.widget.TextView;

import com.gdx.wallpaper.R;

public class DurationPickerControlHolder
        extends EditControlHolder {

    public final TextView textView;

    public DurationPickerControlHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.textView);
    }
}