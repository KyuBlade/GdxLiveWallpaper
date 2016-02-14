package com.gdx.wallpaper.setting.eventbus.playlist;

import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class PlaylistChangedEvent {

    private final long playlistId;
    private final UpdateOperation<Playlist> updateOperation;

    public PlaylistChangedEvent(long playlistId, UpdateOperation<Playlist> updateOperation) {
        this.playlistId = playlistId;
        this.updateOperation = updateOperation;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public UpdateOperation<Playlist> getUpdateOperation() {
        return updateOperation;
    }
}
