package com.gdx.wallpaper.playlist;

import com.gdx.wallpaper.setting.MappedCache;

public class PlaylistCache extends MappedCache<Playlist> {

    protected PlaylistCache() {
        super();
    }

    @Override
    protected void remove(Playlist object) {
        cache.remove(object.getId());
    }

    protected void put(Playlist playlist) {
        put(playlist.getId(), playlist);
    }

    protected Playlist getActive() {
        for (Playlist playlist : cache.values()) {
            if (playlist.isActive()) {
                return playlist;
            }
        }

        return null;
    }
}
