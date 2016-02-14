package com.gdx.wallpaper.setting.eventbus.playlist;

import com.gdx.wallpaper.setting.ui.RadioButton;

public class PlaylistStateChangedEvent {

    private RadioButton view;

    public PlaylistStateChangedEvent(RadioButton view) {
        this.view = view;
    }

    public RadioButton getView() {
        return view;
    }
}
