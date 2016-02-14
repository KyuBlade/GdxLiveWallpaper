package com.gdx.wallpaper.collection;

import com.gdx.wallpaper.setting.MappedCache;

public class CollectionCache extends MappedCache<Collection> {

    protected CollectionCache() {
        super();
    }

    protected void put(Collection collection) {
        put(collection.getId(), collection);
    }

    @Override
    protected void remove(Collection collection) {
        cache.remove(collection.getId());
    }
}
