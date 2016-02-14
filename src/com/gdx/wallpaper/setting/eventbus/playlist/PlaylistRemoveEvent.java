package com.gdx.wallpaper.setting.eventbus.playlist;

public class PlaylistRemoveEvent {

    private long id;

    public PlaylistRemoveEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
