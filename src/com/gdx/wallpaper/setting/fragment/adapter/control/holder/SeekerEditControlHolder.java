package com.gdx.wallpaper.setting.fragment.adapter.control.holder;

import android.view.View;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.ui.SeekerEdit;

public class SeekerEditControlHolder extends EditControlHolder {

    public SeekerEdit seeker;

    public SeekerEditControlHolder(View itemView) {
        super(itemView);
        seeker = (SeekerEdit) itemView.findViewById(R.id.seekerEdit);
    }
}