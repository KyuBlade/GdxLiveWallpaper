package com.gdx.wallpaper.setting.database.operation.playlist;

import android.content.ContentValues;

import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class PlaylistNameUpdateOperation implements UpdateOperation<Playlist> {

    @Override
    public void provide(Playlist object, ContentValues values) {
        values.put(DatabaseHelper.PlaylistColumns.NAME, object.getName());
    }
}
