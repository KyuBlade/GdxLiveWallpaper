package com.gdx.wallpaper.setting.fragment.adapter.control;

import com.gdx.wallpaper.setting.fragment.adapter.control.holder.EditControlHolder;

public class EditControl<H extends EditControlHolder> {

    private final EditControlType type;
    protected final int titleRes;

    public EditControl(EditControlType type, int titleRes) {
        this.type = type;
        this.titleRes = titleRes;
    }

    public void bind(H holder) {
        holder.title.setText(titleRes);
    }

    public EditControlType getType() {
        return type;
    }
}