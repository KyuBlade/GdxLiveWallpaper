package com.gdx.wallpaper.setting.database.operation;

import android.content.ContentValues;

import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.setting.database.DatabaseHelper;

public class PlaylistScrollTypeUpdateOperation implements UpdateOperation<Playlist> {

    @Override
    public void provide(Playlist object, ContentValues values) {
        values.put(DatabaseHelper.PlaylistColumns.SCROLL_TYPE, object.getScrollType().ordinal());
    }
}