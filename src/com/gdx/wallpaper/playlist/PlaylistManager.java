package com.gdx.wallpaper.playlist;

import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class PlaylistManager {

    private final PlaylistFactory factory;

    private PlaylistManager() {
        factory = new PlaylistFactory();
    }

    public static PlaylistManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void insert(Playlist playlist) {
        factory.insert(playlist);
    }

    public void update(Playlist playlist, UpdateOperation<Playlist> updateOperation) {
        factory.update(playlist, updateOperation);
    }

    public void update(long id, UpdateOperation<Playlist> updateOperation) {
        factory.update(id, updateOperation);
    }

    public void remove(Playlist playlist) {
        factory.delete(playlist);
    }

    public void remove(long id) {
        factory.delete(id);
    }

    public Playlist[] getAll() {
        return factory.getAll();
    }

    public Playlist get(long id) {
        return factory.get(id);
    }

    public Playlist getActive() {
        return factory.getActive();
    }

    public boolean isActive(long id) {
        return factory.isActive(id);
    }

    private static class SingletonHolder {

        private static final PlaylistManager INSTANCE = new PlaylistManager();
    }
}
