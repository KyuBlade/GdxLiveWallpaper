package com.gdx.wallpaper.setting.eventbus.playlist;

public class PlaylistEditEvent {

    private long playlistId;

    public PlaylistEditEvent(long playlistId) {
        this.playlistId = playlistId;
    }

    public long getPlaylistId() {
        return playlistId;
    }
}
